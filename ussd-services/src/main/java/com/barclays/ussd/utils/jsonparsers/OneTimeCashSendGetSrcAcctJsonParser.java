package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

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

public class OneTimeCashSendGetSrcAcctJsonParser implements BmgBaseJsonParser {
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	List<AccountDetails> accounts = (List<AccountDetails>) txSessions.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getTranId());
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	int accountIndex = 1;
	StringBuilder pageBody = new StringBuilder();
	if (CollectionUtils.isNotEmpty(accounts)) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    if(acts != null && acts.size() > 0)
		{
			List<String> GpAcc=new ArrayList<String>();
			 for(int i =0;i<acts.size();i++)
			    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
			    		GpAcc.add(acts.get(i).getMkdActNo());

				 for(int j=0;j<accounts.size();j++)
					 if(GpAcc.contains(accounts.get(j).getMkdActNo()))
						 accounts.remove(j);
		}
	    if(accounts != null && accounts.size() > 0)
	    {
	    	for (AccountDetails account : accounts) {
	    		pageBody.append(accountIndex++);
	    		pageBody.append(USSDConstants.DOT_SEPERATOR).append(account.getMkdActNo());
	    		pageBody.append(USSDConstants.NEW_LINE);
	    	    }
	    }
	    else
	    {
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }

}

/* This class compares the customer account w.r.t primary indicator */
