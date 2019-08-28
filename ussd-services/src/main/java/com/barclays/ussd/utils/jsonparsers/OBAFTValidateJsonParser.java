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
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OwnFundTxValidate;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OwnFundTxValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

public class OBAFTValidateJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(OBAFTValidateJsonParser.class);
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final String AMOUNT_LABEL = "label.amount";
    private static final String TOACC_LABEL = "label.tonickname";
    private static final String FROMACC_LABEL = "label.fromaccount";
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private static final String FROM_CREDITCARD_LABEL = "label.from.credit";
    // Xcelerate is offline message
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";
    private String pilotValue = null;

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    OwnFundTxValidate ownFundTxValidateObj = mapper.readValue(jsonString, OwnFundTxValidate.class);
	    if (ownFundTxValidateObj != null) {
		PayHdr payHdr = ownFundTxValidateObj.getPayHdr();

		if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		    menuDTO = renderMenuOnScreen(ownFundTxValidateObj.getPayData(), responseBuilderParamsDTO);
		    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(ownFundTxValidateObj.getPayData().getTxnRefNo());
		    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.INT_FT_VALIDATE.getTranId(), txnRefNo);
		} else if (payHdr != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(payHdr.getResCde());
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
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	if (ownFundTxValidatePayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	    String fromAccLabel = ussdResourceBundle.getLabel(FROMACC_LABEL, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String fromCreditCardLabel = ussdResourceBundle.getLabel(FROM_CREDITCARD_LABEL, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
			    ussdSessionMgmt.getUserProfile().getCountryCode()));

	    String toAccLabel = ussdResourceBundle.getLabel(TOACC_LABEL, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		    .getUserProfile().getCountryCode()));
	    String amountLabel = ussdResourceBundle.getLabel(AMOUNT_LABEL, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		    .getUserProfile().getCountryCode()));
	    String confirmLabel = ussdResourceBundle.getLabel(CONFIRM_LABEL, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));

	    pageBody.append(USSDConstants.NEW_LINE);
	    if(ussdSessionMgmt.getTxSessions().containsKey("CreditCard")){
	    	pageBody.append(fromCreditCardLabel);
	 	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	 	    pageBody.append(ownFundTxValidatePayData.getMkdCrdNo());
	    }else{
	    	pageBody.append(fromAccLabel);
	 	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	 	    pageBody.append(ownFundTxValidatePayData.getFrMskActNo());
	    }

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(toAccLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(ownFundTxValidatePayData.getPayName());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(ownFundTxValidatePayData.getTxnAmt().getAmt());

	    // Pilot Migration check CBP  22/08
		/*ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(BMBContextHolder.getContext());
		listValueResServiceRequest.setListValueKey("OtherBarclayAccnt");
		ListValueResByGroupServiceResponse listResp = listValueResDAOImpl.findListValueResByKey(listValueResServiceRequest);
		pilotValue = listResp.getListValueCahceDTO().get(0).getLabel();*/

	    // CPB changes
		// Removing PilotValue check(pilotValue !=null && pilotValue.equalsIgnoreCase("Y")) its not going with Regulatory 6.0.0 changes - 02/11/2017
		String strCreditCardChk = ownFundTxValidatePayData.getMkdCrdNo();
		//CBP Change

		BMGGlobalContext logContext = BMGContextHolder.getLogContext();
	     if((null!=logContext && logContext.getContextMap().get("isCBPFLAG").equals("Y") || null!=logContext && logContext.getBusinessId().equals("KEBRB")) && ownFundTxValidatePayData.getTxnChargeAmt()!=null  && strCreditCardChk == null){
		    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL,
				    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
				    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    if(ownFundTxValidatePayData.getTxnChargeAmt().getAmt() !=null){

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
	    		append(ownFundTxValidatePayData.getTxnChargeAmt().getCurr()).append(USSDConstants.SINGLE_WHITE_SPACE).append(roundedAccumulatedVal);

				// CPB change fields set for MakeDomesticFundTransferRequest 30/05/2017
				ussdSessionMgmt.getTxSessions().put("CpbMakeDomesticFundTransferFields", "CpbMakeDomesticFundTransferFields");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", ownFundTxValidatePayData.getTxnChargeAmt());
				ussdSessionMgmt.getTxSessions().put("CpbFeeGLAccount", ownFundTxValidatePayData.getFeeGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", ownFundTxValidatePayData.getTaxAmount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxGLAccount", ownFundTxValidatePayData.getTaxGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbChargeNarration", ownFundTxValidatePayData.getChargeNarration());
				ussdSessionMgmt.getTxSessions().put("CpbExciseDutyNarration", ownFundTxValidatePayData.getExciseDutyNarration());
				ussdSessionMgmt.getTxSessions().put("CpbtypeCode", ownFundTxValidatePayData.getTypeCode());
				ussdSessionMgmt.getTxSessions().put("CpbValue", ownFundTxValidatePayData.getValue());
		    }else if(ownFundTxValidatePayData.getXelerateOfflineError()!=null){
		    	pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(xelerateOfflineLabel);

				// Xelerate Offline scenario - fields required by BEM
				ussdSessionMgmt.getTxSessions().put("CpbMakeDomesticFundTransferFields", "xelerateOffline");
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }

}