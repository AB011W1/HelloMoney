/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;
import java.util.Map;

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
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMobileLengthValidator;

/**
 * @author BTCI
 *
 */
public class MobileWalletEnterMobileNewBeneficiaryResponseParser implements BmgBaseJsonParser, SystemPreferenceValidator,ScreenSequenceCustomizer {
    @Autowired
    UssdMenuBuilder ussdMenuBuilder;
    @Autowired
    SystemParameterService systemParameterService;
    private static final String TRANSACTION_AIRTIME_LABEL = "label.enter.new.beneficiary.mobnum";

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
    	USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
    	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
    	Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
    	String airtimeMobileNoLabel = ussdResourceBundle.getLabel(TRANSACTION_AIRTIME_LABEL, locale);
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	menuItemDTO.setPageBody(airtimeMobileNoLabel);
	//ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());

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
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN
				.getSequenceNo();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		
		//TZNBC Menu Optimization
		String paymentTypeInput=null;
		if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")) 
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_BENE_MANAGEMENT.getParamName());
		//Ghana Menu Optimization
		else if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB"))
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_MSISDN_TYPE.getParamName());
		else 		
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_PAYMENT_TYPE.getParamName());
		
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
		//Ghana Menu Optimization - payment input changed
		if (USSDConstants.BUSINESS_ID_GHBRB.equalsIgnoreCase(ussdSessionMgmt.getBusinessId()) && paymentTypeInput.equals("5") && isGHIPS2Flag.equals("Y"))  {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOURTY.getSequenceNo();
		}
		else if(null!=paymentTypeInput && ((ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("2")) || (!ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && paymentTypeInput.equals("4"))) )
		{
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTY.getSequenceNo();
		}
		if(USSDConstants.BUSINESS_ID_GHBRB.equalsIgnoreCase(ussdSessionMgmt.getBusinessId()) && paymentTypeInput.equals("4") && isGHIPS2Flag.equals("Y") )
		{
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOURTYTWO.getSequenceNo();
		}
		return seqNo;
	}
}