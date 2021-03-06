package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class AirtimeTopupMsisdnTypeJsonParser implements BmgBaseJsonParser,
ScreenSequenceCustomizer {
	private static final String AIRTIME_OWN_NUMBER = "label.own.number";
	private static final String AIRTIME_OTHER_NUMBER = "label.other.number";
	private static final String AIRTIME_OTH_NUM_SAVDBNF = "label.other.number.savdbnf";
	private static final String AIRTIME_OTH_NUM_NEWBNF = "label.other.number.newbnf";
	private static final String AIRTIME_OTH_NUM_EDITBNF = "label.other.number.editbnf";
	private static final String AIRTIME_OTH_NUM_DELBNF = "label.other.number.delbnf";

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
					throws USSDBlockingException, USSDNonBlockingException {
		return renderMenuOnScreen(responseBuilderParamsDTO);
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
				.getUssdSessionMgmt();
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append("1");
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(
						AIRTIME_OWN_NUMBER,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append("2");
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(
						AIRTIME_OTHER_NUMBER,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));
		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add("1");
		optionList.add("2");

		//Ghana Menu Optimization - Including Bene Management with Own and Other Number	
		String businessId=responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId();
		String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(null!=businessId && businessId.equalsIgnoreCase("GHBRB") && null!=transNodeId && !transNodeId.equalsIgnoreCase("ussd0.10")) {

			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("3");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AIRTIME_OTH_NUM_SAVDBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("4");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AIRTIME_OTH_NUM_NEWBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("5");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AIRTIME_OTH_NUM_EDITBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("6");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AIRTIME_OTH_NUM_DELBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));

			optionList.add("3");
			optionList.add("4");
			optionList.add("5");
			optionList.add("6");
		}

		//Ghana Data bundle change
		Map<String, Object> txSessions = new HashMap<String, Object>();			
		if(null != responseBuilderParamsDTO
				.getUssdSessionMgmt().getTxSessions())
		{
			txSessions = responseBuilderParamsDTO
					.getUssdSessionMgmt().getTxSessions();
		}

		txSessions.put(USSDInputParamsEnum.AIRTIME_TOPUP_MSISDN_TYPE
				.getTranId(), optionList);
		USSDUtils
		.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		menuItemDTO.setPageBody(pageBody.toString());

		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO
		.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE
				.getSequenceNo());

	}

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();

		//Ghana Data bundle change
		String businessId = ussdSessionMgmt.getBusinessId();
		String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();

		if(businessId.equalsIgnoreCase("GHBRB") && transNodeId.equals("ussd0.10")) {
			if (userInput.equals("1")) {
				Map<String, String> userInputMap = ussdSessionMgmt
						.getUserTransactionDetails().getUserInputMap();
				userInputMap.put(
						USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),
						ussdSessionMgmt.getMsisdnNumber());
				userInputMap.put(BillPaymentConstants.MWALLET_WON_NUMBER,BillPaymentConstants.MWALLET_WON_NUMBER);//CR82
				userInputMap.put("TransNodeId", "ussd0.10GHBRB");
				userInputMap.put("DataBundle", "DataBundleOWN");
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(
						userInputMap);
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ONE.getSequenceNo();
			}
			else
			{
				Map<String, String> userInputMap = ussdSessionMgmt
						.getUserTransactionDetails().getUserInputMap();
				userInputMap.put("DataBundle", "DataBundleOther");
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(
						userInputMap);
				seqNo =  USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();
			}

		}
		else
		{
			if (userInput.equals("1")) {
				Map<String, String> userInputMap = ussdSessionMgmt
						.getUserTransactionDetails().getUserInputMap();
				userInputMap.put(
						USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),
						ussdSessionMgmt.getMsisdnNumber());
				userInputMap.put(BillPaymentConstants.MWALLET_WON_NUMBER,BillPaymentConstants.MWALLET_WON_NUMBER);//CR82
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(
						userInputMap);
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
			}
		}
		//TZNBC Menu Optimization
		//Ghana Menu Optimization - to include one off transaction on selecting other number
		if((businessId.equalsIgnoreCase("TZNBC") || (businessId.equalsIgnoreCase("GHBRB") && !transNodeId.equalsIgnoreCase("ussd0.10"))) && userInput.equals("2"))
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();

		//Ghana Menu Optimization - bene management flow
		if(businessId.equalsIgnoreCase("GHBRB") && !transNodeId.equalsIgnoreCase("ussd0.10")) {

			Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			//new beneficiary
			if (userInput.equals("4")) {
				userInputMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),ussdSessionMgmt.getMsisdnNumber());
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIFTEEN.getSequenceNo();
			}
			//saved beneficiary
			if (userInput.equals("3")) {
				userInputMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),ussdSessionMgmt.getMsisdnNumber());
				userInputMap.put(BillPaymentConstants.AT_MW_SAVED_BENEF,BillPaymentConstants.AT_MW_SAVED_BENEF);//CR82
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
			}
			//DeleteBeneficary
			if (userInput.equals("6")) {
				userInputMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),ussdSessionMgmt.getMsisdnNumber());
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
			}
			//EditBeneficary
			if (userInput.equals("5")) {
				userInputMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),ussdSessionMgmt.getMsisdnNumber());
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
			}
		}
		return seqNo;
	}

}
