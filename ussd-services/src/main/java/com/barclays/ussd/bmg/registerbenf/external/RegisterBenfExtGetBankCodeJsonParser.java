package com.barclays.ussd.bmg.registerbenf.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class RegisterBenfExtGetBankCodeJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {
    @Autowired
    UssdBranchLookUpDAOImpl branchLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = null;
	menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
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
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	bankCodeLetter.append(userInput);
	List<UssdBranchLookUpDTO> bankList = branchLookUpDAOImpl.getBankCodeList(ussdSessionMgmt.getBusinessId(), bankCodeLetter.toString());
	// if (bankList != null && bankList.size() != 0) {
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	if (txSessions == null) {
	    txSessions = new HashMap<String, Object>(5);
	    ussdSessionMgmt.setTxSessions(txSessions);
	}
	if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId().equals("ussd0.4.3.4.2"))
	txSessions.put(USSDInputParamsEnum.REG_BENF_SELECT_BANK_NAME.getTranId(), bankList);
	else
	txSessions.put(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getTranId(), bankList);
	// }

	if (bankList != null && bankList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
		if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId().equals("ussd0.4.3.4.2"))
			txSessions.put(USSDInputParamsEnum.REG_BENF_SELECT_BANK_NAME.getTranId(), bankList);
		else
	    userInputMap.put(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);

	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    // seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	String tranNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();

	if(tranNodeId.equals("ussd0.6.3.1"))
		seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo();
	return seqNo;
    }

}
