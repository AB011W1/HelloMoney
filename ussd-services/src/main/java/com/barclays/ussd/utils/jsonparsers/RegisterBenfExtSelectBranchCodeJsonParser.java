package com.barclays.ussd.utils.jsonparsers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class RegisterBenfExtSelectBranchCodeJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RegisterBenfExtSelectBranchCodeJsonParser.class);

    @Resource(name = "branchCodeCountryList")
    private List<String> branchCodeCountryList;
    private String transNodeId;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	try {
		List<UssdBranchLookUpDTO> bankList=null;
		transNodeId=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(transNodeId.equals("ussd0.4.3.4.2"))
	    bankList = (List<UssdBranchLookUpDTO>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .get(USSDInputParamsEnum.REG_BENF_SELECT_BANK_NAME.getTranId());
		else
			bankList = (List<UssdBranchLookUpDTO>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .get(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getTranId());
	    if (bankList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, bankList);
		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		if(transNodeId.equals("ussd0.4.3.4.2"))
			txSessions.put(USSDInputParamsEnum.REG_BENF_SELECT_BANK_NAME.getTranId(), bankList);
		else
		txSessions.put(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getTranId(), bankList);
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

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<UssdBranchLookUpDTO> branchList) {
	StringBuilder pageBody = new StringBuilder();
	Collections.sort(branchList, new Comparator<UssdBranchLookUpDTO>() {
	    @Override
	    public int compare(UssdBranchLookUpDTO b1, UssdBranchLookUpDTO b2) {
		return b1.getBankName().compareTo(b2.getBankName());
	    }
	});
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	int index = 1;
	for (UssdBranchLookUpDTO branchLookUpDTO : branchList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(branchLookUpDTO.getBankName());
	}
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }

    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	if (!branchCodeCountryList.contains(businessId)&&!transNodeId.equals("ussd0.4.3.4.2")) {
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	}
	return seqNo;
    }
}
