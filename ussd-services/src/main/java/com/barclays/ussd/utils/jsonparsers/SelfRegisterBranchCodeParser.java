/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.CurrentRunningTransaction;
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
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

/**
 * @author BTCI
 *
 */
public class SelfRegisterBranchCodeParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(SelfRegisterBranchCodeParser.class);
    @Autowired
    UssdBranchLookUpDAOImpl ussdBranchLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<UssdBranchLookUpDTO> branchList = null;
	MenuItemDTO menuDTO = null;
	CurrentRunningTransaction currentTrans= null;//CR-86
	boolean backflow= false;
	try {
	    Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    String branchCodeLetter = userInputMap.get(USSDInputParamsEnum.SELFREG_BRANCH_SEARCHER.getParamName());
	    branchList = ussdBranchLookUpDAOImpl.getBranchCodeList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(), branchCodeLetter);
	    if (branchList != null && branchList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, branchList);

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		//Forgot Pin change
		txSessions.put(responseBuilderParamsDTO.getTranDataId(), branchList);
		//txSessions.put(USSDInputParamsEnum.SELFREG_BRANCH.getTranId(), branchList);
	    } else {
	    	LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
	    	//CR-86 back flow changes Forgot pin branchCode invalid
	    	if(responseBuilderParamsDTO != null &&
	    			responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails()!= null){
	    		currentTrans=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction();
	    		if(currentTrans != null && currentTrans.getTranNodeId().equalsIgnoreCase("ussd3.00")){
	    			backflow=true;
	    			throw new USSDNonBlockingException(USSDExceptions.USSD_BRANCH_CODE_INVALID_FPIN.getBmgCode());
	    		}
	    	}
	    	throw new USSDNonBlockingException(USSDExceptions.USSD_NO_BRANCH_FOUND.getBmgCode());
	    }
	} catch (Exception e) {
		LOGGER.error("Exception : ", e);
		if (e instanceof USSDNonBlockingException) {
			//CR-86
			if(backflow){
				((USSDNonBlockingException) e).setBackFlow(true);
				throw ((USSDNonBlockingException) e);
			}

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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    }
    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
    	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
    	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
    	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
    		seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
    	}
    	return seqNo;
    }
}
