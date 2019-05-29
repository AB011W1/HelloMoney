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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class RetrieveAccountListIntNRJsonParser implements BmgBaseJsonParser {
    private static final Logger LOGGER = Logger.getLogger(RetrieveAccountListIntNRJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<AccountDetails> accList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.INT_NR_FT_SOURCE_LIST.getTranId());
	    MenuItemDTO menuDTO = renderMenuOnScreen(accList, responseBuilderParamsDTO);
	    setNextScreenSequenceNumber(menuDTO);
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

    private MenuItemDTO renderMenuOnScreen(List<AccountDetails> payData, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	if ((payData != null)) {
		if(acts != null && acts.size() > 0)
		{
			List<String> GpAcc=new ArrayList<String>();
			 for(int i =0;i<acts.size();i++)
			    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
			    		GpAcc.add(acts.get(i).getMkdActNo());

				 for(int j=0;j<payData.size();j++)
					 if(GpAcc.contains(payData.get(j).getMkdActNo()))
						 payData.remove(j);
		}
		if (payData == null || payData.isEmpty() || payData.size() == 0) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
	    for (AccountDetails accountDetail : payData) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(accountDetail.getMkdActNo());
		index++;
	    }
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());

    }

}

/* This class compares the customer account w.r.t primary indicator */