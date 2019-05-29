package com.barclays.ussd.bmg.groupwallet.response;

import java.util.List;
import java.util.Locale;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CASAccountTransactionList;

public class ViewHistoryJsonParser implements BmgBaseJsonParser {
	private static String LABEL_BILL_DETAILS = "label.bill.details";
	private static String LABEL_BILLERNAME = "label.billername";
	private static String LBLVLPBBILLAMT = "LBLVLPBBILLAMT";
	private static String LBLVLPBPAYDATE = "LBLVLPBPAYDATE";

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		String billDetails= getLabel(responseBuilderParamsDTO, LABEL_BILL_DETAILS);
		String billerName= getLabel(responseBuilderParamsDTO, LABEL_BILLERNAME);
		String billAmount= getLabel(responseBuilderParamsDTO, LBLVLPBBILLAMT);
		String billDate= getLabel(responseBuilderParamsDTO, LBLVLPBPAYDATE);

		pageBody.append(billDetails);
		pageBody.append(USSDConstants.NEW_LINE);

		int userInput = Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("txnRefNo"));

		CASAccountTransactionList tran= getTransactionDetails(responseBuilderParamsDTO.getUssdSessionMgmt().getTransactionList(),
					responseBuilderParamsDTO.getUssdSessionMgmt().getFinalTransactionList().toArray()[userInput-1].toString());
		pageBody.append(billerName);
		pageBody.append(tran.getTransactionActivity().getBeneficiaryORBillerName());
		pageBody.append(USSDConstants.NEW_LINE);

		pageBody.append(billAmount);
		pageBody.append(tran.getTransactionActivity().getAmount());
		pageBody.append(USSDConstants.NEW_LINE);

		pageBody.append(billDate);
		pageBody.append(tran.getTransactionActivity().getDateTime());


		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		menuItemDTO.setStatus(USSDConstants.STATUS_END);
		return menuItemDTO;
	}

	public CASAccountTransactionList getTransactionDetails(List<CASAccountTransactionList> casaTranlist, String transactionNo){
		for(CASAccountTransactionList tran:casaTranlist)
			if(tran.getTransactionActivity().getTransactionRefnbr().equals(transactionNo))
				return tran;
		return null;
	}

	private String getLabel(ResponseBuilderParamsDTO responseBuilderParamsDTO,
			String label) {
		String labelValue = null;
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
				.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO
				.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile()
				.getLanguage(), ussdSessionMgmt.getUserProfile()
				.getCountryCode());
		labelValue = ussdResourceBundle.getLabel(label, locale);
		return labelValue;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO
				.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS
						.getSequenceNo());
	}

}