package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class MobileWalletTopUpSavBnfNickNameJsonParser implements BmgBaseJsonParser {
	@Autowired
    private ListValueResServiceImpl listValueResService;
	  /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AirtimeTopUpSavBnfNickNameJsonParser.class);
    private static final String AIRTIME_SAVE_BNF_NICK_NAME = "label.enter.savbnf.nickname";
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	//Added for max char message
	StringBuilder pageBody = new StringBuilder();
	UserProfile userProfile = responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile();
	try {
		String payNickNameMaxLength = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_BNF,
				SystemPreferenceConstants.PAYEE_NICKNAME_LENGTH_MAX);
		 List<String> params = new ArrayList<String>(1);
			params.add(payNickNameMaxLength);
			String[] paramArray = params.toArray(new String[params.size()]);
			String successMessage =  responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AIRTIME_SAVE_BNF_NICK_NAME, paramArray,
					new Locale(userProfile.getLanguage(), userProfile.getCountryCode()));
			pageBody.append(successMessage);
			menuItemDTO.setPageBody(pageBody.toString());
	} catch (USSDNonBlockingException e) {
		LOGGER.error("Exception : ", e);
	    //if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    /*} else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }*/
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo());

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
	//@Override
	/*public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {

		String isFromSaveBiller = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId());
		String isFromAirTimeTopUpSavBenf = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_SUBMIT.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_SUBMIT.getTranId());
		int nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
		*//**
		 * CR 73
		 *//*
		if(!isFromSaveBiller.equals(StringUtils.EMPTY) && isFromSaveBiller.equals(BillPaymentConstants.ONE_TIME_BILL_PAYMENT_SAVE_BILLER)){
			nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWELVE.getSequenceNo();
		}
		if(!isFromAirTimeTopUpSavBenf.equals(StringUtils.EMPTY) && isFromAirTimeTopUpSavBenf.equals(BillPaymentConstants.ONE_TIME_BILL_PAYMENT_SAVE_BILLER)){
			nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo();
		}
		return nextSequence;
	}*/

}
