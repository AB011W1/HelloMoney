package com.barclays.ussd.bmg.fundtransfer.otherbarclaysfundtx.request;

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
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTBeneficiary;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTPayeeAcct;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTRetrievePayee;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTSrcAcct;

public class OBAFTSrcPayeeListJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{

    private static final Logger LOGGER = Logger.getLogger(OBAFTSrcPayeeListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    OBAFTRetrievePayee accList = mapper.readValue(jsonString, OBAFTRetrievePayee.class);
	    if (accList != null) {
		if (accList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accList.getPayHdr().getResCde())) {
		    List<OBAFTPayeeAcct> payLst = accList.getPayData().getPayLst();
		    if (payLst == null || payLst.isEmpty()) {
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		    }
		    Collections.sort(accList.getPayData().getSrcLst(), new RetrieveOBAFTCustomerAccountComparator());
		    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

		    if (ussdSessionMgmt.getTxSessions() == null) {
			Map<String, Object> txSessionMap = new HashMap<String, Object>(5);
			ussdSessionMgmt.setTxSessions(txSessionMap);
		    }
		    // set the payee accnt list to the session
		    for (OBAFTPayeeAcct payeeDetail : accList.getPayData().getPayLst()) {
			if (USSDConstants.DOMESTIC_FND_TX_PAY_CAT.equalsIgnoreCase(payeeDetail.getPayCat())) {
			    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.INT_FT_PAYEE_LIST.getTranId(), payeeDetail.getBnfLst());
			}
		    }
		    // set the from accnt list to the session
		    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.INT_FT_SOURCE_LIST.getTranId(), accList.getPayData().getSrcLst());

		} else if (accList.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(accList.getPayHdr().getResCde());
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
	List<OBAFTBeneficiary> benfList = (List<OBAFTBeneficiary>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.INT_FT_PAYEE_LIST.getTranId());
	if (benfList != null && benfList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.INT_FT_PAYEE_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }

}

/* This class compares the customer account w.r.t primary indicator */
class RetrieveOBAFTCustomerAccountComparator implements Comparator<OBAFTSrcAcct>, Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public int compare(final OBAFTSrcAcct accountDetail1, final OBAFTSrcAcct accountDetail2) {
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