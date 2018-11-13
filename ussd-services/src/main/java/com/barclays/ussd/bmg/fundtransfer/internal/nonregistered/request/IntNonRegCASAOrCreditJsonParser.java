package com.barclays.ussd.bmg.fundtransfer.internal.nonregistered.request;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class IntNonRegCASAOrCreditJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

	private static final Logger LOGGER = Logger.getLogger(IntNonRegCASAOrCreditJsonParser.class);
	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		Map<String, Object> txSessions = responseBuilderParamsDTO
				.getUssdSessionMgmt().getTxSessions();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
				.getUssdSessionMgmt();
		String language = ussdSessionMgmt.getUserProfile().getLanguage();
		String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
		Locale locale = new Locale(language, countryCode);
		String TRANSACTION_CASA_ACCOUNT = responseBuilderParamsDTO
				.getUssdResourceBundle().getLabel("label.casaaccounts", locale);
		String TRANSACTION_CREDIT_CARD = responseBuilderParamsDTO
				.getUssdResourceBundle().getLabel("label.creditcards", locale);
		String TRANSACTION_LABEL = responseBuilderParamsDTO
				.getUssdResourceBundle().getLabel(
						"label.airtime.select.casacredit", locale);
		int accountIndex = 1;
		StringBuilder pageBody = new StringBuilder();
		pageBody.append(TRANSACTION_LABEL);
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(accountIndex++);
		pageBody.append(USSDConstants.DOT_SEPERATOR).append(
				TRANSACTION_CASA_ACCOUNT);
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(accountIndex++);
		pageBody.append(USSDConstants.DOT_SEPERATOR).append(
				TRANSACTION_CREDIT_CARD);
		pageBody.append(USSDConstants.NEW_LINE);

		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils
				.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.NEW_LINE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

		@Override
		public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO
				.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX
						.getSequenceNo());
	}


		public int getCustomNextScreen(String userInput,
		USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo();
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		if (txSessions.containsKey("CREDIT_CARD_TRAN")) {
			txSessions.remove("CREDIT_CARD_TRAN");
		}

		if (userInput.equalsIgnoreCase("2")) {

			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN
					.getSequenceNo();
			ussdSessionMgmt.getTxSessions().put("CREDIT_CARD_TRAN",
					"CREDIT_CARD_TRAN");
		}
		return seqNo;
	}

	}



