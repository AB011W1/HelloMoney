package com.barclays.ussd.utils.jsonparsers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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

public class GhipsRegisterBenfExtBankNameJsonParser implements BmgBaseJsonParser {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GhipsRegisterBenfExtBankNameJsonParser.class);
	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		try {
			List<UssdBranchLookUpDTO> bankList=null;
			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
			if (txSessions == null) {
				txSessions = new HashMap<String, Object>(8);
				responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
			}
			bankList = (List<UssdBranchLookUpDTO>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST.getTranId());
			if (bankList.size() != 0) {
				menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, bankList);

			} else {
				LOGGER.error("Error while servicing USSDExceptions.USSD_BANK_CODE_LIST_INVALID.getBmgCode()");
				throw new USSDNonBlockingException(USSDExceptions.USSD_BANK_CODE_LIST_INVALID.getBmgCode());
			}
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
			ResponseBuilderParamsDTO responseBuilderParamsDTO,
			List<UssdBranchLookUpDTO> bankList) {
		StringBuilder pageBody = new StringBuilder();
		Collections.sort(bankList, new Comparator<UssdBranchLookUpDTO>() {
			@Override
			public int compare(UssdBranchLookUpDTO b1, UssdBranchLookUpDTO b2) {
				return b1.getBankName().compareTo(b2.getBankName());
			}
		});
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		int index = 1;
		for (UssdBranchLookUpDTO branchLookUpDTO : bankList) {
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(index++);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(branchLookUpDTO.getBankName());
		}
		responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST.getTranId(),bankList);
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
	}

}
