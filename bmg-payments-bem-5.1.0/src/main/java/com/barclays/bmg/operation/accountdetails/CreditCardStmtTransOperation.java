package com.barclays.bmg.operation.accountdetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.operation.accountdetails.request.CreditCardStmtTransOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CreditCardStmtTransOperationResponse;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardStatementDatesServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardStatementDatesServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;

/**
 * @author BMB team
 *
 */

public class CreditCardStmtTransOperation extends AbstractCreditCardOperation {

    private CreditCardDetailsService creditCardDetailsService;
    private AllAccountService allAccountService;
    private ListValueResService listValueResService;

    public ListValueResService getListValueResService() {
	return listValueResService;
    }

    public void setListValueResService(ListValueResService listValueResService) {
	this.listValueResService = listValueResService;
    }

    /**
     * 1. Retrieves creditcard statemented dates. 2. Retrieves creditcard account details. 3. Retrieves creditcard statementd transactions.
     *
     * @param request
     * @return
     *
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CCD_STATEMENT_TRANS", stepId = "1", activityType = "auditCCDStmtTrans")
    public CreditCardStmtTransOperationResponse retrieveCreditCardStmtTrans(CreditCardStmtTransOperationRequest request) {

	CreditCardStmtTransOperationResponse returnCCStmtTransOperationResp = new CreditCardStmtTransOperationResponse();
	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());
	List<Calendar> ccStmtDates = null;
	String respCode = "";
	CreditCardAccountDTO creditCardAccountDTO = null;

	boolean respSuccessFlg = false;

	String orgCode = "";

	String reqAccNo = request.getAccountNo();

	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(context);
	allAccountServiceRequest.setAccountNo(reqAccNo);

	AllAccountServiceResponse allAccountServiceResp = allAccountService.retrieveCreditCardList(allAccountServiceRequest);
	ArrayList<CreditCardStmtBalanceInfoDTO> filterCreditCardStmtBalanceInfoDTO = null;
	if (allAccountServiceResp != null) {

	    respSuccessFlg = allAccountServiceResp.isSuccess();
	    respCode = allAccountServiceResp.getResCde();

	    if (respSuccessFlg) {

		List<? extends CustomerAccountDTO> customerAccountDTOList = allAccountServiceResp.getAccountList();

		for (CustomerAccountDTO customerAccountDTO : customerAccountDTOList) {

		    if (customerAccountDTO instanceof CreditCardAccountDTO) {

			String accNo = customerAccountDTO.getAccountNumber();

			if (accNo.equalsIgnoreCase(reqAccNo)) {

			    creditCardAccountDTO = (CreditCardAccountDTO) customerAccountDTO;

			    if (creditCardAccountDTO.getPrimary() != null) {
				orgCode = creditCardAccountDTO.getPrimary().getCreditCardOrgCode();
			    }
			    break;
			}
		    }
		}

		CreditCardStatementDatesServiceRequest ccStmtDatesServiceReq = new CreditCardStatementDatesServiceRequest();
		ccStmtDatesServiceReq.setContext(context);
		ccStmtDatesServiceReq.setAccountNo(reqAccNo);
		ccStmtDatesServiceReq.setOrgCode(orgCode);

		CreditCardStatementDatesServiceResponse ccStmtDatesServiceResp = creditCardDetailsService
			.retrieveCreditCardStatementDates(ccStmtDatesServiceReq);

		if (ccStmtDatesServiceResp != null) {
			List<CreditCardStmtBalanceInfoDTO> stmtObjList = ccStmtDatesServiceResp.getStatementObj();
		    if (stmtObjList != null) {
			ccStmtDates = getStmtDateList(stmtObjList);
			respSuccessFlg = ccStmtDatesServiceResp.isSuccess();
			// Get System Preferences to display number of records
			int maxDisplayRecord = getMaxDisplayRecord(context);
			returnCCStmtTransOperationResp.setStatementDates(ccStmtDates);

			// check if Number of Statements are less than display number of records
			if (stmtObjList.size() <= maxDisplayRecord) {
			    filterCreditCardStmtBalanceInfoDTO = new ArrayList<CreditCardStmtBalanceInfoDTO>(stmtObjList);
			} else {
			    filterCreditCardStmtBalanceInfoDTO = new ArrayList<CreditCardStmtBalanceInfoDTO>(stmtObjList.subList(0, maxDisplayRecord));
			}
		    }

		}

	    }
	}

	// filterStmtDates.addAll(ccStmtDates.subList(0, maxDisplayRecord));
	returnCCStmtTransOperationResp.setSuccess(respSuccessFlg);
	returnCCStmtTransOperationResp.setResCde(respCode);
	returnCCStmtTransOperationResp.setContext(context);
	returnCCStmtTransOperationResp.setCreditCardAccountDTO(creditCardAccountDTO);
	returnCCStmtTransOperationResp.setCreditCardStmtBalanceInfoDTO(filterCreditCardStmtBalanceInfoDTO);

	if (!returnCCStmtTransOperationResp.isSuccess()) {
	    getMessage(returnCCStmtTransOperationResp);
	}

	return returnCCStmtTransOperationResp;

    }

    public AllAccountService getAllAccountService() {
	return allAccountService;
    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public CreditCardDetailsService getCreditCardDetailsService() {
	return creditCardDetailsService;
    }

    public List<Calendar> getStmtDateList(List<CreditCardStmtBalanceInfoDTO> stmtObjList) {
	List<Calendar> stmtDates = new ArrayList<Calendar>();

	if (stmtObjList != null && stmtObjList.size() > 0) {

	    for (CreditCardStmtBalanceInfoDTO balanceInfo : stmtObjList) {
		Calendar cal = balanceInfo.getStatementDate();
		stmtDates.add(cal);
	    }
	    Collections.sort(stmtDates);
	    Collections.reverse(stmtDates);
	}

	return stmtDates;
    }

    public CreditCardStmtBalanceInfoDTO getbalanceInfo(List<CreditCardStmtBalanceInfoDTO> stmtObjList, Date billDate) {

	if (stmtObjList != null && stmtObjList.size() > 0) {
	    for (CreditCardStmtBalanceInfoDTO balanceInfo : stmtObjList) {
		Calendar eachDt = balanceInfo.getStatementDate();
		Date dt = eachDt.getTime();
		if (billDate.compareTo(dt) == 0) {
		    return balanceInfo;
		}

	    }
	}

	return null;
    }

    public void setCreditCardDetailsService(CreditCardDetailsService creditCardDetailsService) {
	this.creditCardDetailsService = creditCardDetailsService;
    }

    private int getMaxDisplayRecord(Context context) {

	int maxDisplayRecord = 0;
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(context);
	listValueResServiceRequest.setGroup(SystemParameterConstant.SYS_PARAM_CC);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);
	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}
	if(null != listValuesDTOList){
		for (ListValueCacheDTO valueresDTO : listValuesDTOList) {
		    if (valueresDTO.getKey() != null && SystemParameterConstant.CC_STATEMENT_LIST.equals(valueresDTO.getKey())) {
			maxDisplayRecord = Integer.parseInt((valueresDTO.getLabel().trim()));
		    }
		}
	}

	return maxDisplayRecord;

    }

}
