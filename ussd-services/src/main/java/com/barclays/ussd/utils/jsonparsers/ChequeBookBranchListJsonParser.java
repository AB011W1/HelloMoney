package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLocatorLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class ChequeBookBranchListJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ChequeBookBranchListJsonParser.class);

    @Autowired
    UssdBranchLocatorLookUpDAOImpl ussdBranchLocatorLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<UssdBranchLocatorLookUpDTO> branchList = null;
	MenuItemDTO menuDTO = null;
	try {
	    Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    String branchLetter = userInputMap.get(USSDInputParamsEnum.CHECK_BK_BRANCH_NAME.getParamName());
	    /*
	     * if (branchLetter.trim().equals("1")) { branchLetter = null; }
	     */

	    branchList = ussdBranchLocatorLookUpDAOImpl.getBranchList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(), branchLetter);
	    if (branchList != null && branchList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, branchList, "");

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.CHECK_BK_BRANCH_LIST.getTranId(), branchList);
	    } else {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_CHEQUEBOOK_BRANCH_FOUND.getBmgCode());
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
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<UssdBranchLocatorLookUpDTO> branchList,
	    String warningMsg) {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	int index = 1;
	for (UssdBranchLocatorLookUpDTO branchLookUpDTO : branchList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(branchLookUpDTO.getBranchName() + "-" + branchLookUpDTO.getCityName());
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

    }
}
