/**
 * DelBillrValParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillPayFrmAccntLst;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.delbillers.DelBillersValidate;

/**
 * @author BTCI
 *
 */
public class MobileWalletTopUpBenfDtlsJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(MobileWalletTopUpBenfDtlsJsonParser.class);

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.utils.BmgBaseJsonParser#parseJsonIntoJava(com.barclays .ussd.bmg.dto.ResponseBuilderParamsDTO)
     */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	DelBillersValidate delBillrVal = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	try {
	   // delBillrVal = mapper.readValue(jsonString, DelBillersValidate.class);
		BillPayFrmAccntLst billPayFromAcct = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillPayFrmAccntLst.class);
	    if (billPayFromAcct != null) {
		if (billPayFromAcct.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(billPayFromAcct.getPayHdr().getResCde())) {
			Payee payee=billPayFromAcct.getPayData().getPay();
			ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_BENF_DTlS.getTranId(), payee);
			Collections.sort(billPayFromAcct.getPayData().getFrActLst(), new MobileWalletCustomerAccountComparator());//issue fix for primary account top on list
			ussdSessionMgmt.getTxSessions().put("AccountListSaved", billPayFromAcct.getPayData());
		} else if (billPayFromAcct.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(delBillrVal.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    }
	} catch (JsonParseException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (JsonMappingException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;

    }


    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }
}

class MobileWalletCustomerAccountComparator implements Comparator<AccountData>, Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public int compare(final AccountData accountDetail1, final AccountData accountDetail2) {
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
