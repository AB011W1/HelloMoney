package com.barclays.ussd.bmg.user.migration;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class UserMigrationBranchListJsonParser implements BmgBaseJsonParser {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(UserMigrationBranchListJsonParser.class);
    @Autowired
    UssdBranchLookUpDAOImpl ussdBranchLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<UssdBranchLookUpDTO> branchList = null;
	MenuItemDTO menuDTO = null;
	try {
	    Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    String branchCodeLetter = userInputMap.get(USSDInputParamsEnum.USER_MIGRATION_BRANCH_SEARCHER.getParamName());
	    branchList = ussdBranchLookUpDAOImpl.getBranchCodeList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(), branchCodeLetter);
	    if (branchList != null && branchList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, branchList);

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.USER_MIGRATION_BRANCH_LIST.getTranId(), branchList);
	    } else {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_BRANCH_FOUND.getBmgCode());
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

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<UssdBranchLookUpDTO> branchList) {
	StringBuilder pageBody = new StringBuilder();
	Collections.sort(branchList, new Comparator<UssdBranchLookUpDTO>() {
	    @Override
	    public int compare(UssdBranchLookUpDTO b1, UssdBranchLookUpDTO b2) {
		return b1.getBranchName().compareTo(b2.getBranchName());
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
	    pageBody.append(branchLookUpDTO.getBranchName());
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
}