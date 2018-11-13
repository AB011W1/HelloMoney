package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

public class OLAFTGetAmountJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator {
    @Autowired
    private ListValueResServiceImpl listValueResService;

    private static final Logger LOGGER = Logger.getLogger(OLAFTGetAmountJsonParser.class);

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


	//Setting correct Payee List
	//skip below step for kadikope
	if(!"OwnLinkedAcctFundTxCredit".equals(responseBuilderParamsDTO.getUssdSessionMgmt()
			.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName())){

		ArrayList<AccountDetails> lstPayeeAcntsRevised = (ArrayList<AccountDetails>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("lstPayeeAcntsRevised");
		responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId(), lstPayeeAcntsRevised);
	}

	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	// Refer to sequencer.properties to set the below value
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
    }

    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	String sysPrefOwnFtMinOwn = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_FTOWN,
		SystemPreferenceConstants.FTOWN_MIN_AMT_SNDR);
	String sysPrefOwnFtMaxOwn = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_FTOWN,
		SystemPreferenceConstants.FTOWN_MAX_AMT_SNDR);

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(sysPrefOwnFtMinOwn, sysPrefOwnFtMaxOwn));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAmount(userInput);//CR-86
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
	    //CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(sysPrefOwnFtMinOwn);
	    e.addErrorParam(sysPrefOwnFtMaxOwn);
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
