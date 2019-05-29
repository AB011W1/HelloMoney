package com.barclays.bmg.dao.operation.accountservices;

import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.OverrideDetails;
import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.AuthServiceErrorCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthResHeader;

/**
 * Abstract response adapter operation
 *
 * @author E20026338
 *
 */
public class AbstractResAdptOperationAcct {

    /**
     * Check authentication response code
     *
     * @param authResHeader
     * @return
     */

    private static final Logger LOGGER = Logger.getLogger(AbstractResAdptOperationAcct.class);

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

    protected String checkBEMAuthResponse(BEMResHeader resHeader) {

	String resCode = resHeader.getServiceResStatus().getServiceResCode();
	boolean valid = false;
	if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode) || ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
	    valid = true;

	}

	if (!valid && resHeader.getErrorList() != null && resHeader.getErrorList().length > 0) {
	    for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {
		// Crypto err for first time password change required
		if (ErrorCodeConstant.CRYPTO_ERR_CHANGE_PWD.equals(error.getErrorCode())
			|| ErrorCodeConstant.CRYPTO_ERR_CHANGE_PWD_BEM.equals(error.getErrorCode())) {
		    resCode = AuthResponseCodeConstants.AUTH_CHANGE_PWD;
		} else if (ErrorCodeConstant.CRYPTO_UNKNOWN_EXCEPTION.equals(error.getErrorCode())
			&& ErrorCodeConstant.CRYPTO_SHOULD_CHANGE_VALIDATION_FAILED_ERR.equals(error.getPPErrorCode())) {
		    BMBDataAccessException dae = new BMBDataAccessException(error.getPPErrorCode());
		    LOGGER.error("Error for checkBEMAuthResponse", dae);
		    throw dae;
		} else if (ErrorCodeConstant.CRYPTO_UNKNOWN_EXCEPTION.equals(error.getErrorCode())
			&& ErrorCodeConstant.CRYPTO_SHOULD_CHANGE_VALIDATION_PASSED.equals(error.getPPErrorCode())) {
		    BMBDataAccessException dae = new BMBDataAccessException(error.getPPErrorCode());
		    LOGGER.error("Error for checkBEMAuthResponse", dae);
		    throw dae;
		} else {
		    BMBDataAccessException dae = new BMBDataAccessException(error.getErrorCode());
		    LOGGER.error("Error for checkBEMAuthResponse", dae);
		    throw dae;
		}
	    }
	}

	return resCode;

    }

    protected boolean checkResponseHeader(BEMResHeader resHeader) {

	String resCode = resHeader.getServiceResStatus().getServiceResCode();
	boolean valid = false;

	if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode) || ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
	    valid = true;
	}

	if (!valid && resHeader.getErrorList() != null && resHeader.getErrorList().length > 0) {
	    String serviceId = resHeader.getServiceContext().getServiceID();

	    for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {
		if (ErrorCodeConstant.BP_BCD_PRIME_06878.equals(error.getErrorCode())) {
		    resCode = "BPY00621";
		    return valid;
		}
		if ((ErrorCodeConstant.MW_FINANCIAL_SERVICE_TIMEOUT.equals(error.getErrorCode()) && (ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT
			.equals(serviceId)
			|| ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT.equals(serviceId)
			|| ServiceIdConstants.SERVICE_MAKE_DOMESTIC_DEMAND_DRAFT_BY_ACCOUNT.equals(serviceId)
			|| ServiceIdConstants.SERVICE_MAKE_DOMESTIC_FUND_TRANSFER.equals(serviceId) || ServiceIdConstants.SERVICE_MAKE_INTERNATIONAL_FUND_TRANSFER
			.equals(serviceId)))
			|| (ErrorCodeConstant.MW_VAS_TIMEOUT.equals(error.getErrorCode()) && (ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT
				.equals(serviceId) || ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT.equals(serviceId)))) {

		    BMBDataAccessException dae = new BMBDataAccessException(ErrorCodeConstant.PREFIX_FINANCIAL_SERVICE + error.getErrorCode());
		    LOGGER.error("Error in checkResponseHeader for timeout", dae);
		    throw dae;
		} else if (ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT_FOR_ALL.equals(serviceId)
			&& (ErrorCodeConstant.UBP_ERR_CODE.equals(error.getErrorCode()))) {
		    // To handle InProgress transaction
		    BMBDataAccessException dae = new BMBDataAccessException(error.getPPErrorCode(), error.getPPErrorDesc(), resHeader
			    .getServiceContext().getConsumerUniqueRefNo());
		    LOGGER.error("Error for SERVICE_MAKE_BILL_PAYMENT_FOR_ALL", dae);
		    throw dae;
		}else if(ServiceIdConstants.SERVICE_RTRV_IND_CUST_CARD_LIST.equals(serviceId)){
			 BMBDataAccessException dae = new BMBDataAccessException(resCode);
			 LOGGER.error("Error for SERVICE_RTRV_IND_CUST_CARD_LIST", dae);
			return valid;
		}else {
		    BMBDataAccessException dae = new BMBDataAccessException(error.getErrorCode());
		    LOGGER.error("Error in checkResponseHeader ", dae);
		    throw dae;
		}

	    }
	}

	// Added to read OverrideList from BEM error response
	if (!valid && resHeader.getOverrideList() != null && resHeader.getOverrideList().length > 0) {
	    OverrideDetails[] overrideList = resHeader.getOverrideList();
	    BMBDataAccessException dae = new BMBDataAccessException(overrideList[0].getCode(), overrideList[0].getDetails());
	    LOGGER.error("Error in checkResponseHeader from overrideList ", dae);
	    throw dae;
	}

	if (!valid) {
	    BMBDataAccessException dae = new BMBDataAccessException(resCode);
	    LOGGER.error("Error in checkResponseHeader from other ", dae);
	    throw dae;
	}
	return valid;
    }

    protected String getParamvalue(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	RequestContext request = (RequestContext) args[0];
	Context context = request.getContext();
	Map<String, Object> contextMap = context.getContextMap();
	String param_value = (String) contextMap.get(SystemParameterConstant.VISIONPLUS_PRIME);
	return param_value;
    }
}
