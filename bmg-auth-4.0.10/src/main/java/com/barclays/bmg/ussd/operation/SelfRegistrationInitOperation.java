/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.ussd.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.ussd.auth.operation.request.SelfRegistrationInitOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationAccountValidationOperationResponse;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationInitOperationResponse;
import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationExecutionServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationInitServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerDetailsServiceResponse;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationExecutionServiceResponse;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationInitServiceResponse;
import com.barclays.bmg.ussd.service.AddDetailstoMCEService;
import com.barclays.bmg.ussd.service.RetrieveCustomerDetailsService;
import com.barclays.bmg.ussd.service.SelfRegistrationExecutionService;
import com.barclays.bmg.ussd.service.SelfRegistrationInitService;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationInitOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SelfRegistrationInitOperation extends BMBCommonOperation {

    /**
     * The instance variable for selfRegistrationInitService of type SelfRegistrationInitService
     */
    private SelfRegistrationInitService selfRegistrationInitService;

    private SelfRegistrationExecutionService selfRegistrationExecutionService;

    private AllAccountService allAccountService;

    private RetrieveCustomerDetailsService retrieveCustomerDetailsService;

    private AddDetailstoMCEService addDetailstoMCEService;

    private static final String invalid_MobMsgKey = "BEM08793";

    private static final String invalid_AccMsgKey = "REG01171";

    private static final String AccNotFoundMsgKey = "REG01173";


    /**
     * This method createCustomerInCrypto has the purpose to create customer in crypto.
     *
     * @param SelfRegistrationInitOperationRequest
     * @return SelfRegistrationInitOperationResponse
     */
    public SelfRegistrationInitOperationResponse createCustomerInCrypto(SelfRegistrationInitOperationRequest selfRegisInitOperationRequest) {


    	SelfRegistrationInitOperationResponse selfRegistrationInitOperationResponse = new SelfRegistrationInitOperationResponse();
	    SelfRegistrationInitServiceRequest selfRegisInitServiceRequest = new SelfRegistrationInitServiceRequest();
	    selfRegisInitServiceRequest.setMobileNo(selfRegisInitOperationRequest.getMobileNo());
	    selfRegisInitServiceRequest.setAccountNo(selfRegisInitOperationRequest.getAccountNo());
	    selfRegisInitServiceRequest.setBranchCode(selfRegisInitOperationRequest.getBranchCode());

	    Context context = selfRegisInitOperationRequest.getContext();

	    loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	    selfRegisInitOperationRequest.setContext(context);
	    selfRegisInitServiceRequest.setContext(selfRegisInitOperationRequest.getContext());

	    SelfRegistrationInitServiceResponse selfRegisInitServiceResponse = selfRegistrationInitService
		    .createCustomerInCrypto(selfRegisInitServiceRequest);

	    selfRegistrationInitOperationResponse.setServiceResponse(selfRegisInitServiceResponse.getServiceResponse());
	    selfRegistrationInitOperationResponse.setSuccess(selfRegisInitServiceResponse.isSuccess());
	    selfRegistrationInitOperationResponse.setContext(selfRegisInitServiceRequest.getContext());
	    selfRegistrationInitOperationResponse.setResCde(selfRegisInitServiceResponse.getServiceResponse());

	    if (!selfRegistrationInitOperationResponse.isSuccess()) {
		getMessage(selfRegistrationInitOperationResponse);
	    }

	//}
	return selfRegistrationInitOperationResponse;
    }

    // Bmg call added to minimize response time for confirm screen
    public SelfRegistrationInitOperationResponse validateCustomer(SelfRegistrationInitOperationRequest selfRegisInitOperationRequest) {

	Context context = selfRegisInitOperationRequest.getContext();
	SelfRegistrationInitOperationResponse selfRegistrationInitOperationResponse = new SelfRegistrationInitOperationResponse();


	// Retrieve SCVID
	SelfRegistrationExecutionServiceResponse selfRegisExecServiceResponse = retrieveSCVID(selfRegisInitOperationRequest);

	//context.getContextMap().put("selfRegisExecServiceResponse", selfRegisExecServiceResponse);


	selfRegistrationInitOperationResponse.setContext(context);
	selfRegistrationInitOperationResponse.getContext().setCustomerId(selfRegisExecServiceResponse.getScvid());
	selfRegistrationInitOperationResponse.getContext().getContextMap().put(RequestConstants.BANK_CIF, selfRegisExecServiceResponse.getBankCIF());
	// Get Customer Account Details
	selfRegisInitOperationRequest.getContext().setCustomerId(selfRegisExecServiceResponse.getScvid());
	AllAccountServiceResponse allAccountServiceResponse = retrieveAccountsFromCBS(selfRegisInitOperationRequest);

	if (allAccountServiceResponse.isSuccess()) {

	    selfRegisInitOperationRequest.setAccountList(allAccountServiceResponse.getAccountList());
	    selfRegisInitOperationRequest.setBankCIF(selfRegisExecServiceResponse.getBankCIF());
	    RetrieveCustomerDetailsServiceResponse retrieveCustomerDetailsServiceResponse = retrieveCustomerDetails(selfRegisInitOperationRequest);

	    if (retrieveCustomerDetailsServiceResponse.isSuccess()) {
		if (validateMobileNumber(selfRegisInitOperationRequest.getMobileNo(), retrieveCustomerDetailsServiceResponse.getMobileNumberList())) {

		    if (getAccountStatus(selfRegisInitOperationRequest, allAccountServiceResponse)) {

			if (isAccountPresent(selfRegisInitOperationRequest, allAccountServiceResponse)) {
				selfRegistrationInitOperationResponse.setSuccess(true);
			} else {
				selfRegistrationInitOperationResponse.setSuccess(false);
				selfRegistrationInitOperationResponse.setResCde(AccNotFoundMsgKey);
			}
		    } else {
		    	selfRegistrationInitOperationResponse.setSuccess(false);
		    	selfRegistrationInitOperationResponse.setResCde(invalid_AccMsgKey);
		    }

		} else {
			selfRegistrationInitOperationResponse.setSuccess(false);
			selfRegistrationInitOperationResponse.setResCde(invalid_MobMsgKey);
		}
	    } else {
	    	selfRegistrationInitOperationResponse.setSuccess(false);
	    	selfRegistrationInitOperationResponse.setResCde(retrieveCustomerDetailsServiceResponse.getResCde());
	    	selfRegistrationInitOperationResponse.setServiceResponse(retrieveCustomerDetailsServiceResponse.getResCde());
	    }
	} else {
		selfRegistrationInitOperationResponse.setSuccess(false);
		selfRegistrationInitOperationResponse.setResCde(allAccountServiceResponse.getResCde());
		selfRegistrationInitOperationResponse.setServiceResponse(allAccountServiceResponse.getResCde());
	}
	return selfRegistrationInitOperationResponse;

    }

    private boolean validateMobileNumber(String inputMobileNo, List<String> retrievedMobileNumbers) {

	if (retrievedMobileNumbers == null || retrievedMobileNumbers.isEmpty()) {
	    return false;
	} else {

	    for (String retrievedMobileNumber : retrievedMobileNumbers) {

		if (retrievedMobileNumber != null && !retrievedMobileNumber.isEmpty()) {
		    int mobileNoLength = retrievedMobileNumber.length();
		    int beginIndex = mobileNoLength - inputMobileNo.length();

		    if (beginIndex >= 0) {
			String mobileNoAfterSubstringCBS = retrievedMobileNumber.substring(beginIndex, mobileNoLength);
			if (mobileNoAfterSubstringCBS.equals(inputMobileNo)) {
			    return true;
			}
		    }
		}
	    }

	}
	return false;
    }

    /**
     *
     * @param selfRegisInitOperationRequest
     * @return
     */
    private AllAccountServiceResponse retrieveAccountsFromCBS(SelfRegistrationInitOperationRequest selfRegisInitOperationRequest) {

	// Create AllAccountServiceRequest
	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(selfRegisInitOperationRequest.getContext());
	// Get Customer Account
	AllAccountServiceResponse allAccountServiceResponse = allAccountService.retrieveAccountsFromCBS(allAccountServiceRequest);
	return allAccountServiceResponse;
    }

    /**
     * This method retrieveSCVID has the purpose to invoke the service for retrieving mobile registration status in MCE by SCVID.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return SelfRegistrationExecutionOperationResponse
     */

    private SelfRegistrationExecutionServiceResponse retrieveSCVID(SelfRegistrationInitOperationRequest selfRegistrationInitOperationRequest) {

	SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest = new SelfRegistrationExecutionServiceRequest();
	selfRegisExecServiceRequest.setMobileNo(selfRegistrationInitOperationRequest.getMobileNo());
	selfRegisExecServiceRequest.setAccountNo(selfRegistrationInitOperationRequest.getAccountNo());
	selfRegisExecServiceRequest.setBranchCode(selfRegistrationInitOperationRequest.getBranchCode());
	// selfRegisExecServiceRequest.setPrefLang(selfRegisInitOperationRequest.getPrefLang());

	Context context = selfRegistrationInitOperationRequest.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	selfRegistrationInitOperationRequest.setContext(context);
	selfRegisExecServiceRequest.setContext(context);
	SelfRegistrationExecutionServiceResponse selfRegisExecServiceResponse = selfRegistrationExecutionService
		.getCustomerDetailsAndRegister(selfRegisExecServiceRequest);

	selfRegisExecServiceResponse.setContext(selfRegisExecServiceRequest.getContext());
	return selfRegisExecServiceResponse;
    }

    /**
     * This method getAccountStatus has the purpose to get the status of Accounts linked to the customer.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @param AllAccountServiceResponse
     * @return boolean
     */
    private boolean getAccountStatus(SelfRegistrationInitOperationRequest request, AllAccountServiceResponse allAccountServiceResponse) {

	boolean bStatus = false;
	List<? extends CustomerAccountDTO> allAccountsListSrc = getAllAccountsByStatus(request, allAccountServiceResponse.getAccountList());

	if (allAccountsListSrc != null && !allAccountsListSrc.isEmpty())
	    for (CustomerAccountDTO customerAccountDTO : allAccountsListSrc) {
		if (customerAccountDTO instanceof CASAAccountDTO) {
		    if (customerAccountDTO.getAccountNumber().equals(request.getAccountNo())) {
			bStatus = true;
		    }
		}
	    }
	return bStatus;
    }

    /**
     * This method getAccountToBeAddedInMCE has the purpose to get all Accounts to be added in MCE system.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @param Context
     * @param AllAccountServiceResponse
     * @return none
     */
    /*
     * private List<CASAAccountDTO> getAccountToBeAddedInMCE(SelfRegistrationInitOperationRequest request, Context context, List<? extends
     * CustomerAccountDTO> allAccountsList) { List<CASAAccountDTO> accountsToBeAdded = new ArrayList<CASAAccountDTO>();
     *
     * if (allAccountsList != null) { Map<String, Object> contextMap = context.getContextMap(); String ACCOUNTTYPE_SOLO =
     * contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO).toString(); String ACCOUNTTYPE_JOINTOR =
     * contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR).toString(); String ACCOUNTTYPE_MANYOR =
     * contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR).toString();
     *
     * for (CustomerAccountDTO customerAccountDTO : allAccountsList) {
     *
     * if (customerAccountDTO instanceof CASAAccountDTO) {
     *
     * if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO) || customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR)
     * || customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_MANYOR)) { CASAAccountDTO casaAccount = (CASAAccountDTO) customerAccountDTO;
     * accountsToBeAdded.add(casaAccount); } } }
     *
     * }
     *
     * return accountsToBeAdded;
     *
     * }
     */

    private List<CASAAccountDTO> getAccountToBeAddedInMCE(SelfRegistrationInitOperationRequest request, Context context,
	    List<? extends CustomerAccountDTO> allAccountsList) {
	List<CASAAccountDTO> accountsToBeAdded = new ArrayList<CASAAccountDTO>();

	if (allAccountsList != null) {
	    Map<String, Object> contextMap = context.getContextMap();
	    String ACCOUNTTYPE_SOLO = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO).toString() : "";
	    String ACCOUNTTYPE_JOINTOR = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR).toString() : "";
	    String ACCOUNTTYPE_MANYOR = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR).toString() : "";
	    String ACCOUNTTYPE_JOINTAND = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTAND) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTAND).toString() : "";
	    String ACCOUNTTYPE_MANYAND = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYAND) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYAND).toString() : "";

	    for (CustomerAccountDTO customerAccountDTO : allAccountsList) {

		if (customerAccountDTO instanceof CASAAccountDTO) {
		    CASAAccountDTO casaAccount = (CASAAccountDTO) customerAccountDTO;

		    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
		    	 //modification for defect #2046
		    	 //Modified for Defect 2181
		    	if (customerAccountDTO.getAccountNumber().trim().equals(request.getAccountNo())) {
		    	if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTAND) ||
					    	customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_MANYAND))
					    {
					    	accountsToBeAdded.clear();
					    	return accountsToBeAdded;
					    }
		    	}
		    	if(BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.ZMBRB_BUSINESS_ID)){
		    		if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
			    			|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR)){

			    		int branchCode = Integer.parseInt(customerAccountDTO.getBranchCode());
			    		int inputBranchCode = Integer.parseInt(request.getBranchCode());

			    		if (branchCode == inputBranchCode && customerAccountDTO.getAccountNumber().equals(request.getAccountNo())) {
			    			casaAccount.setOperativeFlag(true);
			    		}

			    		accountsToBeAdded.add(casaAccount);
			    	}
		    	}else{
		    		if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
			    			|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR)
			    			|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_MANYOR)) {

			    		int branchCode = Integer.parseInt(customerAccountDTO.getBranchCode());
			    		int inputBranchCode = Integer.parseInt(request.getBranchCode());

			    		if (branchCode == inputBranchCode && customerAccountDTO.getAccountNumber().equals(request.getAccountNo())) {
			    			casaAccount.setOperativeFlag(true);
			    		}

			    		accountsToBeAdded.add(casaAccount);
			    	}

		    	}

		    } else {

			// String ACCOUNTTYPE_JOINTANDFIRST = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTANDFIRST).toString();
			String ACCOUNTTYPE_JOINTORFIRST = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTORFIRST) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTORFIRST).toString() : "";
			String ACCOUNTTYPE_JOINTOROTHER = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOROTHER) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOROTHER).toString() : "";
			String ACCOUNTTYPE_AUS = contextMap.get(SystemParameterConstant.SELFREG_ACCOUNTTYPE_AUS) != null? contextMap.get(SystemParameterConstant.SELFREG_ACCOUNTTYPE_AUS).toString() : "";
			if (customerAccountDTO.getAccountNumber().trim().equals(request.getAccountNo())) {
			    if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
				    || customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTORFIRST)
				    || customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOROTHER)
				    || customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_AUS)) {
			    	//Added so as to involve self registration for AUS customers in KE start
			    	// customerAccountDTO.getRelationshipCode().equals("AUS")
				accountsToBeAdded.add(casaAccount);
				casaAccount.setOperativeFlag(true);
			    }

			}
		    }
		}
	    }
	}

	return accountsToBeAdded;

    }

    /**
     * This method retrieveCustomerDetails has the purpose to invoke the service for retrieving customer details.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return RetrieveCustomerDetailsServiceResponse
     */
    private RetrieveCustomerDetailsServiceResponse retrieveCustomerDetails(SelfRegistrationInitOperationRequest request) {

	RetrieveCustomerDetailsServiceRequest retrieveCustomerDetailsServiceRequest = new RetrieveCustomerDetailsServiceRequest();
	retrieveCustomerDetailsServiceRequest.setBankCIF(request.getBankCIF());
	retrieveCustomerDetailsServiceRequest.setContext(request.getContext());

	return retrieveCustomerDetailsService.retrieveCustomerDetails(retrieveCustomerDetailsServiceRequest);
    }

    private boolean isAccountPresent(SelfRegistrationInitOperationRequest selfRegisInitOperationRequest,
	    AllAccountServiceResponse allAccountServiceResponse) {
	boolean isAccountPresent = true;

	// Validation 1.
	List<CASAAccountDTO> accountToBeAddedInMCE = getAccountToBeAddedInMCE(selfRegisInitOperationRequest, selfRegisInitOperationRequest
		.getContext(), allAccountServiceResponse.getAccountList());

	if (accountToBeAddedInMCE.isEmpty()) {
	    isAccountPresent = false;
	}
	return isAccountPresent;
    }

    /**
     * Getter for SelfRegistrationInitService
     *
     *@param none
     *@return SelfRegistrationInitService
     */
    public SelfRegistrationInitService getSelfRegistrationInitService() {
	return selfRegistrationInitService;
    }

    /**
     * Setter for SelfRegistrationInitService
     *
     * @param SelfRegistrationInitService
     * @return void
     */
    public void setSelfRegistrationInitService(SelfRegistrationInitService selfRegistrationInitService) {
	this.selfRegistrationInitService = selfRegistrationInitService;
    }

    public SelfRegistrationExecutionService getSelfRegistrationExecutionService() {
	return selfRegistrationExecutionService;
    }

    public void setSelfRegistrationExecutionService(SelfRegistrationExecutionService selfRegistrationExecutionService) {
	this.selfRegistrationExecutionService = selfRegistrationExecutionService;
    }

    public AllAccountService getAllAccountService() {
	return allAccountService;
    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public RetrieveCustomerDetailsService getRetrieveCustomerDetailsService() {
	return retrieveCustomerDetailsService;
    }

    public void setRetrieveCustomerDetailsService(RetrieveCustomerDetailsService retrieveCustomerDetailsService) {
	this.retrieveCustomerDetailsService = retrieveCustomerDetailsService;
    }

    public AddDetailstoMCEService getAddDetailstoMCEService() {
	return addDetailstoMCEService;
    }

    public void setAddDetailstoMCEService(AddDetailstoMCEService addDetailstoMCEService) {
	this.addDetailstoMCEService = addDetailstoMCEService;
    }

    public SelfRegistrationInitOperationResponse nameQueryByAccountNoAndBankCode(SelfRegistrationInitOperationRequest selfRegisInitOperationRequest) {

    	Context context = selfRegisInitOperationRequest.getContext();
    	SelfRegistrationInitOperationResponse selfRegistrationInitOperationResponse = new SelfRegistrationInitOperationResponse();
    	SelfRegistrationExecutionServiceResponse selfRegisExecServiceResponse = retrieveSCVID(selfRegisInitOperationRequest);
    	selfRegistrationInitOperationResponse.setContext(context);
    	selfRegistrationInitOperationResponse.getContext().setCustomerId(selfRegisExecServiceResponse.getScvid());
    	selfRegistrationInitOperationResponse.getContext().setFullName(selfRegisExecServiceResponse.getRecipientName());//Changes for GHIPSS
    	selfRegisInitOperationRequest.getContext().setCustomerId(selfRegisExecServiceResponse.getScvid());
    	return selfRegistrationInitOperationResponse;

        }
}
