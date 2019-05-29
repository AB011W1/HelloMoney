package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CpbPesaLinkValidate;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CpbPesaLinkValidatePayData;

@SuppressWarnings("unchecked")
public class KitsSendToAccountConfirmDetailsJsonParser implements BmgBaseJsonParser {

    private static final String AMOUNT_LABEL = "label.att.amount";
    private static final String CURRENCY = "label.currency";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private static final Logger LOGGER = Logger.getLogger(KitsSendToAccountConfirmDetailsJsonParser.class);
    private String pilotValue = null;
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	 MenuItemDTO menuDTO = null;

	 //CPB change 03/05/2017
	 ObjectMapper mapper = new ObjectMapper();
	 String jsonString = responseBuilderParamsDTO.getJsonString();
	CpbPesaLinkValidate cpbValidateObj = null;
	 try {
		 cpbValidateObj = mapper.readValue(jsonString, CpbPesaLinkValidate.class);
		 if (cpbValidateObj != null) {
				if (cpbValidateObj.getPayHdr() != null
					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(cpbValidateObj.getPayHdr().getResCde())) {

					menuDTO = renderMenuOnScreen(cpbValidateObj.getPayData(), responseBuilderParamsDTO);
				}else if (cpbValidateObj.getPayHdr() != null
						&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(cpbValidateObj.getPayHdr().getResCde())) {
				    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode(),true);
				} else if (cpbValidateObj.getPayHdr() != null) {
				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				    throw new USSDNonBlockingException(cpbValidateObj.getPayHdr().getResCde(),true);
				} else {
				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
				}
		 }
    } catch (JsonParseException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	} catch (JsonMappingException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode(),true);
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    }
	}
	 return menuDTO;
    }


    private MenuItemDTO renderMenuOnScreen(CpbPesaLinkValidatePayData cpbValidatePayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {

	    MenuItemDTO menuItemDTO = null;

	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    menuItemDTO = new MenuItemDTO();
	    StringBuilder pageBody = new StringBuilder();
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    String language = ussdSessionMgmt.getUserProfile().getLanguage();
	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	    Locale locale = new Locale(language, countryCode);
	    String confirmLabel = ussdResourceBundle.getLabel(USSDConstants.LBL_CONFIRM, locale);

	    String NumLabel = ussdResourceBundle.getLabel(USSDConstants.NUMBER, locale);
	    String bankLabel= ussdResourceBundle.getLabel(USSDConstants.BANK, locale);
	    String amountLabel = ussdResourceBundle.getLabel(AMOUNT_LABEL, locale);
	    String reasonLabel= ussdResourceBundle.getLabel(USSDConstants.REASON, locale);
	    String currency= ussdResourceBundle.getLabel(CURRENCY, locale);
	    // CPB demo change
	    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL, locale);
	    String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
			    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
        String mbNum=null;
        String accNum=null;
        String bankName=null;
        String sortCode=null;
        String amount=null;
        String reason=null;
        int bankNameSequence=0;
        pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(NumLabel);

    	accNum = userInputMap.get(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUMBER.getParamName());
    	List<String> bankList=(List<String>)ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KITS_STA_BANK_CODE_LIST.getTranId());
    	bankNameSequence=Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_STA_BANK_CODE_LIST.getParamName()))-1;
    	bankName =bankList.get(bankNameSequence);
    	Map<String, String> bankDetailsMap = (Map<String, String>)ussdSessionMgmt.getTxSessions().get("BankDeatilsMap");
    	sortCode=bankDetailsMap.get(bankName);
    	ussdSessionMgmt.getTxSessions().put("SelectedBank", bankName);
    	ussdSessionMgmt.getTxSessions().put("SelectedBankSortCode", sortCode);
    	amount = userInputMap.get(USSDInputParamsEnum.KITS_STA_AMOUNT.getParamName());
    	reason = userInputMap.get(USSDInputParamsEnum.KITS_STA_REASON.getParamName());




    	pageBody.append(accNum);

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(bankLabel);

	    pageBody.append(bankName);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);

	    pageBody.append(currency);
	    //pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(amount);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(reasonLabel);

	    pageBody.append(reason);

	    // Pilot Migration check CBP  22/08
		/*ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(BMBContextHolder.getContext());
		listValueResServiceRequest.setListValueKey("PesaLink");
		ListValueResByGroupServiceResponse listResp = listValueResDAOImpl.findListValueResByKey(listValueResServiceRequest);
		pilotValue = listResp.getListValueCahceDTO().get(0).getLabel();*/

		//CPB change
		// Removing PilotValue check(pilotValue !=null && pilotValue.equalsIgnoreCase("Y")) its not going with Regulatory 6.0.0 changes - 02/11/2017
	  //CBP Change
	    BMGGlobalContext logContext = BMGContextHolder.getLogContext();

	    if(((logContext!=null && logContext.getContextMap().get("isCBPFLAG").equals("Y"))|| (logContext !=null && logContext.getBusinessId().equals("KEBRB"))) && cpbValidatePayData.getTransactionFeeAmount()!=null){

	    	if(cpbValidatePayData.getTransactionFeeAmount().getAmt()!=null){
	    		Double accumulatedCharge = 0.0;
	    		Double roundedAccumulatedVal = 0.0;
	    		Double tranFee = Double.valueOf(cpbValidatePayData.getTransactionFeeAmount().getAmt());
				Double taxAmt = cpbValidatePayData.getTaxAmount();
				if(tranFee!=null && taxAmt!=null){
					accumulatedCharge = tranFee + taxAmt;
					roundedAccumulatedVal =(double) Math.round(accumulatedCharge * 100) / 100;
				}

				//Commented for Kits Pesalink 7.0.0
				pageBody.append(USSDConstants.NEW_LINE);
			    pageBody.append(transactionFeeLabel);
				pageBody.append(cpbValidatePayData.getTransactionFeeAmount().getCurr());
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(roundedAccumulatedVal);

				// CPB change fields set for MakeBillPayemntRequest 09/05/2017
		    	ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "CpbMakeBillPaymentFields");
		    	ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", cpbValidatePayData.getTransactionFeeAmount());
		    	ussdSessionMgmt.getTxSessions().put("CpbFeeGLAccount", cpbValidatePayData.getFeeGLAccount());
		    	ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", cpbValidatePayData.getTaxAmount());
		    	ussdSessionMgmt.getTxSessions().put("CpbTaxGLAccount", cpbValidatePayData.getTaxGLAccount());
		    	ussdSessionMgmt.getTxSessions().put("CpbChargeNarration", cpbValidatePayData.getChargeNarration());
		    	ussdSessionMgmt.getTxSessions().put("CpbExciseDutyNarration", cpbValidatePayData.getExciseDutyNarration());
		    	ussdSessionMgmt.getTxSessions().put("CpbtypeCode", cpbValidatePayData.getTypeCode());
		    	ussdSessionMgmt.getTxSessions().put("CpbValue", cpbValidatePayData.getValue());
	    	}else{
	    		pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(xelerateOfflineLabel);

				// Xelerate Offline scenario - fields required by BEM
				ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "xelerateOffline");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", cpbValidatePayData.getTransactionFeeAmount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", cpbValidatePayData.getTaxAmount());
	    	}
	    }

	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }

	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageBody(pageBody.toString());
	    menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
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