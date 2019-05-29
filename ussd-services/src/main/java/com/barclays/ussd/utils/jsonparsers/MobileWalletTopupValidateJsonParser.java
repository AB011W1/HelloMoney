package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.dao.product.impl.ListValueResDAOImpl;
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
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletTxValidate;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletTxValidatePayData;

public class MobileWalletTopupValidateJsonParser implements BmgBaseJsonParser {

    private static final String DEBIACCNUM_LABEL = "label.debit.accnum";
    private static final String MNOPROVIDER_LABEL = "label.mno.provider";
    private static final String RECHARGE_AMOUNT = "label.recharge.amount";
    private static final String CONFIRM_LABEL = "label.mwallet.confirm";
    private static final String TRANSACTION_MWALLET_LABEL = "label.transaction.mwallet";
    private static final String TRANSACTION_SERVICE_LABEL = "label.transaction.service";
    private static final String CREDIT_LABEL = "label.mwallet.creditCard";
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private String pilotValue = null;
    // private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";

    // private static final String NAVIGATE_MAIN = "label.navigate.main";
    private static final Logger LOGGER = Logger.getLogger(MobileWalletTopupValidateJsonParser.class);

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    MobileWalletTxValidate mobileWalletTxValidate = mapper.readValue(responseBuilderParamsDTO.getJsonString(), MobileWalletTxValidate.class);

	    if (mobileWalletTxValidate != null) {
		if ((mobileWalletTxValidate.getPayHdr() != null)
			&& (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(mobileWalletTxValidate.getPayHdr().getResCde()))) {
		    menuDTO = renderMenuOnScreen(mobileWalletTxValidate.getPayData(), responseBuilderParamsDTO);
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_VALIDATE.getTranId(),
			    mobileWalletTxValidate.getPayData().getTxnRefNo());
		}
		// else if (mobileWalletTxValidate.getPayHdr() != null
		// && TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(mobileWalletTxValidate.getPayHdr().getResCde())) {
		// throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
		// }
		else if (mobileWalletTxValidate.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(mobileWalletTxValidate.getPayHdr().getResCde());
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

    private MenuItemDTO renderMenuOnScreen(MobileWalletTxValidatePayData mobileWalletTxValidatePayData,
	    ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	if (mobileWalletTxValidatePayData != null) {

	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	    String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DEBIACCNUM_LABEL, locale);
	    String mobileWalletAccountNumberLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.mobile.number", locale);
	    String mobileWalletServiceLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.transaction.service", locale);
	    String creditCard = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CREDIT_LABEL, locale);
	    /* Not set in credit card so removed 24/05/2017
	     * String accountNo=mobileWalletTxValidatePayData.getSrcAcct().getMkdActNo();
	     */
	    // CPB demo change
	    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL, locale);
	    String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
			    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	    String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFIRM_LABEL, locale);
	    // CR39 Change for KE confirm screen commented as a part of CR47
	  /*  if (ussdSessionMgmt.getBusinessId().equalsIgnoreCase(USSDConstants.BUSINESS_ID_KEBRB)) {
		pageBody.append(mobileWalletTxValidatePayData.getSrcAcct().getMkdActNo());
		pageBody.append(USSDConstants.NEW_LINE).append(mobileWalletAmountLabel).append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(mobileWalletTxValidatePayData.getTxnAmt().getAmt());
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE).append(mobileWalletTxValidatePayData.getTxnAmt().getCurr());
	    } else {*/
		String paramArray[]={userInputMap.get("txnAmt")};
		String mWallettAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_MWALLET_LABEL, paramArray,
				new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
						responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
		String mbNum = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName());
	    pageBody.append(mWallettAmountLabel).append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(USSDConstants.NEW_LINE).append(mobileWalletAccountNumberLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(mbNum);
	    pageBody.append(USSDConstants.NEW_LINE).append(mobileWalletServiceLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(mobileWalletTxValidatePayData.getPrvder().getBillerName());
  		if(USSDConstants.CREDIT_MOBILE_WALLET.equals(ussdSessionMgmt.getTxSessions().get(USSDConstants.CREDIT_MOBILE_WALLET))){
	    pageBody.append(USSDConstants.NEW_LINE).append(creditCard).append(USSDConstants.SINGLE_WHITE_SPACE).append(mobileWalletTxValidatePayData.getCreditcardJsonMod().getMkdCrdNo());
	    }else {
	    pageBody.append(USSDConstants.NEW_LINE).append(fromAccLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(mobileWalletTxValidatePayData.getSrcAcct().getMkdActNo());
	    }

  		// Pilot Migration check CBP  22/08
		/*ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(BMBContextHolder.getContext());
		listValueResServiceRequest.setListValueKey("MPesa");
		ListValueResByGroupServiceResponse listResp = listValueResDAOImpl.findListValueResByKey(listValueResServiceRequest);
		pilotValue = listResp.getListValueCahceDTO().get(0).getLabel();*/

		// Removing PilotValue check(pilotValue !=null && pilotValue.equalsIgnoreCase("Y")) its not going with Regulatory 6.0.0 changes - 02/11/2017

     	//CBP Change
	    BMGGlobalContext logContext = BMGContextHolder.getLogContext();

		if((logContext !=null && logContext.getContextMap().get("isCBPFLAG").equals("Y")|| logContext.getBusinessId().equals("KEBRB")) && mobileWalletTxValidatePayData.getTxnAmt()!= null && mobileWalletTxValidatePayData.getCreditcardJsonMod() == null
				 /*responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB")*/){
			// Transaction fee only for CASA - CBP
		    if((logContext !=null && logContext.getContextMap().get("isCBPFLAG").equals("Y") || logContext.getBusinessId().equals("KEBRB")) && mobileWalletTxValidatePayData.getCreditcardJsonMod() == null
		    		&& mobileWalletTxValidatePayData.getSrcAcct()!=null && mobileWalletTxValidatePayData.getTxnAmt().getAmt()!=null){

		    	Double accumulatedCharge = 0.0;
		    	Double roundedAccumulatedVal = 0.0;
	    		Double tranFee = Double.valueOf(mobileWalletTxValidatePayData.getTxnAmt().getAmt());
				Double taxAmt = mobileWalletTxValidatePayData.getTaxAmount();
				if(tranFee!=null && taxAmt!=null){
					accumulatedCharge = tranFee + taxAmt;
					roundedAccumulatedVal =(double) Math.round(accumulatedCharge * 100) / 100;
				}

		    	pageBody.append(USSDConstants.NEW_LINE).append(transactionFeeLabel).append(USSDConstants.SINGLE_WHITE_SPACE).
		    		append(mobileWalletTxValidatePayData.getTxnAmt().getCurr()).append(USSDConstants.SINGLE_WHITE_SPACE).append(roundedAccumulatedVal);

		    	// CPB change fields set for MWallet MakeBillPaymentRequest 12/05/2017
				ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "CpbMakeBillPaymentFields");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", mobileWalletTxValidatePayData.getTxnAmt());
				ussdSessionMgmt.getTxSessions().put("CpbFeeGLAccount", mobileWalletTxValidatePayData.getFeeGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", mobileWalletTxValidatePayData.getTaxAmount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxGLAccount", mobileWalletTxValidatePayData.getTaxGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbChargeNarration", mobileWalletTxValidatePayData.getChargeNarration());
				ussdSessionMgmt.getTxSessions().put("CpbExciseDutyNarration", mobileWalletTxValidatePayData.getExciseDutyNarration());
				ussdSessionMgmt.getTxSessions().put("CpbtypeCode", mobileWalletTxValidatePayData.getTypeCode());
				ussdSessionMgmt.getTxSessions().put("CpbValue", mobileWalletTxValidatePayData.getValue());
		    }else {
	    		pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(xelerateOfflineLabel);

				// Xelerate Offline scenario - fields required by BEM
				ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "xelerateOffline");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", mobileWalletTxValidatePayData.getTxnAmt());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", mobileWalletTxValidatePayData.getTaxAmount());
	    	}
		}
        //CR#60 changes to remove Target MNO label from confirmation screen  start
		/*pageBody.append(USSDConstants.NEW_LINE).append(mnoLabel).append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(mobileWalletTxValidatePayData.getPrvder().getBillerName());*/
		//CR#60 changes to remove Target MNO label from confirmation screen  end
		/*pageBody.append(USSDConstants.NEW_LINE).append(mobileWalletAmountLabel).append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(mobileWalletTxValidatePayData.getTxnAmt().getAmt());
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE).append(mobileWalletTxValidatePayData.getTxnAmt().getCurr());*/
	 //   }
	    //&& Condition added as part of CR50
	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE).append(confirmLabel);
	    }
	}

	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().put("BillerName", mobileWalletTxValidatePayData.getPrvder().getBillerName());
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo());
    }

}