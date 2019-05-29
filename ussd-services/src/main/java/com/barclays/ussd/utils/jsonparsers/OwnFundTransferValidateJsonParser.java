package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OwnFundTxValidate;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OwnFundTxValidatePayData;

public class OwnFundTransferValidateJsonParser implements BmgBaseJsonParser {
    private static final String CONFRIM_LABEL = "label.confirm";
    private static final String AMOUNT_LABEL = "label.amount";
    private static final String TOACC_LABEL = "label.toaccount";
    private static final String FROMACC_LABEL = "label.fromaccount";
    private static final String FROM_CRE_LABEL = "label.from.Credit";

    // CBP specific label messages
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(OwnFundTransferValidateJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    OwnFundTxValidate ownFundTxValidateObj = mapper.readValue(jsonString, OwnFundTxValidate.class);
	    if (ownFundTxValidateObj != null) {
		if (ownFundTxValidateObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(ownFundTxValidateObj.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(ownFundTxValidateObj.getPayData(), responseBuilderParamsDTO);
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(ownFundTxValidateObj.getPayData().getTxnRefNo());
		    // set the payee accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.OLAFT_VALIDATE.getTranId(), txnRefNo);
		} else if (ownFundTxValidateObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(ownFundTxValidateObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    }
	} catch (JsonParseException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (JsonMappingException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}

	return menuDTO;
    }

    /**
     * @param ownFundTxValidatePayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(OwnFundTxValidatePayData ownFundTxValidatePayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();

	if (ownFundTxValidatePayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	    String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(FROMACC_LABEL,
	    	new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    /*String fromCreditLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(FROM_CRE_LABEL,
    		new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));*/
	    String toAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TOACC_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String amountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AMOUNT_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFRIM_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	    pageBody.append(USSDConstants.NEW_LINE);
	    if("OwnLinkedAcctFundTxCredit".equals(ussdSessionMgmt
	    		.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName())){
	     pageBody.append("From CreditCard:");
	     pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		 pageBody.append(ownFundTxValidatePayData.getMkdCrdNo());
	    } else{
	    pageBody.append(fromAccLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(ownFundTxValidatePayData.getFrMskActNo());
	    }


	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(toAccLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(ownFundTxValidatePayData.getToMskActNo());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(ownFundTxValidatePayData.getTxnAmt().getAmt());

	    // CBP change OwnFundTransfer
	    //CBP Change
	    BMGGlobalContext logContext = BMGContextHolder.getLogContext();

	    if((logContext!=null && logContext.getContextMap().get("isCBPFLAG").equals("Y") && (responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("BWBRB"))) && ownFundTxValidatePayData.getMkdCrdNo() == null &&
	    		ownFundTxValidatePayData.getTxnChargeAmt()!=null){
		    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL,
				    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    if(ownFundTxValidatePayData.getTxnChargeAmt().getAmt()!=null){

		    	Double accumulatedCharge = 0.0;
		    	Double roundedAccumulatedVal = 0.0;
	    		Double tranFee = Double.valueOf(ownFundTxValidatePayData.getTxnChargeAmt().getAmt());
				Double taxAmt = ownFundTxValidatePayData.getTaxAmount();
				if(tranFee!=null && taxAmt!=null){
					accumulatedCharge = tranFee + taxAmt;
					roundedAccumulatedVal =(double) Math.round(accumulatedCharge * 100) / 100;
				}

				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(transactionFeeLabel).append(USSDConstants.SINGLE_WHITE_SPACE).
	    		append(ownFundTxValidatePayData.getTxnChargeAmt().getCurr()).append(USSDConstants.SINGLE_WHITE_SPACE).
	    		append(roundedAccumulatedVal);

				// CPB change fields set for MakeDomesticRequest 31/05/2017
				ussdSessionMgmt.getTxSessions().put("CpbMakeDomesticFundFields", "CpbMakeDomesticFundFields");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", ownFundTxValidatePayData.getTxnChargeAmt());
				ussdSessionMgmt.getTxSessions().put("CpbFeeGLAccount", ownFundTxValidatePayData.getFeeGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", ownFundTxValidatePayData.getTaxAmount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxGLAccount", ownFundTxValidatePayData.getTaxGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbChargeNarration", ownFundTxValidatePayData.getChargeNarration());
				ussdSessionMgmt.getTxSessions().put("CpbExciseDutyNarration", ownFundTxValidatePayData.getExciseDutyNarration());
				ussdSessionMgmt.getTxSessions().put("CpbtypeCode", ownFundTxValidatePayData.getTypeCode());
				ussdSessionMgmt.getTxSessions().put("CpbValue", ownFundTxValidatePayData.getValue());
		    }else{
		    	 String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
						    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    	pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(xelerateOfflineLabel);

				// Xelerate Offline scenario - fields required by BEM
				ussdSessionMgmt.getTxSessions().put("CpbMakeDomesticFundFields", "xelerateOffline");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", ownFundTxValidatePayData.getTxnChargeAmt());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", ownFundTxValidatePayData.getTaxAmount());
		    }
	    }

	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }

	}

	menuItemDTO.setPageBody(pageBody.toString());

	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	// Refer to sequencer.properties to set the below value
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }

}
