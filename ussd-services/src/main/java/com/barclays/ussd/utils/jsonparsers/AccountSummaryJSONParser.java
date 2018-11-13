/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
public class AccountSummaryJSONParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AccountSummaryJSONParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	try {
	    AuthUserData userAuthObj = (AuthUserData) responseBuilderParamsDTO.getUssdSessionMgmt().getUserAuthObj();
	    if (userAuthObj != null) {
		if (userAuthObj.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(userAuthObj.getPayHdr().getResCde())) {
		    List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();
		    Collections.sort(custActs, new AccountSummaryCustomerAccountComparator());
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
	    if (acntPayData.getCustActs() != null && !acntPayData.getCustActs().isEmpty()) {
		menuItemDTO = new MenuItemDTO();
		int index = 1;
		StringBuilder pageBody = new StringBuilder();
		Map<String, Object> txSessions = new HashMap<String, Object>(acntPayData.getCustActs().size());
		txSessions.put(USSDInputParamsEnum.BAL_ENQ_SEL_AC.getTranId(), acntPayData.getCustActs());
		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		for (CustomerMobileRegAcct accountDetail : acntPayData.getCustActs()) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(index);
		    pageBody.append(USSDConstants.DOT_SEPERATOR);
		    pageBody.append(accountDetail.getMkdActNo());
		    index++;
		}
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		// menuItemDTO.setPageFooter(warningMsg);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    }
	}
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    }
}

/* This class compares the customer account w.r.t primary indicator */
class AccountSummaryCustomerAccountComparator implements Comparator<CustomerMobileRegAcct>, Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public int compare(final CustomerMobileRegAcct accountDetail1, final CustomerMobileRegAcct accountDetail2) {
	int retVal = 0;
	if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail1.getPriInd())) {
	    retVal = -1;
	} else if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail2.getPriInd())) {
	    retVal = 1;
	} else {
	    retVal = 0;
	}
	return retVal;
    }
}
