package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class GhipsRegisterBenfExtSearchBankJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

	@Autowired
	 UssdBranchLookUpDAOImpl branchLookUpDAOImpl;

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// Refer to sequencer.properties to set the below value
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
	}

	 public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
			StringBuffer bankCodeLetter = new StringBuffer();
			int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
			Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			bankCodeLetter.append(userInput);
			List<UssdBranchLookUpDTO> bankList = branchLookUpDAOImpl.getBankListGHIPS(bankCodeLetter.toString());
			// if (bankList != null && bankList.size() != 0) {
			Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
			if (txSessions == null) {
			    txSessions = new HashMap<String, Object>(5);
			    ussdSessionMgmt.setTxSessions(txSessions);
			}
			txSessions.put(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST.getTranId(), bankList);
			// }

			if (bankList != null && bankList.size() == 1) {
			    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
			    userInputMap.put(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
			    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
			    // seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
			}
			return seqNo;
		    }

}
