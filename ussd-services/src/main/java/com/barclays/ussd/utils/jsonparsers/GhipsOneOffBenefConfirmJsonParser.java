package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.RegBenfIntBean;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.ValidateRegBenfIntPayData;

public class GhipsOneOffBenefConfirmJsonParser implements BmgBaseJsonParser {
	private static final String NICK_NAME_LABEL = "label.nickname";
	private static final String ACCNT_NO_LABEL = "label.accoun.number";
	private static final String BANK_NAME_LBL = "label.ghips.benef.bank.name";
	private static final String CONFIRM_LABEL = "label.confirm";
	private static final String LABEL_CONFIRM_HEADER_MESSAGE = "label.ghips.ft.save.benef.confirm.header.message";

	private static final Logger LOGGER = Logger
			.getLogger(GhipsOneOffBenefConfirmJsonParser.class);

	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDNonBlockingException {
		String jsonString = responseBuilderParamsDTO.getJsonString();
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {

			RegBenfIntBean regBenfInt = mapper.readValue(jsonString,
					RegBenfIntBean.class);
			if (regBenfInt != null) {
				if (regBenfInt.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode()
								.equalsIgnoreCase(
										regBenfInt.getPayHdr().getResCde())) {
					menuDTO = renderMenuOnScreen(regBenfInt.getPayData(),
							responseBuilderParamsDTO);
					String txnRefNo = new String ("");
					txnRefNo=regBenfInt.getPayData().getTxnRefNo();

					Map<String, Object> txSessions = responseBuilderParamsDTO
							.getUssdSessionMgmt().getTxSessions();
					if (txSessions == null) {
						txSessions = new HashMap<String, Object>(5);
						responseBuilderParamsDTO.getUssdSessionMgmt()
								.setTxSessions(txSessions);
					}
					// set the payee accnt list to the session
					responseBuilderParamsDTO
							.getUssdSessionMgmt()
							.getTxSessions()
							.put(
									USSDInputParamsEnum.GHIPS_FUND_TRANSFER_NICK_NAME_SUBMIT
											.getTranId(), txnRefNo);
				} else if (regBenfInt.getPayHdr() != null) {
					LOGGER.error("Error while servicing "
							+ responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(regBenfInt.getPayHdr()
							.getResCde());
				} else {
					LOGGER.error("Error while servicing "
							+ responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(
							USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
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

		return menuDTO;
	}

	/**
	 * @param payData
	 * @param responseBuilderParamsDTO
	 * @return MenuItemDTO
	 */
	private MenuItemDTO renderMenuOnScreen(
			ValidateRegBenfIntPayData validateRegBenfIntPayData,
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
				.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt
				.getUserTransactionDetails().getUserInputMap();
		UssdBranchLookUpDTO branchCodeLookUpDTO = null;
		String userBranchSelection = null;

		userBranchSelection = userInputMap
				.get(USSDInputParamsEnum.GHIPS_BANK_LIST
						.getParamName());

		if (userBranchSelection != null
				&& StringUtils.isNotEmpty(userBranchSelection)) {
			List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt
					.getTxSessions().get(
							USSDInputParamsEnum.GHIPS_BANK_LIST
									.getTranId());
			branchCodeLookUpDTO = branchList.get(Integer
					.parseInt(userBranchSelection)-1);
		}

		if (validateRegBenfIntPayData != null
				&& validateRegBenfIntPayData.getBeneficiaryInfo() != null) {
			String confirmLabel = responseBuilderParamsDTO
					.getUssdResourceBundle().getLabel(
							CONFIRM_LABEL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode()));
			String accntNoLabel = responseBuilderParamsDTO
					.getUssdResourceBundle().getLabel(
							ACCNT_NO_LABEL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode()));
			String bankNameLbl = responseBuilderParamsDTO.getUssdResourceBundle()
					.getLabel(
							BANK_NAME_LBL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode()));
			String nickNameLabel = responseBuilderParamsDTO
					.getUssdResourceBundle().getLabel(
							NICK_NAME_LABEL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode()));
			String headerLabel = responseBuilderParamsDTO
					.getUssdResourceBundle().getLabel(
							LABEL_CONFIRM_HEADER_MESSAGE,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode()));

			String customerName= String.valueOf(ussdSessionMgmt.getTxSessions().get(USSDConstants.GHIPPS_CUSTOMER_NAME));

			pageBody.append(headerLabel);
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(accntNoLabel);
			pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo()
					.getActNo());

			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("Name: "+customerName);

			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(bankNameLbl);
			pageBody.append(branchCodeLookUpDTO.getBankName());

			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(nickNameLabel);
			pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo()
					.getPayNckNam());
			pageBody.append(USSDConstants.NEW_LINE);

			if (!responseBuilderParamsDTO.isErrorneousPage()) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(confirmLabel);
			}

		}

		// insert the back and home options
		USSDUtils
				.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// Refer to sequencer.properties to set the below value
		menuItemDTO
				.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN
						.getSequenceNo());
	}

}
