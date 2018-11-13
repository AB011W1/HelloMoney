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

public class RegisterBenfExtGetBranchCodeJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	StringBuffer bankCodeLetter = new StringBuffer();
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	String branchName = userInput;
	String businessId = ussdSessionMgmt.getBusinessId();
	String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	List<UssdBranchLookUpDTO> tempBranchList=null;
	UssdBranchLookUpDTO bankCodeLookUpDTO=null;

	if(transNodeId.equals("ussd0.4.3.4.2")){
		tempBranchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.REG_BENF_SELECT_BANK_NAME.getTranId());
		bankCodeLookUpDTO = tempBranchList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.REG_BENF_SELECT_BANK_NAME
				.getParamName())) - 1);
	}
	else{
	tempBranchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getTranId());
	bankCodeLookUpDTO = tempBranchList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST
		.getParamName())) - 1);
	}
	String bankCode = bankCodeLookUpDTO.getBankCode();
	String bankName = bankCodeLookUpDTO.getBankName();

	List<UssdBranchLookUpDTO> branchList = branchLookUpDAOImpl.getBranchList(businessId, bankCode, bankName, branchName);

	// if (branchList != null && branchList.size() != 0) {
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	if (txSessions == null) {
	    txSessions = new HashMap<String, Object>(5);
	    ussdSessionMgmt.setTxSessions(txSessions);
	}
	if(transNodeId.equals("ussd0.4.3.4.2"))
		txSessions.put(USSDInputParamsEnum.REG_BENF_SELECT_BRANCH_NAME.getTranId(), branchList);
	else
		txSessions.put(USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE_LIST.getTranId(), branchList);
	// }

	if (branchList != null && branchList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    if(transNodeId.equals("ussd0.4.3.4.2"))
	    	userInputMap.put(USSDInputParamsEnum.REG_BENF_SELECT_BRANCH_NAME.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    else
	    	userInputMap.put(USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    // seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
	}
	return seqNo;
    }
}
