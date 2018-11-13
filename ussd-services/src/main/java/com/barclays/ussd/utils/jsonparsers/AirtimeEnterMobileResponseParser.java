/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMobileLengthValidator;

/**
 * @author BTCI
 *
 */
public class AirtimeEnterMobileResponseParser implements BmgBaseJsonParser, SystemPreferenceValidator {
    @Autowired
    UssdMenuBuilder ussdMenuBuilder;
    @Autowired
	ComponentResDAOImpl componentResDAOImpl;

    private static final String TRANSACTION_AIRTIME_LABEL = "label.enter.mobnum";

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
    	USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
    	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
    	Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
    	String airtimeMobileNoLabel = ussdResourceBundle.getLabel(TRANSACTION_AIRTIME_LABEL, locale);
    	String ubpBusinessId=componentResDAOImpl.getUBPBusinessId();
    	/*if(ubpBusinessId.contains(ussdSessionMgmt.getBusinessId())){
	    	ArrayList<Biller> sBlr=(ArrayList<Biller>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("ATT001");
	    	int loc=Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("billerId"));
	    	airtimeMobileNoLabel = componentResDAOImpl.getBillerLabelByKey(sBlr.get(loc-1).getBillerId(), ussdSessionMgmt.getBusinessId(),locale.getLanguage());
    	}*/
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		menuItemDTO.setPageBody(airtimeMobileNoLabel);
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

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
