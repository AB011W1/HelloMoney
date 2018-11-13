package com.barclays.bmg.json.model.builder.accountsummary;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.InsuranceAccountDTO;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.dto.LoanAccountDTO;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.AccountJSONModel;
import com.barclays.bmg.json.model.AccountSummaryJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.model.InsuranceAccountJSONModel;
import com.barclays.bmg.json.model.InvestmentAccountJSONModel;
import com.barclays.bmg.json.model.LoanAccountJSONModel;
import com.barclays.bmg.json.model.TDAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class AccountSummaryJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof AccountSummaryOperationResponse) {
	    AccountSummaryOperationResponse resp = (AccountSummaryOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
	    populatePayLoad(resp, bmbPayload);

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    protected BMBPayloadHeader createHeader(AccountSummaryOperationResponse resp) {

	BMBPayloadHeader header = new BMBPayloadHeader();

	header.setResCde(resp.getResCde());
	header.setResMsg(resp.getResMsg());
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.ACCOUNT_SUMMARY_RESPONSE_ID);
	if (resp.isSuccess() && resp.getResMsg() != null) {
	    header.setResCde(AccountServiceResponseCodeConstant.WARNING_CODE);
	}
	return header;

    }

    protected void populatePayLoad(AccountSummaryOperationResponse response, BMBPayload bmbPayload) {

	AccountSummaryJSONModel accountSummaryJSONModel = new AccountSummaryJSONModel();

	Context context = response.getContext();
	accountSummaryJSONModel.getCustProf().setCustNam(context.getFullName());
	accountSummaryJSONModel.getCustProf().setLastLgnTm(context.getLastLoginTime());
	accountSummaryJSONModel.getCustProf().setPrefLang(context.getLanguageId());
	accountSummaryJSONModel.getCustProf().setPinSta(context.getPinStatus());
	if (response.isSuccess()) {
	    // Get list of all account
	    List<CustomerAccountDTO> allAccountList = (List<CustomerAccountDTO>) response.getAccountList();

	    if (allAccountList != null && allAccountList.size() > 0) {
		accountSummaryJSONModel.setCustActs(getCategorisedAccounts(allAccountList, response));
	    }

	    // Added for 5.x migration
	    String localCurrency = response.getContext().getLocalCurrency();

	    accountSummaryJSONModel.setTotAsst(BMGFormatUtility.getJSONAmount(localCurrency, BMGFormatUtility.getFormattedAmount(response
		    .getTotalAsset())));
	    accountSummaryJSONModel.setTotDebt(BMGFormatUtility.getJSONAmount(localCurrency, BMGFormatUtility.getFormattedAmount(response
		    .getTotalDebt())));
	    accountSummaryJSONModel.setTotNetWorth(BMGFormatUtility.getJSONAmount(localCurrency, BMGFormatUtility.getFormattedAmount(response
		    .getTotalNetWorth())));

	}

	bmbPayload.setPayData(accountSummaryJSONModel);

    }

    /**
     * 
     * @param accountList
     * @return
     */
    protected List<? extends AccountJSONModel> getCategorisedAccounts(List<CustomerAccountDTO> accountList, ResponseContext response) {
	List<AccountJSONModel> returnList = new ArrayList<AccountJSONModel>();

	if (accountList != null && accountList.size() > 0) {
	    for (CustomerAccountDTO customerAccount : accountList) {

		// Get the AccountModel object
		AccountJSONModel accountJSONModel = getBean(customerAccount);

		// Set the AccountModel into the list based upon category
		if (accountJSONModel != null) {

		    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
			accountJSONModel.setMkdActNo(getMaskedAccountNumber(customerAccount.getBranchCode(), customerAccount.getAccountNumber()));
		    } else {
			accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, customerAccount.getAccountNumber()));
		    }
		    accountJSONModel.setActNo(getCorrelationId(customerAccount.getAccountNumber(), response));

		    returnList.add(accountJSONModel);

		}
	    }
	}

	return returnList;

    }

    /**
     * Returns the Appropriate AccountModel
     * 
     * @param customerAccountDTO
     * @return
     */
    protected AccountJSONModel getBean(CustomerAccountDTO customerAccountDTO) {
	AccountJSONModel accountJSONModel = null;
	if (customerAccountDTO instanceof CASAAccountDTO) {
	    CASAAccountDTO account = (CASAAccountDTO) customerAccountDTO;
	    accountJSONModel = new CasaAccountJSONModel(account);
	    accountJSONModel.setTyp(AccountConstants.CASA_ACCOUNTS);
	} else if (customerAccountDTO instanceof TdAccountDTO) {
	    TdAccountDTO account = (TdAccountDTO) customerAccountDTO;
	    accountJSONModel = new TDAccountJSONModel(account);
	    accountJSONModel.setTyp(AccountConstants.TD_ACCOUNTS);
	} else if (customerAccountDTO instanceof LoanAccountDTO) {
	    LoanAccountDTO account = (LoanAccountDTO) customerAccountDTO;
	    accountJSONModel = new LoanAccountJSONModel(account);
	    accountJSONModel.setTyp(AccountConstants.LOAN_ACCOUNTS);
	} else if (customerAccountDTO instanceof CreditCardAccountDTO) {
	    CreditCardAccountDTO account = (CreditCardAccountDTO) customerAccountDTO;
	    CreditCardAccountJSONModel ccActJson = new CreditCardAccountJSONModel(account);
	    ccActJson.setTyp(AccountConstants.CC_ACCOUNTS);

	    // Setting Product Description
	    ccActJson.setDesc(account.getProductCode());
	    ccActJson.setMkdCrdNo(getMaskedCreditCardNumber(ccActJson.getCrdNo()));

	    accountJSONModel = ccActJson;
	} else if (customerAccountDTO instanceof InsuranceAccountDTO) {
	    InsuranceAccountDTO account = (InsuranceAccountDTO) customerAccountDTO;
	    accountJSONModel = new InsuranceAccountJSONModel(account);
	    accountJSONModel.setTyp(AccountConstants.INSURE_ACCOUNTS);
	} else if (customerAccountDTO instanceof InvestmentAccountDTO) {
	    InvestmentAccountDTO account = (InvestmentAccountDTO) customerAccountDTO;
	    accountJSONModel = new InvestmentAccountJSONModel(account);
	    accountJSONModel.setTyp(AccountConstants.INVEST_ACCOUNTS);
	}

	return accountJSONModel;
    }

}
