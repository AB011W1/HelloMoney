package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

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

public class GhipsRegisterBenefBnkAcnoJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(GhipsRegisterBenefBnkAcnoJsonParser.class);
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
			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
			if (txSessions == null) {
				txSessions = new HashMap<String, Object>(8);
				responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
			}
			USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
			setNextScreenSequenceNumber(menuItemDTO);
		}catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// Refer to sequencer.properties to set the below value
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
	}
	/* @Override
	    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		USSDCompositeValidator validator = null;

		String accountNoLen = ussdResourceBundle.getLabel(GHIPS_ACCTN_NO_LEN, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
			.getUserProfile().getCountryCode()));
		validator = new USSDCompositeValidator(new USSDAccountNoLengthValidator(accountNoLen));

		try {
		    validator.validate("" + userInput.length());
		} catch (USSDNonBlockingException e) {
		    LOGGER.error(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode(), e);
		    e.setErrorCode(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
		    throw e;
		}
	    }
*/
}
