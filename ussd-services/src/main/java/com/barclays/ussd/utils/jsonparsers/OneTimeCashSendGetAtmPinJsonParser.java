package com.barclays.ussd.utils.jsonparsers;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.validation.USSDBackFlowValidator;

public class OneTimeCashSendGetAtmPinJsonParser implements BmgBaseJsonParser , SystemPreferenceValidator {//CR-86 back Flow changes

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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }

    //CR-86
	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException, USSDNonBlockingException {
		USSDBackFlowValidator validator = new USSDBackFlowValidator();

		try {
		    validator.validateCashSendATMPin(userInput, ussdSessionMgmt);
		} catch (USSDNonBlockingException e) {
		    // TODO
			e.setBackFlow(true);
			e.addErrorParam(userInput);
		    e.setErrorCode("USSD_INVALID_ATM_PIN");
		    throw e;
		}
		// TODO Auto-generated method stub

	}

}
