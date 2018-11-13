/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthenticateUserPayData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 * 
 */
public class WlcmScrnOthAcctAccountSummaryJSONParser implements BmgBaseJsonParser {

    private static final String PRIMARY_INDICATOR_TRUE = "Y";
    private static final String WELCOME_OTHER_ACCOUNTS_NOT_FOUND = "welcome.other.accounts.not.found";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WlcmScrnOthAcctAccountSummaryJSONParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	try {
	    AuthUserData userAuthObj = (AuthUserData) responseBuilderParamsDTO.getUssdSessionMgmt().getUserAuthObj();
	    if (userAuthObj != null) {
		if (userAuthObj.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(userAuthObj.getPayHdr().getResCde())) {
		    Collections.sort(userAuthObj.getPayData().getCustActs(), new AccountSummaryCustomerAccountComparator());
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, userAuthObj);
		} else if (userAuthObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(userAuthObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    } else {
		LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	return menuDTO;
    }

    /**
     * @param responseBuilderParamsDTO
     * @param userAuthObj
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, AuthUserData userAuthObj) {
	MenuItemDTO menuItemDTO = null;
	AuthenticateUserPayData acntPayData = userAuthObj.getPayData();
	if (acntPayData != null) {
	    List<CustomerMobileRegAcct> custActs = acntPayData.getCustActs();
	    if (custActs != null && !custActs.isEmpty()) {
		menuItemDTO = new MenuItemDTO();
		if (custActs.size() == 1 && StringUtils.equalsIgnoreCase(custActs.get(0).getPriInd(), PRIMARY_INDICATOR_TRUE)) {
		    menuItemDTO.setPageHeader(WELCOME_OTHER_ACCOUNTS_NOT_FOUND);
		    menuItemDTO.setPaginationType(PaginationEnum.SPACED);
		} else {
		    List<CustomerMobileRegAcct> custActListWOMainAcct = new ArrayList<CustomerMobileRegAcct>();

		    int index = 1;
		    StringBuilder pageBody = new StringBuilder();

		    for (CustomerMobileRegAcct accountDetail : custActs) {
			if (StringUtils.equalsIgnoreCase(accountDetail.getPriInd(), USSDConstants.PRIMARY_INDICATOR_YES)) {
			    continue;
			}
			custActListWOMainAcct.add(accountDetail);

			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(index);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(accountDetail.getMkdActNo());
			index++;
		    }
		    menuItemDTO.setPageBody(pageBody.toString());
		    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		    menuItemDTO.setPaginationType(PaginationEnum.LISTED);

		    Map<String, Object> txSessions = new HashMap<String, Object>(custActs.size());
		    txSessions.put(USSDInputParamsEnum.WLCME_BAL_ENQ_SEL_AC.getTranId(), custActListWOMainAcct);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		setNextScreenSequenceNumber(menuItemDTO);
	    }
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }
}

/* This class compares the customer account w.r.t primary indicator */
/*
 * class CustomerAccountComparator implements Comparator<AccountDetails> {
 * 
 * public int compare(AccountDetails accountDetail1, AccountDetails accountDetail2) { int retVal = 0; if
 * (USSDConstants.PRIMARY_INDICATOR_YES.equalsIgnoreCase(accountDetail1.getPriInd())) { retVal = 1; } else { retVal = -1; } return retVal; } }
 */
