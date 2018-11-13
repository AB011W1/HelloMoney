package com.barclays.ussd.bmg.statementrequest;

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

import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.AccountChequBookDetails;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.ChequeBookRequestJSON;

public class StatementReqRetrieveSrcAccountListJsonParser implements BmgBaseJsonParser
// ,ScreenSequenceCustomizer
{
    private static final Logger LOGGER = Logger.getLogger(StatementReqRetrieveSrcAccountListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    ChequeBookRequestJSON chequeBookRequestJSON = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ChequeBookRequestJSON.class);
	    if (chequeBookRequestJSON != null) {
		if (chequeBookRequestJSON.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(chequeBookRequestJSON.getPayHdr().getResCde())) {
		    Collections.sort(chequeBookRequestJSON.getPayData().getSrcLst(), new CustomerAccountComparator());

		    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>(3));
		    }
		    // set the payee accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			    .put(USSDInputParamsEnum.STMT_REQ_SRC_ACT.getTranId(), chequeBookRequestJSON.getPayData().getSrcLst());

		} else if (chequeBookRequestJSON.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(chequeBookRequestJSON.getPayHdr().getResCde());
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<AccountChequBookDetails> srcAcctList = (List<AccountChequBookDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.STMT_REQ_SRC_ACT.getTranId());
	if (srcAcctList != null && srcAcctList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.STMT_REQ_SRC_ACT.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }
}

/* This class compares the customer account w.r.t primary indicator */
class CustomerAccountComparator implements Comparator<AccountChequBookDetails>, Serializable {

    private static final long serialVersionUID = 1L;

    public int compare(final AccountChequBookDetails accountDetail1, final AccountChequBookDetails accountDetail2) {
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