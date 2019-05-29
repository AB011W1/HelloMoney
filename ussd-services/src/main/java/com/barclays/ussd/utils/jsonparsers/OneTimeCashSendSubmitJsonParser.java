package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OneTimeCashSendSubmit;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.OneTimeCashSendSubmitPayData;

public class OneTimeCashSendSubmitJsonParser implements BmgBaseJsonParser {
    private static final String CASHSEND_ID_LABEL = "label.cashSendId";
    private static final String AMOUNT_LABEL = "label.amount";
    private static final String MOBILE_NO_LABEL = "label.cashsend.beneficiary.mobNo";
    private static final String BENEFICIARY_PIN_LABEL = "label.cashsend.beneficiary.pin";
    private static final String CURRENCY_LABEL = "label.currency";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(OneTimeCashSendSubmitJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    OneTimeCashSendSubmit oneTimeCashSendSubmitObj = mapper.readValue(jsonString, OneTimeCashSendSubmit.class);
	    if (oneTimeCashSendSubmitObj != null) {
		if (oneTimeCashSendSubmitObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(oneTimeCashSendSubmitObj.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(oneTimeCashSendSubmitObj.getPayData(), responseBuilderParamsDTO);
		} else if (oneTimeCashSendSubmitObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(oneTimeCashSendSubmitObj.getPayHdr().getResCde());
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
     * @param oneTimeCashSendSubmitPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(OneTimeCashSendSubmitPayData oneTimeCashSendSubmitPayData,
	    ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	if (oneTimeCashSendSubmitPayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	    String cashSendIdLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CASHSEND_ID_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String mobilNoLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MOBILE_NO_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String beneficiaryPinLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(BENEFICIARY_PIN_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));



	    /*String cashSendAmt = userInputMap.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_AMOUNT.getParamName());

	    if (cashSendAmt != null && StringUtils.isNotEmpty(cashSendAmt)) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(amountLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(currency);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(cashSendAmt);
	    }*/



	    String mobileNo = userInputMap.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_MOBILE_NUM.getParamName());
	    // String mobileNo = oneTimeCashSendValidatePayData.getMobileNo();
	    if (mobileNo != null && StringUtils.isNotEmpty(mobileNo)) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(mobilNoLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		//modified for defect# 2196
		if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("BWBRB"))
		{       if (mobileNo.length() == 8) {
				    mobileNo = "0" + mobileNo;
				}

		}else{
				if (mobileNo.length() == 9) {
				    mobileNo = "0" + mobileNo;
				}
		}
		pageBody.append(mobileNo);
	    }

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(cashSendIdLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(oneTimeCashSendSubmitPayData.getVoucherId());

	    // Add beneficiaryPinLabel
	    String benfPin = userInputMap.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_REENTERED_ATM_PIN.getParamName());
	    if (benfPin != null && StringUtils.isNotEmpty(benfPin)) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(beneficiaryPinLabel);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(benfPin);
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }

}
