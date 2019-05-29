package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.AccountChequBookDetails;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.ChequeBookSizeList;

public class ChequeBookAccountListResponseJSONParser implements BmgBaseJsonParser {// , ScreenSequenceCustomizer {

    private static final Logger LOGGER = Logger.getLogger(ChequeBookAccountListResponseJSONParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	try {

	    // ChequeBookRequestJSON chequeBookRequestJSON = mapper.readValue(jsonString, ChequeBookRequestJSON.class);
	    // ChequeBookRequestBeanJSON chequeBookRequestBeanJSON = (ChequeBookRequestBeanJSON) responseBuilderParamsDTO.getUssdSessionMgmt()
	    // .getTxSessions().get(USSDInputParamsEnum.CHECK_BK_SRC_ACT.getTranId());

	    ArrayList<AccountChequBookDetails> SrcActList = (ArrayList<AccountChequBookDetails>) responseBuilderParamsDTO.getUssdSessionMgmt()
		    .getTxSessions().get(USSDInputParamsEnum.CHECK_BK_SRC_ACT.getTranId());

	    MenuItemDTO menuDTO = renderMenuOnScreen(SrcActList, responseBuilderParamsDTO);
	    return menuDTO;
	    /*
	     * if (chequeBookRequestJSON != null) { if (chequeBookRequestJSON.getPayHdr() != null &&
	     * USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(chequeBookRequestJSON.getPayHdr().getResCde())) {
	     * Collections.sort(chequeBookRequestJSON.getPayData().getSrcLst(), new ChequeBookCustomerAccountComparator()); menuDTO =
	     * renderMenuOnScreen(chequeBookRequestJSON.getPayData(), responseBuilderParamsDTO);
	     *
	     * if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) { Map<String, Object> txSessionMap = new HashMap<String,
	     * Object>(3); responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap); } // set the src accnt list to the session
	     * responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.CHECK_BK_SRC_ACT.getTranId(),
	     * chequeBookRequestJSON.getPayData().getSrcLst());
	     *
	     * // set the book size list to the session
	     * responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.CHECK_BK_SIZE.getTranId(),
	     * chequeBookRequestJSON.getPayData().getBkSizeLst());
	     *
	     * } else if (chequeBookRequestJSON.getPayHdr() != null) { LOGGER.error("Error while servicing " +
	     * responseBuilderParamsDTO.getBmgOpCode()); throw new USSDNonBlockingException(chequeBookRequestJSON.getPayHdr().getResCde()); } else {
	     * LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode()); throw new
	     * USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode()); } }
	     */

	} /*
	   * catch (JsonParseException e) { LOGGER.error("JsonParseException : ", e); throw new
	   * USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode()); } catch (JsonMappingException e) {
	   * LOGGER.error("JsonParseException : ", e); throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode()); }
	   */catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}


    }

    /**
     * @param chequeBookRequestBeanJSON
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ArrayList<AccountChequBookDetails> SrcActList, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	if (SrcActList != null && !SrcActList.isEmpty()) {
		if(acts != null && acts.size() > 0)
		{
			List<String> GpAcc=new ArrayList<String>();
			 for(int i =0;i<acts.size();i++)
			    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
			    		GpAcc.add(acts.get(i).getMkdActNo());

				 for(int j=0;j<SrcActList.size();j++)
					 if(GpAcc.contains(SrcActList.get(j).getMkdActNo()))
						 SrcActList.remove(j);
		}
		if(SrcActList == null || SrcActList.isEmpty() || SrcActList.size() == 0){
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
	    for (AccountChequBookDetails accountDetail : SrcActList) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(accountDetail.getMkdActNo());
		index++;
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<ChequeBookSizeList> lstCheckSize = (List<ChequeBookSizeList>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CHECK_BK_SIZE.getTranId());
	if ((lstCheckSize != null) && (!lstCheckSize.isEmpty()) && (lstCheckSize.size() == 1)) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.CHECK_BK_SIZE.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();

	}
	return seqNo;
    }

}

/* This class compares the customer account w.r.t primary indicator */
class ChequeBookCustomerAccountComparator implements Comparator<AccountChequBookDetails>, Serializable {
    /**
	 *
	 */
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