package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.validation.USSDCompositeValidator;

public class KitsSendToAccountBankCodeJsonParser implements BmgBaseJsonParser,SystemPreferenceValidator {


	    private static final Logger LOGGER = Logger.getLogger(KitsSendToAccountBankCodeJsonParser.class);
	    @Autowired
	    private UssdResourceBundle ussdResourceBundle;
	    String KITS_BANKNAME_FA_LEN="KITS_BANKNAME_FA_LEN";


	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
	    }

	    @Override
	    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	    	String accountNoLen = ussdResourceBundle.getLabel(KITS_BANKNAME_FA_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
	    			.getUserProfile().getCountryCode()));
	    	if(userInput.length()>Integer.parseInt(accountNoLen))
	    	{
	    		USSDNonBlockingException e= new USSDNonBlockingException();
	    		e.setErrorCode(USSDExceptions.USSD_BANK_CODE_LIST_INVALID.getUssdErrorCode());
	    		throw e;
	    	}
	    	if(StringUtils.isNumericSpace(userInput))
	    	{
	    		USSDNonBlockingException e= new USSDNonBlockingException();
	    		e.setErrorCode(USSDExceptions.USSD_BANK_CODE_LIST_INVALID.getUssdErrorCode());
	    		throw e;
	    	}
	    }




}





