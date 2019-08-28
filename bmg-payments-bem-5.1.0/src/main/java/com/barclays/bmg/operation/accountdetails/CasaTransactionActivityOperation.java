package com.barclays.bmg.operation.accountdetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.AccountActivityListDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.request.CasaTransactionActivityOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionActivityOperationResponse;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionTrnxHistoryOperationResponse;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASAAccountTrnxHistoryServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.utils.DateTimeUtil;

public class CasaTransactionActivityOperation extends BMBCommonOperation {

    private AllAccountService allAccountService;
    private CASADetailsService casaDetailsService;

    public static final String BUSINESS_DATE = "BUSINESS_DATE";
    public static final String SHORT_DATE_FORMAT_KEY = "dateFormatting_shortDateFormat";

    /**
     * 1. Loads the system parameter into context 2. Retrieve the CASA account activity list w.r.t Casa account number and transaction days. BEM
     * request
     *
     * @param request
     * @return
     *
     */

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CASA_TXN_ACTIVITY", stepId = "1", activityType = "auditCASAActivity")
    public CasaTransactionActivityOperationResponse retrieveCasaTransactionActivity(CasaTransactionActivityOperationRequest request) {

	Context context = request.getContext();
	CASAAccountDTO casaAccountDTO = request.getCasaAccountDTO();
	CasaTransactionActivityOperationResponse returnCasaTranxActvOperResp = new CasaTransactionActivityOperationResponse();
	returnCasaTranxActvOperResp.setContext(context);

	boolean respSuccessFlg = true;
	String respCode = AccountServiceResponseCodeConstant.SUCCESS_CODE;

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	String branchCode = request.getBrnCde();// getBranchCode(request);

	CASAAccountActivityServiceResponse casaActActvServiceResp = null;

	if (branchCode != null) {

	    CASAAccountActivityServiceRequest casaAccountActivityServiceRequest = new CASAAccountActivityServiceRequest();
	    casaAccountActivityServiceRequest.setAccountNo(request.getAccountNo());
	    casaAccountActivityServiceRequest.setBranchCode(branchCode);
	    casaAccountActivityServiceRequest.setStatementTrxFlag(false);

	    /* -- Code to get page number ---- */

	    int pageSize = 50;

	    try {
		pageSize = Integer.parseInt(getSysParamValue(context, "BEM_PAGE_SIZE_CASA_ACTIVITY", ActivityConstant.MINI_STMT_ACTIVITY_ID));
	    } catch (Exception e) {
		// set default value.
		pageSize = 50;
	    }

	    int maxPages = 10;

	    try {
		maxPages = Integer.parseInt(getSysParamValue(context, "BEM_MAX_PAGES_CASA_ACTIVITY", ActivityConstant.MINI_STMT_ACTIVITY_ID));
	    } catch (Exception e) {
		// set default value.
		maxPages = 10;
	    }

	    /* page number code end */
	    Date businessDate = null;

	    businessDate = getBusinessDate(context);

	    Date endDate = businessDate;
	    Date startDate = null;
	    if ("currentperiod".equalsIgnoreCase(request.getDays())) {
		if (casaAccountDTO != null && casaAccountDTO.getStatementStartDate() != null && casaAccountDTO.getStatementEndDate() != null) {

		    startDate = casaAccountDTO.getStatementStartDate();
		    endDate = casaAccountDTO.getStatementEndDate();

		} else {
		    String parameterName = null;
		    String activityId = null;
		    int actiSearchCurrDateRange;
		    parameterName = AccountConstants.CASA_ACTIVITY_SEARCH_CURRENT_PERIOD_DATERANGE;
		    activityId = ActivityConstant.MINI_STMT_ACTIVITY_ID;
		    String returnValue = getSysParamValue(context, parameterName, activityId);
		    if (returnValue != null && !returnValue.equals(" ")) {
			actiSearchCurrDateRange = Integer.parseInt(returnValue);
		    } else {
			actiSearchCurrDateRange = AccountConstants.DEFAULT_CURRENT_PERIOD_DATERAMGE;
		    }
		    startDate = DateTimeUtil.getDateWithMonthDifference(endDate, -actiSearchCurrDateRange);
		}

	    } else {
		Integer txPeriod;
		try {
		    txPeriod = Integer.parseInt(request.getDays());
		    startDate = DateTimeUtil.getDateWithDaysDifference(endDate, -txPeriod);
		} catch (NumberFormatException e) {

		}

	    }

	    if (startDate != null) {

		casaAccountActivityServiceRequest.setStartDate(startDate);
		casaAccountActivityServiceRequest.setEndDate(endDate);
		casaAccountActivityServiceRequest.setContext(request.getContext());
		int pageNumber = 1;
		casaAccountActivityServiceRequest.setPageNo(pageNumber);

		// Service call for CASA Activity
		casaActActvServiceResp = casaDetailsService.retrieveCASAAccountActivity(casaAccountActivityServiceRequest);

		if (casaActActvServiceResp != null) {

		    // get service call.

		    AccountActivityListDTO rstObj = casaActActvServiceResp.getAccountActivityListDTO();

		    AccountActivityListDTO allObj = rstObj;

		    if (rstObj != null) {

			int records = rstObj.getActivityList().size();
			int page = 1;
			while (records >= pageSize) {
			    if (page >= maxPages) {
				break;
			    }
			    casaAccountActivityServiceRequest.setPageNo(++pageNumber);

			    casaActActvServiceResp = casaDetailsService.retrieveCASAAccountActivity(casaAccountActivityServiceRequest);

			    if (casaActActvServiceResp != null) {
				rstObj = casaActActvServiceResp.getAccountActivityListDTO();
				if (rstObj != null && rstObj.getActivityList() != null) {
				    for (AccountActivityDTO acctActivity : rstObj.getActivityList()) {
					allObj.addAccountActivity(acctActivity);
				    }
				} else {
				    break;
				}

			    }
			    records = rstObj.getActivityList().size();

			    page++;
			}
		    }

		    returnCasaTranxActvOperResp.setAccountActivityListDTO(allObj);
		}

	    } else {
		respSuccessFlg = false;
		respCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_TXN_DAY;

	    }
	} else {
	    respSuccessFlg = false;
	    respCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
	}

	returnCasaTranxActvOperResp.setSuccess(respSuccessFlg);
	returnCasaTranxActvOperResp.setResCde(respCode);
	returnCasaTranxActvOperResp.setResMsg("");

	if (!returnCasaTranxActvOperResp.isSuccess()) {
	    getMessage(returnCasaTranxActvOperResp);
	}

	return returnCasaTranxActvOperResp;
    }

    /**
     * Returns branch code for the given account number
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getBranchCode(CasaTransactionActivityOperationRequest request) {

	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(request.getContext());

	// Retrive all account List from BEM
	AllAccountServiceResponse allAccountServiceResponse = allAccountService.retrieveAllAccount(allAccountServiceRequest);

	List<CustomerAccountDTO> accountList = (List<CustomerAccountDTO>) allAccountServiceResponse.getAccountList();

	String branchCode = null;

	if (accountList != null) {
	    for (CustomerAccountDTO each : accountList) {
		if ((each.getAccountNumber()).equals(request.getAccountNo())) {
		    branchCode = each.getBranchCode();
		    break;
		}
	    }
	}
	return branchCode;
    }

    /**
     * returns business date.
     *
     * @param context
     * @return
     */
    private Date getBusinessDate(Context context) {

	Date returnDate = null;

	try {

	    String businessDate = (String) context.getContextMap().get(BUSINESS_DATE);

	    String dateFormatKey = (String) context.getContextMap().get(SHORT_DATE_FORMAT_KEY);

	    DateFormat df = new SimpleDateFormat(dateFormatKey);

	    if (businessDate == null || businessDate.equals("")) {

		returnDate = new Date();
	    } else {
		returnDate = df.parse(businessDate);
	    }
	} catch (Exception e) {
	    returnDate = new Date();
	}

	return returnDate;
    }

    /**
     * Added for the Mini statment
     *
     * @param request
     * @return
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CASA_TXN_ACTIVITY", stepId = "1", activityType = "auditCASAActivity")
    public CasaTransactionTrnxHistoryOperationResponse retrieveCasaTransactionHistoryActivity(CasaTransactionActivityOperationRequest request) {

	Context context = request.getContext();
	CasaTransactionTrnxHistoryOperationResponse returnCasaTranxActvOperResp = new CasaTransactionTrnxHistoryOperationResponse();
	returnCasaTranxActvOperResp.setContext(context);

	boolean respSuccessFlg = true;
	String respCode = AccountServiceResponseCodeConstant.SUCCESS_CODE;

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	// String branchCode = getBranchCode(request);

	CASAAccountTrnxHistoryServiceResponse casaActActvServiceResp = null;

	// if (branchCode != null) {

	    CASAAccountActivityServiceRequest casaAccountActivityServiceRequest = new CASAAccountActivityServiceRequest();
	    casaAccountActivityServiceRequest.setAccountNo(request.getAccountNo());
	// casaAccountActivityServiceRequest.setBranchCode(branchCode);
	    casaAccountActivityServiceRequest.setStatementTrxFlag(false);
	    casaAccountActivityServiceRequest.setStartDate(null);
	    casaAccountActivityServiceRequest.setEndDate(null);
	    casaAccountActivityServiceRequest.setContext(request.getContext());
	    // Service call for CASA Activity
	    casaActActvServiceResp = casaDetailsService.retrieveAcctTransactionHistory(casaAccountActivityServiceRequest);
	/*
	 * } else { respSuccessFlg = false; respCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO; }
	 */

	returnCasaTranxActvOperResp.setAccountTrnxHistoryDTO(casaActActvServiceResp.getAccountTrnxHistoryDTO());
	returnCasaTranxActvOperResp.setSuccess(respSuccessFlg);
	returnCasaTranxActvOperResp.setResCde(respCode);
	returnCasaTranxActvOperResp.setResMsg("");

	if (!returnCasaTranxActvOperResp.isSuccess()) {
	    getMessage(returnCasaTranxActvOperResp);
	}

	return returnCasaTranxActvOperResp;
    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public void setCasaDetailsService(CASADetailsService casaDetailsService) {
	this.casaDetailsService = casaDetailsService;
    }

}