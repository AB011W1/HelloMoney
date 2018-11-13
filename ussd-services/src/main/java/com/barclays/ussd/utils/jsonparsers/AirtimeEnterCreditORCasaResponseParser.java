package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.FromAcntLst;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

public class AirtimeEnterCreditORCasaResponseParser implements BmgBaseJsonParser,ScreenSequenceCustomizer{
	 private static final Logger LOGGER = Logger.getLogger(AirtimeEnterAmountResponseParser.class);
 @Override
	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		return renderMenuOnScreen(responseBuilderParamsDTO);
	    }

	    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		 USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		 String language = ussdSessionMgmt.getUserProfile().getLanguage();
		    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
		Locale locale = new Locale(language, countryCode);
	   // String airtimeServiceLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.transaction.service", locale);
	 String TRANSACTION_AIRTIME_CASA_ACCOUNT = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.casaaccounts", locale);
	 String TRANSACTION_AIRTIME_CREDIT_CARD= responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.creditcards", locale);
     String TRANSACTION_AIRTIME_LABEL = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.airtime.select.casacredit", locale);

		int accountIndex = 1;
		StringBuilder pageBody = new StringBuilder();


		pageBody.append(TRANSACTION_AIRTIME_LABEL);

		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(accountIndex++);
		pageBody.append(USSDConstants.DOT_SEPERATOR).append(TRANSACTION_AIRTIME_CASA_ACCOUNT);
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(accountIndex++);
		pageBody.append(USSDConstants.DOT_SEPERATOR).append(TRANSACTION_AIRTIME_CREDIT_CARD);
		pageBody.append(USSDConstants.NEW_LINE);

		//CR82



		//responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.NEW_LINE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	    }

	    @Override
	    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {

		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
	    }

		@Override
		/*public void validate(String userInput,
				USSDSessionManagement ussdSessionMgmt)
				throws USSDBlockingException, USSDNonBlockingException {
			// TODO Auto-generated method stub

		}*/
		public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
			int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
			/*Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			List<Account> srcAccList = (List<Account>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getTranId());
			if (srcAccList != null && srcAccList.size() == 1) {
			    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
			    userInputMap.put(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
			    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
			    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo();*/


			if(userInput.equalsIgnoreCase("2"))
			{
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYTHREE.getSequenceNo();
            }
			return seqNo;
		    }

}
