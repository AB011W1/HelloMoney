/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeSubmitResponse;

/**
 * @author BTCI
 *
 */
public class AirtimeSubmitResponseParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(AirtimeSubmitResponseParser.class);

    @Resource(name = "inprogressErrorCodeList")
    private List<String> inprogressErrorCodeList;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	try {
	    AirtimeSubmitResponse airtimeSubmitResponse = mapper.readValue(jsonString, AirtimeSubmitResponse.class);
	    //airtimeSubmitResponse.getPayHdr().setResCde("00000");//mocked for testing
	    String txnRefNo = null;
	    String displayMessage = null;
	    String saveABenefeciaryLabel = null;//CR82

	    if (airtimeSubmitResponse != null) {
		if (airtimeSubmitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(airtimeSubmitResponse.getPayHdr().getResCde())) {
			//CR82
		    txnRefNo = airtimeSubmitResponse.getPayData().getTxnRefNo();//mocked for testing

		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_AIRTIME_TOPUP_SUCCESS);
		    //CR82
		    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		    String mAtWtSaveBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
		    String mWalletWon=userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER)== null?"":(String) userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER);
		    if(!(mWalletWon.equals(BillPaymentConstants.MWALLET_WON_NUMBER)|| mAtWtSaveBenf.equalsIgnoreCase(BillPaymentConstants.AT_MW_SAVED_BENEF)))
		    saveABenefeciaryLabel = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_OTBP_SAVE_BENEFECIARY);//CR82
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage, saveABenefeciaryLabel);

		} else if (isInProgressErrorCode(airtimeSubmitResponse.getPayHdr().getResCde())) {


		    txnRefNo = airtimeSubmitResponse.getPayHdr().getTxnRefNo();
		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_AIRTIME_TOPUP_INPROGRESS);
		    //CR82
		    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		    String mAtWtSaveBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
		    String mWalletWon=userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER)== null?"":(String) userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER);
		    if(!(mWalletWon.equals(BillPaymentConstants.MWALLET_WON_NUMBER)|| mAtWtSaveBenf.equalsIgnoreCase(BillPaymentConstants.AT_MW_SAVED_BENEF)))
		    saveABenefeciaryLabel = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_OTBP_SAVE_BENEFECIARY);//CR82
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage, saveABenefeciaryLabel);

		} else if (airtimeSubmitResponse.getPayHdr() != null) {
		    LOGGER.fatal("unable to service: " + airtimeSubmitResponse.getPayHdr().getResMsg());
		    throw new USSDNonBlockingException(airtimeSubmitResponse.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing: " + airtimeSubmitResponse.getPayHdr().getResMsg());
		    throw new USSDNonBlockingException(airtimeSubmitResponse.getPayHdr().getResCde());
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

    private boolean isInProgressErrorCode(String resCode) {
	boolean result = false;
	String bemResCode = null;
	if (resCode != null) {
	    bemResCode = resCode;
	    if (bemResCode.startsWith("BEM")) {
		bemResCode = bemResCode.substring(3);
	    }
	    return inprogressErrorCodeList.contains(bemResCode);
	}
	return result;
    }

    /**
     * @param airtimeSubmitPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, String txnRefNo, String displayMessage, String saveABenefeciaryLabel) {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	String txIdLabel = getLabel(responseBuilderParamsDTO, USSDConstants.TRAN_ID_LABEL_ID);
	pageBody.append(displayMessage);
	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(txIdLabel);
	pageBody.append(txnRefNo);

	String mblLabel=getLabel(responseBuilderParamsDTO, USSDConstants.USSD_TRANSACTION_MWALLETE_MOBILE);
	String srvcLabel=getLabel(responseBuilderParamsDTO, USSDConstants.USSD_TRANSACTION_MWALLETE_SERVICE);
	Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	/*pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(amtLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("txnAmt"));*/
	pageBody.append(USSDConstants.NEW_LINE);

	pageBody.append(mblLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("mblNo"));
	pageBody.append(USSDConstants.NEW_LINE);

	pageBody.append(srvcLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("BillerName"));

	//CR82 Start
	pageBody.append(USSDConstants.NEW_LINE);
	if(null != saveABenefeciaryLabel){
		pageBody.append(saveABenefeciaryLabel);
	}
	//End

	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);//CR82
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	setNextScreenSequenceNumber(menuItemDTO);
	//responseBuilderParamsDTO.getUssdSessionMgmt().clean();//CR82
	return menuItemDTO;
    }

    private String getLabel(ResponseBuilderParamsDTO responseBuilderParamsDTO, String label) {
	String labelValue = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	labelValue = ussdResourceBundle.getLabel(label, locale);
	return labelValue;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());
    }

    private String getTransactionRefNumber(Object refNumberList) {
	String refNumber = null;
	if (refNumberList != null) {
	    ArrayList<String> refList = (ArrayList<String>) refNumberList;
	    if (refList != null) {
		refNumber = refList.get(0);
		if (refNumber.contains("-")) {
		    refNumber = refNumber.substring(0, refNumber.indexOf("-"));
		}
	    }
	}
	return refNumber;

    }

}
