package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class GhipsOneOffPaymentReasonListJsonParser implements BmgBaseJsonParser {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GhipsOneOffBnkListJsonParser.class);

	private static final String LABEL_GHIPS_MENUS_PYMT_REASON = "label.ghips.menus.payment.reason";

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		try {
			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
			if (txSessions == null) {
				txSessions = new HashMap<String, Object>(8);
				responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
			}
				menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);

		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
/*			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {*/
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			//}
		}
		return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		List<AccountDetails> lstAccntDet = (List<AccountDetails>)responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getTranId());
		if (lstAccntDet != null && !lstAccntDet.isEmpty()) {
		    AccountDetails acntDet = lstAccntDet
			    .get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getParamName())) - 1);
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getParamName(), acntDet);
		}
		String message = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_GHIPS_MENUS_PYMT_REASON,
				new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(), responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
		menuItemDTO.setPageBody(message);
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo());
	}

}
