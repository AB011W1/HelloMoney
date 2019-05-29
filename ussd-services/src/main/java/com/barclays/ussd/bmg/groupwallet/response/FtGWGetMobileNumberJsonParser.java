package com.barclays.ussd.bmg.groupwallet.response;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.validation.USSDAccountNoLengthValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;

public class FtGWGetMobileNumberJsonParser implements BmgBaseJsonParser , SystemPreferenceValidator {
	private static final Logger LOGGER = Logger.getLogger(FtGWGetMobileNumberJsonParser.class);

	 private static final String ACCT_NO_LEN = "ACCTN_NO_LEN";

	 @Autowired
	 private UssdResourceBundle ussdResourceBundle;

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
	}

	@Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	USSDCompositeValidator validator = null;

	String accountNoLen = ussdResourceBundle.getLabel(ACCT_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		.getUserProfile().getCountryCode()));
	validator = new USSDCompositeValidator(new USSDAccountNoLengthValidator(accountNoLen));

	try {
	    validator.validate("" + userInput.length());
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode(), e);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
	    throw e;
	}

    }
}
