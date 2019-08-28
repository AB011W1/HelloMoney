package com.barclays.bmg.dao.service.impl;

import org.apache.log4j.Logger;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bmg.constants.AuthServiceErrorCodeConstant;
import com.barclays.bmg.constants.ServiceErrorCodeConstant;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;
//import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthResHeader;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthResHeader;

public abstract class AbstractTransmissionService implements TransmissionService {

    /**
     * Check the response header and error code, throw data access exceptions.
     * 
     * @param header
     * 
     */
    private static final Logger LOGGER = Logger.getLogger(AbstractTransmissionService.class);

    protected void convertException(AuthResHeader header) throws BMBDataAccessException {
	String errorCode = null;
	if (header != null && header.getErrorList() != null && header.getErrorList().length > 0) {
	    for (com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.Error error : header.getErrorList()) {

		if (AuthServiceErrorCodeConstant.MW_NO_SCV_CUST_FOUND.equals(error.getErrorCode())
			|| AuthServiceErrorCodeConstant.SEC_USER_INVALID_USER_STATUS.equals(error.getErrorCode())) {
		    errorCode = error.getErrorCode();

		} else if (AuthServiceErrorCodeConstant.MW_INVALID_CUSTOMER_NUMBER.equals(error.getErrorCode())) {

		    errorCode = AuthServiceErrorCodeConstant.MW_INVALID_CUSTOMER_NUMBER_BIR;

		} else if (AuthServiceErrorCodeConstant.MW_TOO_MANY_HOST_RECORDS_FOUND.equals(error.getErrorCode())) {

		    errorCode = AuthServiceErrorCodeConstant.MW_TOO_MANY_HOST_RECORDS_FOUND_BIR;

		} else if (AuthServiceErrorCodeConstant.MW_CONNECT_HOST_EXCEPTION.equals(error.getErrorCode())) {
		    // do not show the BEM servic error directly
		    errorCode = AuthServiceErrorCodeConstant.MW_CONNECT_HOST_EXCEPTION;
		    if (error.getSource() != null) {
			errorCode = error.getSource() + "-" + errorCode;
		    }

		} else if (AuthServiceErrorCodeConstant.SEC_AUTH_SRV_VERFIY_ERROR.equals(error.getErrorCode())) {
		    errorCode = AuthServiceErrorCodeConstant.SEC_AUTH_SRV_VERFIY_ERROR;

		} else if (AuthServiceErrorCodeConstant.RES_CODE_OTP_SERVICE_ERROR.equals(error.getErrorCode())) {
		    errorCode = AuthServiceErrorCodeConstant.RES_CODE_OTP_SERVICE_ERROR;

		} else if (AuthServiceErrorCodeConstant.SEC_USER_DUPLICATE_ERROR.equals(error.getErrorCode())) {
		    errorCode = AuthServiceErrorCodeConstant.SEC_USER_DUPLICATE_ERROR;

		} else if (AuthServiceErrorCodeConstant.MW_SEARCH_INDIVIDUAL_CUST_FOR_REGISTRATION_SERVICE_EXCEPTION.equals(error.getErrorCode())) {
		    errorCode = AuthServiceErrorCodeConstant.MW_SEARCH_INDIVIDUAL_CUST_FOR_REGISTRATION_SERVICE_EXCEPTION;

		} else if (AuthServiceErrorCodeConstant.SEC_USER_RETURNED_TOO_MANY_RESULTS.equals(error.getErrorCode())) {
		    errorCode = AuthServiceErrorCodeConstant.SEC_USER_RETURNED_TOO_MANY_RESULTS;

		} else if (AuthServiceErrorCodeConstant.SEC_NO_CUSTOMER_FOUND_ID_INCORRECT.equals(error.getErrorCode())) {
		    errorCode = error.getErrorCode();

		} else if (AuthServiceErrorCodeConstant.SEC_NO_CUSTOMER_FOUND_DOB_INCORRECT.equals(error.getErrorCode())) {
		    errorCode = error.getErrorCode();

		} else if (AuthServiceErrorCodeConstant.SEC_NO_CUSTOMER_FOUND_ACC_INCORRECT.equals(error.getErrorCode())) {
		    errorCode = error.getErrorCode();

		} else if (AuthServiceErrorCodeConstant.MW_BRAINS_CONNECTION_ERROR.equals(error.getErrorCode())) {
		    errorCode = error.getErrorCode();

		}

		if (errorCode != null) {
		    BMBDataAccessException dae = new BMBDataAccessException(errorCode);
		    LOGGER.error("BMB data access exception", dae);
		    throw dae;
		}
	    }

	}
    }

    protected void convertException(BEMResHeader header) throws BMBDataAccessException {
	// TODO Auto-generated method stub
	String errorCode = null;
	if (header != null && header.getErrorList() != null && header.getErrorList().length > 0) {

	    for (com.barclays.bem.BEMServiceHeader.Error error : header.getErrorList()) {
		if (ServiceErrorCodeConstant.MW_CONNECT_HOST_EXCEPTION.equals(error.getErrorCode())
			|| ServiceErrorCodeConstant.MW_FINANCIAL_SERVICE_TIMEOUT.equals(error.getErrorCode())) {
		    // do not show the BEM servic error directly
		    errorCode = ServiceErrorCodeConstant.MW_CONNECT_HOST_EXCEPTION;
		    if (error.getSource() != null) {
			errorCode = error.getSource() + errorCode;
		    }
		}

		if (errorCode != null) {
		    BMBDataAccessException dae = new BMBDataAccessException(errorCode);
		    LOGGER.error("BMB data access exception", dae);
		    throw dae;
		}

	    }
	}
    }
}
