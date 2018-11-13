package com.barclays.ussd.bmg.vlpb.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.DateComparator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.BillerListVO;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.TransHist;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillerList;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillerLstDet;

public class VLPBRetrieveBillerLstJsonParser implements BmgBaseJsonParser
// ,ScreenSequenceCustomizer
{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(VLPBRetrieveBillerLstJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	Map<String, List<BillerListVO>> billersMap = new HashMap<String, List<BillerListVO>>();
	try {
	    VLPBBillerList vLPBBillerList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), VLPBBillerList.class);
	    if (vLPBBillerList != null) {
		if (vLPBBillerList.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(vLPBBillerList.getPayHdr().getResCde())) {
		    boolean blnResult = buildBillerList(vLPBBillerList.getPayData(), billersMap);
		    if (!blnResult) {
			throw new USSDNonBlockingException(USSDExceptions.USSD_VLPB_NOT_PRESENT.getUssdErrorCode());
		    }
		    List<BillerListVO> lstBillers = buildBillerList(billersMap);
		    // added comparator for the date sort
		    Collections.sort(lstBillers, new DateComparator());
		    setSessionVals(lstBillers, responseBuilderParamsDTO);
		} else if (vLPBBillerList.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(vLPBBillerList.getPayHdr().getResCde());
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

    /**
     * @param billersMap
     * @return List<BillerListVO>
     */
    private List<BillerListVO> buildBillerList(Map<String, List<BillerListVO>> billersMap) {
	List<BillerListVO> lstBillers = null;
	if (!billersMap.isEmpty()) {
	    lstBillers = new ArrayList<BillerListVO>();

	    for (Map.Entry<String, List<BillerListVO>> entry : billersMap.entrySet()) {
		lstBillers.addAll(entry.getValue());
	    }

	}
	return lstBillers;
    }

    /**
     * @param payData
     * @param billersMap
     * @return boolean
     */
    private boolean buildBillerList(VLPBBillerLstDet payData, Map<String, List<BillerListVO>> billersMap) {
	int result = 0;
	if (payData != null) {
	    if (payData.getTransactionHistoryList() != null && !payData.getTransactionHistoryList().isEmpty()) {
		for (TransHist ele : payData.getTransactionHistoryList()) {
		    if (USSDConstants.SUCCESS.equalsIgnoreCase(ele.getStatus())) {
			result++;
			if (ele.getBillerInfo() != null) {
			    BillerListVO billerVO = populateBillerlist(ele);
			    if (billersMap.containsKey(ele.getBillerInfo().getBillerRefNo())) {
				// if (billersMap.get(ele.getBillerInfo().getBillerRefNo()).size() < USSDConstants.VLPB_LIST_SIZE) {
				billersMap.get(ele.getBillerInfo().getBillerRefNo()).add(billerVO);
				// }
			    } else {
				List<BillerListVO> lstBillrVO = new ArrayList<BillerListVO>();
				lstBillrVO.add(billerVO);
				billersMap.put(ele.getBillerInfo().getBillerRefNo(), lstBillrVO);
			    }
			}
		    }
		}
	    }
	}
	if (result == 0) {
	    return false;
	} else {
	    return true;
	}
    }

    /**
     * populateBillerlist
     * 
     * @param ele
     * @return BillerListVO
     */
    private BillerListVO populateBillerlist(TransHist ele) {
	BillerListVO billerLstVO = new BillerListVO();
	billerLstVO.setBillerName(ele.getBillerInfo().getBillerName());
	billerLstVO.setBillDate(ele.getTransactionDate());
	billerLstVO.setTransRefNo(ele.getTransactionRefNumber());
	return billerLstVO;
    }

    /**
     * @param lstBillers
     * @param responseBuilderParamsDTO
     */
    private void setSessionVals(List<BillerListVO> lstBillers, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	if (lstBillers != null && !lstBillers.isEmpty()) {
	    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
		Map<String, Object> txSessionsMap = new HashMap<String, Object>(3);
		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionsMap);
	    }
	    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.VLPB_BILLER_LST.getTranId(), lstBillers);
	}
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<BillerListVO> billerList = (List<BillerListVO>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.VLPB_BILLER_LST.getTranId());
	if (billerList != null && billerList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.VLPB_BILLER_LST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }
}