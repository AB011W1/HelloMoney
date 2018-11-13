package com.barclays.ussd.utils.jsonparsers;

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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeList;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeLstData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.ToList;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class KEOthBnkRetrieveBenfListJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(EBFTPayeeListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    PayeeList payeeList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), PayeeList.class);
	    if (payeeList != null) {
		if (payeeList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payeeList.getPayHdr().getResCde())) {
		    Collections.sort(payeeList.getPayData().getSrcLst(), new KEBFTPayeeListCustomerAccountComparator());
		    setOutputInSession(responseBuilderParamsDTO, payeeList);
		} else if (payeeList.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(payeeList.getPayHdr().getResCde());
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

    /**
     * @param responseBuilderParamsDTO
     * @param payeeList
     */
    private void setOutputInSession(ResponseBuilderParamsDTO responseBuilderParamsDTO, PayeeList payeeList) {
	PayeeLstData payeeLstData = payeeList.getPayData();
	if (payeeLstData != null) {
	    Map<String, Object> txSessionMap = new HashMap<String, Object>(2);
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap);
	    if (payeeLstData.getSrcLst() != null && !payeeLstData.getSrcLst().isEmpty()) {
		responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			.put(USSDInputParamsEnum.KE_EXT_BANK_FT_SEL_FRM_AC.getTranId(), payeeLstData.getSrcLst());
	    }
	    List<ToList> lstFTto = payeeLstData.getPayLst();
	    if (lstFTto != null && !lstFTto.isEmpty()) {
		ToList toList = lstFTto.get(0);
		if (toList != null && toList.getBnfLst() != null && !toList.getBnfLst().isEmpty()) {
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			    .put(USSDInputParamsEnum.KE_EXT_BANK_FT_TO_AC.getTranId(), toList.getBnfLst());

		}
	    }
	}

    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<BeneData> payeeList = (List<BeneData>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KE_EXT_BANK_FT_TO_AC.getTranId());
	if (payeeList != null && payeeList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.KE_EXT_BANK_FT_TO_AC.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }
}

/* This class compares the customer account w.r.t primary indicator */
class KEBFTPayeeListCustomerAccountComparator implements Comparator<AccountDetails>, Serializable {

    private static final long serialVersionUID = 1L;

    public int compare(final AccountDetails accountDetail1, final AccountDetails accountDetail2) {
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
