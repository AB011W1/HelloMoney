/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidateResponse;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;

/**
 * @author BTCI
 *
 */
public class AirtimeValidateResponseParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
    private static final String AMOUNT_LABEL = "label.att.amount";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final Logger LOGGER = Logger.getLogger(AirtimeValidateResponseParser.class);
	private static final String TRANSACTION_AIRTIME_LABEL = "label.airtime";
	private static final String DEBIACCNUM_LABEL = "label.debit.accnum";
	private static final String TRANSACTION_SERVICE_LABEL = "label.transaction.service";
	private static final String TRANSACTION_DATABUNDLE_LABEL = "label.databundle";
	
	//Databundle cancel label
	private static final String GHANA_DATABUNDLE_CANCEL = "label.databundle.cancel";

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    AirtimeValidateResponse airtimeValidateResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    AirtimeValidateResponse.class);

	    if (airtimeValidateResponse != null) {
		if (airtimeValidateResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(airtimeValidateResponse.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, airtimeValidateResponse.getPayData());
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(airtimeValidateResponse.getPayData().getTxnRefNo());
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.AIRTIME_CONFIRM.getTranId(), txnRefNo);
		} else if (airtimeValidateResponse.getPayHdr() != null
			&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(airtimeValidateResponse.getPayHdr().getResCde())
			&& !(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase("GHBRB") && 
					responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId().equals("ussd0.10"))) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TOP_UP_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
		} 
		//Ghana Data Bundle change
		else if(airtimeValidateResponse.getPayHdr() != null
				&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(airtimeValidateResponse.getPayHdr().getResCde())
				&& responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase("GHBRB") && 
				responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId().equals("ussd0.10")) {
			throw new USSDNonBlockingException(USSDExceptions.USSD_DATABUNDLE_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());			
		}
		else if (airtimeValidateResponse.getPayHdr() != null) {
		    LOGGER.fatal("unable to service: " + airtimeValidateResponse.getPayHdr().getResMsg());
		    throw new USSDNonBlockingException(airtimeValidateResponse.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing: " + airtimeValidateResponse.getPayHdr().getResMsg());
		    throw new USSDNonBlockingException(airtimeValidateResponse.getPayHdr().getResCde());
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
     * @param responseBuilderParamsDTO
     * @param airtimeValidatePayData
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, AirtimeValidatePayData airtimeValidatePayData) {
	MenuItemDTO menuItemDTO = null;
	if (airtimeValidatePayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    menuItemDTO = new MenuItemDTO();
	    StringBuilder pageBody = new StringBuilder();
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    String paramArray[]={userInputMap.get("txnAmt")};
		String airtimeTopupAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AIRTIME_LABEL, paramArray,
				new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
						responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
		
		//Data Bundle change for amount
		/*if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && 
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId().equals("ussd0.10"))
		{
			if(null != userInputMap.get("BUNDLE_AMOUNT"))	{
				airtimeTopupAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_DATABUNDLE_LABEL, 
						new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
								responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
				
				airtimeTopupAmountLabel = airtimeTopupAmountLabel + " " + userInputMap.get("BUNDLE_AMOUNT").toString();
			}
			
		 }*/

	    String language = ussdSessionMgmt.getUserProfile().getLanguage();
	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	    Locale locale = new Locale(language, countryCode);
	    String confirmLabel = ussdResourceBundle.getLabel(USSDConstants.LABEL_AIRTIME_CONFIRM, locale);
	    String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DEBIACCNUM_LABEL, locale);
	    String mobileNumLabel = ussdResourceBundle.getLabel(USSDConstants.USSD_TRANSACTION_MWALLETE_MOBILE, locale);
	    String airtimeServiceLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.transaction.service", locale);
	    userInputMap.put("BillerName", airtimeValidatePayData.getPrvder().getBillerName());
	    String mbNum = userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName());
	    Account account = airtimeValidatePayData.getSrcAcct();
	    //change for confirmation page Data Bundle
	    if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && 
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId().equals("ussd0.10")) {
	    	airtimeTopupAmountLabel = userInputMap.get("BillerName").toString() + " " + "Data Bundle for" + " " + mbNum + " " + userInputMap.get("BundleData").toString() + " " +
	    	userInputMap.get("BUNDLE_AMOUNT").toString()+"GHS "+userInputMap.get("BundleLife").toString();
	    	pageBody.append(airtimeTopupAmountLabel);
	    	
	    }else {
	    pageBody.append(airtimeTopupAmountLabel);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(mobileNumLabel);
	    
	    /*//CR82
		Payee payee=(Payee)ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId());
		String mAtWt=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
		if(mAtWt.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
			 mbNum = payee.getRefNo() ;
		}*/
	    pageBody.append(mbNum);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(airtimeServiceLabel);
	    pageBody.append(airtimeValidatePayData.getPrvder().getBillerName());
	    }
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(fromAccLabel);
	    pageBody.append(account.getMkdActNo());
	   

	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }
	    
	    //Ghana databundle change
	    if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && 
	    		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId().equals("ussd0.10")) {
	    	String cancelLabel = ussdResourceBundle.getLabel(GHANA_DATABUNDLE_CANCEL, locale);
	    	 if (!responseBuilderParamsDTO.isErrorneousPage()) {
	    			pageBody.append(USSDConstants.NEW_LINE);
	    			pageBody.append(cancelLabel);
	    		    }
	    }
	    
	    responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageBody(pageBody.toString());
	    menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    setNextScreenSequenceNumber(menuItemDTO);
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
    }

	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException {
		// TODO Auto-generated method stub
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN
				.getSequenceNo();
		String businessId = ussdSessionMgmt.getBusinessId();
		String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(userInput.equals("2")) {
			if(businessId.equalsIgnoreCase("GHBRB") && transNodeId.equals("ussd0.10"))
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYFIVE
				.getSequenceNo();			
		}
		return seqNo;
	}
}
