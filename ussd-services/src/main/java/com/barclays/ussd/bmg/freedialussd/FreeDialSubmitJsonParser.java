/**
 *
 */
package com.barclays.ussd.bmg.freedialussd;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

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
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeSubmitResponse;


public class FreeDialSubmitJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(FreeDialSubmitJsonParser.class);

    @Resource(name = "inprogressErrorCodeList")
    private List<String> inprogressErrorCodeList;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    AirtimeSubmitResponse airtimeSubmitResponse = mapper.readValue(jsonString, AirtimeSubmitResponse.class);
	   // airtimeSubmitResponse.getPayHdr().setResCde("00000");//mocked for testing
	    String txnRefNo = null;
	    String displayMessage = null;

	    if (airtimeSubmitResponse != null) {
		if (airtimeSubmitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(airtimeSubmitResponse.getPayHdr().getResCde())) {
		    txnRefNo = airtimeSubmitResponse.getPayData().getTxnRefNo();//mocked for testing
		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_AIRTIME_TOPUP_SUCCESS);
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage);

		} else if (isInProgressErrorCode(airtimeSubmitResponse.getPayHdr().getResCde())) {
		    txnRefNo = airtimeSubmitResponse.getPayHdr().getTxnRefNo();
		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_AIRTIME_TOPUP_INPROGRESS);
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage);

		} else if (airtimeSubmitResponse.getPayHdr() != null) {
			LOGGER.fatal("unable to service: " + airtimeSubmitResponse.getPayHdr().getResMsg());
			USSDSessionManagement session = responseBuilderParamsDTO.getUssdSessionMgmt();
			Map<String, Object> userInputMapAirtel = session.getTxSessions();
			if(userInputMapAirtel!=null && (userInputMapAirtel.get("extra").toString().equals("FREEDIALAIRTEL") || userInputMapAirtel.get("extra").toString().equals("FREEDIALAIRTELZM"))){
				throw new USSDBlockingException(airtimeSubmitResponse.getPayHdr().getResCde());
			}
			else
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
	    }if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    }  else {
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
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, String txnRefNo, String displayMessage) {
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


	pageBody.append(USSDConstants.NEW_LINE);

	pageBody.append(mblLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("mblNo"));
	pageBody.append(USSDConstants.NEW_LINE);

	pageBody.append(srvcLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("BillerName"));

	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_END);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	/*responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setBackOptionReq("TRUE");*/
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	setNextScreenSequenceNumber(menuItemDTO);
	responseBuilderParamsDTO.getUssdSessionMgmt().clean();
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
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
