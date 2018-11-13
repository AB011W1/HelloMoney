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

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.service.RetrieveAllCustAcctService;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;
import com.barclays.bmg.ussd.auth.operation.request.SelfRegistrationExecutionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationExecutionOperationResponse;
import com.barclays.bmg.ussd.auth.service.request.AddDetailstoMCEServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.ReissuePinServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationExecutionServiceRequest;
import com.barclays.bmg.ussd.auth.service.request.UpdateDetailstoMCEServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.AddDetailstoMCEServiceResponse;
import com.barclays.bmg.ussd.auth.service.response.ReissuePinServiceResponse;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerDetailsServiceResponse;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationExecutionServiceResponse;
import com.barclays.bmg.ussd.auth.service.response.UpdateDetailstoMCEServiceResponse;
import com.barclays.bmg.ussd.service.AddDetailstoMCEService;
import com.barclays.bmg.ussd.service.ReissuePinService;
import com.barclays.bmg.ussd.service.RetrieveCustomerDetailsService;
import com.barclays.bmg.ussd.service.SMSDetailsService;
import com.barclays.bmg.ussd.service.SelfRegistrationExecutionService;
import com.barclays.bmg.ussd.service.UpdateDetailstoMCEService;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SelfRegistrationExecutionOperation extends BMBCommonOperation {

    /**
     * The instance variable active_message is to throw error code if customer is already actively registered.
     */
    private static final String active_message = "REG01172";
    /**
     * The instance variable CREATED_BY is to input value for Self Registration report.
     */
    private static final String CREATED_BY = "SHM";
    /**
     * The instance variable for selfRegistrationExecutionService of type SelfRegistrationExecutionService
     */
    private SelfRegistrationExecutionService selfRegistrationExecutionService;

    /**
     * The instance variable for retrieveCustomerDetailsService of type RetrieveCustomerDetailsService
     */
    private RetrieveCustomerDetailsService retrieveCustomerDetailsService;

    /**
     * The instance variable for allAccountService of type AllAccountService
     */
    private AllAccountService allAccountService;

    /**
     * The instance variable for addDetailstoMCEService of type AddDetailstoMCEService
     */
    private AddDetailstoMCEService addDetailstoMCEService;

    /**
     * The instance variable for updateDetailstoMCEService of type UpdateDetailstoMCEService
     */
    private UpdateDetailstoMCEService updateDetailstoMCEService;

    /**
     * The instance variable for reissuePinService of type ReissuePinService
     */
    private ReissuePinService reissuePinService;

    /**
     * The instance variable for listValueResService of type ListValueResService
     */
    private ListValueResService listValueResService;

    /**
     * The instance variable for smsDetailsService of type SMSDetailsService
     */
    private SMSDetailsService smsDetailsService;

    /**
     * The instance variable named "retrieveAllCustAcctService" is created.
     */
    private RetrieveAllCustAcctService retrieveAllCustAcctService;

    /**
     * This method getDetailsAndRegister has the purpose to get customer details and register it.
     *
     * @param SelfRegistrationExecutionOperationRequest
     * @return SelfRegistrationExecutionOperationResponse
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_SELF_REGISTRATION", stepId = "1", activityType = "auditSelfRegistration")
    public SelfRegistrationExecutionOperationResponse getDetailsAndRegister(SelfRegistrationExecutionOperationRequest selfRegisExecOperationRequest) {

	SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest = new SelfRegistrationExecutionServiceRequest();
	SelfRegistrationExecutionOperationResponse selfRegisExecOperationResponse = new SelfRegistrationExecutionOperationResponse();

	if (selfRegisExecOperationRequest.getMobileNo() != null) {
	    selfRegisExecServiceRequest.setMobileNo(selfRegisExecOperationRequest.getMobileNo());
	}

	selfRegisExecServiceRequest.setAccountNo(selfRegisExecOperationRequest.getAccountNo());

	selfRegisExecServiceRequest.setBranchCode(selfRegisExecOperationRequest.getBranchCode());
	selfRegisExecServiceRequest.setPrefLang(selfRegisExecOperationRequest.getPrefLang());

	Context context = selfRegisExecOperationRequest.getContext();
	// Start Bmg call added to minimize response time for last step
	String bankCif=(String)selfRegisExecOperationRequest.getContext().getContextMap().get(RequestConstants.BANK_CIF);
	//End Bmg call added to minimize response time for last step
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	selfRegisExecOperationRequest.setContext(context);

	selfRegisExecServiceRequest.setContext(selfRegisExecOperationRequest.getContext());
	selfRegisExecOperationResponse.setContext(selfRegisExecServiceRequest.getContext());
	// Start Bmg call added to minimize response time for last step
	String scvid=selfRegisExecServiceRequest.getContext().getCustomerId();
	//End Bmg call added to minimize response time for last step

	// Added just For Reports Data
	selfRegisExecOperationResponse.setCreatedByInMCE(CREATED_BY);
	// Start Bmg call added to minimize response time for last step
	if(bankCif!=null){
	selfRegisExecServiceRequest.setScvid(scvid);
	    selfRegisExecServiceRequest.setBankCIF(bankCif);
	    context.setCustomerId(scvid);
	  //End Bmg call added to minimize response time for last step

	    RetrieveAllCustAcctServiceRequest retrieveAllCustAcctServiceRequest = new RetrieveAllCustAcctServiceRequest();
	    retrieveAllCustAcctServiceRequest.setContext(context);
	    RetrieveAllCustAcctServiceResponse retrieveAllCustAcctServiceResponse = retrieveAllCustAcctService
		    .retrieveAllCustAccount(retrieveAllCustAcctServiceRequest);
	    CustomerDTO custDto = null;
	    String userStatusInMCE = null;
	    String mobilePhoneMCE = "";
	    if (retrieveAllCustAcctServiceResponse.getCustomer() != null) {
		custDto = retrieveAllCustAcctServiceResponse.getCustomer();
		userStatusInMCE = custDto.getUserStatusInMCE();
		mobilePhoneMCE = custDto.getMobilePhone();
	    }
	    if (retrieveAllCustAcctServiceResponse.isSuccess() && (userStatusInMCE == null)
		    || (userStatusInMCE != null && userStatusInMCE.equalsIgnoreCase(""))) {
		doRegistration(selfRegisExecServiceRequest, selfRegisExecOperationResponse, context);
	    } else if (retrieveAllCustAcctServiceResponse.isSuccess() && !userStatusInMCE.equals(null)
		    && (userStatusInMCE.equalsIgnoreCase("DEREGISTER") || userStatusInMCE.equalsIgnoreCase("BLOCK"))) {
		doReRegistration(selfRegisExecServiceRequest, selfRegisExecOperationResponse, context, userStatusInMCE, mobilePhoneMCE);
	    } else if (retrieveAllCustAcctServiceResponse.isSuccess() && !userStatusInMCE.equals(null)
		    && (userStatusInMCE.equalsIgnoreCase("ACTIVE") || userStatusInMCE.equalsIgnoreCase("SUSPEND"))) {
		doReRegistration(selfRegisExecServiceRequest, selfRegisExecOperationResponse, context, userStatusInMCE, mobilePhoneMCE);
	    } else {
		selfRegisExecOperationResponse.setSuccess(false);
		selfRegisExecOperationResponse.setResCde(retrieveAllCustAcctServiceResponse.getResCde());
		selfRegisExecOperationResponse.setServiceResponse(retrieveAllCustAcctServiceResponse.getResCde());
	    }

	} else {
	    selfRegisExecOperationResponse.setSuccess(false);
	}

	if (!selfRegisExecOperationResponse.isSuccess()) {
	    getMessage(selfRegisExecOperationResponse);
	}

	return selfRegisExecOperationResponse;
    }

    /**
     * This method doRegistration has the purpose do the Self-Registration of a customer.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @param SelfRegistrationExecutionOperationResponse
     * @param Context
     * @return none
     */
    private void doRegistration(SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest,
	    SelfRegistrationExecutionOperationResponse selfRegisExecOperationResponse, Context context) {
	AllAccountServiceResponse allAccountServiceResponse = retrieveAllAccount(selfRegisExecServiceRequest);

	if (allAccountServiceResponse.isSuccess()) {
	    if (getAccountStatus(selfRegisExecServiceRequest, allAccountServiceResponse)) {
		List<CASAAccountDTO> accountToBeAddedInMCE = getAccountToBeAddedInMCE(selfRegisExecServiceRequest, context, allAccountServiceResponse
			.getAccountList());
		if (!accountToBeAddedInMCE.isEmpty()) {
		    selfRegisExecServiceRequest.setAccountList(accountToBeAddedInMCE);
		    AddDetailstoMCEServiceResponse addDetailstoMCEServiceResponse = addDetailsToMCE(selfRegisExecServiceRequest);

		    if (addDetailstoMCEServiceResponse.isSuccess()) {
			reissuePinStatus(selfRegisExecServiceRequest, selfRegisExecOperationResponse);
		    } else {
			selfRegisExecOperationResponse.setSuccess(false);
			selfRegisExecOperationResponse.setResCde(addDetailstoMCEServiceResponse.getServiceResponse());
			selfRegisExecOperationResponse.setServiceResponse(addDetailstoMCEServiceResponse.getServiceResponse());
		    }
		}
	    }
	} else {
	    selfRegisExecOperationResponse.setSuccess(false);
	    selfRegisExecOperationResponse.setResCde(allAccountServiceResponse.getResCde());
	    selfRegisExecOperationResponse.setServiceResponse(allAccountServiceResponse.getResCde());
	}
    }

    /**
     * This method doReRegistration has the purpose do the Re-Registration of a customer.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @param SelfRegistrationExecutionOperationResponse
     * @param Context
     * @param String
     * @return none
     */
    private void doReRegistration(SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest,
	    SelfRegistrationExecutionOperationResponse selfRegisExecOperationResponse, Context context, String userMCEStatus,
	    String userMCEMobileNumber) {
	AllAccountServiceResponse allAccountServiceResponse = retrieveAllAccount(selfRegisExecServiceRequest);

	if (allAccountServiceResponse.isSuccess()) {
	    if (getAccountStatus(selfRegisExecServiceRequest, allAccountServiceResponse)) {

		List<CASAAccountDTO> accountToBeAddedInMCE = getAccountToBeAddedInMCE(selfRegisExecServiceRequest, context, allAccountServiceResponse
			.getAccountList());
		if (!accountToBeAddedInMCE.isEmpty()) {
		    if ((userMCEStatus.equalsIgnoreCase("ACTIVE") || userMCEStatus.equalsIgnoreCase("SUSPEND") || userMCEStatus
				    .equalsIgnoreCase("BLOCK"))
				    && userMCEMobileNumber.equals(selfRegisExecServiceRequest.getMobileNo())) {
				selfRegisExecOperationResponse.setSuccess(false);
				selfRegisExecOperationResponse.setResCde(active_message);
			    } else {
				selfRegisExecServiceRequest.setAccountList(accountToBeAddedInMCE);
				UpdateDetailstoMCEServiceResponse updateDetailstoMCEServiceResponse = updateDetailsToMCE(selfRegisExecServiceRequest);

				if (updateDetailstoMCEServiceResponse.isSuccess()) {
				    selfRegisExecOperationResponse.setSuccess(true);
				} else {
				    selfRegisExecOperationResponse.setSuccess(false);
				    selfRegisExecOperationResponse.setResCde(updateDetailstoMCEServiceResponse.getServiceResponse());
				    selfRegisExecOperationResponse.setServiceResponse(updateDetailstoMCEServiceResponse.getServiceResponse());
				}
			    }

		}
	    }
	} else {
	    selfRegisExecOperationResponse.setSuccess(false);
	    selfRegisExecOperationResponse.setResCde(allAccountServiceResponse.getResCde());
	    selfRegisExecOperationResponse.setServiceResponse(allAccountServiceResponse.getResCde());
	}
    }

    /**
     *
     * @param retrieveCustomerDetailsServiceResponse
     * @param selfRegisExecServiceRequest
     * @return
     */
    private boolean matchCBSMobileWithInputMobileNumber(RetrieveCustomerDetailsServiceResponse retrieveCustomerDetailsServiceResponse,
	    SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest) {

	String inputMobileNo = selfRegisExecServiceRequest.getMobileNo();
	boolean match = false;

	for (String retrievedMobileNo : retrieveCustomerDetailsServiceResponse.getMobileNumberList()) {

	    if (retrievedMobileNo != null && retrievedMobileNo.length() > 0) {
		int mobileNoLength = retrievedMobileNo.length();
		int beginIndex = mobileNoLength - inputMobileNo.length();
		if (beginIndex >= 0) {
		    String mobileNoAfterSubstring = retrievedMobileNo.substring(beginIndex, mobileNoLength);

		    if (mobileNoAfterSubstring.equals(inputMobileNo)) {

			match = true;
		    }
		}
	    }
	}
	return match;
    }

    /**
     * This method reissuePinStatus has the purpose get the pin creation status.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @param SelfRegistrationExecutionOperationResponse
     * @return none
     */
    private void reissuePinStatus(SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest,
	    SelfRegistrationExecutionOperationResponse selfRegisExecOperationResponse) {
	ReissuePinServiceResponse reissuePinServiceResponse = reissuePin(selfRegisExecServiceRequest);

	if (reissuePinServiceResponse.isSuccess()) {
	    selfRegisExecOperationResponse.setSuccess(true);
	    selfRegisExecOperationResponse.setPin(reissuePinServiceResponse.getPin());
	} else {
	    selfRegisExecOperationResponse.setSuccess(false);
	    selfRegisExecOperationResponse.setResCde(reissuePinServiceResponse.getServiceResponse());
	    selfRegisExecOperationResponse.setServiceResponse(reissuePinServiceResponse.getServiceResponse());
	}
    }

    /**
     * This method getAccountToBeAddedInMCE has the purpose to get all Accounts to be added in MCE system.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @param Context
     * @param AllAccountServiceResponse
     * @return none
     */
    private List<CASAAccountDTO> getAccountToBeAddedInMCE(SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest, Context context,
	    List<? extends CustomerAccountDTO> allAccountsList) {
	List<CASAAccountDTO> accountsToBeAdded = new ArrayList<CASAAccountDTO>();

	if (allAccountsList != null) {
	    Map<String, Object> contextMap = context.getContextMap();
	    String ACCOUNTTYPE_SOLO =  contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO).toString() : "";
	    String ACCOUNTTYPE_JOINTOR =  contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR).toString() : "";
	    String ACCOUNTTYPE_MANYOR =  contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR) != null? contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR).toString() : "";

	    for (CustomerAccountDTO customerAccountDTO : allAccountsList) {

		if (customerAccountDTO instanceof CASAAccountDTO) {
		    CASAAccountDTO casaAccount = (CASAAccountDTO) customerAccountDTO;

		    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {

		    	if(BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.ZMBRB_BUSINESS_ID)){

		    		if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
		    				|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR)){

		    			int branchCode = Integer.parseInt(customerAccountDTO.getBranchCode());
		    			int inputBranchCode = Integer.parseInt(selfRegisExecServiceRequest.getBranchCode());

		    			if (branchCode == inputBranchCode
		    					&& customerAccountDTO.getAccountNumber().equals(selfRegisExecServiceRequest.getAccountNo())) {
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
		    			int inputBranchCode = Integer.parseInt(selfRegisExecServiceRequest.getBranchCode());

		    			if (branchCode == inputBranchCode
		    					&& customerAccountDTO.getAccountNumber().equals(selfRegisExecServiceRequest.getAccountNo())) {
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
			if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
				|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTORFIRST)
				|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOROTHER)
				 || customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_AUS)) {
				//Added so as to involve self registration for AUS customers in KE start
				// customerAccountDTO.getRelationshipCode().equals("AUS")
			    if (customerAccountDTO.getAccountNumber().trim().equals(selfRegisExecServiceRequest.getAccountNo())) {
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

    /**
     * This method getAccountStatus has the purpose to get the status of Accounts linked to the customer.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @param AllAccountServiceResponse
     * @return boolean
     */
    private boolean getAccountStatus(SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest,
	    AllAccountServiceResponse allAccountServiceResponse) {

	boolean bStatus = false;
	List<? extends CustomerAccountDTO> allAccountsListSrc = getAllAccountsByStatus(selfRegisExecServiceRequest, allAccountServiceResponse
		.getAccountList());
	if (allAccountsListSrc != null && !allAccountsListSrc.isEmpty())
	    for (CustomerAccountDTO customerAccountDTO : allAccountsListSrc) {
		if (customerAccountDTO instanceof CASAAccountDTO) {
		    if (customerAccountDTO.getAccountNumber().equals(selfRegisExecServiceRequest.getAccountNo())) {
			bStatus = true;
		    }
		}
	    }
	return bStatus;
    }

    /**
     * This method retrieveAllAccount has the purpose to invoke the service for retrieving accounts from CBS.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return AllAccountServiceResponse
     */
    private AllAccountServiceResponse retrieveAllAccount(SelfRegistrationExecutionServiceRequest request) {

	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	request.getContext().setCustomerId(request.getScvid());
	allAccountServiceRequest.setContext(request.getContext());

	return allAccountService.retrieveAccountsFromCBS(allAccountServiceRequest);
    }

    /**
     * This method retrieveCustomerDetails has the purpose to invoke the service for retrieving customer details.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return RetrieveCustomerDetailsServiceResponse
     */
    private RetrieveCustomerDetailsServiceResponse retrieveCustomerDetails(SelfRegistrationExecutionServiceRequest request) {

	RetrieveCustomerDetailsServiceRequest retrieveCustomerDetailsServiceRequest = new RetrieveCustomerDetailsServiceRequest();
	retrieveCustomerDetailsServiceRequest.setBankCIF(request.getBankCIF());
	retrieveCustomerDetailsServiceRequest.setContext(request.getContext());

	return retrieveCustomerDetailsService.retrieveCustomerDetails(retrieveCustomerDetailsServiceRequest);
    }

    /**
     * This method addDetailsToMCE has the purpose to invoke the service for adding customer details to MCE.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return AddDetailstoMCEServiceResponse
     */
    private AddDetailstoMCEServiceResponse addDetailsToMCE(SelfRegistrationExecutionServiceRequest request) {
	AddDetailstoMCEServiceRequest addDetailstoMCEServiceRequest = new AddDetailstoMCEServiceRequest();
	addDetailstoMCEServiceRequest.setAccountList(request.getAccountList());
	addDetailstoMCEServiceRequest.setMobileNo(request.getMobileNo());
	addDetailstoMCEServiceRequest.setScvid(request.getScvid());
	addDetailstoMCEServiceRequest.setPrefLang(request.getPrefLang());
	addDetailstoMCEServiceRequest.setContext(request.getContext());
	addDetailstoMCEServiceRequest.setCustomerAccessStatusCode("Y");
	return addDetailstoMCEService.addDetailsToMCE(addDetailstoMCEServiceRequest);
    }

    /**
     * This method updateDetailsToMCE has the purpose to invoke the service for updating customer details to MCE.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return UpdateDetailstoMCEServiceResponse
     */
    private UpdateDetailstoMCEServiceResponse updateDetailsToMCE(SelfRegistrationExecutionServiceRequest request) {

	UpdateDetailstoMCEServiceRequest updateDetailstoMCEServiceRequest = new UpdateDetailstoMCEServiceRequest();
	updateDetailstoMCEServiceRequest.setAccountList(request.getAccountList());
	updateDetailstoMCEServiceRequest.setMobileNo(request.getMobileNo());
	updateDetailstoMCEServiceRequest.setScvid(request.getScvid());
	updateDetailstoMCEServiceRequest.setPrefLang(request.getPrefLang());
	updateDetailstoMCEServiceRequest.setContext(request.getContext());
	updateDetailstoMCEServiceRequest.setRegistrationStatus("ACTIVE");
	updateDetailstoMCEServiceRequest.setCustomerAccessStatusCode("Y");
	updateDetailstoMCEServiceRequest.setActionType("Re-register");

	return updateDetailstoMCEService.updateDetailsToMCE(updateDetailstoMCEServiceRequest);
    }

    /**
     * This method reissuePin has the purpose to invoke the service for creating/reissuing of PIN.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return ReissuePinServiceResponse
     */
    private ReissuePinServiceResponse reissuePin(SelfRegistrationExecutionServiceRequest request) {

	ReissuePinServiceRequest reissuePinServiceRequest = new ReissuePinServiceRequest();
	reissuePinServiceRequest.setMobileNumber(request.getMobileNo());
	reissuePinServiceRequest.setContext(request.getContext());
	reissuePinServiceRequest.setPrefLang(request.getPrefLang());
	return reissuePinService.reissuePin(reissuePinServiceRequest);
    }

    /**
     * This method retrieveSCVID has the purpose to invoke the service for retrieving mobile registration status in MCE by SCVID.
     *
     * @param SelfRegistrationExecutionServiceRequest
     * @return SelfRegistrationExecutionOperationResponse
     */

    public SelfRegistrationExecutionOperationResponse retrieveSCVID(SelfRegistrationExecutionOperationRequest selfRegisExecOperationRequest) {

	SelfRegistrationExecutionServiceRequest selfRegisExecServiceRequest = new SelfRegistrationExecutionServiceRequest();
	SelfRegistrationExecutionOperationResponse selfRegisExecOperationResponse = new SelfRegistrationExecutionOperationResponse();

	if (selfRegisExecOperationRequest.getMobileNo() != null) {
	    selfRegisExecServiceRequest.setMobileNo(selfRegisExecOperationRequest.getMobileNo());
	}

	selfRegisExecServiceRequest.setAccountNo(selfRegisExecOperationRequest.getAccountNo());

	selfRegisExecServiceRequest.setBranchCode(selfRegisExecOperationRequest.getBranchCode());

	Context context = selfRegisExecOperationRequest.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	selfRegisExecOperationRequest.setContext(context);

	selfRegisExecServiceRequest.setContext(selfRegisExecOperationRequest.getContext());
	selfRegisExecOperationResponse.setContext(selfRegisExecServiceRequest.getContext());

	SelfRegistrationExecutionServiceResponse selfRegisExecServiceResponse = selfRegistrationExecutionService
		.getCustomerDetailsAndRegister(selfRegisExecServiceRequest);

	selfRegisExecOperationResponse.setSuccess(selfRegisExecServiceResponse.isSuccess());
	selfRegisExecOperationResponse.setResCde(selfRegisExecServiceResponse.getServiceResponse());
	selfRegisExecOperationResponse.setServiceResponse(selfRegisExecServiceResponse.getServiceResponse());
	// Added just For Reports Data
	selfRegisExecOperationResponse.setCreatedByInMCE(CREATED_BY);

	if (selfRegisExecServiceResponse.isSuccess()) {

	    selfRegisExecServiceRequest.setScvid(selfRegisExecServiceResponse.getScvid());
	    selfRegisExecServiceRequest.setBankCIF(selfRegisExecServiceResponse.getBankCIF());

	    selfRegisExecOperationResponse.setScvid(selfRegisExecServiceResponse.getScvid());
	    selfRegisExecOperationResponse.setBankCIF(selfRegisExecServiceResponse.getBankCIF());

	    //Changes for GHIPSS receipnt name
	    selfRegisExecOperationResponse.setRecipientName(selfRegisExecServiceResponse.getRecipientName());

	} else {
	    selfRegisExecOperationResponse.setSuccess(false);
	}

	if (!selfRegisExecOperationResponse.isSuccess()) {
	    getMessage(selfRegisExecOperationResponse);
	}

	return selfRegisExecOperationResponse;
    }

    /**
     * This method sendSMSSuccessAcknowledgment has the purpose to send SMS acknowledgement for successful self registration.
     *
     * @param selfRegistrationExecutionOperationResponse
     *
     * @param SelfRegistrationExecutionOperationRequest
     *            void
     */
    public void sendSMSSuccessAcknowledgment(SelfRegistrationExecutionOperationRequest request,
	    SelfRegistrationExecutionOperationResponse selfRegistrationExecutionOperationResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);

	smsRequest.setParentResponse(selfRegistrationExecutionOperationResponse);
	smsRequest.setParentRequest(request);
	smsRequest.setPrefLang(request.getPrefLang());
	smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_PIN_REISSUE_SUCCESS));
	smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_PINISSUE_SUCCESS_PRIORITY));
	smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_PIN_REISSUE_FIELD_SUCCESS));

	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method sendSMSFailAcknowledgment has the purpose to send SMS acknowledgement for self registration failure.
     *
     * @param selfRegistrationExecutionOperationResponse
     *
     * @param SelfRegistrationExecutionOperationRequest
     *            void
     */
    public void sendSMSFailAcknowledgment(SelfRegistrationExecutionOperationRequest request,
	    SelfRegistrationExecutionOperationResponse selfRegistrationExecutionOperationResponse) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);

	smsRequest.setParentResponse(selfRegistrationExecutionOperationResponse);
	smsRequest.setParentRequest(request);

	smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_PIN_REISSUE_FAIL));
	smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_PINISSUE_FAIL_PRIORITY));
	smsRequest.setDynamicFields(getSMSPriority(request, SMSConstants.SMS_PIN_REISSUE_FIELD_FAIL));

	smsDetailsService.getSMSDetails(smsRequest);

    }

    /**
     * This method getSMSEvent has the purpose to get event, for which SMS has to be sent.
     *
     * @param SelfRegistrationExecutionOperationRequest
     * @return String
     */
    private String getSMSEvent(SelfRegistrationExecutionOperationRequest request, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String event = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return event;

    }

    /**
     * This method getSMSPriority has the purpose to get the priority of the SMS to be sent.
     *
     * @param SelfRegistrationExecutionOperationRequest
     * @return String
     */
    private String getSMSPriority(SelfRegistrationExecutionOperationRequest request, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String priority = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return priority;

    }

    /**
     * Getter for SelfRegistrationExecutionService
     *
     *@param none
     *@return SelfRegistrationExecutionService
     */
    public SelfRegistrationExecutionService getSelfRegistrationExecutionService() {
	return selfRegistrationExecutionService;
    }

    /**
     * Setter for SelfRegistrationExecutionService
     *
     * @param SelfRegistrationExecutionService
     * @return void
     */
    public void setSelfRegistrationExecutionService(SelfRegistrationExecutionService selfRegistrationExecutionService) {
	this.selfRegistrationExecutionService = selfRegistrationExecutionService;
    }

    /**
     * Getter for AllAccountService
     *
     *@param none
     *@return AllAccountService
     */
    public AllAccountService getAllAccountService() {
	return allAccountService;
    }

    /**
     * Setter for AllAccountService
     *
     * @param AllAccountService
     * @return void
     */
    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    /**
     * Getter for RetrieveCustomerDetailsService
     *
     *@param none
     *@return RetrieveCustomerDetailsService
     */
    public RetrieveCustomerDetailsService getRetrieveCustomerDetailsService() {
	return retrieveCustomerDetailsService;
    }

    /**
     * Setter for RetrieveCustomerDetailsService
     *
     * @param RetrieveCustomerDetailsService
     * @return void
     */
    public void setRetrieveCustomerDetailsService(RetrieveCustomerDetailsService retrieveCustomerDetailsService) {
	this.retrieveCustomerDetailsService = retrieveCustomerDetailsService;
    }

    /**
     * Getter for AddDetailstoMCEService
     *
     *@param none
     *@return AddDetailstoMCEService
     */
    public AddDetailstoMCEService getAddDetailstoMCEService() {
	return addDetailstoMCEService;
    }

    /**
     * Setter for AddDetailstoMCEService
     *
     * @param AddDetailstoMCEService
     * @return void
     */
    public void setAddDetailstoMCEService(AddDetailstoMCEService addDetailstoMCEService) {
	this.addDetailstoMCEService = addDetailstoMCEService;
    }

    /**
     * Getter for UpdateDetailstoMCEService
     *
     *@param none
     *@return UpdateDetailstoMCEService
     */
    public UpdateDetailstoMCEService getUpdateDetailstoMCEService() {
	return updateDetailstoMCEService;
    }

    /**
     * Setter for UpdateDetailstoMCEService
     *
     * @param UpdateDetailstoMCEService
     * @return void
     */
    public void setUpdateDetailstoMCEService(UpdateDetailstoMCEService updateDetailstoMCEService) {
	this.updateDetailstoMCEService = updateDetailstoMCEService;
    }

    /**
     * Getter for ReissuePinService
     *
     *@param none
     *@return ReissuePinService
     */
    public ReissuePinService getReissuePinService() {
	return reissuePinService;
    }

    /**
     * Setter for ReissuePinService
     *
     * @param ReissuePinService
     * @return void
     */
    public void setReissuePinService(ReissuePinService reissuePinService) {
	this.reissuePinService = reissuePinService;
    }

    /**
     * Getter for ListValueResService
     *
     *@param none
     *@return ListValueResService
     */
    @Override
    public ListValueResService getListValueResService() {
	return listValueResService;
    }

    /**
     * Setter for ListValueResService
     *
     * @param ListValueResService
     * @return void
     */
    @Override
    public void setListValueResService(ListValueResService listValueResService) {
	this.listValueResService = listValueResService;
    }

    /**
     * Getter for SMSDetailsService
     *
     *@param none
     *@return SMSDetailsService
     */
    public SMSDetailsService getSmsDetailsService() {
	return smsDetailsService;
    }

    /**
     * Setter for SMSDetailsService
     *
     * @param SMSDetailsService
     * @return void
     */
    public void setSmsDetailsService(SMSDetailsService smsDetailsService) {
	this.smsDetailsService = smsDetailsService;
    }

    /**
     * Getter for RetrieveAllCustAcctService
     *
     *@param none
     *@return RetrieveAllCustAcctService
     */
    public RetrieveAllCustAcctService getRetrieveAllCustAcctService() {
	return retrieveAllCustAcctService;
    }

    /**
     * Setter for RetrieveAllCustAcctService
     *
     * @param RetrieveAllCustAcctService
     * @return void
     */
    public void setRetrieveAllCustAcctService(RetrieveAllCustAcctService retrieveAllCustAcctService) {
	this.retrieveAllCustAcctService = retrieveAllCustAcctService;
    }

}
