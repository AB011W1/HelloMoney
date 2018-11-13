/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.json.model.builder.accountdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.AccountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.RetrieveAllCustAcctJSONModel;
import com.barclays.bmg.json.model.accounts.CustomerProfileJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-response-model-2.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctJSONBuilder.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 04, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctJSONBuilder created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctJSONBuilder extends BMBCommonJSONBuilder implements BMBJSONBuilder {
    /**
     * The Constant named "RESID" is created.
     */
    private static final String RESID = "RES0201";

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.json.model.builder.BMBJSONBuilder#createJSONResponse(com.barclays.bmg.context.ResponseContext)
     */
    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof RetrieveAllCustAcctOperationResponse) {
	    RetrieveAllCustAcctOperationResponse resp = (RetrieveAllCustAcctOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
	    populatePayLoad(resp, bmbPayload);

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    /**
     * The method is written for Creates the header.
     * 
     * @param resp
     *            the resp
     * @return the BMBPayloadHeader's object
     */
    protected BMBPayloadHeader createHeader(RetrieveAllCustAcctOperationResponse resp) {
	BMBPayloadHeader header = new BMBPayloadHeader();
	header.setResCde(resp.getResCde());
	header.setResMsg(resp.getResMsg());
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(RESID);
	return header;
    }

    /**
     * The method is written for Populate pay load.
     * 
     * @param response
     *            the response
     * @param bmbPayload
     *            the bmb payload
     */
    protected void populatePayLoad(RetrieveAllCustAcctOperationResponse response, BMBPayload bmbPayload) {

	RetrieveAllCustAcctJSONModel model = new RetrieveAllCustAcctJSONModel();

	Context context = response.getContext();
	CustomerProfileJSONModel cusProf = new CustomerProfileJSONModel();
	cusProf.setLastLgnTm(context.getLastLoginTime() == null ? BMGConstants.EMPTYSTR : context.getLastLoginTime());
	if (response.isSuccess()) {
	    CustomerDTO customer = response.getCustomer();
	    cusProf.setCustNam(customer.getFullName() == null ? BMGConstants.EMPTYSTR : customer.getFullName());
	    cusProf.setPrefLang(customer.getLanguage() == null ? BMGConstants.EMPTYSTR : customer.getLanguage());
	    cusProf.setPinSta(customer.getPinStatus() == null ? BMGConstants.EMPTYSTR : customer.getPinStatus());

	    List<? extends CustomerAccountDTO> accountList = response.getAccountList();
	    if (accountList != null && !accountList.isEmpty()) {
		model.setCustActs(getCategorisedAccounts(accountList, response));
	    }
	}
	model.setCustProf(cusProf);
	bmbPayload.setPayData(model);
    }

    /**
     * Gets the categorised accounts.
     * 
     * @param accountList
     *            the account list
     * @param response
     *            the response
     * @return the categorised accounts
     */
    private List<? extends AccountJSONModel> getCategorisedAccounts(List<? extends CustomerAccountDTO> accountList, ResponseContext response) {
	List<AccountJSONModel> returnList = new ArrayList<AccountJSONModel>();
	String localCurrency = response.getContext().getLocalCurrency();

	if (accountList != null && !accountList.isEmpty()) {
	    for (CustomerAccountDTO customerAccount : accountList) {
		if (customerAccount instanceof CASAAccountDTO) {
		    CASAAccountDTO account = (CASAAccountDTO) customerAccount;
		    AccountJSONModel accountJSONModel = new CasaAccountJSONModel(account);
		    accountJSONModel.setTyp(AccountConstants.CASA_ACCOUNTS);
		    accountJSONModel.setActNo(getCorrelationId(customerAccount.getAccountNumber(), response));

		    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
			accountJSONModel.setMkdActNo(getMaskedAccountNumber(customerAccount.getBranchCode(), customerAccount.getAccountNumber()));
		    } else {
			accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, customerAccount.getAccountNumber()));
		    }

		    accountJSONModel.setAccStatus(getAccountStatus(account.getAccountStatus(), response));
		    String currencyCode = account.getCurrency();
		    String currencyCd = null;

		    if (currencyCode != null && !currencyCode.equals("")) {
			currencyCd = currencyCode;
		    } else {
			currencyCd = localCurrency;
		    }

		    accountJSONModel.setCurrentBookBalanceAmount(BMGFormatUtility.getJSONAmount(currencyCd, BMGFormatUtility
			    .getFormattedAmount(account.getCurrentBookBalanceAmount())));
		    accountJSONModel.setNetBalanceAmount(BMGFormatUtility.getJSONAmount(currencyCd, BMGFormatUtility.getFormattedAmount(account
			    .getNetBalanceAmount())));

		    returnList.add(accountJSONModel);
		}
	    }
	}
	return returnList;
    }

    private String getAccountStatus(String accountStatus, ResponseContext response) {
	Map<String, Object> contextMap = response.getContext().getContextMap();
	return contextMap.get("CASA_ACCOUNT_STATUS_" + accountStatus).toString();
    }
}