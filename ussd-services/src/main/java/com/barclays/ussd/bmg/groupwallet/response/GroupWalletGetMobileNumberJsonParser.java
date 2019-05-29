package com.barclays.ussd.bmg.groupwallet.response;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMobileLengthValidator;

public class GroupWalletGetMobileNumberJsonParser implements BmgBaseJsonParser,SystemPreferenceValidator {
	@Autowired
	UssdMenuBuilder ussdMenuBuilder;

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYSEVEN.getSequenceNo());
	}
	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getBusinessId());
		String mobileLength = Integer.toString(msisdnDTO.getSnlen());

		USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMobileLengthValidator(mobileLength));
		try {
			validator.validate(userInput);
		} catch (USSDNonBlockingException e) {
			throw e;
		}
	}
}