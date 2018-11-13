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

public class RetrievePayeeAccountListDetailsJsonParser implements BmgBaseJsonParser {

    @SuppressWarnings("unchecked")
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	List<AccountDetails> lstPayeeAccounts = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId());

	if (lstPayeeAccounts == null || lstPayeeAccounts.isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
	}
	menuDTO = renderMenuOnScreen(lstPayeeAccounts, responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    @SuppressWarnings("unchecked")
    private MenuItemDTO renderMenuOnScreen(List<AccountDetails> lstpayeeAcnts, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	List<AccountDetails> srcActList = (List<AccountDetails>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId());
	String payeeAcNoInput = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName());
	String srcAc = srcActList.get(Integer.parseInt(payeeAcNoInput) - 1).getActNo();

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
