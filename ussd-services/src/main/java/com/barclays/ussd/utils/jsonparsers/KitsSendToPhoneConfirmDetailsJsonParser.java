package com.barclays.ussd.utils.jsonparsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidateResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CpbPesaLinkValidate;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CpbPesaLinkValidatePayData;

@SuppressWarnings("unchecked")
public class KitsSendToPhoneConfirmDetailsJsonParser implements BmgBaseJsonParser {

    private static final String AMOUNT_LABEL = "label.att.amount";
    private static final String CURRENCY = "label.currency";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final Logger LOGGER = Logger.getLogger(KitsSendToPhoneConfirmDetailsJsonParser.class);
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
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
				    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
				} else if (cpbValidateObj.getPayHdr() != null) {
				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				    throw new USSDNonBlockingException(cpbValidateObj.getPayHdr().getResCde());
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
        String mbNum=null;
        String accNum=null;
        String bankName=null;
        String amount=null;
        String reason=null;
        String sortCode=null;




//        if (!responseBuilderParamsDTO.isErrorneousPage()) {
//    		pageBody.append(USSDConstants.NEW_LINE);
//    		pageBody.append(confirmLabel);
//    	    }

        //Added on 30/09/2016 by g01022861
        String  individualName=null;
        String namelabel=ussdResourceBundle.getLabel(USSDConstants.NAME, locale);

        int bankNameSequence=0;
        List<String> bankList=new ArrayList<String>();

        bankList=(List<String>)(((List) (ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KITS_STP_BANK_NAME.getTranId()))).get(1));
    	individualName=(String)(((List) (ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KITS_STP_BANK_NAME.getTranId()))).get(0));

    	pageBody.append(USSDConstants.NEW_LINE);
    	pageBody.append(namelabel);
	    pageBody.append(individualName);


        // Ended


        pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(NumLabel);

    	mbNum = userInputMap.get(USSDInputParamsEnum.KITS_STP_MOBILE_NUM.getParamName());
    	 //Added on 30/09/2016 by g01022861
    	// Ended
    	bankNameSequence=Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_STP_BANK_NAME.getParamName()))-1;
    	bankName =bankList.get(bankNameSequence);
    	Map<String, String> bankDetailsMap = (Map<String, String>)ussdSessionMgmt.getTxSessions().get("BankDeatilsMap");
    	sortCode=bankDetailsMap.get(bankName);
    	ussdSessionMgmt.getTxSessions().put("SelectedBank", bankName);
    	ussdSessionMgmt.getTxSessions().put("SelectedBankSortCode", sortCode);
    	amount = userInputMap.get(USSDInputParamsEnum.KITS_STP_AMOUNT.getParamName());
    	reason = userInputMap.get(USSDInputParamsEnum.KITS_STP_REASON.getParamName());
    	pageBody.append(mbNum);

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(bankLabel);

	    pageBody.append(bankName);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);

	    pageBody.append(currency);
	   // pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
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
	    if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB") && cpbValidatePayData.getTransactionFeeAmount()!=null){

		    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL, locale);
		    if(cpbValidatePayData.getTransactionFeeAmount().getAmt()!=null){

		    	Double accumulatedCharge = 0.0;
		    	Double roundedAccumulatedVal = 0.0;
	    		Double tranFee = Double.valueOf(cpbValidatePayData.getTransactionFeeAmount().getAmt());
				Double taxAmt = cpbValidatePayData.getTaxAmount();
				if(tranFee!=null && taxAmt!=null){
					accumulatedCharge = tranFee + taxAmt;
					roundedAccumulatedVal =(double) Math.round(accumulatedCharge * 100) / 100;
				}

		    	pageBody.append(USSDConstants.NEW_LINE);
			    pageBody.append(transactionFeeLabel);
			    pageBody.append(cpbValidatePayData.getTransactionFeeAmount().getCurr());
			    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(roundedAccumulatedVal);

				// CPB change fields set for MakeBillPayemntRequest 11/05/2017
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
		    	 String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
						    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    	pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(xelerateOfflineLabel);

				// Xelerate Offline scenario - fields required by BEM
				ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "xelerateOffline");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", cpbValidatePayData.getTransactionFeeAmount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", cpbValidatePayData.getTaxAmount());
		    }
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
    }
}
