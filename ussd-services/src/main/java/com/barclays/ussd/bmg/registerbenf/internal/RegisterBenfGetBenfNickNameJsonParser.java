package com.barclays.ussd.bmg.registerbenf.internal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.FundTransferConstants;
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
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;

public class RegisterBenfGetBenfNickNameJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator, ScreenSequenceCustomizer {
    @Autowired
    private ListValueResServiceImpl listValueResService;

    private static final Logger LOGGER = Logger.getLogger(RegisterBenfGetBenfNickNameJsonParser.class);

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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	String payNickNameMaxLength = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_BNF,
		SystemPreferenceConstants.PAYEE_NICKNAME_LENGTH_MAX);
	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDLengthValidator(payNickNameMaxLength));
	try {
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode(), e);
	    e.addErrorParam(payNickNameMaxLength);
	    e.setErrorCode(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode());
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

    /**
	 * CR 73
	 */
	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		String isFromSaveBeneficiary = StringUtils.EMPTY;
		if(ussdSessionMgmt.getTxSessions()!= null){
			isFromSaveBeneficiary = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId())== null?StringUtils.EMPTY:(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId());
		}
		int nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();

		if(!isFromSaveBeneficiary.equals(StringUtils.EMPTY) && isFromSaveBeneficiary.equals(FundTransferConstants.FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BILLER)){
			nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo();
		}
		return nextSequence;
	}
}
