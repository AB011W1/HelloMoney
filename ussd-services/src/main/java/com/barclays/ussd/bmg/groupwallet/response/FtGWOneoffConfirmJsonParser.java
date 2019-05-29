package com.barclays.ussd.bmg.groupwallet.response;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered.IntNRFundTxValidate;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;

public class FtGWOneoffConfirmJsonParser implements BmgBaseJsonParser {
	private static final String DEBIACCNUM_LABEL = "LBLFRMACNO";

	private static final String CONFIRM_LABEL = "label.mwallet.confirm";
	private static final String TRANSACTION_MWALLET_LABEL = "label.transaction.mwallet";
	private static final Logger LOGGER = Logger
			.getLogger(FtGWOneoffConfirmJsonParser.class);

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
		    IntNRFundTxValidate intNRFundTxValidateObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), IntNRFundTxValidate.class);
		    if (intNRFundTxValidateObj != null) {
			if ((intNRFundTxValidateObj.getPayHdr() != null)
				&& (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(intNRFundTxValidateObj.getPayHdr().getResCde()))) {
			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
			} else if (intNRFundTxValidateObj.getPayHdr() != null) {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(intNRFundTxValidateObj.getPayHdr().getResCde());
			} else {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		    }
		} catch (JsonParseException e) {
		    LOGGER.error("JsonParseException : ", e);
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (JsonMappingException e) {
		    LOGGER.error("JsonParseException : ", e);
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (Exception e) {
		    LOGGER.error("Exception : ", e);
		    if (e instanceof USSDNonBlockingException) {
			throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
		    } else {
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		    }
		}
		return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
				.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt
				.getUserTransactionDetails().getUserInputMap();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile()
				.getLanguage(), ussdSessionMgmt.getUserProfile()
				.getCountryCode());
		String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(DEBIACCNUM_LABEL, locale);
		String mobileWalletAccountNumberLabel = responseBuilderParamsDTO
				.getUssdResourceBundle()
				.getLabel("LBLTOACNO", locale);
		String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle()
				.getLabel(CONFIRM_LABEL, locale);

		String paramArray[] = { userInputMap.get("txnAmt") };
		String mWallettAmountLabel = responseBuilderParamsDTO
				.getUssdResourceBundle().getLabel(
						TRANSACTION_MWALLET_LABEL,
						paramArray,
						new Locale(responseBuilderParamsDTO
								.getUssdSessionMgmt().getUserProfile()
								.getLanguage(), responseBuilderParamsDTO
								.getUssdSessionMgmt().getUserProfile()
								.getCountryCode()));
		String mbNum = userInputMap
				.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER
						.getParamName());
		pageBody.append(mWallettAmountLabel).append(
				USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(USSDConstants.NEW_LINE).append(
				mobileWalletAccountNumberLabel).append(
				USSDConstants.SINGLE_WHITE_SPACE).append(mbNum);

		String userInput = userInputMap
				.get(USSDInputParamsEnum.GROUP_WALLET_SELECT_ACC.getParamName());
		List<AccountAdditionalInfo> srcLst = (List<AccountAdditionalInfo>) ussdSessionMgmt
				.getCustaccountList();
		AccountAdditionalInfo selectedFrmAcct = srcLst.get(Integer
				.parseInt(userInput) - 1);
		pageBody.append(USSDConstants.NEW_LINE).append(fromAccLabel).append(
				USSDConstants.SINGLE_WHITE_SPACE).append(
				selectedFrmAcct.getAccountAdditionalInfo().getAccountId());

		if (!responseBuilderParamsDTO.isErrorneousPage()) {
			pageBody.append(USSDConstants.NEW_LINE).append(confirmLabel);
		}
		USSDUtils
				.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO
				.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX
						.getSequenceNo());
	}

}
