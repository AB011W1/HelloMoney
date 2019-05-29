package com.barclays.ussd.bmg.groupwallet.response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.bmg.groupwallet.requests.GroupWalletGetAmountRequestBuilder;
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

public class FtGWGetAmountJsonParser implements BmgBaseJsonParser,SystemPreferenceValidator {
	@Autowired
	private ListValueResServiceImpl listValueResService;
	private static final Logger LOGGER = Logger.getLogger(GroupWalletGetAmountRequestBuilder.class);
	private static String LABEL_ENTER_AMOUNT="label.enter.amount";
	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
	}
	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		UserProfile profile = ussdSessionMgmt.getUserProfile();
		/*if(ussdSessionMgmt.getBusinessId().equals("GHBRB") && ussdSessionMgmt.isFreeDialUssdFlow()==true){
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setBackOptionReq("false");
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("false");
	}*/

		if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID) && userInput.length()>10){
			userInput="";
		}
		String mininimumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_MWALLET,
				SystemPreferenceConstants.MWALLET_MIN_AMT_SNDR);

		String maximumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_MWALLET,
				SystemPreferenceConstants.MWALLET_MAX_AMT_SNDR);

		USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(mininimumAmtSndr, maximumAmtSndr));
		USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
		try {
			backFlowValidator.validateAmount(userInput);//CR-86
			validator.validate(userInput);
		} catch (USSDNonBlockingException e) {
			LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
			//CR-86 Back flow changes
			e.setBackFlow(true);
			e.addErrorParam(userInput);
			e.addErrorParam(mininimumAmtSndr);
			e.addErrorParam(maximumAmtSndr);
			e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
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
			throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(), USSDExceptions.USSD_SYS_PREF_MISSING
					.getUssdErrorCode());
		}
		return listValueCacheDTO.getLabel();
	}
}