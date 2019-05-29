package com.barclays.ussd.bmg.groupwallet.response;

import java.util.List;
import java.util.Locale;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;

public class FtGWAccTranHistoryJsonParser implements BmgBaseJsonParser {
	private static final String TRANSACTION_FROM_ACCOUNT_LABEL = "label.select.accno";
	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		List<AccountAdditionalInfo> lstFrmAcnt = (List<AccountAdditionalInfo>) ussdSessionMgmt.getCustaccountList();

		if (lstFrmAcnt == null || lstFrmAcnt.isEmpty()) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}

		if (lstFrmAcnt != null && !lstFrmAcnt.isEmpty()) {
		    int index = 1;
		    StringBuilder pageBody = new StringBuilder();
		    String amount=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("txnAmt");
		    String[] paramArray ={amount};
			String fundTxAccountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FROM_ACCOUNT_LABEL, paramArray ,
	    			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
	    					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
			pageBody.append(fundTxAccountLabel);


		    for (AccountAdditionalInfo ele : lstFrmAcnt) {
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(index);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(ele.getAccountAdditionalInfo().getMaskedAccountId());
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
		// TODO Auto-generated method stub
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWELVE.getSequenceNo());
	}

}
