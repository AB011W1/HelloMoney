package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dao.product.impl.ListValueResDAOImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered.IntNRFundTxValidate;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered.IntNRFundTxValidatePayData;

public class InternalNonRegFundTransferValidateJsonParser implements BmgBaseJsonParser {
    private static final String ACCNUM_LABEL = "label.to.acc.number";
    private static final String AMOUNT_LABEL = "label.amount";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final String FROMACCNUM_LABEL = "label.from.acc.number";
    private static final String FROM_CREDITCARD_LABEL = "label.from.credit";
    private static final Logger LOGGER = Logger.getLogger(InternalNonRegFundTransferValidateJsonParser.class);
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";
    private String pilotValue = null;

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    IntNRFundTxValidate intNRFundTxValidateObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), IntNRFundTxValidate.class);
	    if (intNRFundTxValidateObj != null) {
		if ((intNRFundTxValidateObj.getPayHdr() != null)
			&& (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(intNRFundTxValidateObj.getPayHdr().getResCde()))) {
		    menuDTO = renderMenuOnScreen(intNRFundTxValidateObj.getPayData(), responseBuilderParamsDTO);
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(intNRFundTxValidateObj.getPayData().getTxnRefNo());
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId(), txnRefNo);
		} else if (intNRFundTxValidateObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(intNRFundTxValidateObj.getPayHdr().getResCde());
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
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    private MenuItemDTO renderMenuOnScreen(IntNRFundTxValidatePayData intNRFundTxValidatePayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	if (intNRFundTxValidatePayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	    String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(FROMACCNUM_LABEL, locale);
	    String fromCreditCardLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(FROM_CREDITCARD_LABEL, locale);

	    String toAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(ACCNUM_LABEL, locale);
	    String amountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AMOUNT_LABEL, locale);
	    String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFIRM_LABEL, locale);


	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(USSDConstants.NEW_LINE);
	    if (ussdSessionMgmt.getTxSessions().containsKey("CREDIT_CARD_TRAN")) {
	    		pageBody.append(fromCreditCardLabel);
	    		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    		pageBody.append(intNRFundTxValidatePayData.getCreditcard().getMkdCrdNo());
			} else {
				pageBody.append(fromAccLabel);
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(intNRFundTxValidatePayData.getFrActNo().getMkdActNo());
			}


	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(toAccLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(intNRFundTxValidatePayData.getToMskActNo());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(intNRFundTxValidatePayData.getTxnAmt().getAmt());

	    // Pilot Migration check CBP  22/08
		/*ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(BMBContextHolder.getContext());
		listValueResServiceRequest.setListValueKey("OtherBarclayAccnt");
		ListValueResByGroupServiceResponse listResp = listValueResDAOImpl.findListValueResByKey(listValueResServiceRequest);
		pilotValue = listResp.getListValueCahceDTO().get(0).getLabel();*/


	    // Transaction fee only for CASA - CPB change 25/05/2017
		// Removing PilotValue check(pilotValue !=null && pilotValue.equalsIgnoreCase("Y")) its not going with Regulatory 6.0.0 changes - 02/11/2017
	    if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB") && intNRFundTxValidatePayData.getCreditcard() == null &&
	    		intNRFundTxValidatePayData.getTxnChargeAmt()!=null){
		    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL,
				    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    if(intNRFundTxValidatePayData.getTxnChargeAmt().getAmt()!=null){

		    	Double accumulatedCharge = 0.0;
		    	Double roundedAccumulatedVal = 0.0;
	    		Double tranFee = Double.valueOf(intNRFundTxValidatePayData.getTxnChargeAmt().getAmt());
				Double taxAmt = intNRFundTxValidatePayData.getTaxAmount();
				if(tranFee!=null && taxAmt!=null){
					accumulatedCharge = tranFee + taxAmt;
					roundedAccumulatedVal =(double) Math.round(accumulatedCharge * 100) / 100;
				}

				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(transactionFeeLabel).append(USSDConstants.SINGLE_WHITE_SPACE).
	    		append(intNRFundTxValidatePayData.getTxnChargeAmt().getCurr()).append(USSDConstants.SINGLE_WHITE_SPACE).
	    		append(roundedAccumulatedVal);

				// CPB change fields set for MakeBillPayemntRequest 31/05/2017
				ussdSessionMgmt.getTxSessions().put("CpbMakeDomesticFundFields", "CpbMakeDomesticFundFields");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", intNRFundTxValidatePayData.getTxnChargeAmt());
				ussdSessionMgmt.getTxSessions().put("CpbFeeGLAccount", intNRFundTxValidatePayData.getFeeGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", intNRFundTxValidatePayData.getTaxAmount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxGLAccount", intNRFundTxValidatePayData.getTaxGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbChargeNarration", intNRFundTxValidatePayData.getChargeNarration());
				ussdSessionMgmt.getTxSessions().put("CpbExciseDutyNarration", intNRFundTxValidatePayData.getExciseDutyNarration());
				ussdSessionMgmt.getTxSessions().put("CpbtypeCode", intNRFundTxValidatePayData.getTypeCode());
				ussdSessionMgmt.getTxSessions().put("CpbValue", intNRFundTxValidatePayData.getValue());
		    }else{
		    	 String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
						    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    	pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(xelerateOfflineLabel);

				// Xelerate Offline scenario - fields required by BEM
				ussdSessionMgmt.getTxSessions().put("CpbMakeDomesticFundFields", "xelerateOffline");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", intNRFundTxValidatePayData.getTxnChargeAmt());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", intNRFundTxValidatePayData.getTaxAmount());
		    }
	    }
	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }

	}

	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);

	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo());

    }

}