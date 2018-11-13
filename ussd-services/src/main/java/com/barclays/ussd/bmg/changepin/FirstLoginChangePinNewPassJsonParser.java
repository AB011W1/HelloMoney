package com.barclays.ussd.bmg.changepin;

import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class FirstLoginChangePinNewPassJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FirstLoginChangePinNewPassJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	if (StringUtils.equalsIgnoreCase(USSDConstants.USER_STATUS_MIGRATED, responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile()
		.getUsrSta())) {
	    // set new message for showing the user that the system has upgraded.
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	}
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	try {
	    if (ussdSessionMgmt.getFirstLoginOldPin().equals(userInput)
		    && StringUtils.equalsIgnoreCase(USSDConstants.USER_STATUS_MIGRATED, ussdSessionMgmt.getUserProfile().getUsrSta())) {
		throw new USSDNonBlockingException(USSDExceptions.THM_USER_SAME_OLD_NEW_PIN.getUssdErrorCode());
	    }
	} catch (USSDNonBlockingException e) {
	    // TODO
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug(USSDExceptions.THM_USER_SAME_OLD_NEW_PIN, e);
	    }
	    e.setErrorCode("THM_USER_SAME_OLD_NEW_PIN");
	    throw e;
	}
    }

}
