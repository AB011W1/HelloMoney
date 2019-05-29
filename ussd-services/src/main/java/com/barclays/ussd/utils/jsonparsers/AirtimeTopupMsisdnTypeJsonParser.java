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

public class AirtimeTopupMsisdnTypeJsonParser implements BmgBaseJsonParser,
		ScreenSequenceCustomizer {
	private static final String AIRTIME_OWN_NUMBER = "label.own.number";
	private static final String AIRTIME_OTHER_NUMBER = "label.other.number";

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
		Map<String, Object> txSessions = responseBuilderParamsDTO
				.getUssdSessionMgmt().getTxSessions();
		txSessions.put(USSDInputParamsEnum.AIRTIME_TOPUP_MSISDN_TYPE
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
				.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE
						.getSequenceNo());

	}

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE
				.getSequenceNo();

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
		return seqNo;
	}

}
