package com.barclays.ussd.bmg.creditcard.at.a.glance;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class RetrieveNoOfCardListJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RetrieveCreditCardListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    AuthUserData creditCardListObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), AuthUserData.class);
	    if (creditCardListObj != null) {
		if (creditCardListObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(creditCardListObj.getPayHdr().getResCde())) {
		    List<CustomerMobileRegAcct> custActs = creditCardListObj.getPayData().getCustActs();
		    Collections.sort(custActs, new RetrieveNoOfCardListComparator());
		    Map<String, Object> txSessions = new HashMap<String, Object>(custActs.size());

		    String serviceName = responseBuilderParamsDTO.getUssdSessionMgmt()
		    		.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName();
		    if("OwnLinkedAcctFundTxCredit".equals(serviceName)){
		    txSessions.put(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId(), custActs);
		    }else{
		    txSessions.put(USSDInputParamsEnum.AT_A_GLANCE_CARD_LIST.getTranId(), custActs);
		    }
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		} else if (creditCardListObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(creditCardListObj.getPayHdr().getResCde());
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
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<CustomerMobileRegAcct> cardList = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.AT_A_GLANCE_CARD_LIST.getTranId());
	if (cardList != null && cardList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.AT_A_GLANCE_CARD_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }

}

/* This class compares the customer account w.r.t primary indicator */
class RetrieveNoOfCardListComparator implements Comparator<CustomerMobileRegAcct>, Serializable {
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
