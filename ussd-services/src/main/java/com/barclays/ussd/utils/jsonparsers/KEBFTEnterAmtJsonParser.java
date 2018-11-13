package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Locale;

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
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

public class KEBFTEnterAmtJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator {
	 /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(EBFTEnterAmtJsonParser.class);

	private static final String TRANSACTION_AMOUNT_LABEL = "label.enter.amount.DFT";

    @Autowired
    private ListValueResServiceImpl listValueResService;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = new MenuItemDTO();
	if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("MZBRB")){
		ArrayList<BeneData> benefList=(ArrayList<BeneData>)(responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("KEBAFT001"));
		int pos=Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("payId"));
		BeneData bd= benefList.get(pos-1);
		String bname=bd.getDisLbl();
		String[] paramArray ={bname};
		String fundTxAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AMOUNT_LABEL, paramArray ,
    			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
    					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
		menuDTO.setPageBody(fundTxAmountLabel);
	}
	menuDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
	menuDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile profile = ussdSessionMgmt.getUserProfile();

	String mininimumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_DFT,
		SystemPreferenceConstants.DFT_MIN_AMT_SNDR);
	String maximumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_DFT, SystemPreferenceConstants.DFT_MAX_AMT_SNDR);

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(mininimumAmtSndr, maximumAmtSndr));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	 try {
	  backFlowValidator.validateAmount(userInput);//CR-86
	  validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT.getUssdErrorCode(), e);
	    e.setBackFlow(true);
	    e.addErrorParam(userInput);
	    e.addErrorParam(mininimumAmtSndr);
	    e.addErrorParam(maximumAmtSndr);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
	    e.setErrorMsg(USSDConstants.USSD_BE_00001);
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
