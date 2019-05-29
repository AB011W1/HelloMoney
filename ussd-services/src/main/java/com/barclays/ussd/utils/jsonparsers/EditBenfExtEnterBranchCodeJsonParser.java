package com.barclays.ussd.utils.jsonparsers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class EditBenfExtEnterBranchCodeJsonParser implements BmgBaseJsonParser {

	/** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(EditBenfExtEnterBranchCodeJsonParser.class);
	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
	MenuItemDTO menuDTO = null;

	List<UssdBranchLookUpDTO> branchList=null;
	try {

	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
	    			USSDInputParamsEnum.EDIT_BENF_SELECT_BRANCH_NAME.getTranId());
	    if (branchList !=null  &&  branchList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, branchList);
		responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			.put(USSDInputParamsEnum.EDIT_BENF_SELECT_BRANCH_NAME.getTranId(), branchList);
	    } else {
		LOGGER.error("Error while servicing OLAFTBenfExtEnterBranchCodeJsonParser ");
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

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO,
			List<UssdBranchLookUpDTO> branchList) {
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
		    String city=branchLookUpDTO.getCityName();
		    pageBody.append(" - "+city);
		    responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put("city", city);

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
