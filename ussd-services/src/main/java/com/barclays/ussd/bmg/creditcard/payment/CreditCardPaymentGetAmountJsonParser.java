/**
 * BillPayEnterAmountJsonParser.java
 */
package com.barclays.ussd.bmg.creditcard.payment;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
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
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

/**
 * @author BTCI
 *
 */
public class CreditCardPaymentGetAmountJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator {

    private static final String SYSTEM_ID_UB = "UB";
    private static final String SERVICE_ENABLED_TRUE = "Y";
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private ListValueResServiceImpl listValueResService;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
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
	UserProfile profile = ussdSessionMgmt.getUserProfile();
	USSDCompositeValidator validator = null;
	String mininimumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_CCOWN, SystemPreferenceConstants.CCOWN_MIN_AMT);
	String maximumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_CCOWN, SystemPreferenceConstants.CCOWN_MAX_AMT);
	validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(mininimumAmtSndr, maximumAmtSndr));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAmount(userInput);//CR-86
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
		//CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(mininimumAmtSndr);
	    e.addErrorParam(maximumAmtSndr);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
	    throw e;
	}
    }

    protected String getSysParamValue(String businessId, String countryCode, String sysParamKey) {
	String paramValue = "";
	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
	systemParameterDTO.setBusinessId(businessId);
	systemParameterDTO.setSystemId(SYSTEM_ID_UB);
	systemParameterDTO.setActivityId(SystemParameterConstant.COMMON_ACTIVITY_ID);
	systemParameterDTO.setParameterId(sysParamKey);
	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
	SystemParameterServiceResponse response = systemParameterService.getSystemParameter(systemParameterServiceRequest);
	if (response != null && response.getSystemParameterDTO() != null) {
	    paramValue = response.getSystemParameterDTO().getParameterValue();
	} else {
	    paramValue = SERVICE_ENABLED_TRUE;
	}
	return paramValue;
    }

    private String getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),
		listValueKey);
	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.findListValueResByGroupKey(listValReq);
	ListValueCacheDTO listValueCacheDTO = listValueByGroup.getListValueCacheDTOOneRow();
	if (listValueCacheDTO == null) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(), USSDExceptions.USSD_SYS_PREF_MISSING
		    .getUssdErrorCode());
	}
	return listValueCacheDTO.getLabel();
    }
}
