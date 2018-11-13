package com.barclays.ussd.bmg.registerbenf.internal;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

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
import com.barclays.ussd.validation.USSDAccountNoLengthValidator;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;

public class RegisterBenfGetAccNoJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator, ScreenSequenceCustomizer {
    private static final String ACCT_NO_LEN = "ACCTN_NO_LEN";

    @Resource(name = "branchCodeCountryList")
    private List<String> branchCodeCountryList;

    @Autowired
    private UssdResourceBundle ussdResourceBundle;

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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	USSDCompositeValidator validator = null;

	String accountNoLen = ussdResourceBundle.getLabel(ACCT_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		.getUserProfile().getCountryCode()));
	validator = new USSDCompositeValidator(new USSDAccountNoLengthValidator(accountNoLen));
	USSDBackFlowValidator backFlowAccnoValidator = new USSDBackFlowValidator();//CR-86

	try {
		backFlowAccnoValidator.validateAccountNumber(userInput);//CR-86
	    validator.validate("" + userInput.length());
	} catch (USSDNonBlockingException e) {
		//CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.setErrorCode(USSDExceptions.USSD_USER_INPUT_INVALID_ACCNO_REGBENF.getUssdErrorCode());
	    throw e;
	}

    }
    //CR#48 implementation
    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	int seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo();
	if(businessId.equalsIgnoreCase("TZBRB"))
	{
		seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	}
	return seqNo;
    }
}
