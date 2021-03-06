/**
 * MiniStmtAcSumJSONParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.barclays.bmg.context.BMBContextHolder;
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

/**
 * @author BTCI
 *
 */
public class MiniStmtAcSumJSONParser implements BmgBaseJsonParser {

    private static final String ACCOUNT_STATUS_ACTIVE = "Active";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MiniStmtAcSumJSONParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	try {
	    String localCurrency = BMBContextHolder.getContext().getLocalCurrency();

	    AuthUserData userAuthObj = (AuthUserData) responseBuilderParamsDTO.getUssdSessionMgmt().getUserAuthObj();
	    if (userAuthObj != null) {
		if (userAuthObj.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(userAuthObj.getPayHdr().getResCde())) {
		    List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();
		    if (custActs != null && !custActs.isEmpty()) {
			List<CustomerMobileRegAcct> localCcyCustActsList = new ArrayList<CustomerMobileRegAcct>();
			for (CustomerMobileRegAcct acct : custActs) {
			    if (StringUtils.equalsIgnoreCase(acct.getCurr(), localCurrency)
				    && StringUtils.equalsIgnoreCase(ACCOUNT_STATUS_ACTIVE, acct.getAccStatus())) {
				localCcyCustActsList.add(acct);
			    }
			}
			if (localCcyCustActsList.isEmpty()) {
			    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
			}
			Collections.sort(localCcyCustActsList, new MiniStmtAccountSummaryCustomerAccountComparator());
			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, localCcyCustActsList);

		    }
		} else if (userAuthObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(userAuthObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
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
     * @param localCcyCustActsList
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<CustomerMobileRegAcct> localCcyCustActsList) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;
	menuItemDTO = new MenuItemDTO();
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	Map<String, Object> txSessions = null;
	if(null != localCcyCustActsList)
		txSessions = new HashMap<String, Object>(localCcyCustActsList.size());
	if(null != txSessions)
		txSessions.put(USSDInputParamsEnum.MINI_STMT_SEL_AC.getTranId(), localCcyCustActsList);
	responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
	if(localCcyCustActsList != null && !localCcyCustActsList.isEmpty())
	{
		for(int i =0;i<localCcyCustActsList.size();i++)
	    	if(localCcyCustActsList.get(i).getGroupWalletIndicator()!=null && localCcyCustActsList.get(i).getGroupWalletIndicator().equals("Y"))
	    		localCcyCustActsList.remove(i);
		if (localCcyCustActsList == null || localCcyCustActsList.isEmpty() || localCcyCustActsList.size() == 0) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
		for (CustomerMobileRegAcct accountDetail : localCcyCustActsList) {
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(index);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(accountDetail.getMkdActNo());
			index++;
		}
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    }
}

/* This class compares the customer account w.r.t primary indicator */
class MiniStmtAccountSummaryCustomerAccountComparator implements Comparator<CustomerMobileRegAcct>, Serializable {
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
