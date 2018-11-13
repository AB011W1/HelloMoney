package com.barclays.ussd.bmg.airtime.request;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.barclays.ussd.utils.jsonparsers.AirtimeInitResponseParser;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeInitPayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeInitResponse;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;

public class AirtimeTopupSrcMnosJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{
    private static final Logger LOGGER = Logger.getLogger(AirtimeInitResponseParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    AirtimeInitResponse airtimeInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), AirtimeInitResponse.class);

	    if (airtimeInitResponse != null) {
		if (airtimeInitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(airtimeInitResponse.getPayHdr().getResCde())) {
		    Collections.sort(airtimeInitResponse.getPayData().getSrcLst(), new AirtimeInitCustomerAccountComparator());
		    setOutputInSession(responseBuilderParamsDTO, airtimeInitResponse);
		} else if (airtimeInitResponse.getPayHdr() != null) {
		    LOGGER.fatal("unable to service: " + airtimeInitResponse.getPayHdr().getResMsg());
		    throw new USSDNonBlockingException(airtimeInitResponse.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing: " + airtimeInitResponse.getPayHdr().getResMsg());
		    throw new USSDNonBlockingException(airtimeInitResponse.getPayHdr().getResCde());
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

    /**
     * @param responseBuilderParamsDTO
     * @param airtimeInitResponse
     */
    private void setOutputInSession(ResponseBuilderParamsDTO responseBuilderParamsDTO, AirtimeInitResponse airtimeInitResponse) {
	AirtimeInitPayData airtimeInitPayData = airtimeInitResponse.getPayData();
	if (airtimeInitPayData != null) {
	    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    if (txSessions == null) {
		txSessions = new HashMap<String, Object>();
	    }
	    txSessions.put(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getTranId(), airtimeInitPayData.getSrcLst());
	    txSessions.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId(), airtimeInitPayData.getPrvderLst());
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
	}
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<Biller> mnoList = (List<Biller>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
	if (mnoList != null && mnoList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo();
	}
	return seqNo;
    }

}

/* This class compares the customer account w.r.t primary indicator */
class AirtimeInitCustomerAccountComparator implements Comparator<Account>, Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public int compare(final Account accountDetail1, final Account accountDetail2) {
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
