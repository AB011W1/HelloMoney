package com.barclays.ussd.bmg.fundtransfer.internal.editbenf;

import java.util.ArrayList;
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
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTInitPayData;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTPayeeAcct;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTRetrievePayee;

public class EditBenfInternalPayeeAcctListJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{
    private static final Logger LOGGER = Logger.getLogger(EditBenfInternalPayeeAcctListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    OBAFTRetrievePayee accList = mapper.readValue(jsonString, OBAFTRetrievePayee.class);
	    if (accList != null) {
		if (accList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accList.getPayHdr().getResCde())) {
		    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

		    if (ussdSessionMgmt.getTxSessions() == null) {
			Map<String, Object> txSessionMap = new HashMap<String, Object>(8);
			ussdSessionMgmt.setTxSessions(txSessionMap);
		    }
		    // set the payee accnt list to the session
		    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.EDIT_BEN_INT_BENF_ID.getTranId(), getBeneficiaries(accList.getPayData()));
		    setNextScreenSequenceNumber(menuDTO);
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
	return menuDTO;
    }

    private List<OBAFTBeneficiary> getBeneficiaries(OBAFTInitPayData initPayData) {
	List<OBAFTBeneficiary> bnfLst = null;
	if (initPayData != null && initPayData.getPayLst() != null && !initPayData.getPayLst().isEmpty()) {
	    bnfLst = new ArrayList<OBAFTBeneficiary>();
	    for (OBAFTPayeeAcct ele : initPayData.getPayLst()) {
		String payCat = ele.getPayCat();
		if (StringUtils.isNotEmpty(payCat) && USSDConstants.DOMESTIC_FND_TX_PAY_CAT.equalsIgnoreCase(payCat)) {
		    bnfLst.addAll(ele.getBnfLst());
		}
	    }
	}
	return bnfLst;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    @SuppressWarnings("unchecked")
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	List<OBAFTBeneficiary> bnfList = (List<OBAFTBeneficiary>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.EDIT_BEN_INT_BENF_ID.getTranId());
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	if (bnfList != null && bnfList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.EDIT_BEN_INT_BENF_ID.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }
}
