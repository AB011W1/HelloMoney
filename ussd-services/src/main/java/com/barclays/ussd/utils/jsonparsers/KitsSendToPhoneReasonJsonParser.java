package com.barclays.ussd.utils.jsonparsers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.bmg.registerbenf.internal.RegisterBenfGetBenfNickNameJsonParser;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;
import com.barclays.ussd.validation.USSDMobileLengthValidator;

public class KitsSendToPhoneReasonJsonParser implements BmgBaseJsonParser,SystemPreferenceValidator {

	@Autowired
    private ListValueResServiceImpl listValueResService;

    private static final Logger LOGGER = Logger.getLogger(KitsSendToPhoneReasonJsonParser.class);

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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
    	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
    	String reasonMaxLength = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_KITS_REASON,
    			SystemPreferenceConstants.KITS_REASON_LENGTH_MAX);
    	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDLengthValidator(reasonMaxLength));
    	try {
    		validator.validate(userInput);
    	} catch (USSDNonBlockingException e) {
    		LOGGER.error(USSDExceptions.USSD_KITS_REASON_INVALID_LEN.getUssdErrorCode(), e);
    		e.addErrorParam(reasonMaxLength);
    		e.setErrorCode(USSDExceptions.USSD_KITS_REASON_INVALID_LEN.getUssdErrorCode());
    		throw e;
    	}
    }

    private String getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
    	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),
    			listValueKey);
    	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.findListValueResByGroupKey(listValReq);
    	ListValueCacheDTO listValueCacheDTO = listValueByGroup.getListValueCacheDTOOneRow();
    	if (listValueCacheDTO == null) {
    		LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
    		throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(),
    				USSDExceptions.USSD_SYS_PREF_MISSING.getUssdErrorCode());
    	}
    	return listValueCacheDTO.getLabel();
    }

}
