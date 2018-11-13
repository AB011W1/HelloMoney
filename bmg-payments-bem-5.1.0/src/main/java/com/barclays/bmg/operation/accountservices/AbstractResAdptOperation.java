package com.barclays.bmg.operation.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.AuthServiceErrorCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthResHeader;

/**
 * Abstract response adapter operation
 * 
 * @author E20026338
 * 
 */
public class AbstractResAdptOperation {

    /**
     * Check authentication response code
     * 
     * @param authResHeader
     * @return
     */
    public String checkAuthResponse(AuthResHeader authResHeader) {

	String resCde = authResHeader.getServiceResStatus().getServiceResCode();
	String resDesc = authResHeader.getServiceResStatus().getServiceResDesc();

	if (AuthServiceErrorCodeConstant.RESP_CODE_SUCCESS.equals(resCde)) {
	    resCde = AuthResponseCodeConstants.AUTH_SUCCESS;
	} else if (AuthServiceErrorCodeConstant.SEC_INVALID_PASSWORD.equals(resCde)) {
	    resCde = AuthResponseCodeConstants.AUTH_INVALID_PASSWORD;
	} else if (AuthServiceErrorCodeConstant.SEC_INVALID_USERNAME.equals(resCde)) {
	    if ("LOGIN_USER_INACTIVE".equals(resDesc)) {
		resCde = AuthResponseCodeConstants.USER_INACTIVE;
	    } else {
		resCde = AuthResponseCodeConstants.AUTH_INVALID_USERNAME;
	    }

	} else if (AuthServiceErrorCodeConstant.SEC_INVALID_OTP.equals(resCde)) {
	    resCde = AuthResponseCodeConstants.AUTH_INVALID_OTP;
	} else if (AuthServiceErrorCodeConstant.SEC_INVALID_SQA.equals(resCde)) {
	    resCde = AuthResponseCodeConstants.AUTH_INVALID_SQA;
	} else if (AuthServiceErrorCodeConstant.SEC_CHALLENGE_NOT_FOUND.equals(resCde)) {
	    resCde = AuthResponseCodeConstants.AUTH_OTP_EXPIRED;
	} else if (AuthServiceErrorCodeConstant.SEC_AUTH_USER_LOCKED.equals(resCde)) {
	    resCde = AuthResponseCodeConstants.USER_LOCKED;
	} else {
	    throw new BMBDataAccessException(resCde);
	}

	return resCde;

    }

    protected boolean checkResponseHeader(BEMResHeader resHeader) {
	String resCode = resHeader.getServiceResStatus().getServiceResCode();
	boolean valid = false;
	if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode) || ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
	    valid = true;
	}
	String serviceId = resHeader.getServiceContext().getServiceID();
	if (!valid && resHeader.getErrorList() != null && resHeader.getErrorList().length > 0) {

	    for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {
		// Error Message Scenario for Payments.

		if ((ErrorCodeConstant.MW_FINANCIAL_SERVICE_TIMEOUT.equals(error.getErrorCode()) && (ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT
			.equals(serviceId)
			|| ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT.equals(serviceId)
			|| ServiceIdConstants.SERVICE_MAKE_DOMESTIC_DEMAND_DRAFT_BY_ACCOUNT.equals(serviceId)
			|| ServiceIdConstants.SERVICE_MAKE_DOMESTIC_FUND_TRANSFER.equals(serviceId) || ServiceIdConstants.SERVICE_MAKE_INTERNATIONAL_FUND_TRANSFER
			.equals(serviceId)))
			|| (ErrorCodeConstant.MW_VAS_TIMEOUT.equals(error.getErrorCode()) && (ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT
				.equals(serviceId) || ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT.equals(serviceId)))) {
		    BMBDataAccessException dae = new BMBDataAccessException(ErrorCodeConstant.PREFIX_FINANCIAL_SERVICE + error.getErrorCode());
		    throw dae;
		} else {
		    BMBDataAccessException dae = new BMBDataAccessException(error.getErrorCode());

		    throw dae;
		}

		/*
		 * if(ErrorCodeConstant.MW_CONNECT_HOST_EXCEPTION.equals(error. getErrorCode()) || ErrorCodeConstant.MW_FINANCIAL_SERVICE_TIMEOUT
		 * .equals(error.getErrorCode())) { // do not show the BEM servic error directly errorCode =
		 * ErrorCodeConstant.MW_CONNECT_HOST_EXCEPTION; if (error.getSource() != null) { errorCode = error.getSource() + "-"+ errorCode; } }
		 * if(errorCode != null){ BMBDataAccessException dae = new BMBDataAccessException(errorCode); throw dae; }else{
		 * BMBDataAccessException dae = new BMBDataAccessException (error.getErrorCode(),error.getErrorDesc()); throw
		 * dae; }
		 */

	    }
	}

	if (!valid) {
	    BMBDataAccessException dae = new BMBDataAccessException(resCode);
	    throw dae;
	}

	return valid;
    }
}
