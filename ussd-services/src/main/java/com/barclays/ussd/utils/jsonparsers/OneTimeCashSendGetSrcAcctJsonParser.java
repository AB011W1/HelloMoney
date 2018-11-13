package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class OneTimeCashSendGetSrcAcctJsonParser implements BmgBaseJsonParser {
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	List<AccountDetails> accounts = (List<AccountDetails>) txSessions.get(USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getTranId());
	int accountIndex = 1;
	StringBuilder pageBody = new StringBuilder();
	if (CollectionUtils.isNotEmpty(accounts)) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    for (AccountDetails account : accounts) {
		pageBody.append(accountIndex++);
		pageBody.append(USSDConstants.DOT_SEPERATOR).append(account.getMkdActNo());
		pageBody.append(USSDConstants.NEW_LINE);
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
