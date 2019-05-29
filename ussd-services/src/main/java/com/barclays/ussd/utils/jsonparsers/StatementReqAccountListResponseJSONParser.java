package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.AccountChequBookDetails;

public class StatementReqAccountListResponseJSONParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(StatementReqAccountListResponseJSONParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	try {
	    List<AccountChequBookDetails> srcAcctList = (List<AccountChequBookDetails>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .get(USSDInputParamsEnum.STMT_REQ_SRC_ACT.getTranId());
	    MenuItemDTO menuDTO = renderMenuOnScreen(srcAcctList, responseBuilderParamsDTO);
	    return menuDTO;
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}

    }

    /**
     * @param chequeBookRequestBeanJSON
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(List<AccountChequBookDetails> srcAcctList, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	if (srcAcctList != null && !srcAcctList.isEmpty()) {
		if(acts != null && acts.size() > 0)
		{
			List<String> GpAcc=new ArrayList<String>();
			 for(int i =0;i<acts.size();i++)
			    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
			    		GpAcc.add(acts.get(i).getMkdActNo());

				 for(int j=0;j<srcAcctList.size();j++)
					 if(GpAcc.contains(srcAcctList.get(j).getMkdActNo()))
						 srcAcctList.remove(j);
		}
		if (srcAcctList == null || srcAcctList.isEmpty() || srcAcctList.size() == 0) {
			   throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
	    for (AccountChequBookDetails accountDetail : srcAcctList) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(accountDetail.getMkdActNo());
		index++;
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
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