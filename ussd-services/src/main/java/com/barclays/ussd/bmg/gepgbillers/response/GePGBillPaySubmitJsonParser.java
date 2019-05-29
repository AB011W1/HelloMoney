/**
 * BillPayConfirmJsonParser.java
 */
package com.barclays.ussd.bmg.gepgbillers.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillPayConfirm;

/**
 * @author BTCI
 *
 */
public class GePGBillPaySubmitJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GePGBillPaySubmitJsonParser.class);
    @Resource(name = "inprogressErrorCodeList")
    private List<String> inprogressErrorCodeList;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {

		    BillPayConfirm billPayConfirm = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillPayConfirm.class);
		    String txnRefNo = null;
		    String displayMessage = null;
		    String rcptNumber = null;
		    if (null != billPayConfirm) {
				if (null != billPayConfirm.getPayHdr()
					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(billPayConfirm.getPayHdr().getResCde())) {
				    if (null != billPayConfirm.getPayData()) {
						txnRefNo = billPayConfirm.getPayData().getTxnRefNo();
					    rcptNumber = billPayConfirm.getPayData().getRcptNo();
				    }
				    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.PAY_BILL_CONFIRM_LBL);
				    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage, rcptNumber);
				} else if (isInProgressErrorCode(billPayConfirm.getPayHdr().getResCde())) {
				    txnRefNo = billPayConfirm.getPayHdr().getTxnRefNo();
				    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.PAY_BILL_INPROCESS_LBL);
				    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage, rcptNumber);

				} else if (null != billPayConfirm.getPayHdr()) {
				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				    throw new USSDNonBlockingException(billPayConfirm.getPayHdr().getResCde());
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
		if (null != resCode) {
		    bemResCode = resCode;
		    if (bemResCode.startsWith("BEM")) {
		    	bemResCode = bemResCode.substring(3);
		    }
		    return inprogressErrorCodeList.contains(bemResCode);
		}
		return result;
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, String txnRefNo, String displayMessage, String rcptNumber) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		pageBody.append(displayMessage);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(txnRefNo);
		if(null != rcptNumber || "".equalsIgnoreCase(rcptNumber)){
			pageBody.append("Reciept No: ").append(rcptNumber);
		}
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_END);
		menuItemDTO.setPaginationType(PaginationEnum.SPACED);
		responseBuilderParamsDTO.getUssdSessionMgmt().clean();
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

    @SuppressWarnings({ "unused", "unchecked" })
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