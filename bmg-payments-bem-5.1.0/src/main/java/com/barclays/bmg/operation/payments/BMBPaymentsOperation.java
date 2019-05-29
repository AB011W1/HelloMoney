package com.barclays.bmg.operation.payments;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.proxy.util.MaskingMode;
import com.barclays.bmg.dao.core.proxy.util.MaskingRule;
import com.barclays.bmg.dao.core.proxy.util.MaskingRuleImpl;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.service.AddOrganizationBeneficiaryService;
import com.barclays.bmg.service.ApplyTDService;
import com.barclays.bmg.service.BillerService;
import com.barclays.bmg.service.RetreiveBeneficiaryDetailsService;
import com.barclays.bmg.service.RetreivePayeeListService;
import com.barclays.bmg.service.RetrieveBillDetailsService;
import com.barclays.bmg.service.TransactionAbilityService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.RetreiveBeneficiaryDetailsServiceRequest;
import com.barclays.bmg.service.request.RetreivePayeeListServiceRequest;
import com.barclays.bmg.service.request.TransactionAbilityRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.request.UpgradeTransactionLimitServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;
import com.barclays.bmg.service.response.RetreivePayeeListServiceResponse;
import com.barclays.bmg.service.response.TransactionAbilityResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

/**
 * @author e20027734
 *
 */
/**
 * @author e20027734
 *
 */
public class BMBPaymentsOperation extends BMBCommonOperation {
    private AllAccountService allAccountService;
    private ProductEligibilityService productEligibilityService;
    private RetreivePayeeListService retreivePayeeListService;
    private RetreiveBeneficiaryDetailsService retreiveBeneficiaryDetailsService;
    private TransactionLimitService transactionLimitService;
    private TransactionAbilityService transactionAbilityService;
    private BillerService billerService;
    private AddOrganizationBeneficiaryService addOrgBeneficairyService;
    private ApplyTDService applyTDAddProblemService;
    private RetrieveBillDetailsService retrieveBillDetailsService;

    public AddOrganizationBeneficiaryService getAddOrgBeneficairyService() {
	return addOrgBeneficairyService;
    }

    public void setAddOrgBeneficairyService(AddOrganizationBeneficiaryService addOrgBeneficairyService) {
	this.addOrgBeneficairyService = addOrgBeneficairyService;
    }

    public ApplyTDService getApplyTDAddProblemService() {
	return applyTDAddProblemService;
    }

    public void setApplyTDAddProblemService(ApplyTDService applyTDAddProblemService) {
	this.applyTDAddProblemService = applyTDAddProblemService;
    }

    /**
     * Get all the accounts for the user.
     *
     * @param request
     * @param response
     * @return
     */
    protected List<? extends CustomerAccountDTO> getAllAccounts(RequestContext request, ResponseContext response) {
	List<? extends CustomerAccountDTO> allAccounts = null;
	AllAccountServiceRequest allAccountRequest = new AllAccountServiceRequest();
	allAccountRequest.setContext(request.getContext());
	AllAccountServiceResponse allAccountResponse = allAccountService.retrieveAllAccount(allAccountRequest);

	if (allAccountResponse != null && allAccountResponse.isSuccess()) {
	    allAccounts = allAccountResponse.getAccountList();
	} else if (allAccountResponse != null) {
	    response.setResCde(allAccountResponse.getResCde());
	    response.setSuccess(false);
	}
	return allAccounts;
    }

    /**
 * Get all the Credit card for the user.
     *
     * @param request
     * @param response
     * @return
     */
    protected List<? extends CustomerAccountDTO> getCreditCardAccounts(AllAccountServiceRequest request, ResponseContext response) {
	List<? extends CustomerAccountDTO> allAccounts = null;
	AllAccountServiceRequest allAccountRequest = new AllAccountServiceRequest();
	allAccountRequest.setContext(request.getContext());
	allAccountRequest.setAccountType(request.getAccountType());
	AllAccountServiceResponse allAccountResponse = allAccountService.retrieveCreditCardList(allAccountRequest);

	if (allAccountResponse != null && allAccountResponse.isSuccess()) {
	    allAccounts = allAccountResponse.getAccountList();
	} else if (allAccountResponse != null) {
	    response.setResCde(allAccountResponse.getResCde());
	    response.setSuccess(false);
	}
	return allAccounts;
    }
     /**
     * @param accountList
     * @param request
     * @return List<CustomerAccountDTO> Get Source account list. Product indicator should be DR
     */
    protected List<? extends CustomerAccountDTO> getSourceAccountsList(List<? extends CustomerAccountDTO> accountList, RequestContext request) {

	ProductEligibilityServiceRequest sourceProductEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	sourceProductEligibilityServiceRequest.setContext(request.getContext());
	sourceProductEligibilityServiceRequest.setActivityId(request.getContext().getActivityId());
	sourceProductEligibilityServiceRequest.setProductIndicator(CommonConstants.DEBIT_PRODUCT);
	sourceProductEligibilityServiceRequest.setAccountList(accountList);
	sourceProductEligibilityServiceRequest.setDrOrCr(CommonConstants.DEBIT_PRODUCT);
	ProductEligibilityListServiceResponse sourceProductEligibilityListServiceResponse = productEligibilityService
		.getEligibleAccounts(sourceProductEligibilityServiceRequest);

	List<? extends CustomerAccountDTO> eligibleAccounts = sourceProductEligibilityListServiceResponse.getAccountList();

	return eligibleAccounts;
    }

    /**
     * @param accountList
     * @param request
     * @return List<CustomerAccountDTO> Get Destination account list. Product indicator should be CR
     */
    protected List<? extends CustomerAccountDTO> getDestinationAccountsList(List<? extends CustomerAccountDTO> accountList, RequestContext request) {

	ProductEligibilityServiceRequest destProductEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	destProductEligibilityServiceRequest.setContext(request.getContext());
	destProductEligibilityServiceRequest.setActivityId(request.getContext().getActivityId());
	destProductEligibilityServiceRequest.setProductIndicator(CommonConstants.CREDIT_PRODUCT);
	destProductEligibilityServiceRequest.setAccountList(accountList);
	destProductEligibilityServiceRequest.setDrOrCr(CommonConstants.CREDIT_PRODUCT);
	/*
	 * ProductEligibilityListServiceResponse destProductEligibilityListServiceResponse =
	 * productEligibilityService.getEligibleAccounts(destProductEligibilityServiceRequest);
	 */

	// List<? extends CustomerAccountDTO> eligibleAccounts = destProductEligibilityListServiceResponse.getAccountList();
	List<? extends CustomerAccountDTO> eligibleAccounts = destProductEligibilityServiceRequest.getAccountList();

	return eligibleAccounts;
    }

    /**
     * Get the sorted and active payee List for user
     *
     * @param request
     * @param response
     * @return
     */
    protected List<BeneficiaryDTO> retrievePayeeList(RequestContext request, ResponseContext response) {
	List<BeneficiaryDTO> payeeList = null;
	RetreivePayeeListServiceRequest retreivePayeeListServiceRequest = new RetreivePayeeListServiceRequest();
	retreivePayeeListServiceRequest.setContext(request.getContext());
	RetreivePayeeListServiceResponse retreivePayeeListServiceResponse = retreivePayeeListService
		.retreivePayeeList(retreivePayeeListServiceRequest);

	if (retreivePayeeListServiceResponse != null && retreivePayeeListServiceResponse.getPayeeList() != null) {
	    List<BeneficiaryDTO> sortedPayeeList = sortPayeeList(retreivePayeeListServiceResponse.getPayeeList());
	    payeeList = getActivePayeeList(sortedPayeeList);
	}/*
	  * else { response.setSuccess(false); if (request.getContext().getActivityId().equals(ActivityConstant.BILL_PAYMENT_ACTIVITY_ID)) {
	  * response.setResCde(BillPaymentResponseCodeConstants.NO_PAYEES_REGISTERED); } else if
	  * (request.getContext().getActivityId().equals(ActivityConstant.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID)) {
	  * response.setResCde(BeneficiaryResponseCodeConstants.NO_PAYEES_REGISTERED); } }
	  */
	BeneficiaryDTO payeeListArray[]= null;
	if(null != payeeList)
		payeeListArray = payeeList.toArray(new BeneficiaryDTO[payeeList.size()]);

	String businessId=request.getContext().getBusinessId();
	if(businessId!=null&&businessId.equals("MZBRB")){
		for(int i=0;i<payeeListArray.length;i++){
		BeneficiaryDTO benf=payeeListArray[i];
		String accno=benf.getDestinationAccountNumber();
		if(accno!=null){
		MaskingRule masker = MaskingRuleImpl.getInstance();
		accno = masker.mask(accno, MaskingMode.NUMBERPARTIAL);
		}
		benf.setDestinationAccountNumber(accno);
		payeeListArray[i]=benf;
		}
		payeeList=Arrays.asList(payeeListArray);
	}
	return payeeList;
    }

    /**
     * Sort payee list by nick name
     *
     * @param payeeList
     * @return
     */
    private List<BeneficiaryDTO> sortPayeeList(List<BeneficiaryDTO> payeeList) {

	Collections.sort(payeeList, new Comparator<BeneficiaryDTO>() {
	    public int compare(BeneficiaryDTO o1, BeneficiaryDTO o2) {
		String name = o2.getPayeeNickname();
		name = name == null ? "" : name;

		String name1 = o1.getPayeeNickname();
		name1 = name1 == null ? "" : name1;
		return name1.compareToIgnoreCase(name);
	    }
	});

	return payeeList;
    }

    /**
     * Get Active Payee lIst.
     *
     * @param sortedList
     * @return
     */
    private List<BeneficiaryDTO> getActivePayeeList(List<BeneficiaryDTO> sortedList) {

	List<BeneficiaryDTO> activePayeeList = new ArrayList<BeneficiaryDTO>();
	for (Iterator<BeneficiaryDTO> iterator = sortedList.iterator(); iterator.hasNext();) {
	    BeneficiaryDTO beneficiaryDto = iterator.next();
	    if (BillPaymentConstants.ACTIVE_STATUS.equals(beneficiaryDto.getStatus())) {
		activePayeeList.add(beneficiaryDto);
	    }
	}
	return activePayeeList;
    }

    /**
     * Get the payee information for particular payee id.
     *
     * @param request
     * @param response
     * @param payId
     * @return
     */
    protected BeneficiaryDTO getBeneficiaryDetails(RequestContext request, ResponseContext response, String payId, String payGrp) {
	RetreiveBeneficiaryDetailsServiceRequest retreiveBeneficiaryDetailsServiceRequest = new RetreiveBeneficiaryDetailsServiceRequest();

	retreiveBeneficiaryDetailsServiceRequest.setContext(request.getContext());
	retreiveBeneficiaryDetailsServiceRequest.setPayeeId(payId);
	retreiveBeneficiaryDetailsServiceRequest.setPayeeType(payGrp);

	RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetailsServiceResponse = retreiveBeneficiaryDetailsService
		.retreiveBeneficiaryDetails(retreiveBeneficiaryDetailsServiceRequest);
	BeneficiaryDTO beneficiaryDTO = null;
	if (retreiveBeneficiaryDetailsServiceResponse != null && retreiveBeneficiaryDetailsServiceResponse.isSuccess()
		&& retreiveBeneficiaryDetailsServiceResponse.getBeneficiaryDTO() != null) {
	    beneficiaryDTO = retreiveBeneficiaryDetailsServiceResponse.getBeneficiaryDTO();
	    checkForMobileBiller(request, beneficiaryDTO);
	} else {
	    response.setSuccess(false);
	    response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_INVALID_BENEFICIARY);
	}

	return beneficiaryDTO;
    }

    /**
     * Check if beneficiary is mobile provider.
     *
     * @param request
     * @param beneficiaryDTO
     */
    private void checkForMobileBiller(RequestContext request, BeneficiaryDTO beneficiaryDTO) {
	Map<String, Object> sysMap = request.getContext().getContextMap();
	String mobileBillers = (String) sysMap.get("PMT_MOBILEBILLERS");
	if (mobileBillers != null) {
	    String[] billerIds = mobileBillers.split(",");
	    if (beneficiaryDTO.getBillerId() != null) {
		String billerId = beneficiaryDTO.getBillerId();
		for (int i = 0; i < billerIds.length; i++) {
		    if (billerId.trim().equals(billerIds[i])) {
			beneficiaryDTO.setMobileProvider(true);
		    }
		}
	    }

	}
    }

    /**
     * Filter account list by Local currency.
     *
     * @param accountList
     * @param currency
     * @return
     */
    protected List<? extends CustomerAccountDTO> filterAccountListByCurrency(List<? extends CustomerAccountDTO> accountList, String currency) {
	if (StringUtils.isEmpty(currency)) {
	    return accountList;
	}
	List<CustomerAccountDTO> filteredAccountList = new ArrayList<CustomerAccountDTO>();
	List<String> currencyList = new ArrayList<String>();
	if (currency != null) {
	    for (String code : currency.split(",")) {
		currencyList.add(code);
	    }
	}
	for (CustomerAccountDTO account : accountList) {
	    if (currencyList.contains(account.getCurrency())) {
		filteredAccountList.add(account);
	    }
	}

	return filteredAccountList;
    }

    /**
     * Get a valid daily limit for customer specific to the activity Id.
     *
     * @param request
     * @param activityId
     * @return
     */
    protected BigDecimal getAValidDailyLimit(RequestContext request, String activityId) {
	BigDecimal aValidDailyLimit = null;
	TransactionLimitServiceRequest transactionLimitServiceRequest = new TransactionLimitServiceRequest();
	transactionLimitServiceRequest.setContext(request.getContext());
	transactionLimitServiceRequest.getContext().setActivityId(activityId);
	TransactionLimitServiceResponse transactionLimitServiceResponse = transactionLimitService.getTransactionLimit(transactionLimitServiceRequest);

	if (transactionLimitServiceResponse != null) {
	    aValidDailyLimit = transactionLimitServiceResponse.getAValidDailyLimit();
	}
	return aValidDailyLimit;
    }

    /**
     * @param acctNumber
     * @param accountList
     * @return Get account from account list using account number.
     */
    protected CustomerAccountDTO getAccountByAccountNumber(String acctNumber, List<? extends CustomerAccountDTO> accountList) {

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
     * Checks transaction ability for particular activity Id.
     *
     * @param request
     * @return
     */
    protected boolean checkTransactionAbility(RequestContext request) {
	TransactionAbilityRequest transactionAbilityRequest = new TransactionAbilityRequest();
	transactionAbilityRequest.setContext(request.getContext());
	TransactionAbilityResponse transactionAbilityResponse = transactionAbilityService.checkTransactionAbility(transactionAbilityRequest);

	return transactionAbilityResponse.isTransactionable();
    }

    protected void upgradeTransactionLimit(RequestContext request, BigDecimal txnAmt) {
	UpgradeTransactionLimitServiceRequest upgradeTransactionLimitServiceRequest = new UpgradeTransactionLimitServiceRequest();
	upgradeTransactionLimitServiceRequest.setContext(request.getContext());
	Amount amtInLCY = new Amount();
	amtInLCY.setAmount(txnAmt);
	upgradeTransactionLimitServiceRequest.setAmtInLCY(amtInLCY);
	upgradeTransactionLimitServiceRequest.setTxnDate(new Date());
	transactionLimitService.upgradeTransactionLimit(upgradeTransactionLimitServiceRequest);
    }

    /**
     * Get all biller list.
     *
     * @param request
     * @return
     */
    protected List<BillerDTO> getAllBillerList(RequestContext request) {
	BillerServiceRequest billerServiceRequest = new BillerServiceRequest();
	billerServiceRequest.setContext(request.getContext());
	billerServiceRequest.setBillerCategoryId("BP");
	//billerServiceRequest.setBillerCategoryId(((MergeBillerInfoOperationRequest)request).getBeneficiaryDTO().getBillerCategoryId());
	BillerServiceResponse billerServiceResponse = billerService.getAllBillerList(billerServiceRequest);
	return billerServiceResponse.getBillerList();
    }

    public AllAccountService getAllAccountService() {
	return allAccountService;
    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public ProductEligibilityService getProductEligibilityService() {
	return productEligibilityService;
    }

    public void setProductEligibilityService(ProductEligibilityService productEligibilityService) {
	this.productEligibilityService = productEligibilityService;
    }

    public RetreivePayeeListService getRetreivePayeeListService() {
	return retreivePayeeListService;
    }

    public void setRetreivePayeeListService(RetreivePayeeListService retreivePayeeListService) {
	this.retreivePayeeListService = retreivePayeeListService;
    }

    public RetreiveBeneficiaryDetailsService getRetreiveBeneficiaryDetailsService() {
	return retreiveBeneficiaryDetailsService;
    }

    public void setRetreiveBeneficiaryDetailsService(RetreiveBeneficiaryDetailsService retreiveBeneficiaryDetailsService) {
	this.retreiveBeneficiaryDetailsService = retreiveBeneficiaryDetailsService;
    }

    public TransactionLimitService getTransactionLimitService() {
	return transactionLimitService;
    }

    public void setTransactionLimitService(TransactionLimitService transactionLimitService) {
	this.transactionLimitService = transactionLimitService;
    }

    public TransactionAbilityService getTransactionAbilityService() {
	return transactionAbilityService;
    }

    public void setTransactionAbilityService(TransactionAbilityService transactionAbilityService) {
	this.transactionAbilityService = transactionAbilityService;
    }

    public BillerService getBillerService() {
	return billerService;
    }

    public void setBillerService(BillerService billerService) {
	this.billerService = billerService;
    }

	/**
	 * @return the retrieveBillDetailsService
	 */
	public RetrieveBillDetailsService getRetrieveBillDetailsService() {
		return retrieveBillDetailsService;
	}

	/**
	 * @param retrieveBillDetailsService the retrieveBillDetailsService to set
	 */
	public void setRetrieveBillDetailsService(
			RetrieveBillDetailsService retrieveBillDetailsService) {
		this.retrieveBillDetailsService = retrieveBillDetailsService;
	}

}
