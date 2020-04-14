package com.barclays.ussd.utils.jsonparsers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

public class RegisterBenfExtEnterBranchCodeJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RegisterBenfExtEnterBranchCodeJsonParser.class);
    String transNodeId=null;
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	transNodeId=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	List<UssdBranchLookUpDTO> branchList=null;
	try {

	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    if(transNodeId.equals("ussd0.4.3.4.2"))
	    	branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
	    			USSDInputParamsEnum.REG_BENF_SELECT_BRANCH_NAME.getTranId());
	    else
	    	branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
	    			USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE_LIST.getTranId());
	    if (branchList !=null  &&  branchList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, branchList);
		if(transNodeId.equals("ussd0.4.3.4.2"))
			responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
				.put(USSDInputParamsEnum.REG_BENF_SELECT_BRANCH_NAME.getTranId(), branchList);
		else
			responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			.put(USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE_LIST.getTranId(), branchList);
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
	String transNodeId=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	for (UssdBranchLookUpDTO branchLookUpDTO : branchList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(branchLookUpDTO.getBranchName());
	    if(transNodeId.equals("ussd0.4.3.4.2")){
	    	String city=branchLookUpDTO.getCityName();
	    	pageBody.append(" - "+city);
	    	responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put("city", city);
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }

	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException {
		//ZMBRB,BWBRB,TZBRB one-off
		String businessId = ussdSessionMgmt.getBusinessId();
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
		if(businessId.equalsIgnoreCase("ZMBRB") && transNodeId.equals("ussd4.3.3.2") || 
				(businessId.equalsIgnoreCase("BWBRB") && transNodeId.equals("ussd0.3.3.2")) ||
				(businessId.equalsIgnoreCase("TZBRB") && transNodeId.equals("ussd0.3.3.2")) ) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYONE.getSequenceNo();
		}
		
		return seqNo;
	}
}
