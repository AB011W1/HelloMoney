/**
 * EBFTPayeeListJsonParser.java
 */
package com.barclays.ussd.bmg.fundtransfer.external.editbenf;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
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
import com.barclays.ussd.utils.jsonparsers.RegisterBenfExtEnterBranchCodeJsonParser;

/**
 * @author BTCI
 * 
 */
public class EditBenfExtRetrieveBranchListJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RegisterBenfExtEnterBranchCodeJsonParser.class);

    @Autowired
    UssdBranchLookUpDAOImpl branchLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<UssdBranchLookUpDTO> branchList = null;
	MenuItemDTO menuDTO = null;

	try {

	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    String branchName = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_EXT_BRANCH_CODE.getParamName());
	    String businessId = responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId();
	    // String bankNameWithCode = userInputMap.get(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getParamName());

	    List<UssdBranchLookUpDTO> tempBankList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.EDIT_BENF_EXT_BANK_CODE_LIST.getTranId());
	    UssdBranchLookUpDTO bankCodeLookUpDTO = tempBankList.get(Integer.parseInt(userInputMap
		    .get(USSDInputParamsEnum.EDIT_BENF_EXT_BANK_CODE_LIST.getParamName())) - 1);

	    String bankCode = bankCodeLookUpDTO.getBankCode();
	    String bankName = bankCodeLookUpDTO.getBankName();

	    branchList = branchLookUpDAOImpl.getBranchList(businessId, bankCode, bankName, branchName);
	    if (branchList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, branchList);
		responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.EDIT_BENF_EXT_BRANCH_CODE_LIST.getTranId(),
			branchList);
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_BRANCH_CODE_LIST_INVALID.getBmgCode());
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

    }
}
