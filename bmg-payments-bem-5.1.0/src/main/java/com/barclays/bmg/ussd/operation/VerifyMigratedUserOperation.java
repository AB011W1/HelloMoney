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

import org.apache.log4j.Logger;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.ussd.auth.operation.request.VerifyMigratedUserOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.VerifyMigratedUserOperationResponse;
import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationExecutionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerDetailsServiceResponse;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationExecutionServiceResponse;
import com.barclays.bmg.ussd.service.RetrieveCustomerDetailsService;
import com.barclays.bmg.ussd.service.SelfRegistrationExecutionService;

public class VerifyMigratedUserOperation extends BMBCommonOperation {

    private static final Logger logger = Logger.getLogger(VerifyMigratedUserOperation.class);

    private SelfRegistrationExecutionService selfRegistrationExecutionService;
    private AllAccountService allAccountService;
    private RetrieveCustomerDetailsService retrieveCustomerDetailsService;

    private static final String invalid_MobMsgKey = "BEM08793";

    private static final String invalid_AccMsgKey = "REG01171";

    private static final String AccNotFoundMsgKey = "REG01173";

    public VerifyMigratedUserOperationResponse validateCustomer(VerifyMigratedUserOperationRequest verifyMigratedUserOperationRequest) {
	logger.debug("into VerifyMigratedUserOperation.validateCustomer");

	VerifyMigratedUserOperationResponse verifyMigratedUserOperationResponse = new VerifyMigratedUserOperationResponse();
	verifyMigratedUserOperationResponse.setContext(verifyMigratedUserOperationRequest.getContext());

	// Retrieve SCVID
	SelfRegistrationExecutionServiceResponse selfRegistrationExecutionServiceResponse = retrieveSCVID(verifyMigratedUserOperationRequest);

	// Get Customer Account Details
	verifyMigratedUserOperationRequest.getContext().setCustomerId(selfRegistrationExecutionServiceResponse.getScvid());
	AllAccountServiceResponse allAccountServiceResponse = retrieveAccountsFromCBS(verifyMigratedUserOperationRequest);

	if (allAccountServiceResponse.isSuccess()) {

	    verifyMigratedUserOperationRequest.setAccountList(allAccountServiceResponse.getAccountList());
	    verifyMigratedUserOperationRequest.setBankCIF(selfRegistrationExecutionServiceResponse.getBankCIF());
	    RetrieveCustomerDetailsServiceResponse retrieveCustomerDetailsServiceResponse = retrieveCustomerDetails(verifyMigratedUserOperationRequest);

	    if (retrieveCustomerDetailsServiceResponse.isSuccess()) {
		if (validateMobileNumber(verifyMigratedUserOperationRequest.getMobileNo(), retrieveCustomerDetailsServiceResponse
			.getMobileNumberList())) {

		    if (getAccountStatus(verifyMigratedUserOperationRequest, allAccountServiceResponse)) {

			if (isAccountPresent(verifyMigratedUserOperationRequest, allAccountServiceResponse)) {
			    verifyMigratedUserOperationResponse.setSuccess(true);
			    verifyMigratedUserOperationResponse.setResCde(retrieveCustomerDetailsServiceResponse.getResCde());
			    verifyMigratedUserOperationResponse.setServiceResponse(retrieveCustomerDetailsServiceResponse.getResCde());
			    verifyMigratedUserOperationResponse.setScvid(selfRegistrationExecutionServiceResponse.getScvid());
			} else {
			    verifyMigratedUserOperationResponse.setSuccess(false);
			    verifyMigratedUserOperationResponse.setResCde(AccNotFoundMsgKey);
			}
		    } else {
			verifyMigratedUserOperationResponse.setSuccess(false);
			verifyMigratedUserOperationResponse.setResCde(invalid_AccMsgKey);
		    }

		} else {
		    verifyMigratedUserOperationResponse.setSuccess(false);
		    verifyMigratedUserOperationResponse.setResCde(invalid_MobMsgKey);
		}
	    } else {
		verifyMigratedUserOperationResponse.setSuccess(false);
		verifyMigratedUserOperationResponse.setResCde(retrieveCustomerDetailsServiceResponse.getResCde());
		verifyMigratedUserOperationResponse.setServiceResponse(retrieveCustomerDetailsServiceResponse.getResCde());
	    }
	} else {
	    verifyMigratedUserOperationResponse.setSuccess(false);
	    verifyMigratedUserOperationResponse.setResCde(allAccountServiceResponse.getResCde());
	    verifyMigratedUserOperationResponse.setServiceResponse(allAccountServiceResponse.getResCde());
	}

	logger.debug("Exit VerifyMigratedUserOperation.validateCustomer");
	return verifyMigratedUserOperationResponse;
    }

    private SelfRegistrationExecutionServiceResponse retrieveSCVID(VerifyMigratedUserOperationRequest verifyMigratedUserOperationRequest) {

	SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest = new SelfRegistrationExecutionServiceRequest();
	selfRegisExecServiceRequest.setMobileNo(verifyMigratedUserOperationRequest.getMobileNo());
	selfRegisExecServiceRequest.setAccountNo(verifyMigratedUserOperationRequest.getAccountNo());
	selfRegisExecServiceRequest.setBranchCode(verifyMigratedUserOperationRequest.getBranchCode());
	// selfRegisExecServiceRequest.setPrefLang(selfRegisInitOperationRequest.getPrefLang());

	Context context = verifyMigratedUserOperationRequest.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	verifyMigratedUserOperationRequest.setContext(context);
	selfRegisExecServiceRequest.setContext(context);
	SelfRegistrationExecutionServiceResponse selfRegistrationExecutionServiceResponse = selfRegistrationExecutionService
		.getCustomerDetailsAndRegister(selfRegisExecServiceRequest);

	selfRegistrationExecutionServiceResponse.setContext(selfRegisExecServiceRequest.getContext());
	return selfRegistrationExecutionServiceResponse;
    }

    private AllAccountServiceResponse retrieveAccountsFromCBS(VerifyMigratedUserOperationRequest verifyMigratedUserOperationRequest) {

	// Create AllAccountServiceRequest
	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(verifyMigratedUserOperationRequest.getContext());
	// Get Customer Account
	AllAccountServiceResponse allAccountServiceResponse = allAccountService.retrieveAccountsFromCBS(allAccountServiceRequest);
	return allAccountServiceResponse;
    }

    private RetrieveCustomerDetailsServiceResponse retrieveCustomerDetails(VerifyMigratedUserOperationRequest request) {

	RetrieveCustomerDetailsServiceRequest retrieveCustomerDetailsServiceRequest = new RetrieveCustomerDetailsServiceRequest();
	retrieveCustomerDetailsServiceRequest.setBankCIF(request.getBankCIF());
	retrieveCustomerDetailsServiceRequest.setContext(request.getContext());

	return retrieveCustomerDetailsService.retrieveCustomerDetails(retrieveCustomerDetailsServiceRequest);
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

    private boolean getAccountStatus(VerifyMigratedUserOperationRequest request, AllAccountServiceResponse allAccountServiceResponse) {

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

    private boolean isAccountPresent(VerifyMigratedUserOperationRequest selfRegisInitOperationRequest,
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

    private List<CASAAccountDTO> getAccountToBeAddedInMCE(VerifyMigratedUserOperationRequest request, Context context,
	    List<? extends CustomerAccountDTO> allAccountsList) {
	List<CASAAccountDTO> accountsToBeAdded = new ArrayList<CASAAccountDTO>();

	if (allAccountsList != null) {
	    Map<String, Object> contextMap = context.getContextMap();
	    String ACCOUNTTYPE_SOLO = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO).toString() : "";
	    String ACCOUNTTYPE_JOINTOR = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR).toString() : "";
	    String ACCOUNTTYPE_MANYOR = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR).toString() : "";

	    for (CustomerAccountDTO customerAccountDTO : allAccountsList) {

		if (customerAccountDTO instanceof CASAAccountDTO) {
		    CASAAccountDTO casaAccount = (CASAAccountDTO) customerAccountDTO;

		    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
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
		    	}
		    	else{
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

			String ACCOUNTTYPE_JOINTANDFIRST = contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTANDFIRST) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTANDFIRST).toString() : "";
			String ACCOUNTTYPE_AUS = (contextMap.get(SystemParameterConstant.SELFREG_ACCOUNTTYPE_AUS) != null) ? contextMap.get(SystemParameterConstant.SELFREG_ACCOUNTTYPE_AUS).toString() : "";
			if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
				|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTANDFIRST)
				|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_AUS)) {

			    if (customerAccountDTO.getAccountNumber().trim().equals(request.getAccountNo())) {
				casaAccount.setOperativeFlag(true);
			    }

			    accountsToBeAdded.add(casaAccount);
			}

		    }

		}
	    }
	}

	return accountsToBeAdded;

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

}
