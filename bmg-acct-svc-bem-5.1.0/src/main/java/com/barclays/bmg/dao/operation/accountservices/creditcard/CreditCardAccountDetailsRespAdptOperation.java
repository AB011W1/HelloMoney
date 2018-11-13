package com.barclays.bmg.dao.operation.accountservices.creditcard;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCreditCardAcctDetails.RetrieveCreditCardAccountDetailsResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.mapper.CreditCardAccountToCoreCreditCardAccount;
import com.barclays.bmg.service.accountdetails.response.CreditCardAccountDetailsServiceResponse;

public class CreditCardAccountDetailsRespAdptOperation extends AbstractResAdptOperation {

    public CreditCardAccountDetailsServiceResponse adaptResponseForCreditCardAccountDetails(WorkContext workContext, Object obj) throws Exception {

	CreditCardAccountDetailsServiceResponse returnCCAccDetailsServiceResponse = new CreditCardAccountDetailsServiceResponse();

	RetrieveCreditCardAccountDetailsResponse retrieveCCAccDetailsResponse = (RetrieveCreditCardAccountDetailsResponse) obj;

	String respCode = checkServiceResponseHeader(retrieveCCAccDetailsResponse.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE) || respCode.equals(ErrorCodeConstant.PARTIAL_SUCCESS_CODE)) {
	    CreditCardAccountToCoreCreditCardAccount creditCardAccountMapper = new CreditCardAccountToCoreCreditCardAccount();
	    CreditCardAccountDTO creditCardAccountDTO = creditCardAccountMapper.mapBean(retrieveCCAccDetailsResponse.getCreditCardAccountDetails(),
		    null);

	    returnCCAccDetailsServiceResponse.setCreditCardAccountDTO(creditCardAccountDTO);
	    returnCCAccDetailsServiceResponse.setSuccess(true);
	    respCode = AccountErrorCodeConstant.SUCCESS_CODE;
	} else {
	    returnCCAccDetailsServiceResponse.setSuccess(false);
	}

	returnCCAccDetailsServiceResponse.setResCde(respCode);

	return returnCCAccDetailsServiceResponse;

    }

    /*
     * checking for error response.
     */
    private String checkServiceResponseHeader(BEMResHeader resHeader) throws Exception {

	String returnCode = "";

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();
	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode) || AccountErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;
	    }

	    else if (errorList != null) {
		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {

		    if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EXIST)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EMPTY_PRIME)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO;
		    } else {
			throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		    }
		}
	    }
	}

	return returnCode;
    }

}
