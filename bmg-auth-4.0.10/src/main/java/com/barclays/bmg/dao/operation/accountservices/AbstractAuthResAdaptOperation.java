package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.AuthServiceErrorCodeConstant;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthResHeader;

abstract public class AbstractAuthResAdaptOperation {

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
	} else if (resCde == null) {
	    String errorCode = getErrorCode(authResHeader);
	    if (AuthServiceErrorCodeConstant.SEC_CUSTOMER_NOT_EXIST.equals(errorCode)) {
		resCde = AuthResponseCodeConstants.AUTH_CUSTOMER_NOT_EXIST;
	    } else {
		throw new BMBDataAccessException(errorCode);
	    }

	} else {
	    throw new BMBDataAccessException(resCde);
	}

	return resCde;

    }

    /**
     * Check the response header and error code, throw data access exceptions.
     * 
     * @param header
     */
    protected String getErrorCode(AuthResHeader header) throws BMBDataAccessException {
	String errorCode = null;
	if (header != null && header.getErrorList() != null && header.getErrorList().length > 0) {
	    for (com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.Error error : header.getErrorList()) {

		errorCode = error.getErrorCode();

	    }
	}
	return errorCode;
    }
}
