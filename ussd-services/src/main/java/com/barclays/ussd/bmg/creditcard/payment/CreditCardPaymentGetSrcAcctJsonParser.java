/**
 * BillPayFromAcntLstJsonParser.java
 */
package com.barclays.ussd.bmg.creditcard.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.creditcard.at.a.glance.RetrieveCreditCardListJsonParser;
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
public class CreditCardPaymentGetSrcAcctJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RetrieveCreditCardListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	ObjectMapper mapper = new ObjectMapper();
	try {
	    AuthUserData casaCardListObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), AuthUserData.class);
	    if (casaCardListObj != null) {
		if (casaCardListObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(casaCardListObj.getPayHdr().getResCde())) {
			Collections.sort(casaCardListObj.getPayData().getCustActs(), new CreditCardPaymentSrcAcctComparator());
			MenuItemDTO menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, casaCardListObj);
			return menuDTO;
		} else if (casaCardListObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(casaCardListObj.getPayHdr().getResCde());
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

    }

    /**
     * @param responseBuilderParamsDTO
     * @param userAuthObj
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, AuthUserData userAuthObj)
	    throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;
	AuthenticateUserPayData acntPayData = userAuthObj.getPayData();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();//CR82 changes
    AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	if (acntPayData != null) {
	    if (acntPayData.getCustActs() != null && !acntPayData.getCustActs().isEmpty()) {
		menuItemDTO = new MenuItemDTO();
		int index = 1;
		StringBuilder pageBody = new StringBuilder();
		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		txSessions.put(USSDInputParamsEnum.CR_CARD_PAYMENT_SRC_ACC_NO.getTranId(), acntPayData.getCustActs());

		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		List<String> GpAcc=new ArrayList<String>();
		 for(int i =0;i<acts.size();i++)
		    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
		    		GpAcc.add(acts.get(i).getMkdActNo());
		    List<CustomerMobileRegAcct> srcAcc=acntPayData.getCustActs();

			 for(int j=0;j<srcAcc.size();j++)
				 if(GpAcc.contains(srcAcc.get(j).getMkdActNo()))
					 srcAcc.remove(j);
			 if (srcAcc == null || srcAcc.isEmpty() || srcAcc.size() == 0) {
				    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
				}
		for (CustomerMobileRegAcct accountDetail : srcAcc) {
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
		setNextScreenSequenceNumber(menuItemDTO);
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_CREDIT_CARD_FOUND.getBmgCode());
	    }
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }

}

/* This class compares the customer account w.r.t primary indicator */
class CreditCardPaymentSrcAcctComparator implements Comparator<CustomerMobileRegAcct>, Serializable {
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