package com.barclays.ussd.bmg.fundtransfer.internal.editbenf;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;

public class EditBenfInternalGetAccNoJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator, ScreenSequenceCustomizer {

    private static final String ACCT_NO_LEN = "ACCTN_NO_LEN";
    @Resource(name = "branchCodeCountryList")
    private List<String> branchCodeCountryList;

    @Autowired
    private UssdResourceBundle ussdResourceBundle;

    private static final Logger LOGGER = Logger.getLogger(EditBenfInternalGetAccNoJsonParser.class);

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
	// Refer to sequencer.properties to set the below value
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	USSDCompositeValidator validator = null;

	String accountNoLen = ussdResourceBundle.getLabel(ACCT_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		.getUserProfile().getCountryCode()));
	validator = new USSDCompositeValidator(new USSDLengthValidator(accountNoLen));

	try {
	    validator.validate("" + userInput.length());
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode(), e);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
	    throw e;
	}
    }

    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
	if (businessId.equalsIgnoreCase("TZBRB")) {
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	}
	return seqNo;
    }

}
