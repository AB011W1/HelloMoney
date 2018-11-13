package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

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
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.validation.USSDAccountNoLengthValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMobileLengthValidator;

public class KitsSendToAccountAccountNumJsonParser implements BmgBaseJsonParser,SystemPreferenceValidator
{

	    private static final String KITS_ACCTN_NO_LEN = "KITS_ACCTN_NO_LEN";
	    @Autowired
	    UssdMenuBuilder ussdMenuBuilder;
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

	    }



	    @Override
	    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	    	USSDCompositeValidator validator = null;

	    	String accountNoLen = ussdResourceBundle.getLabel(KITS_ACCTN_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
	    			.getUserProfile().getCountryCode()));
	    	//validator = new USSDCompositeValidator(new USSDAccountNoLengthValidator(accountNoLen));

//	    	try {
//	    		validator.validate("" + userInput.length());
//	    	} catch (USSDNonBlockingException e) {
//	    		e.setErrorCode(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
//	    		throw e;
//	    	}
	    	if(userInput.length()>Integer.parseInt(accountNoLen))
	    	{
	    		USSDNonBlockingException e= new USSDNonBlockingException();
	    		e.setErrorCode(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
	    		throw e;
	    	}

//	    	else if(Integer.parseInt(userInput)==Integer.parseInt((String)ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUMBER.getParamName())))
//	    	{
//	    		USSDNonBlockingException e= new USSDNonBlockingException();
//	    		e.setErrorCode(USSDExceptions.BEM08707.getUssdErrorCode());
//	    		throw e;
//	    	}




	    }
	}

