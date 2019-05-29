/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
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
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidateResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class AirtimeEnterCreditCardValidateResponseParser implements BmgBaseJsonParser,ScreenSequenceCustomizer{
	  private static final String AMOUNT_LABEL = "label.att.amount";
	    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
	    private static final Logger LOGGER = Logger.getLogger(AirtimeEnterCreditCardValidateResponseParser.class);
		private static final String TRANSACTION_AIRTIME_LABEL = "label.airtime";
		private static final String DEBIACCNUM_LABEL = "label.creditcard";
		private static final String TRANSACTION_SERVICE_LABEL = "label.transaction.service";

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	MenuItemDTO menuDTO = null;
    	ObjectMapper mapper = new ObjectMapper();

    	try {
    	    AirtimeValidateResponse airtimeValidateResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
    		    AirtimeValidateResponse.class);


    	    if (airtimeValidateResponse != null ) {
    		if ((airtimeValidateResponse.getPayHdr() != null
    			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(airtimeValidateResponse.getPayHdr().getResCde())) ) {
    			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, airtimeValidateResponse);
    		    List<String> txnRefNo = new ArrayList<String>(1);
    		    txnRefNo.add(airtimeValidateResponse.getPayData().getTxnRefNo());
    		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.AIRTIME_CONFIRM.getTranId(), txnRefNo);
    		} else if (airtimeValidateResponse.getPayHdr() != null
    			&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(airtimeValidateResponse.getPayHdr().getResCde())) {
    		    throw new USSDNonBlockingException(USSDExceptions.USSD_TOP_UP_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
    		} else if (airtimeValidateResponse.getPayHdr() != null) {
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

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, AirtimeValidateResponse airtimeValidateResponse) {
    	MenuItemDTO menuItemDTO = null;
    	AirtimeValidatePayData airtimeValidatePayData=airtimeValidateResponse.getPayData();
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

    	    String language = ussdSessionMgmt.getUserProfile().getLanguage();
    	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
    	    Locale locale = new Locale(language, countryCode);
    	    String confirmLabel = ussdResourceBundle.getLabel(USSDConstants.LABEL_AIRTIME_CONFIRM, locale);
    	    String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DEBIACCNUM_LABEL, locale);
    	    String mobileNumLabel = ussdResourceBundle.getLabel(USSDConstants.USSD_TRANSACTION_MWALLETE_MOBILE, locale);
    	    String airtimeServiceLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.transaction.service", locale);
    	    userInputMap.put("BillerName", airtimeValidatePayData.getPrvder().getBillerName());

    	    CustomerMobileRegAcct cusstAccount=   (CustomerMobileRegAcct) airtimeValidatePayData.getCreditcardJsonModel();

    	    pageBody.append(airtimeTopupAmountLabel);
    	    pageBody.append(USSDConstants.NEW_LINE);
    	    pageBody.append(mobileNumLabel);
    	    String mbNum = userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName());
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

    	    pageBody.append(USSDConstants.NEW_LINE);
    	    pageBody.append(fromAccLabel);
    	   pageBody.append(cusstAccount.getMkdCrdNo());
    	  if (!responseBuilderParamsDTO.isErrorneousPage()) {
    		pageBody.append(USSDConstants.NEW_LINE);
    		pageBody.append(confirmLabel);
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
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		// TODO Auto-generated method stub
		return USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
	}

}
