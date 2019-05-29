package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;

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

public class RetrievePayeeAccountListDetailsJsonParser implements BmgBaseJsonParser {

    @SuppressWarnings("unchecked")
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	List<AccountDetails> lstPayeeAccounts = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId());

	if (lstPayeeAccounts == null || lstPayeeAccounts.isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
	}
	MenuItemDTO menuDTO = renderMenuOnScreen(lstPayeeAccounts, responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    @SuppressWarnings("unchecked")
    private MenuItemDTO renderMenuOnScreen(List<AccountDetails> lstpayeeAcnts, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	List<AccountDetails> srcActList = (List<AccountDetails>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId());
	String payeeAcNoInput = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName());
	String srcAc = srcActList.get(Integer.parseInt(payeeAcNoInput) - 1).getActNo();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();//CR82 changes
    AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
    List<String> GpAcc=new ArrayList<String>();
	 for(int i =0;i<acts.size();i++)
	    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
	    		GpAcc.add(acts.get(i).getMkdActNo());
	    List<AccountDetails> srcAcc=lstpayeeAcnts;
	    if(null != srcAcc){
	    	 for(int j=0;j<srcAcc.size();j++)
				 if(GpAcc.contains(srcAcc.get(j).getMkdActNo()))
					 srcAcc.remove(j);
	    }

	if (lstpayeeAcnts != null && !lstpayeeAcnts.isEmpty()) {
	    List<AccountDetails> lstPayeeAcntsRevised = new ArrayList<AccountDetails>(lstpayeeAcnts.size() - 1);
	    for (AccountDetails accountDetail : lstpayeeAcnts) {
		if (!srcAc.equalsIgnoreCase(accountDetail.getActNo())) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(index++);
		    pageBody.append(USSDConstants.DOT_SEPERATOR);
		    pageBody.append(accountDetail.getMkdActNo());
		    lstPayeeAcntsRevised.add(accountDetail);
		}
	    }
	    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId(), lstPayeeAcntsRevised);
	}
	else
	{
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
	}

	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	// Refer to sequencer.properties to set the below value
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    }

}