package com.barclays.bmg.operation.accountservices;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;

public class RetrieveAccountListOperation extends BMBPaymentsOperation {

    /**
     * Returns Source Account List as per PE for activity Id in context.
     * 
     * @param request
     * @return
     */
    public RetrieveAcctListOperationResponse getSourceAcctList(RetrieveAcctListOperationRequest request) {
	Context context = request.getContext();
	RetrieveAcctListOperationResponse response = new RetrieveAcctListOperationResponse();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	response.setContext(context);

	List<? extends CustomerAccountDTO> allAccounts = getAllAccounts(request, response);

	List<? extends CustomerAccountDTO> sourceAccts = getAllAccountsByStatus(request, allAccounts);

	List<? extends CustomerAccountDTO> finalList = filterAccountsByStatus(request, sourceAccts);
	// List<? extends CustomerAccountDTO> sourceAccts = getSourceAccountsList(allAccounts, request);

	if (finalList != null && !finalList.isEmpty()) {
	    response.setAcctList(finalList);
	} else {
	    response.setSuccess(false);
	    response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	}

	if (!response.isSuccess()) {
	    getMessage(response);
	}

	return response;
    }

    /**
     * Returns Credit Card Account List as per PE for activity Id in context.
     *
     * @param request
     * @return
     */
    public RetrieveAcctListOperationResponse getCreditCardAcctList(AllAccountServiceRequest request) {
	Context context = request.getContext();
	RetrieveAcctListOperationResponse response = new RetrieveAcctListOperationResponse();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	response.setContext(context);

	List<? extends CustomerAccountDTO> finalList = getCreditCardAccounts(request, response);

	if (finalList != null && !finalList.isEmpty()) {
	    response.setAcctList(finalList);
	} else {
	    response.setSuccess(false);
	    response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	}

	if (!response.isSuccess()) {
	    getMessage(response);
	}

	return response;
    }

    /**
     * Returns Only CASA Account List as per PE for activity Id in context.
     * 
     * @param request
     * @return
     */
    public RetrieveAcctListOperationResponse getCASASourceAccounts(RetrieveAcctListOperationRequest request) {
	List<CustomerAccountDTO> casaList = null;
	RetrieveAcctListOperationResponse response = getSourceAcctList(request);
	if (response.isSuccess()) {
	    List<? extends CustomerAccountDTO> sourceAccts = response.getAcctList();
	    casaList = new ArrayList<CustomerAccountDTO>();
	    if (sourceAccts != null && !sourceAccts.isEmpty()) {
		for (CustomerAccountDTO sourceAcct : sourceAccts) {
		    if (sourceAcct instanceof CASAAccountDTO) {
			casaList.add(sourceAcct);
		    }
		}
	    }
	    if (casaList != null && !casaList.isEmpty()) {
		response.setAcctList(casaList);
	    } else {
		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	    }

	    if (!response.isSuccess()) {
		getMessage(response);
	    }

	}

	return response;
    }

    /**
     * Returns Only CASA Account List as per PE for activity Id in context.
     * 
     * @param request
     * @return
     */
    public RetrieveAcctListOperationResponse getCASASourceAccountsForLocalCurrency(RetrieveAcctListOperationRequest request) {
	List<CustomerAccountDTO> casaList = null;
	RetrieveAcctListOperationResponse response = getSourceAcctList(request);
	String localCurr = request.getContext().getLocalCurrency();
	if (response.isSuccess()) {
	    List<? extends CustomerAccountDTO> sourceAccts = response.getAcctList();
	    casaList = new ArrayList<CustomerAccountDTO>();
	    if (sourceAccts != null && !sourceAccts.isEmpty()) {
		for (CustomerAccountDTO sourceAcct : sourceAccts) {
		    if (sourceAcct instanceof CASAAccountDTO) {
			if (localCurr.equals(sourceAcct.getCurrency())) {
			    casaList.add(sourceAcct);
			}
		    }
		}
	    }
	    if (casaList != null && !casaList.isEmpty()) {
		response.setAcctList(casaList);
	    } else {
		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	    }
	}

	if (!response.isSuccess()) {
	    getMessage(response);
	}
	return response;
    }

    /**
     * Returns Source Account List as per PE for activity Id in context and filtered as per local currency.
     * 
     * @param request
     * @return
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST", serviceDescription = "SD_RETRIEVE_IND_BENE_LIST", stepId = "1", activityType = "auditPayeeList")
    public RetrieveAcctListOperationResponse getSourceAccountsForLocalCurrency(RetrieveAcctListOperationRequest request) {
	List<CustomerAccountDTO> currSrcActLst = null;
	String localCurr = request.getContext().getLocalCurrency();
	RetrieveAcctListOperationResponse response = getSourceAcctList(request);
	if (response.isSuccess() && !StringUtils.isEmpty(localCurr)) {
	    List<? extends CustomerAccountDTO> sourceAccts = response.getAcctList();
	    currSrcActLst = new ArrayList<CustomerAccountDTO>();

	    if (sourceAccts != null && !sourceAccts.isEmpty()) {
		for (CustomerAccountDTO sourceAcct : sourceAccts) {
		    if (localCurr.equals(sourceAcct.getCurrency())) {
			currSrcActLst.add(sourceAcct);
		    }
		}
	    }
	    if (currSrcActLst != null && !currSrcActLst.isEmpty()) {
		response.setAcctList(currSrcActLst);
	    } else {
		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	    }

	    if (!response.isSuccess()) {
		getMessage(response);
	    }

	}

	return response;
    }

    public RetrieveAcctListOperationResponse getDestAccountsForLocalCurrency(RetrieveAcctListOperationRequest request) {
	List<CustomerAccountDTO> currSrcActLst = null;
	String localCurr = request.getContext().getLocalCurrency();
	RetrieveAcctListOperationResponse response = getDestAcctList(request);
	if (response.isSuccess() && !StringUtils.isEmpty(localCurr)) {
	    List<? extends CustomerAccountDTO> sourceAccts = response.getAcctList();
	    currSrcActLst = new ArrayList<CustomerAccountDTO>();

	    if (sourceAccts != null && !sourceAccts.isEmpty()) {
		for (CustomerAccountDTO sourceAcct : sourceAccts) {
		    if (localCurr.equals(sourceAcct.getCurrency())) {
			currSrcActLst.add(sourceAcct);
		    }
		}
	    }
	    if (currSrcActLst != null && !currSrcActLst.isEmpty()) {
		response.setAcctList(currSrcActLst);
	    } else {
		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	    }

	    if (!response.isSuccess()) {
		getMessage(response);
	    }

	}

	return response;
    }

    /**
     * Returns Destination Account List as per PE for activity Id in context
     * 
     * @param request
     * @return
     */

    public RetrieveAcctListOperationResponse getDestAcctList(RetrieveAcctListOperationRequest request) {
	Context context = request.getContext();
	RetrieveAcctListOperationResponse response = new RetrieveAcctListOperationResponse();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	response.setContext(context);

	List<? extends CustomerAccountDTO> allAccounts = getAllAccounts(request, response);
	// 893 Issues Fixes
	allAccounts = getAllAccountsByStatus(request, allAccounts);
	List<? extends CustomerAccountDTO> destAccts = getDestinationAccountsList(allAccounts, request);

	if (destAccts != null && !destAccts.isEmpty()) {
	    response.setAcctList(destAccts);
	} else {
	    response.setSuccess(false);
	    response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	}

	if (!response.isSuccess()) {
	    getMessage(response);
	}

	return response;
    }

    /**
     * Returns Only CASA Account List for Destination as per PE for activity Id in context.
     * 
     * @param request
     * @return
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST", serviceDescription = "SD_RETRIEVE_IND_BENE_LIST", stepId = "1", activityType = "auditAcctList")
    public RetrieveAcctListOperationResponse getCASADestAccounts(RetrieveAcctListOperationRequest request) {
	List<CustomerAccountDTO> casaList = null;
	RetrieveAcctListOperationResponse response = getDestAcctList(request);
	if (response.isSuccess()) {
	    List<? extends CustomerAccountDTO> destAccts = response.getAcctList();
	    casaList = new ArrayList<CustomerAccountDTO>();
	    if (destAccts != null && !destAccts.isEmpty()) {
		for (CustomerAccountDTO destAcct : destAccts) {
		    if (destAcct instanceof CASAAccountDTO) {
			casaList.add(destAcct);
		    }
		}
	    }
	    if (casaList != null && !casaList.isEmpty()) {
		response.setAcctList(casaList);
	    } else {
		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	    }

	    if (!response.isSuccess()) {
		getMessage(response);
	    }

	}

	return response;
    }
}
