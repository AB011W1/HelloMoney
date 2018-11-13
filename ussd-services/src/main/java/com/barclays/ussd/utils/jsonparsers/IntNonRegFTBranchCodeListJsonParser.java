/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

/**
 * @author BTCI
 *
 */
public class IntNonRegFTBranchCodeListJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {

	//added for Ghana branch code letter
	@Autowired
    UssdBranchLookUpDAOImpl ussdBranchLookUpDAOImpl;
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(IntNonRegFTBranchCodeListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	//added for Ghana branch code letter
	List<UssdBranchLookUpDTO> branchList = null;
	try {
		/* start
		 * code change for Ghana branch code letter
		 */
		Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    String branchCodeLetter = userInputMap.get(USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE_LETTER.getParamName());
	    branchList = ussdBranchLookUpDAOImpl.getBranchCodeList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(), branchCodeLetter);
	    if (branchList != null && branchList.size() != 0) {
		// menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, branchList);

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getTranId(), branchList);
	    } else {
		LOGGER.error("Error while servicing  IntNonRegFTBranchCodeListJsonParser responseBuilderParamsDTO.getBmgOpCode()");
		throw new USSDNonBlockingException(USSDExceptions.USSD_BRANCH_CODE_LIST_INVALID.getBmgCode());
	    }
	    /*	end  code change for Ghana branch code letter   */
	   //code commented for Ghana branch code letter
	    /* List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getTranId());*/
	    menuDTO = renderMenuOnScreen(branchList, responseBuilderParamsDTO);
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(List<UssdBranchLookUpDTO> branchList, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	StringBuilder pageBody = new StringBuilder();

	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	int index = 1;
	for (UssdBranchLookUpDTO branch : branchList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(branch.getBranchName());
	}
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }
    //CR#48 implementation
    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	if(businessId.equalsIgnoreCase("TZBRB"))
	{
	seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
	}

	return seqNo;
    }
}
