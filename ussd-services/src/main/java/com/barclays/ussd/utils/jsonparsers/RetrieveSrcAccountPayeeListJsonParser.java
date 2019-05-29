package com.barclays.ussd.utils.jsonparsers;

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
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccListPayData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.RetrieveAccList;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class RetrieveSrcAccountPayeeListJsonParser implements BmgBaseJsonParser {// , ScreenSequenceCustomizer {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RetrieveSrcAccountPayeeListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {

	    RetrieveAccList accList = mapper.readValue(jsonString, RetrieveAccList.class);
	    if (accList != null) {
		if (accList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accList.getPayHdr().getResCde())) {
		    List<AccountDetails> srcLst = accList.getPayData().getSrcLst();
		    if (srcLst == null || srcLst.isEmpty()) {
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		    }
		    Collections.sort(accList.getPayData().getSrcLst(), new RetriveSrcAccountCustomerAccountComparator());
		    menuDTO = renderMenuOnScreen(accList.getPayData(), responseBuilderParamsDTO);
		    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
			Map<String, Object> txSessionMap = new HashMap<String, Object>(5);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap);
		    }
		    // set the from accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId(),
			    accList.getPayData().getSrcLst());

		    // set the payee accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId(),
			    accList.getPayData().getPayLst());

		 // set the payee accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put("lstPayeeAcntsOrigional",accList.getPayData().getPayLst());

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

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(AccListPayData payData, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();//CR82 changes
    AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	if (payData != null && payData.getSrcLst() != null && !payData.getSrcLst().isEmpty()) {
		List<String> GpAcc=new ArrayList<String>();
		 for(int i =0;i<acts.size();i++)
		    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
		    		GpAcc.add(acts.get(i).getMkdActNo());
		    List<AccountDetails> srcAcc=payData.getSrcLst();
		    if(null != srcAcc){
		    	for(int j=0;j<srcAcc.size();j++)
					 if(GpAcc.contains(srcAcc.get(j).getMkdActNo()))
						 srcAcc.remove(j);
		    }

			 if (srcAcc == null || srcAcc.isEmpty() || srcAcc.size() == 0) {
				    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
				}
	    // for (AccountDetails accountDetail : payData.getPayLst()) {
	    for (AccountDetails accountDetail : payData.getSrcLst()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index++);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(accountDetail.getMkdActNo());
	    }
	}

	menuItemDTO.setPageBody(pageBody.toString());

	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);

	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	// Refer to sequencer.properties to set the below value
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	ArrayList<AccountDetails> SrcActList = (ArrayList<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId());
	if ((SrcActList != null) && (!SrcActList.isEmpty()) && (SrcActList.size() == 1)) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();

	    List<AccountDetails> lstPayeeAccounts = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId());
	    if ((lstPayeeAccounts != null) && (!lstPayeeAccounts.isEmpty()) && (lstPayeeAccounts.size() == 1)) {
		userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
		userInputMap.put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
		ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	    }
	}
	return seqNo;
    }

}

/* This class compares the customer account w.r.t primary indicator */
class RetriveSrcAccountCustomerAccountComparator implements Comparator<AccountDetails>, Serializable {

    /**
	 *
	 */
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