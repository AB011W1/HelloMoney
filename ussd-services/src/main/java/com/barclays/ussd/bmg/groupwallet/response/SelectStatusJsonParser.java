package com.barclays.ussd.bmg.groupwallet.response;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
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
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletTxValidate;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CASAccountTransactionList;

public class SelectStatusJsonParser implements BmgBaseJsonParser {
	private static final String TRXNID_LABEL = "label.transact.id";
	private static final String LABEL_APPROVE = "label.approve";
	private static final String LABEL_REJECT = "label.reject";
	private static final Logger LOGGER = Logger
			.getLogger(SelectStatusJsonParser.class);

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			MobileWalletTxValidate mobileWalletTxValidate = mapper.readValue(
					responseBuilderParamsDTO.getJsonString(),
					MobileWalletTxValidate.class);

			if (mobileWalletTxValidate != null) {
				if ((mobileWalletTxValidate.getPayHdr() != null)
						&& (USSDExceptions.SUCCESS.getBmgCode()
								.equalsIgnoreCase(mobileWalletTxValidate
										.getPayHdr().getResCde()))) {
					StringBuilder pageBody = new StringBuilder();
					USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
							.getUssdSessionMgmt();

					String language = ussdSessionMgmt.getUserProfile()
							.getLanguage();
					String countryCode = ussdSessionMgmt.getUserProfile()
							.getCountryCode();
					Locale locale = new Locale(language, countryCode);
					int tranSelected = Integer.parseInt(ussdSessionMgmt
							.getUserTransactionDetails().getUserInputMap().get(
									"tranNo"));

					String trxnidLabel = responseBuilderParamsDTO
							.getUssdResourceBundle().getLabel(TRXNID_LABEL,
									locale);
					String approveLabel = responseBuilderParamsDTO
							.getUssdResourceBundle().getLabel(LABEL_APPROVE,
									locale);
					String rejectLabel = responseBuilderParamsDTO
							.getUssdResourceBundle().getLabel(LABEL_REJECT,
									locale);
					pageBody.append(trxnidLabel);
					Object tranList[] = ussdSessionMgmt
							.getFinalTransactionList().toArray();
					pageBody.append(tranList[tranSelected - 1]);
					pageBody.append(USSDConstants.NEW_LINE);
					pageBody.append(approveLabel);
					pageBody.append(USSDConstants.NEW_LINE);
					pageBody.append(rejectLabel);
					List<CASAccountTransactionList> transList = ussdSessionMgmt
							.getTransactionList();
					String ct = "0";
					if(transList!=null && transList.size()>0
							&& transList.get(0).getTransactionActivity()!=null
							&& transList.get(0).getTransactionActivity().getCorporateUserAuthDetailsType()!=null)
						ct = String.valueOf(transList.get(0).getTransactionActivity().getCorporateUserAuthDetailsType().size());

					ussdSessionMgmt.getUserTransactionDetails()
							.getUserInputMap().put("count", ct);
					USSDUtils.appendHomeAndBackOption(menuItemDTO,
							responseBuilderParamsDTO);
					menuItemDTO.setPageBody(pageBody.toString());
					menuItemDTO.setPageHeader(responseBuilderParamsDTO
							.getHeaderId());
					menuItemDTO.setPaginationType(PaginationEnum.LISTED);
					menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
					setNextScreenSequenceNumber(menuItemDTO);
				}
			}
		} catch (JsonParseException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE
					.getBmgCode());
		} catch (JsonMappingException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE
					.getBmgCode());
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(
						((USSDNonBlockingException) e).getErrorCode());
			} else {
				throw new USSDNonBlockingException(
						USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO
				.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTYFOUR
						.getSequenceNo());
	}

}