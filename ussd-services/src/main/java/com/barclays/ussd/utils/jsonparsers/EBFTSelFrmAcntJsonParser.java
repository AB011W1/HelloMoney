/**
 * EBFTSelFrmAcntJsonParser.java
 */
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

/**
 * @author BTCI
 *
 */
public class EBFTSelFrmAcntJsonParser implements BmgBaseJsonParser {

    @SuppressWarnings("unchecked")
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	List<AccountDetails> lstFrmAcnt = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.EXT_BANK_FT_SEL_FRM_AC.getTranId());

	if (lstFrmAcnt == null || lstFrmAcnt.isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
	}

	if (lstFrmAcnt != null && !lstFrmAcnt.isEmpty()) {
	    int index = 1;
	    StringBuilder pageBody = new StringBuilder();
	    if(acts != null && acts.size() > 0)
		{
			List<String> GpAcc=new ArrayList<String>();
			 for(int i =0;i<acts.size();i++)
			    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
			    		GpAcc.add(acts.get(i).getMkdActNo());

				 for(int j=0;j<lstFrmAcnt.size();j++)
					 if(GpAcc.contains(lstFrmAcnt.get(j).getMkdActNo()))
						 lstFrmAcnt.remove(j);
		}
	    if (lstFrmAcnt == null || lstFrmAcnt.isEmpty() || lstFrmAcnt.size() == 0) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
	    for (AccountDetails ele : lstFrmAcnt) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(ele.getMkdActNo());
		index++;
	    }
	    menuItemDTO.setPageBody(pageBody.toString());
	    // insert the back and home options
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	}
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }

}
