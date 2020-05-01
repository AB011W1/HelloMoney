package com.barclays.bmg.dao.operation.accountservices;

import java.util.Map;

import org.apache.log4j.Logger;

import java.util.regex.Pattern;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.OverrideDetails;
import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.AuthServiceErrorCodeConstant;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CreditCardActivityDTO;
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
    
    // Changes has been done for First Vision--April 2020--Starts
	protected String getSystemParameterValueById(Context context, String param_id) {
		Map<String, Object> contextMap = context.getContextMap();
		String param_value = (String) contextMap.get(param_id);
		return param_value;
	}

	// Added this method to suppress and merge fees with foreign transaction--First
	// Vision Changes
	public boolean isForeignTransaction(CreditCardActivityDTO creditCardActDto) {
		if ("6".equals(creditCardActDto.getQualityInd()) && creditCardActDto.getForeignTransCode() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFeesTransaction(CreditCardActivityDTO creditCardActDto, List<String> transactionFeesArrayList) {
		if (transactionFeesArrayList != null
				&& transactionFeesArrayList.contains(creditCardActDto.getTransactionTypeCode())) {
			return true;
		}
		return false;
	}

	public boolean isTransactionActivityBeSupressed(CreditCardActivityDTO creditCardActDto,
			List<String> transactionCodeForSupList) {

		if (transactionCodeForSupList != null
				&& transactionCodeForSupList.contains(creditCardActDto.getTransactionTypeCode())) {
			return true;
		}
		return false;

	}

	public boolean isLoanActivitybeSupressed(CreditCardActivityDTO creditCardActiityDTO, String supressPatternCode) {
		if (creditCardActiityDTO.getCreditPlan() != null && StringUtils.isNotEmpty(supressPatternCode)
				&& Pattern.matches(supressPatternCode, creditCardActiityDTO.getCreditPlan())) {
			return true;
		}
		return false;
	}
	// calculate total fees

	// Added this method to suppress and merge fees with foreign transaction--First
	// Vision Changes
	public List<CreditCardActivityDTO> getUpdatedTransactionActivityList(List<CreditCardActivityDTO> activityList,
			Context context) {

		List<CreditCardActivityDTO> creditCardActivityDTOList = new ArrayList<CreditCardActivityDTO>();
		Map<String, CreditCardActivityDTO> feesTransactionActMap = new HashMap<String, CreditCardActivityDTO>();
		Map<String, CreditCardActivityDTO> foreignTransactionActMap = new HashMap<String, CreditCardActivityDTO>();
		Map<String, List<CreditCardActivityDTO>> localTransactionActMapList = new HashMap<String, List<CreditCardActivityDTO>>();

		final String loanSupressPattern = getSystemParameterValueById(context,
				SystemParameterConstant.TRANSACTION_SUPRESS_LOAN); // "[6-9][0-9][1-9][0-9]{2}";//fetch
		// from database
		final String transactionForignFeesCode = getSystemParameterValueById(context,
				SystemParameterConstant.TRANSACTION_FOREIGN_FEES_CODE);
		final String isTransactionMergeRequired = getSystemParameterValueById(context,
				SystemParameterConstant.IS_CC_TRX_MERGE_REQD);
		final String transactionsuppressionRow = getSystemParameterValueById(context,
				SystemParameterConstant.TRX_SUPRS_CC_ROW_COUNT);

		List<String> transactionFeesArrayList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(transactionForignFeesCode)) {
			transactionFeesArrayList = Arrays.asList(transactionForignFeesCode.split(CommonConstants.COMMA));
		}
		List<String> transactionCodeForSuppress = new ArrayList<String>();
		if (transactionsuppressionRow != null) {
			int noOfRows = Integer.parseInt(transactionsuppressionRow);
			for (int row = 0; row < noOfRows; row++) {
				String[] supCode = null;
				final String transactionCodeForSup = getSystemParameterValueById(context,
						(SystemParameterConstant.TRX_SUPRS_CREDIT_CARD + row));
				if (transactionCodeForSup != null) {
					supCode = transactionCodeForSup.split(",");
					transactionCodeForSuppress.addAll(Arrays.asList(supCode));
				}
			}
		}

		if (activityList != null) {
			for (CreditCardActivityDTO creditCardActivityDTO : activityList) {
				// Remove supressed transaction
				if (isTransactionActivityBeSupressed(creditCardActivityDTO, transactionCodeForSuppress)
						|| isLoanActivitybeSupressed(creditCardActivityDTO, loanSupressPattern)) {
					// do nothing for transaction to be supreessed from
					// calculation and also on view
				} else {
					if (isForeignTransaction(creditCardActivityDTO)
							&& !isFeesTransaction(creditCardActivityDTO, transactionFeesArrayList)) {
						foreignTransactionActMap.put(creditCardActivityDTO.getTransactoinReferenceNumber(),
								creditCardActivityDTO);
					} else if (isFeesTransaction(creditCardActivityDTO, transactionFeesArrayList)) {
						feesTransactionActMap.put(creditCardActivityDTO.getTransactoinReferenceNumber(),
								creditCardActivityDTO);
					} else {
						List<CreditCardActivityDTO> creditCardActLocalList = new ArrayList<CreditCardActivityDTO>();
						if (localTransactionActMapList
								.get(creditCardActivityDTO.getTransactoinReferenceNumber()) != null) {
							creditCardActLocalList = localTransactionActMapList
									.get(creditCardActivityDTO.getTransactoinReferenceNumber());
						}
						creditCardActLocalList.add(creditCardActivityDTO);
						localTransactionActMapList.put(creditCardActivityDTO.getTransactoinReferenceNumber(),
								creditCardActLocalList);
					}
				}
			}
		}

		if (CommonConstants.YES.equals(isTransactionMergeRequired)) {
			for (String key : foreignTransactionActMap.keySet()) {
				CreditCardActivityDTO mergedCardActivityDTO = foreignTransactionActMap.get(key);
				BigDecimal mergedAmount = mergedCardActivityDTO.getTransactionAmount();
				if (feesTransactionActMap.get(key) != null) {
					// Merge fees for Foreign
					mergedCardActivityDTO.setMergedFlag(true);
					mergedAmount = mergedAmount.add(feesTransactionActMap.get(key).getTransactionAmount());
					mergedCardActivityDTO.setTransactionAmount(mergedAmount);
				}
				mergedCardActivityDTO.setTransactionAmount(mergedAmount);
				creditCardActivityDTOList.add(mergedCardActivityDTO);
			}
			for (String feesKey : feesTransactionActMap.keySet()) {
				if (foreignTransactionActMap.get(feesKey) == null) {
					CreditCardActivityDTO feesCardActivityDTO = feesTransactionActMap.get(feesKey);
					creditCardActivityDTOList.add(feesCardActivityDTO);
				}
			}
			for (String localKey : localTransactionActMapList.keySet()) {
				List<CreditCardActivityDTO> localCardActivityDTO = (ArrayList<CreditCardActivityDTO>) localTransactionActMapList
						.get(localKey);
				for (CreditCardActivityDTO crdDTO : localCardActivityDTO) {
					creditCardActivityDTOList.add(crdDTO);
				}
			}
		} else {
			for (CreditCardActivityDTO crdActDTO : activityList) {
				if (!(isTransactionActivityBeSupressed(crdActDTO, transactionCodeForSuppress)
						|| isLoanActivitybeSupressed(crdActDTO, loanSupressPattern))) {
					creditCardActivityDTOList.add(crdActDTO);
				}
			}
		}
		return creditCardActivityDTOList;
	}

	// Changes has been done for First Vision--April 2020--Ends
    
    
    
    
}
