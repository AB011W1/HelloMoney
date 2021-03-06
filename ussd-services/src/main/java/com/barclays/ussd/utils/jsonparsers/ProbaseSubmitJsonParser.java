package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
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
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPSubmitResponse;

public class ProbaseSubmitJsonParser  implements BmgBaseJsonParser  {

    private static final Logger LOGGER = Logger.getLogger(OneTimeBillPaySubmitJsonParser.class);
    @Resource(name = "inprogressErrorCodeList")
    private List<String> inprogressErrorCodeList;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	try {
		if(txSessions == null){
			txSessions = new HashMap<String, Object>();
		}
	    OTBPSubmitResponse otbpSubmitResponse = mapper.readValue(jsonString, OTBPSubmitResponse.class);
	    String txnNo = null;
	    String token = null;
	    String displayMessage = null;
	    if (otbpSubmitResponse != null) {
		if (otbpSubmitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(otbpSubmitResponse.getPayHdr().getResCde())) {

			//CR73
			txSessions.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId(),BillPaymentConstants.ONE_TIME_BILL_PAYMENT_SAVE_BILLER);
			ussdSessionMgmt.setTxSessions(txSessions);

		    if (otbpSubmitResponse.getPayData() != null) {
				txnNo = otbpSubmitResponse.getPayData().getTxnRefNo();
			    token = otbpSubmitResponse.getPayData().getToken();
		    }
		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_OTBP_SUCCESS);
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnNo, token, displayMessage);

		} else if (isInProgressErrorCode(otbpSubmitResponse.getPayHdr().getResCde())) {

			//CR73
			txSessions.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId(),BillPaymentConstants.ONE_TIME_BILL_PAYMENT_SAVE_BILLER);
			ussdSessionMgmt.setTxSessions(txSessions);

		    txnNo = otbpSubmitResponse.getPayHdr().getTxnRefNo();
		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_OTBP_INPROGRESS);
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnNo, token, displayMessage);
		} else if (otbpSubmitResponse.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(otbpSubmitResponse.getPayHdr().getResCde());
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
     * @param otbpSubmitPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, String txnRefNo, String token, String displayMessage) {

    StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	pageBody.append(displayMessage).append(USSDConstants.NEW_LINE);
	pageBody.append(txnRefNo).append(USSDConstants.NEW_LINE);
	if(null != token && !"".equalsIgnoreCase(token)){
		pageBody.append("Token: ").append(token).append(USSDConstants.NEW_LINE);
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
//	responseBuilderParamsDTO.getUssdSessionMgmt().clean();
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
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
}