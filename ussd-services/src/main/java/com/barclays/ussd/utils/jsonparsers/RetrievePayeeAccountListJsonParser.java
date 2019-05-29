package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.RetrieveAccList;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class RetrievePayeeAccountListJsonParser implements BmgBaseJsonParser {

	  /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RetrievePayeeAccountListJsonParser.class);

    @SuppressWarnings("unchecked")
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	String jsonString = responseBuilderParamsDTO.getJsonString();
	ObjectMapper mapper = new ObjectMapper();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	if("OwnLinkedAcctFundTxCredit".equals(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName())){

		try {

		    RetrieveAccList accList = mapper.readValue(jsonString, RetrieveAccList.class);
		    if (accList != null) {
			if (accList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accList.getPayHdr().getResCde())) {
			    List<AccountDetails> lstPayeeAccounts = accList.getPayData().getPayLst();
			    if (lstPayeeAccounts == null || lstPayeeAccounts.isEmpty()) {
				throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
			    }
			    Collections.sort(lstPayeeAccounts , new RetriveSrcAccountCustomerAccountComparator());
			    menuDTO = renderMenuOnScreenCredit(lstPayeeAccounts, responseBuilderParamsDTO);


			    // set the payee accnt list to the session
			    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId(),
				    accList.getPayData().getPayLst());

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

	} else {
	List<AccountDetails> lstPayeeAccounts = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.OLAFT_PAYEE_LIST.getTranId());

	if (lstPayeeAccounts == null || lstPayeeAccounts.isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
	}
	menuDTO = renderMenuOnScreen(lstPayeeAccounts, responseBuilderParamsDTO);
	}
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    @SuppressWarnings("unchecked")
    private MenuItemDTO renderMenuOnScreen(List<AccountDetails> lstpayeeAcnts, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	List<AccountDetails> srcActList = (List<AccountDetails>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.OLAFT_SOURCE_LIST.getTranId());
	String payeeAcNoInput = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.OLAFT_SOURCE_LIST.getParamName());
	String srcAc = srcActList.get(Integer.parseInt(payeeAcNoInput) - 1).getActNo();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();//CR82 changes
    AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	if (lstpayeeAcnts != null && !lstpayeeAcnts.isEmpty()) {
	    List<AccountDetails> lstPayeeAcntsRevised = new ArrayList<AccountDetails>(lstpayeeAcnts.size() - 1);
	    List<String> GpAcc=new ArrayList<String>();
		 for(int i =0;i<acts.size();i++)
		    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
		    		GpAcc.add(acts.get(i).getMkdActNo());
		    List<AccountDetails> srcAcc=lstpayeeAcnts;

			 for(int j=0;j<srcAcc.size();j++)
				 if(GpAcc.contains(srcAcc.get(j).getMkdActNo()))
					 srcAcc.remove(j);
			 if (lstpayeeAcnts == null || lstpayeeAcnts.isEmpty() || lstpayeeAcnts.size() == 0) {
				    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
				}
	    for (AccountDetails accountDetail : lstpayeeAcnts) {
		if (!srcAc.equalsIgnoreCase(accountDetail.getActNo())) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(index++);
		    pageBody.append(USSDConstants.DOT_SEPERATOR);
		    pageBody.append(accountDetail.getMkdActNo());
		    lstPayeeAcntsRevised.add(accountDetail);
		}
	    }

	   responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put("lstPayeeAcntsRevised", lstPayeeAcntsRevised);
	}

	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }


    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    @SuppressWarnings("unchecked")
    private MenuItemDTO renderMenuOnScreenCredit(List<AccountDetails> lstPayeeAccounts, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	int index = 1;
    	StringBuilder pageBody = new StringBuilder();
    	MenuItemDTO menuItemDTO = new MenuItemDTO();

    	for (AccountDetails accountDetail : lstPayeeAccounts) {
    	    pageBody.append(USSDConstants.NEW_LINE);
    	    pageBody.append(index++);
    	    pageBody.append(USSDConstants.DOT_SEPERATOR);
    	    pageBody.append(accountDetail.getMkdActNo());
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
    }

}
