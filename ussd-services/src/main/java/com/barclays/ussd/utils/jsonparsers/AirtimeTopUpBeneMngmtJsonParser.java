package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
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

public class AirtimeTopUpBeneMngmtJsonParser implements BmgBaseJsonParser,
ScreenSequenceCustomizer {
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
						AIRTIME_OTH_NUM_NEWBNF,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append("2");
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(
						AIRTIME_OTH_NUM_EDITBNF,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append("3");
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(
						AIRTIME_OTH_NUM_DELBNF,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));

		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add("1");
		optionList.add("2");
		optionList.add("3");
		Map<String, Object> txSessions = responseBuilderParamsDTO
				.getUssdSessionMgmt().getTxSessions();
		txSessions.put(USSDInputParamsEnum.AIRTIME_TOPUP_BENE_MANAGEMENT
				.getTranId(), optionList);
		USSDUtils
				.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		menuItemDTO.setPageBody(pageBody.toString());

		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}
	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO
				.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE
						.getSequenceNo());

	}

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		Map<String, String> userInputMap = ussdSessionMgmt
		.getUserTransactionDetails().getUserInputMap();
		userInputMap.put(BillPaymentConstants.AT_MW_SAVED_BENEF,BillPaymentConstants.AT_MW_DEFAULT);//CR82
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE
				.getSequenceNo();

		if (userInput.equals("1")) {
			userInputMap.put(
					USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),
					ussdSessionMgmt.getMsisdnNumber());
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(
					userInputMap);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIFTEEN.getSequenceNo();
		}
		
		//DeleteBeneficary
		if (userInput.equals("3")) {
			userInputMap.put(
					USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),
					ussdSessionMgmt.getMsisdnNumber());
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(
					userInputMap);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
		}
		//EditBeneficary
		if (userInput.equals("2")) {
			userInputMap.put(
					USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),
					ussdSessionMgmt.getMsisdnNumber());
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(
					userInputMap);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
		}
		return seqNo;
	}
}
