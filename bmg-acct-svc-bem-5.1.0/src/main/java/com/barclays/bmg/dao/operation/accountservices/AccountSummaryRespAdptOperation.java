package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.CASAAccountSummary.CASAAccountSummary;
import com.barclays.bem.CreditCardAccount.CreditCardAccount;
import com.barclays.bem.RetrieveIndividualCustAcctList.AccountList;
import com.barclays.bem.RetrieveIndividualCustAcctList.RetrieveIndividualCustomerAccountListResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.mapper.CASAAccountMapper;
import com.barclays.bmg.mapper.CreditCardAccountMapper;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;

public class AccountSummaryRespAdptOperation extends AbstractResAdptOperation {

    public static final String SOURCE_FCR = "FCR";

    public static final String SOURCE_PRIME = "PRIME";

    public static final String SOURCE_TELESTO = "BEM";

    public AllAccountServiceResponse adaptResponseForAllAccountSummary(WorkContext workContext, Object obj) throws Exception {

	AllAccountServiceResponse allAccountServiceResponse = new AllAccountServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AllAccountServiceRequest allAccountServiceRequest = (AllAccountServiceRequest) args[0];

	allAccountServiceResponse.setContext(allAccountServiceRequest.getContext());

	RetrieveIndividualCustomerAccountListResponse customerAccountListResponse = (RetrieveIndividualCustomerAccountListResponse) obj;

	List<CustomerAccountDTO> customerAccountList = new ArrayList<CustomerAccountDTO>();

	String respCode = checkServiceResponseHeader(customerAccountListResponse.getResponseHeader(), allAccountServiceResponse);

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE) || respCode.equals(ErrorCodeConstant.PARTIAL_SUCCESS_CODE)) {

	    AccountList accountList = customerAccountListResponse.getAccountList();

	    CASAAccountSummary[] cASAAccountSummaryArr = accountList.getCASAAccountList();
	    if (cASAAccountSummaryArr != null && cASAAccountSummaryArr.length > 0) {
		CASAAccountMapper cASAAccountMapper = new CASAAccountMapper();
		customerAccountList.addAll(cASAAccountMapper.mapCollection(cASAAccountSummaryArr));
	    }

	    CreditCardAccount[] creditCardAccountArr = accountList.getCreditCardAccountList();
	    if (creditCardAccountArr != null && creditCardAccountArr.length > 0) {
		CreditCardAccountMapper creditCardAccountMapper = new CreditCardAccountMapper();

		customerAccountList.addAll(creditCardAccountMapper.mapCollectionVision(creditCardAccountArr));
	    }

	    allAccountServiceResponse.setSuccess(true);
	    respCode = AccountErrorCodeConstant.SUCCESS_CODE;

	}
	// TODO Verify this for Credit Card .
	// else if (respCode.equals(ErrorCodeConstant.BUSINESS_ERROR)) {
	// allAccountServiceResponse.setSuccess(true);
	// allAccountServiceResponse.setAccountList(null);
	// allAccountServiceResponse.setResCde(respCode);
	// }
	else {
	    allAccountServiceResponse.setSuccess(false);
	    allAccountServiceResponse.setAccountList(null);
	    allAccountServiceResponse.setResCde(respCode);
	}

	allAccountServiceResponse.setAccountList(customerAccountList);

	if (!ErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(respCode)) {
	    allAccountServiceResponse.setResCde(respCode);
	}

	return allAccountServiceResponse;

    }

    private String checkServiceResponseHeader(BEMResHeader resHeader, AllAccountServiceResponse allAccountServiceResponse) throws Exception {

	String returnCode = "";

	if (resHeader.getServiceResStatus() != null) {

	    String resCode = resHeader.getServiceResStatus().getServiceResCode();

	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode) || AccountErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;

	    } else if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
		returnCode = resCode;
	    } else if (errorList != null) {

		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {

		    // REMOVE-CODE-SECTION
		    // if
		    // (AccountErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(resCode))
		    // {

		    if (checkHostErrorStatus(error.getSource(), error.getErrorCode(), allAccountServiceResponse)) {

			returnCode = allAccountServiceResponse.getResCde().toString();

			return returnCode;
		    }
		    // }

		    throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		}
	    }
	}

	return returnCode;
    }

    /**
     * This method used to check whether there are any host error happens
     * 
     * @param sourceHost
     * @param errorList
     * 
     * @return
     */
    private boolean checkHostErrorStatus(String sourceHost, String error, AllAccountServiceResponse response) {

	if (sourceHost.indexOf(SOURCE_FCR) != -1 && error.equals(AccountServiceResponseCodeConstant.FCR_NO_ACCOUNT_EXCEPTION)) {
	    response.setResCde(AccountServiceResponseCodeConstant.ACT_ACTSUMMARY_NOACTFORSUMMARY);
	    return true;
	} else if (SOURCE_FCR.equals(sourceHost) && error.indexOf(SOURCE_FCR) != -1) {
	    response.setResCde(AccountServiceResponseCodeConstant.FCR_HOST_ERROR);
	    return true;
	} else if (SOURCE_PRIME.equals(sourceHost) && SOURCE_PRIME.equalsIgnoreCase(error)) {
	    response.setResCde(AccountServiceResponseCodeConstant.PRIME_HOST_ERROR);
	    return true;
	} else if (SOURCE_TELESTO.equals(sourceHost) && SOURCE_TELESTO.equalsIgnoreCase(error)) {
	    response.setResCde(AccountServiceResponseCodeConstant.TELESCO_HOST_ERROR);
	    return true;
	} else {
	    response.setResCde(AccountErrorCodeConstant.PARTIAL_SUCCESS_CODE);
	}
	return false;
    }

}
