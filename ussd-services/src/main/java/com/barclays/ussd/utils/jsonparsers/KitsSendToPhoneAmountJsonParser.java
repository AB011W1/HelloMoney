package com.barclays.ussd.utils.jsonparsers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class KitsSendToPhoneAmountJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator{

    @Autowired
    private ListValueResServiceImpl listValueResService;

    private static final Logger LOGGER = Logger.getLogger(KitsSendToPhoneAmountJsonParser.class);

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
	StringBuilder pageBody = new StringBuilder();
	pageBody.append(USSDConstants.NEW_LINE);
    pageBody.append("Note: Decimals are not allowed.");
    menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
    }

    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	String sysPrefKitsMinAmt=null;
	String sysPrefKitsMaxAmt=null;

	sysPrefKitsMinAmt = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_KITS_PTP,
	SystemPreferenceConstants.MIN_KITS_PTP_AMT);
	sysPrefKitsMaxAmt = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_KITS_PTP,
	SystemPreferenceConstants.MAX_KITS_PTP_AMT);


	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(sysPrefKitsMinAmt, sysPrefKitsMaxAmt));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAmount(userInput);//CR-86
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
	    //CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(sysPrefKitsMinAmt);
	    e.addErrorParam(sysPrefKitsMaxAmt);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
	    e.setKitsFlow(true);
	    throw e;
	}

	// End Fix Fix Fix
	Map<String, String> userInputMap=ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<AccountDetails> accList= (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KITS_STP_ACCOUNT_NUM.getTranId());
    int selectedAccSeq=Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_STP_ACCOUNT_NUM.getParamName()))-1;
    AccountDetails acc=accList.get(selectedAccSeq);

//    try {

		if(!(!acc.getAvblBal().getAmt().isEmpty()&& new BigDecimal(acc.getAvblBal().getAmt()).longValue()>Long.parseLong(userInput)))
		{

			USSDNonBlockingException e= new USSDNonBlockingException();
			LOGGER.error(USSDExceptions.BPY00614.getUssdErrorCode(), e);
		    e.addErrorParam(sysPrefKitsMinAmt);
		    e.addErrorParam(sysPrefKitsMaxAmt);
		    e.setErrorCode(USSDExceptions.BPY00614.getUssdErrorCode());
		    e.setKitsFlow(true);
		    throw e;
		}
//		    } catch (NumberFormatException e) {
//		USSDNonBlockingException eNumber= new USSDNonBlockingException();
//		LOGGER.error(USSDExceptions.BPY00614.getUssdErrorCode(), e);
//		eNumber.addErrorParam(sysPrefKitsMinAmt);
//		eNumber.addErrorParam(sysPrefKitsMaxAmt);
//		eNumber.setErrorCode(USSDExceptions.BPY00614.getUssdErrorCode());
//	    throw eNumber;}

	// End Fix Fix Fix

    }

    private String getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),listValueKey);
	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.findListValueResByGroupKey(listValReq);
	ListValueCacheDTO listValueCacheDTO = listValueByGroup.getListValueCacheDTOOneRow();
	if (listValueCacheDTO == null) {
	    LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(), USSDExceptions.USSD_SYS_PREF_MISSING
		    .getUssdErrorCode(),true);
	}
	return listValueCacheDTO.getLabel();
    }



}
