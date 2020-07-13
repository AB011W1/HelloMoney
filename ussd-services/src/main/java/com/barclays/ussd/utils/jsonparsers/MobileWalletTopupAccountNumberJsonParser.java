package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.MsisdnDTO;
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
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMobileLengthValidator;

public class MobileWalletTopupAccountNumberJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator, ScreenSequenceCustomizer {
    @Autowired
    UssdMenuBuilder ussdMenuBuilder;
    @Autowired
    SystemParameterService systemParameterService;
    private static final String TRANSACTION_MWALLETE_LABEL = "label.enter.mwallet.mobnum";
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
    USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	String mwalleteLabel = ussdResourceBundle.getLabel(TRANSACTION_MWALLETE_LABEL, locale);

	MenuItemDTO menuItemDTO = new MenuItemDTO();
	menuItemDTO.setPageBody(mwalleteLabel);
	//ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	// Mobile Number Validation
	int userInputNumber = 0;
	String provider = null;
	if(ussdSessionMgmt.getUserTransactionDetails() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().containsKey("billerId"))
		userInputNumber  = Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("billerId"));
	if(ussdSessionMgmt.getTxSessions() != null && ussdSessionMgmt.getTxSessions().containsKey("MWTU001") && userInputNumber != 0 && !((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).isEmpty() )
	provider = ((MobileWalletProvider) ((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).get(userInputNumber - 1)).getBillerId();

	if(ussdSessionMgmt.getBusinessId().equals("UGBRB") && provider.contains(ussdSessionMgmt.getMobileValidationBiller()) && ussdSessionMgmt.isMobileValidation())
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUNCE_NUMBER_FOURTYFIVE.getSequenceNo());
	else
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	//CR#47
	//menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
    	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getBusinessId());
	String mobileLength = Integer.toString(msisdnDTO.getSnlen());

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMobileLengthValidator(mobileLength));
	try {
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
		//Mobile wallet B2W : error msg missing
		e.setErrorCode(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
		throw e;
	}

    }
    @Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {

		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
		int userInputNumber = 0;
		String provider = null;
		if(ussdSessionMgmt.getUserTransactionDetails() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().containsKey("billerId"))
			userInputNumber  = Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("billerId"));
		if(ussdSessionMgmt.getTxSessions() != null && ussdSessionMgmt.getTxSessions().containsKey("MWTU001") && userInputNumber != 0 && !((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).isEmpty() )
		provider = ((MobileWalletProvider) ((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).get(userInputNumber - 1)).getBillerId();

		if(ussdSessionMgmt.getBusinessId().equals("UGBRB") && provider.contains(ussdSessionMgmt.getMobileValidationBiller()) && ussdSessionMgmt.isMobileValidation())
		seqNo=USSDSequenceNumberEnum.SEQUNCE_NUMBER_FOURTYFIVE.getSequenceNo();
		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
    	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
    	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
    	systemParameterDTO.setBusinessId(BMBContextHolder.getContext().getBusinessId().toString());
    	systemParameterDTO.setSystemId("UB");
    	systemParameterDTO.setParameterId("isGHIPS2Flag");
    	String isGHIPS2Flag="";
		SystemParameterServiceResponse response = systemParameterService.getStatusParameter(systemParameterServiceRequest);
		if(response!=null && response.getSystemParameterDTO()!=null && response.getSystemParameterDTO().getParameterValue()!=null)
			isGHIPS2Flag = response.getSystemParameterDTO().getParameterValue();
		if(USSDConstants.BUSINESS_ID_GHBRB.equalsIgnoreCase(ussdSessionMgmt.getBusinessId()) && isGHIPS2Flag.equals("Y"))
		{
			seqNo=(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTYNINE.getSequenceNo());
		}
		return seqNo;
    }
}
