package com.barclays.ussd.bmg.auth.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;
import com.barclays.bmg.service.utils.BMGFormatUtils;
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
import com.barclays.ussd.utils.UssdResourceBundle;

/**
 * Parser to display the change summary for USSD Authentication from Sybrin
 *
 */
public class AuthenticateCustomerJsonParser implements BmgBaseJsonParser {
	private static final Logger LOGGER = Logger.getLogger(AuthenticateCustomerJsonParser.class);

	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {

		String jsonString = responseBuilderParamsDTO.getJsonString();
		LOGGER.debug("Json String: " + jsonString);
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			AuthRequestDetails authRequestDetailsObj = mapper.readValue(jsonString, AuthRequestDetails.class);

			if (authRequestDetailsObj != null && authRequestDetailsObj.getPayHdr() != null && USSDExceptions.SUCCESS
					.getBmgCode().equalsIgnoreCase(authRequestDetailsObj.getPayHdr().getResCde())) {
				menuDTO = renderMenuOnScreen(authRequestDetailsObj.getPayData(), responseBuilderParamsDTO);

			} else if (authRequestDetailsObj != null && authRequestDetailsObj.getPayHdr() != null) {

				LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				throw new USSDNonBlockingException(authRequestDetailsObj.getPayHdr().getResCde(),
						authRequestDetailsObj.getPayHdr().getResMsg());

			} else {
				LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}

		} catch (USSDNonBlockingException e) {
			LOGGER.error("USSDNonBlockingException : ", e);
			List<String> eerrorParams = new ArrayList<String>();
			eerrorParams.add(((USSDNonBlockingException) e).getErrorMsg());
			((USSDNonBlockingException) e).setErrorParams(eerrorParams);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());

		}

		return menuDTO;
	}

	/**
	 * @param AuthRequestDetailsPayData
	 *            authRequestDetailsPayData, ResponseBuilderParamsDTO
	 *            responseBuilderParamsDTO
	 * @return MenuItemDTO
	 * 
	 *         This method is used to create the page body to display on screen
	 */
	private MenuItemDTO renderMenuOnScreen(AuthRequestDetailsPayData authRequestDetailsPayData,
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		List<GetRecordResDocumentInfo> documentList = authRequestDetailsPayData!=null ? authRequestDetailsPayData.getDocuments() : null;
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		StringBuilder pageBody = new StringBuilder();
		char index = 'a';
		boolean isStandingOrderNewOneTime = true;
		boolean isStandingOrderCancelOneTime = true;
		boolean isAmendOneTime = true;
		boolean isTermDepositNewOneTime = true;
		boolean isTermDepositCancelOneTime = true;
		boolean isCreditCardCancelOneTime = true;
		boolean isDormantAccountReactivationOneTime = true;

		// To get the messages from file
		UssdResourceBundle resourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		String approveLabel = resourceBundle.getLabel(USSDConstants.AUTHREQUEST_APPROVE_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String rejectLabel = resourceBundle.getLabel(USSDConstants.AUTHREQUEST_REJECT_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String nonFianancialAmend = resourceBundle.getLabel(USSDConstants.NON_FINANCIAL_AMEND_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String standingOrderNew = resourceBundle.getLabel(USSDConstants.STANDING_ORDER_NEW_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String standingOrderCancel = resourceBundle.getLabel(USSDConstants.STANDING_ORDER_CANCEL_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String termDepositNew = resourceBundle.getLabel(USSDConstants.TERM_DEPOSIT_NEW_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String termDepositCancel = resourceBundle.getLabel(USSDConstants.TERM_DEPOSIT_CANCEL_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String creditCardCancel = resourceBundle.getLabel(USSDConstants.CREDIT_CARD_CANCEL_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String dormantReactivation = resourceBundle.getLabel(USSDConstants.DORMANT_ACCOUNT_REACTIVATION_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

		pageBody.append(USSDConstants.NEW_LINE);

		StringBuilder termsInDaysAndMonths=new StringBuilder("");
		if (authRequestDetailsPayData != null && documentList != null) {
			for (GetRecordResDocumentInfo document : documentList) {
				if (USSDConstants.STANDING_ORDER_NEW_USECASE.equals(document.getCaseType())) {
					if (isStandingOrderNewOneTime) {
						index = 'a';
						pageBody.append(standingOrderNew);
						pageBody.append(USSDConstants.NEW_LINE);
						isStandingOrderNewOneTime = false;
					}

					if (USSDConstants.USSD_AUTH_ACCOUNT_NUMBER.equals(document.getFieldName())) {
						pageBody.append(index);
						pageBody.append(USSDConstants.DOT_SEPERATOR);
						pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
						pageBody.append("To- " + new BMGFormatUtils().maskAccount(document.getNewValue()));
						pageBody.append(USSDConstants.NEW_LINE);
						++index;
					} else if (USSDConstants.USSD_AUTH_AMOUNT_IN_FIGURES.equals(document.getFieldName())) {
						pageBody.append(index);
						pageBody.append(USSDConstants.DOT_SEPERATOR);
						pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
						pageBody.append("For Amount- " + document.getNewValue());
						pageBody.append(USSDConstants.NEW_LINE);
						++index;
					} else if (USSDConstants.USSD_AUTH_FREQUENCY.equals(document.getFieldName())) {
						pageBody.append(index);
						pageBody.append(USSDConstants.DOT_SEPERATOR);
						pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
						pageBody.append("Frequency- " + document.getNewValue());
						pageBody.append(USSDConstants.NEW_LINE);
						++index;
					}
				} else if (USSDConstants.STANDING_ORDER_CANCEL_USECASE.equals(document.getCaseType())) {
					if (isStandingOrderCancelOneTime) {
						index = 'a';
						pageBody.append(standingOrderCancel);
						pageBody.append(USSDConstants.NEW_LINE);
						isStandingOrderCancelOneTime = false;
					}

					pageBody.append(index);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
					if (USSDConstants.USSD_AUTH_ACCOUNT_NUMBER.equals(document.getFieldName())) {
						pageBody.append(document.getFieldName() + "- "
								+ new BMGFormatUtils().maskAccount(document.getNewValue()));
					} else {
						pageBody.append(document.getFieldName() + "- " + document.getNewValue());
					}
					pageBody.append(USSDConstants.NEW_LINE);
					++index;

				} else if (USSDConstants.TERM_DEPOSIT_NEW_USECASE.equals(document.getCaseType())) {
					if (isTermDepositNewOneTime) {
						index = 'a';
						pageBody.append(termDepositNew);
						pageBody.append(USSDConstants.NEW_LINE);
						isTermDepositNewOneTime = false;
					}
					
					if (USSDConstants.USSD_AUTH_DEPOSIT_AMOUNT.equals(document.getFieldName())) {
						pageBody.append(index);
						pageBody.append(USSDConstants.DOT_SEPERATOR);
						pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
						pageBody.append("Amount- " + document.getNewValue());
						pageBody.append(USSDConstants.NEW_LINE);
						++index;
						
					} else if (USSDConstants.USSD_AUTH_TERM_IN_DAYS.equals(document.getFieldName()) || USSDConstants.USSD_AUTH_TERM_IN_MONTHS.equals(document.getFieldName())) {
						if("".equals(termsInDaysAndMonths.toString())) {
							pageBody.append(index);
							pageBody.append(USSDConstants.DOT_SEPERATOR);
							pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
							termsInDaysAndMonths.append("Term- " + document.getNewValue() + " ");
							
							if (USSDConstants.USSD_AUTH_TERM_IN_DAYS.equals(document.getFieldName())){
								termsInDaysAndMonths.append("Days");
							}else if(USSDConstants.USSD_AUTH_TERM_IN_MONTHS.equals(document.getFieldName())) {
								termsInDaysAndMonths.append("Months");
							}
							
						} else {
							if (USSDConstants.USSD_AUTH_TERM_IN_DAYS.equals(document.getFieldName())){
								termsInDaysAndMonths.append(" " + document.getNewValue() + " Days");
								
							} else if(USSDConstants.USSD_AUTH_TERM_IN_MONTHS.equals(document.getFieldName())) {
								termsInDaysAndMonths.append(" " + document.getNewValue() + " Months");
							}
							
							pageBody.append(termsInDaysAndMonths);
							pageBody.append(USSDConstants.NEW_LINE);
							++index;
						}
						
					} else if (USSDConstants.USSD_AUTH_INTEREST_RATE.equals(document.getFieldName())) {
						pageBody.append(index);
						pageBody.append(USSDConstants.DOT_SEPERATOR);
						pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
						pageBody.append("Interest Rate- " + document.getNewValue());
						pageBody.append(USSDConstants.NEW_LINE);
						++index;
					}

				} else if (USSDConstants.TERM_DEPOSIT_CANCEL_USECASE.equals(document.getCaseType())) {
					if (isTermDepositCancelOneTime) {
						index = 'a';
						pageBody.append(termDepositCancel);
						pageBody.append(USSDConstants.NEW_LINE);
						isTermDepositCancelOneTime = false;
					}

					pageBody.append(index);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
					pageBody.append(document.getFieldName() + "- " + document.getNewValue());
					pageBody.append(USSDConstants.NEW_LINE);
					++index;

				} else if (USSDConstants.CREDIT_CARD_CANCEL_USECASE.equals(document.getCaseType())) {
					if (isCreditCardCancelOneTime) {
						index = 'a';
						pageBody.append(creditCardCancel);
						pageBody.append(USSDConstants.NEW_LINE);
						isCreditCardCancelOneTime = false;
					}

					pageBody.append(index);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
					if (USSDConstants.CREDIT_CARD_NUMBER.equals(document.getFieldName())) {
						pageBody.append(document.getFieldName() + "- "
								+ new BMGFormatUtils().maskAccount(document.getNewValue()));
					} else {
						pageBody.append(document.getFieldName() + "- " + document.getNewValue());
					}
					pageBody.append(USSDConstants.NEW_LINE);
					++index;

				} else if (USSDConstants.DORMANT_ACCOUNT_REACTIVATION_USECASE.equals(document.getCaseType())) {
					if (isDormantAccountReactivationOneTime) {
						index = 'a';
						pageBody.append(dormantReactivation);
						isDormantAccountReactivationOneTime = false;
					}

					if (USSDConstants.DORMANT_ACCOUNT_NUMBER.equals(document.getFieldName())) {
						pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
						pageBody.append(new BMGFormatUtils().maskAccount(document.getNewValue()));
						pageBody.append(USSDConstants.NEW_LINE);
						++index;
					}

				} else {
					if (isAmendOneTime) {
						index = 'a';
						pageBody.append(nonFianancialAmend);
						pageBody.append(USSDConstants.NEW_LINE);
						isAmendOneTime = false;
					}

					if (USSDConstants.TERM_DEPOSIT_UPON_MATURITY.equals(document.getFieldName())) {
						continue;
					}

					pageBody.append(index);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
					pageBody.append(document.getFieldName() + " to " + document.getNewValue());
					pageBody.append(USSDConstants.NEW_LINE);
					++index;

				}
			}
		}

		Map<String, Object> txnSession = new HashMap<String, Object>();
		txnSession.put(USSDConstants.DOCUMENTS, documentList);
		ussdSessionMgmt.setTxSessions(txnSession);
		responseBuilderParamsDTO.setUssdSessionMgmt(ussdSessionMgmt);

		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(approveLabel);
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(rejectLabel);

		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;

	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

	}

}
