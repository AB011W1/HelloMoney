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
public class AirtimeTopUpBenfDtlsJsonParser implements BmgBaseJsonParser {

    private static final String BILLERID_LABEL = "label.biller.id";
    private static final String BILLERTYPE_LABEL = "label.biller.type";
    private static final String BILLER_NICK_NAME_LABEL = "label.biller.nick.name";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final Logger LOGGER = Logger.getLogger(AirtimeTopUpBenfDtlsJsonParser.class);

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
			//List billerDetails = new ArrayList<E>();
			Payee payee=billPayFromAcct.getPayData().getPay();
			ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId(), payee);
			//issue fix for primary account top on list
			 Collections.sort(billPayFromAcct.getPayData().getFrActLst(), new AirtimeInitCustomerAccountComparator());
			//ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getTranId(), payee.getRefNo().getPhNo());
			//ussdSessionMgmt.getTxSessions().put("BillerId", payee.getBilrId());
			ussdSessionMgmt.getTxSessions().put("AccountListSaved", billPayFromAcct.getPayData());

		   // menuDTO = renderMenuOnScreen(delBillrVal.getPayData(), responseBuilderParamsDTO);
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }
}

/* This class compares the customer account w.r.t primary indicator */
class AirtimeInitCustomerAccountComparator implements Comparator<AccountData>, Serializable {
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
