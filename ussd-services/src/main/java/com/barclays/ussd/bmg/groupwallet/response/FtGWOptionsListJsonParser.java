package com.barclays.ussd.bmg.groupwallet.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.NonPersonalAccountDetails;

public class FtGWOptionsListJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
	private static final String MOBILEWALLET_GROUPWALLET_ONEOFF = "label.groupwallet.oneoff";
	private static final String MOBILEWALLET_GROUPWALLET_CONFIRMTRAN = "label.groupwallet.confirmtransaction";
	private static final String MOBILEWALLET_GROUPWALLET_LASTPAIDBILL = "label.groupwallet.lastpaidbill";

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		NonPersonalAccountDetails nonPersonalAccountDetails=null;
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = responseBuilderParamsDTO.getJsonString();
		try {
			nonPersonalAccountDetails = mapper.readValue(jsonString, NonPersonalAccountDetails.class);
			responseBuilderParamsDTO.getUssdSessionMgmt().setCustaccountList(nonPersonalAccountDetails.getPayData().getNonPersonalCustAcctList());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
						MOBILEWALLET_GROUPWALLET_ONEOFF,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append("2");
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(
						MOBILEWALLET_GROUPWALLET_CONFIRMTRAN,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append("3");
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(
						MOBILEWALLET_GROUPWALLET_LASTPAIDBILL,
						new Locale(ussdSessionMgmt.getUserProfile()
								.getLanguage(), ussdSessionMgmt
								.getUserProfile().getCountryCode())));

		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add("1");
		optionList.add("2");
		optionList.add("3");

		Map<String, Object> txSessions = new HashMap<String,Object>();
		txSessions.put(USSDInputParamsEnum.MOBILE_WALLET_PAYMENT_TYPE
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
		.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYSIX
				.getSequenceNo());
	}
	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		// TODO Auto-generated method stub
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();

		if (userInput.equals("2")) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
		}
		if (userInput.equals("3")) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo();
		}
		return seqNo;
	}

}
