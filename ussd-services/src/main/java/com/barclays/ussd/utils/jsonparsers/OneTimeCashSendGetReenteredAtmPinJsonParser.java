package com.barclays.ussd.utils.jsonparsers;

import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDChangePasswordValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;

public class OneTimeCashSendGetReenteredAtmPinJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator {
    private static final Logger LOGGER = Logger.getLogger(OneTimeCashSendGetReenteredAtmPinJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = null;
	menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDChangePasswordValidator(userInputMap
		.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_ATM_PIN.getParamName()), userInput));
	USSDBackFlowValidator backFlowvalidator = new USSDBackFlowValidator();
	try {
		backFlowvalidator.validateCashSendATMPin(userInput, ussdSessionMgmt);
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    // TODO
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug(USSDExceptions.USSD_REENTERED_ATM_PIN_NT_MTCH, e);
	    }
	    e.setBackFlow(true);
	    e.addErrorParam(userInput);//CR-86
	    e.setErrorCode("USSD_REENTERED_ATM_PIN_NT_MTCH");
	    throw e;
	}

    }
}
