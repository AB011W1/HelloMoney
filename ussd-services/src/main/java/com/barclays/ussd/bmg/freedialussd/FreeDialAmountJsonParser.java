/**
 *
 */
package com.barclays.ussd.bmg.freedialussd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bem.Account.Account;
import com.barclays.bmg.constants.BillPaymentConstants;
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
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

public class FreeDialAmountJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(FreeDialAmountJsonParser.class);

	private static final String TRANSACTION_AIRTIME_LABEL = "label.enter.the.airtime.amount";

	@Autowired
	private ListValueResServiceImpl listValueResService;

	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		return renderMenuOnScreen(responseBuilderParamsDTO);
	}

	@SuppressWarnings("unchecked")
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		StringBuffer pageBody=new StringBuffer();
		Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

		ArrayList<Biller> ar= (ArrayList<Biller>)responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("FDU001");
		String paramArray[]={(ar.get(Integer.parseInt(userInputMap.get("billerId"))-1)).getBillerName()};
		String airtimeAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AIRTIME_LABEL, paramArray,
				new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
						responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
		pageBody.append(airtimeAmountLabel);


		menuItemDTO.setPageBody(pageBody.toString());
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

	}


	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		UserProfile profile = ussdSessionMgmt.getUserProfile();
		// Code for replacing sys pref ref data code
		String minimumTopupAmt = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_TOPUP, SystemPreferenceConstants.MIN_TOPUP_AMT);
		String maximumTopupAmt = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_TOPUP, SystemPreferenceConstants.MAX_TOPUP_AMT);

		USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(minimumTopupAmt, maximumTopupAmt));
		USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
		try {
			backFlowValidator.validateAmount(userInput);//CR-86

			validator.validate(userInput);
		} catch (USSDNonBlockingException e) {
			//CR-86 Back flow changes
			e.setBackFlow(true);
			e.addErrorParam(userInput);
			e.addErrorParam(minimumTopupAmt);
			e.addErrorParam(maximumTopupAmt);
			LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
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
