package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class GhipsOneOffBnkAcnoJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(LeadGenerationSubmitJsonParser.class);
	private static final String LABEL_GHIPS_ENTER_BENEF_ACC_NUM = "label.ghips.enter.benef.acc.num";

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		try{
			List<UssdBranchLookUpDTO> bankList=null;
			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
			if (txSessions == null) {
				txSessions = new HashMap<String, Object>(8);
				responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
			}
			bankList = (List<UssdBranchLookUpDTO>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			.get(USSDInputParamsEnum.GHIPS_BANK_LIST.getTranId());
			Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
			userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
			String bankName="";
			if (bankList.size() != 0) {
				UssdBranchLookUpDTO ussdBranchLookUpDTO = bankList
				.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_BANK_LIST.getParamName())) - 1);
				bankName=ussdBranchLookUpDTO.getBankName();
			}

			String[] paramArray = new String[]{bankName};
			USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
			String message = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_GHIPS_ENTER_BENEF_ACC_NUM, paramArray,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
			USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			StringBuilder pageBody = new StringBuilder();
			pageBody.append(message);
			menuItemDTO.setPageBody(pageBody.toString());
			menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
			setNextScreenSequenceNumber(menuItemDTO);
		}catch (Exception e) {
			LOGGER.error("Exception : ", e);
/*			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {*/
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			//}
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// Refer to sequencer.properties to set the below value
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
	}
	/*@Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	USSDCompositeValidator validator = null;

	String accountNoLen = ussdResourceBundle.getLabel(ACCT_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		.getUserProfile().getCountryCode()));
	validator = new USSDCompositeValidator(new USSDAccountNoLengthValidator(accountNoLen));

	try {
	    validator.validate("" + userInput.length());
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode(), e);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
	    throw e;
	}
    }*/
}
