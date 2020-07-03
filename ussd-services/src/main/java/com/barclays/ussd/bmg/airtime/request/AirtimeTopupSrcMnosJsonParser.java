package com.barclays.ussd.bmg.airtime.request;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.AirtimeInitResponseParser;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeInitPayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeInitResponse;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;

public class AirtimeTopupSrcMnosJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer
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
	  //TZNBC Menu Optimization
		List<Biller> mnoList = (List<Biller>) airtimeInitPayData.getPrvderLst();
		if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("TZNBC")){
			mnoList=getSortedTznbcMNOList(mnoList);
		}
		if (mnoList != null && !mnoList.isEmpty()) {
			txSessions.put(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getTranId(), airtimeInitPayData.getSrcLst());
			txSessions.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId(), mnoList);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
	}
    }
    
    private List<Biller> getSortedTznbcMNOList(List<Biller> mnoList) {
		List<Biller> billerList = new ArrayList<Biller>(mnoList);
		List<Biller> sortedBillerList=new ArrayList<Biller>();

		for (Biller biller : billerList) {
			if(biller.getBillerId().startsWith("ATOP")){
				sortedBillerList.add(biller);
				break;
			}
		}

		for (Biller billerProvider : billerList) {
			if(billerProvider.getBillerId().startsWith("VTOP")){
				sortedBillerList.add(billerProvider);
				break;
			}
		}
		for (Biller billerProvider : billerList) {
			if(billerProvider.getBillerId().startsWith("TTOP")){
				sortedBillerList.add(billerProvider);
				break;
			}
		}
		for (Biller billerProvider : billerList) {
			if(billerProvider.getBillerId().startsWith("ZTOP")){
				sortedBillerList.add(billerProvider);
				break;
			}
		}



		billerList = removeAll(billerList,sortedBillerList);
		sortedBillerList.addAll(billerList);
		return sortedBillerList;

	}

    private List<Biller> removeAll(List<Biller> collection, List<Biller> remove)
	{
		List<Biller> list = new ArrayList<Biller>();
		for (Biller billerProvider : collection) {
			if (!remove.contains(billerProvider)) {
				list.add(billerProvider);
			}
		}
		return list;
	}


    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	
	
	//TZNBC Menu Optimization - to redirect to bene management
	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	if(null!=tranDataId && tranDataId.equalsIgnoreCase("ussd0.8.2.3") && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC"))
		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYFIVE.getSequenceNo();
	
	//Ghana Databundle change
	String businessId = ussdSessionMgmt.getBusinessId();
	String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	if (null != userInputMap && null != userInputMap.get("TransNodeId") && businessId.equalsIgnoreCase("GHBRB") && transNodeId.equals("ussd0.10")
			&& null != userInput  && !(userInput.equals("3")) && !(userInput.equals("4")) && !(userInput.equals("5"))) {

		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();

	}
	else if (businessId.equalsIgnoreCase("GHBRB") && transNodeId.equals("ussd0.10") && null != userInput  && userInput.equals("3"))
		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTYONE.getSequenceNo();
	
	else if (businessId.equalsIgnoreCase("GHBRB") && transNodeId.equals("ussd0.10") && null != userInput  && userInput.equals("4"))
		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTYTHREE.getSequenceNo();
	
	/*List<Biller> mnoList = (List<Biller>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
	if (mnoList != null && mnoList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo();
	}*/
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
