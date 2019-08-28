package com.barclays.bmg.operation.accounts;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accounts.request.AccountSummaryOperationRequest;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;

public class AccountSummaryOperation extends BMBCommonOperation {

    private AllAccountService allAccountService;
    private ProductEligibilityService productEligibilityService;

    /**
     * 1. Retrieve all account list form BEM 2. Retrieve product eligibility from DB 3. Filter account list with the product eligibility critiria.
     *
     * @param request
     * @return //EvictServiceCache
     *
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CUST_ACCT_LIST", stepId = "1", activityType = "auditAccountSummary")
    public AccountSummaryOperationResponse retrieveAllAccount(AccountSummaryOperationRequest request) {

	Context context = request.getContext();
	AccountSummaryOperationResponse returnActSummaryOperationResp = new AccountSummaryOperationResponse();
	returnActSummaryOperationResp.setContext(context);
	boolean respSuccessFlg = false;
	String respCode = "";

	// set the context parameter with the activity id
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	// service request
	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(request.getContext());
	allAccountServiceRequest.setAccountType(request.getAccountType());

	// Retrive all account List from BEM
	AllAccountServiceResponse allAccountServiceResp = allAccountService.retrieveAllAccount(allAccountServiceRequest);
	respCode = allAccountServiceResp.getResCde();
	if (allAccountServiceResp.isSuccess()) {
	    respSuccessFlg = allAccountServiceResp.isSuccess();

	    List<CustomerAccountDTO> filterActList = (List<CustomerAccountDTO>) allAccountServiceResp.getAccountList();

	    List<? extends CustomerAccountDTO> orderActList = orderCustomerAccountList(filterActList);

	    List<? extends CustomerAccountDTO> flterActList = getAllAccountsByStatus(request, orderActList);
	    List<? extends CustomerAccountDTO> finalActList = filterAccountsByStatus(request, flterActList);

	    if (finalActList.isEmpty()) {
		respCode = AccountServiceResponseCodeConstant.ACT_ACTSUMMARY_NOACTFORSUMMARY;
		respSuccessFlg = false;
	    }
	    returnActSummaryOperationResp.setAccountList(finalActList);

	}

	returnActSummaryOperationResp.setSuccess(respSuccessFlg);
	returnActSummaryOperationResp.setResCde(respCode);

	if (!returnActSummaryOperationResp.isSuccess()) {
	    getMessage(returnActSummaryOperationResp);
	}

	if (AccountServiceResponseCodeConstant.FCR_HOST_ERROR.equals(respCode)
		|| AccountServiceResponseCodeConstant.PRIME_HOST_ERROR.equals(respCode)
		|| AccountServiceResponseCodeConstant.TELESCO_HOST_ERROR.equals(respCode)) {
	    getMessage(returnActSummaryOperationResp);
	}

	return returnActSummaryOperationResp;
    }

    /**
     *
     * @param request
     * @return
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CCD_ACCT_LIST", stepId = "1", activityType = "auditCCDAtAGlance")
    public AccountSummaryOperationResponse retrieveCreditCardList(AccountSummaryOperationRequest request) {

	boolean respSuccessFlg = false;
	String respCode = "";

	AccountSummaryOperationResponse accountSummaryOperationResp = new AccountSummaryOperationResponse();
	accountSummaryOperationResp.setContext(request.getContext());

	loadParameters(request.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(request.getContext());
	allAccountServiceRequest.setAccountType(request.getAccountType());
	AllAccountServiceResponse allAccountServiceResp = allAccountService.retrieveCreditCardList(allAccountServiceRequest);
	respSuccessFlg = allAccountServiceResp.isSuccess();
	if (respSuccessFlg) {
	    respCode = allAccountServiceResp.getResCde();
	    if (respSuccessFlg) {
		List<CustomerAccountDTO> filterActList = getFilterActList(request, allAccountServiceResp.getAccountList());
		if (filterActList.isEmpty()) {
		    respCode = AccountServiceResponseCodeConstant.ACT_ACTSUMMARY_NOACTFORCCD;
		    respSuccessFlg = false;
		}
		accountSummaryOperationResp.setAccountList(filterActList);
	    }
	} else {
	    respCode = AccountServiceResponseCodeConstant.ACT_ACTSUMMARY_NOACTFORCCD;
	    respSuccessFlg = false;
	}

	accountSummaryOperationResp.setSuccess(respSuccessFlg);
	accountSummaryOperationResp.setResCde(respCode);

	if (!accountSummaryOperationResp.isSuccess()) {
	    getMessage(accountSummaryOperationResp);
	}

	if (AccountServiceResponseCodeConstant.FCR_HOST_ERROR.equals(respCode)
		|| AccountServiceResponseCodeConstant.PRIME_HOST_ERROR.equals(respCode)
		|| AccountServiceResponseCodeConstant.TELESCO_HOST_ERROR.equals(respCode)) {
	    getMessage(accountSummaryOperationResp);
	}

	return accountSummaryOperationResp;
    }

    /**
     *
     * @param request
     * @param lstAccounts
     * @return
     */
    private List<CustomerAccountDTO> getFilterActList(AccountSummaryOperationRequest request, List<? extends CustomerAccountDTO> lstAccounts) {

	List<CustomerAccountDTO> filterActList = new ArrayList<CustomerAccountDTO>();

	if (ActivityConstant.CREDIT_CARD_ATAGLANCE_ACTIVITY_ID.equalsIgnoreCase(request.getActivityIDParam())) {
	    filterActList = filterCCDForAtAGlance(request, lstAccounts);
	}

	if ((ActivityConstant.CREDIT_CARD_STATEMENT_TRANS_ACTIVITY_ID.equalsIgnoreCase(request.getActivityIDParam()))
		|| (ActivityConstant.CREDIT_CARD_UNBILLED_TRANS_ACTIVITY_ID.equalsIgnoreCase(request.getActivityIDParam()))) {
	    filterActList = filterCCDForUnbilledStmtTrans(request, lstAccounts);
	}

	if (ActivityConstant.CREDIT_CARD_OWN_PAYMENT_ACTIVITY_ID.equalsIgnoreCase(request.getActivityIDParam())) {
	    filterActList = filterCCDForPayment(request, lstAccounts);
	}
	return filterActList;
    }

    /**
     * Arrange the Account List
     *
     * @param filterActList
     * @return
     */
    private List<CustomerAccountDTO> orderCustomerAccountList(List<CustomerAccountDTO> filterActList) {
	List<CustomerAccountDTO> tempActList = new ArrayList<CustomerAccountDTO>();

	List<CustomerAccountDTO> casaList = new ArrayList<CustomerAccountDTO>();
	List<CustomerAccountDTO> ccdList = new ArrayList<CustomerAccountDTO>();
	List<CustomerAccountDTO> restActList = new ArrayList<CustomerAccountDTO>();

	for (CustomerAccountDTO account : filterActList) {
	    if (account instanceof CASAAccountDTO) {
		casaList.add(account);
	    } else if (account instanceof CreditCardAccountDTO) {
		ccdList.add(account);
	    } else {
		restActList.add(account);
	    }
	}

	// Collections.sort(casaList, new CASAAccountDetailsComparator()); commented as no requirement to sort

	tempActList.addAll(casaList);
	tempActList.addAll(ccdList);
	tempActList.addAll(restActList);

	return tempActList;

    }

    /**
     * Filter the Account list by using Product eligibility service
     *
     * @param accountList
     * @param request
     * @param response
     * @return
     */
    private List<CustomerAccountDTO> filterAccountList(List<? extends CustomerAccountDTO> accountList, AccountSummaryOperationRequest request,
	    AccountSummaryOperationResponse response) {
	// TODO Auto-generated method stub
	ProductEligibilityServiceRequest productEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	productEligibilityServiceRequest.setContext(request.getContext());
	productEligibilityServiceRequest.setActivityId(request.getContext().getActivityId());
	productEligibilityServiceRequest.setRoleCategoryCode(request.getContext().getSystemId());
	productEligibilityServiceRequest.setAccountList(accountList);

	// filter account list by product eligibility
	ProductEligibilityListServiceResponse productEligListServiceResp = productEligibilityService
		.getEligibleAccounts(productEligibilityServiceRequest);

	response.setTotalAsset(productEligListServiceResp.getTotalAsset());
	response.setTotalDebt(productEligListServiceResp.getTotalDebt());
	response.setTotalNetWorth(productEligListServiceResp.getTotalNetWorth());
	return (List<CustomerAccountDTO>) productEligListServiceResp.getAccountList();

    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public void setProductEligibilityService(ProductEligibilityService productEligibilityService) {
	this.productEligibilityService = productEligibilityService;
    }

}
