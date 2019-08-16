package com.barclays.bmg.operation.accountdetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.request.CASADetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CASADetailsOperationResponse;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;
import com.barclays.bmg.utils.DateTimeUtil;

/**
 * @author BMB Team
 * 
 */

public class CASADetailsOperation extends BMBCommonOperation {

    private AllAccountService allAccountService;
    private CASADetailsService casaDetailsService;
    private ProductEligibilityService productEligibilityService;

    public static final String BUSINESS_DATE = "BUSINESS_DATE";
    public static final String SHORT_DATE_FORMAT_KEY = "dateFormatting_shortDateFormat";

    /**
     * 1. Loads the system parameter into context 2. Retrieve the CASA account details. BEM request 3. Retrieve the CASA account activity w.r.t Casa
     * account. BEM request
     * 
     * @param request
     * @return
     * 
     */

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CASA_ACCT_DETAILS", stepId = "1", activityType = "auditCASADetail")
    public CASADetailsOperationResponse retrieveCASADetails(CASADetailsOperationRequest request) {

	Context context = request.getContext();

	CASADetailsOperationResponse casaDetailsOperationResponse = new CASADetailsOperationResponse();
	casaDetailsOperationResponse.setContext(context);

	boolean respSuccessFlg = false;
	String respCode = "";

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	List<? extends CustomerAccountDTO> allAccountList = getAllAccountList(request);

	/*
	 * List<? extends CustomerAccountDTO> filterAccountList = getFilteredAccountsList( allAccountList, request);
	 */
	List<? extends CustomerAccountDTO> filterAccountList = allAccountList;

	CustomerAccountDTO casaAccount = getAccountByAccountNumber(request.getAccountNo(), allAccountList);

	boolean accountElegiblity = checkIfAcctEligible(filterAccountList, casaAccount);

	if (accountElegiblity) {
	    CASADetailsServiceRequest casaDetailsServiceRequest = new CASADetailsServiceRequest();
	    casaDetailsServiceRequest.setContext(context);
	    casaDetailsServiceRequest.setAccountNo(request.getAccountNo());
	    casaDetailsServiceRequest.setBranchCode(casaAccount.getBranchCode());

	    // Service call for CASA details
	    CASADetailsServiceResponse casaDetailsServiceResponse = casaDetailsService.retrieveCASADetails(casaDetailsServiceRequest);

	    if (casaDetailsServiceResponse != null) {
		respSuccessFlg = casaDetailsServiceResponse.isSuccess();
		respCode = casaDetailsServiceResponse.getResCde();

		// Add product description to account
		CASAAccountDTO casaAccountDetl = casaDetailsServiceResponse.getCasaAccountDTO();
		casaAccountDetl.setDesc(casaAccount.getDesc());
		casaAccountDetl.setIban(casaAccount.getIban());

		casaAccountDetl.setPriInd(casaAccount.getPriInd());

		casaDetailsOperationResponse.setCasaAccountDTO(casaAccountDetl);

		if (respSuccessFlg) {

		    CASAAccountActivityServiceRequest casaAccountActivityServiceRequest = new CASAAccountActivityServiceRequest();
		    casaAccountActivityServiceRequest.setAccountNo(request.getAccountNo());

		    casaAccountActivityServiceRequest.setStatementTrxFlag(false);
		    casaAccountActivityServiceRequest.setBranchCode(casaAccount.getBranchCode());

		    Date businessDate = null;

		    businessDate = getBusinessDate(context);

		    Date endDate = businessDate;
		    Date startDate = DateTimeUtil.getDateWithDaysDifference(endDate, -30);

		    casaAccountActivityServiceRequest.setStartDate(startDate);
		    casaAccountActivityServiceRequest.setEndDate(endDate);//
		    casaAccountActivityServiceRequest.setContext(context);
		    int pageNumber = 1;
		    casaAccountActivityServiceRequest.setPageNo(pageNumber);
		    // Service call for CASA Account Activities

		    // added for the Uganda because Uganda has saperate Mini Stetement Service
		    if (StringUtils.equalsIgnoreCase(context.getBusinessId(), "TZBRB")) {
			CASAAccountActivityServiceResponse casaActActivityServiceResp = casaDetailsService
				.retrieveCASAAccountActivity(casaAccountActivityServiceRequest);

			if (casaActActivityServiceResp != null) {

			    respSuccessFlg = casaActActivityServiceResp.isSuccess();
			    respCode = casaActActivityServiceResp.getResCde();

			    if (respSuccessFlg) {
				casaDetailsOperationResponse.setAccountActivityListDTO(casaActActivityServiceResp.getAccountActivityListDTO());
			    }

			}
		    }

		}
	    }
	} else {
	    respSuccessFlg = false;
	    respCode = AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO;
	}

	casaDetailsOperationResponse.setSuccess(respSuccessFlg);
	casaDetailsOperationResponse.setResCde(respCode);
	casaDetailsOperationResponse.setResMsg("");

	if (!casaDetailsOperationResponse.isSuccess()) {
	    getMessage(casaDetailsOperationResponse);
	}

	return casaDetailsOperationResponse;
    }

    /**
     * 1. Loads the system parameter into context 2. Retrieve the CASA account details. BEM request
     * 
     * @param request
     * @return
     * 
     */
    public CASADetailsOperationResponse retrieveCASAAccountDTO(CASADetailsOperationRequest request) {

	Context context = request.getContext();

	CASADetailsOperationResponse casaDetailsOperationResponse = new CASADetailsOperationResponse();
	casaDetailsOperationResponse.setContext(context);

	boolean respSuccessFlg = false;
	String respCode = "";

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	List<? extends CustomerAccountDTO> allAccountList = getAllAccountList(request);
	List<? extends CustomerAccountDTO> filterAccountList = allAccountList;

	CustomerAccountDTO casaAccount = getAccountByAccountNumber(request.getAccountNo(), allAccountList);

	boolean accountElegiblity = checkIfAcctEligible(filterAccountList, casaAccount);

	if (accountElegiblity) {
	    CASADetailsServiceRequest casaDetailsServiceRequest = new CASADetailsServiceRequest();
	    casaDetailsServiceRequest.setContext(context);
	    casaDetailsServiceRequest.setAccountNo(request.getAccountNo());
	    casaDetailsServiceRequest.setBranchCode(casaAccount.getBranchCode());

	    // Service call for CASA details
	    CASADetailsServiceResponse casaDetailsServiceResponse = casaDetailsService.retrieveCASADetails(casaDetailsServiceRequest);

	    if (casaDetailsServiceResponse != null) {
		respSuccessFlg = casaDetailsServiceResponse.isSuccess();
		respCode = casaDetailsServiceResponse.getResCde();

		// Add product description to account
		CASAAccountDTO casaAccountDetl = casaDetailsServiceResponse.getCasaAccountDTO();
		casaAccountDetl.setDesc(casaAccount.getDesc());
		casaAccountDetl.setIban(casaAccount.getIban());

		casaAccountDetl.setPriInd(casaAccount.getPriInd());

		casaDetailsOperationResponse.setCasaAccountDTO(casaAccountDetl);
	    }
	} else {
	    respSuccessFlg = false;
	    respCode = AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO;
	}

	casaDetailsOperationResponse.setSuccess(respSuccessFlg);
	casaDetailsOperationResponse.setResCde(respCode);
	casaDetailsOperationResponse.setResMsg("");

	if (!casaDetailsOperationResponse.isSuccess()) {
	    getMessage(casaDetailsOperationResponse);
	}

	return casaDetailsOperationResponse;
    }

    /**
     * Filters account list w.r.t. product eligibility
     * 
     * @param accountList
     * @param request
     * @return
     */
    private List<? extends CustomerAccountDTO> getFilteredAccountsList(List<? extends CustomerAccountDTO> accountList,
	    CASADetailsOperationRequest request) {

	// It is not the bug so no need to look into this
	ProductEligibilityServiceRequest sourceProductEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	sourceProductEligibilityServiceRequest.setContext(request.getContext());
	sourceProductEligibilityServiceRequest.setActivityId(request.getContext().getActivityId());
	sourceProductEligibilityServiceRequest.setProductIndicator(CommonConstants.DEBIT_PRODUCT);
	sourceProductEligibilityServiceRequest.setAccountList(accountList);
	sourceProductEligibilityServiceRequest.setDrOrCr(CommonConstants.DEBIT_PRODUCT);

	ProductEligibilityListServiceResponse sourceProductEligibilityListServiceResponse = productEligibilityService
		.getEligibleAccounts(sourceProductEligibilityServiceRequest);

	return sourceProductEligibilityListServiceResponse.getAccountList();
    }

    /**
     * Checks account legibility
     * 
     * @param accountList
     * @param custAccnt
     * @return
     */
    private boolean checkIfAcctEligible(List<? extends CustomerAccountDTO> accountList, CustomerAccountDTO custAccnt) {
	if (accountList != null) {
	    for (CustomerAccountDTO account : accountList) {
		if (account != null && custAccnt != null) {
		    if (account.getAccountNumber().equals(custAccnt.getAccountNumber())) {
			return true;
		    }
		}

	    }
	}

	return false;
    }

    /**
     * Retrieve all accounts from BEM.
     * 
     * @param request
     * @return
     */
    private List<? extends CustomerAccountDTO> getAllAccountList(CASADetailsOperationRequest request) {
	// TODO Auto-generated method stub
	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(request.getContext());

	// Retrieve all account List from BEM
	AllAccountServiceResponse allAccountServiceResponse = allAccountService.retrieveAllAccount(allAccountServiceRequest);

	@SuppressWarnings("unchecked")
	List<CustomerAccountDTO> accountList = (List<CustomerAccountDTO>) allAccountServiceResponse.getAccountList();

	return accountList;
    }

    /**
     * Returns Account DTO by passing account number
     * 
     * @param acctNumber
     * @param accountList
     * @return
     */
    private CustomerAccountDTO getAccountByAccountNumber(String acctNumber, List<? extends CustomerAccountDTO> accountList) {

	if (accountList != null && acctNumber != null && accountList.size() > 0) {
	    for (CustomerAccountDTO account : accountList) {
		if (acctNumber.equals(account.getAccountNumber())) {
		    return account;
		}
	    }
	}
	return null;
    }

    /**
     * Get the Business date
     * 
     * @param request
     * @return
     * 
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

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public void setCasaDetailsService(CASADetailsService casaDetailsService) {
	this.casaDetailsService = casaDetailsService;
    }

    public void setProductEligibilityService(ProductEligibilityService productEligibilityService) {
	this.productEligibilityService = productEligibilityService;
    }

}
