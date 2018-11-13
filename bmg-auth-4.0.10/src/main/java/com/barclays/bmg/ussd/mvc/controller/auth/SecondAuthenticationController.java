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

package com.barclays.bmg.ussd.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.QuesWithPosDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.ussd.auth.operation.request.SecondAuthenticationOperationRequest;
import com.barclays.bmg.ussd.auth.operation.request.SelfRegistrationExecutionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SecondAuthenticationOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.SecondAuthenticationCommand;
import com.barclays.bmg.ussd.operation.SecondAuthenticationOperation;
import com.barclays.bmg.ussd.operation.SelfRegistrationExecutionOperation;


/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SecondAuthenticationController extends BMBAbstractCommandController {

    /**
     * The instance variable for fundTransferURL of type String
     */
    private String fundTransferURL;
    /**
     * The instance variable for billPaymentURL of type String
     */
    private String billPaymentURL;
    /**
     * The instance variable for internationalURL of type String
     */
    private String internationalURL;
    /**
     * The instance variable for externalURL of type String
     */
    private String externalURL;

    /**
     * The instance variable for selfRegistrationURL of type String
     */
    private String selfRegistrationURL;

    /**
     * The instance variable for activityId of type String
     */
    private String activityId;

    /**
     * The instance variable for secondAuthOperation of type SecondAuthenticationOperation
     */
    private SecondAuthenticationOperation secondAuthOperation;

    /**
     * The instance variable for secondAuthJSONBldr of type BMBJSONBuilder
     */
    private BMBJSONBuilder secondAuthJSONBldr;

    /**
     * The instance variable named "retrieveAllCustAcctOperation" is created.
     */
    private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;

    /**
     * The instance variable for selfRegistrationExecutionOperation of type SelfRegistrationExecutionOperation
     */
    private SelfRegistrationExecutionOperation selfRegistrationExecutionOperation;



    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	SecondAuthenticationCommand secondAuthCommand = (SecondAuthenticationCommand) command;

	String quesAndPos = secondAuthCommand.getSQA();

	SecondAuthenticationOperationRequest secondAuthenticationOperationRequest = makeRequest(request);

	SecondAuthenticationOperationResponse secondAuthenticationOperationResponse;

	boolean quesAndPosValid = (quesAndPos != null);

	if (quesAndPosValid) {

	    secondAuthenticationOperationRequest.setQuestionWithPositions(quesAndPos);

	    QuesWithPosDTO quesAndPosFromChallenge[] = (QuesWithPosDTO[]) getFromProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION,
		    RequestConstants.SECOND_AUTH_QUESTION_ID_AND_POSITIONS);

	    secondAuthenticationOperationRequest.setQuesAndPosFromChallenge(quesAndPosFromChallenge);

	    String accountNo = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.ACCOUNT_NO);
	    String branchCode = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.BRANCH_CODE);
	    String scvId = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.SCV_ID);
	    secondAuthenticationOperationRequest.setAccountNo(accountNo);
	    secondAuthenticationOperationRequest.setBranch(branchCode);
	    Context context = secondAuthenticationOperationRequest.getContext();

	    SelfRegistrationExecutionOperationRequest selfRegisExecutionOperationRequest = new SelfRegistrationExecutionOperationRequest();

	    selfRegisExecutionOperationRequest.setMobileNo(secondAuthenticationOperationRequest.getMobileNo());
	    selfRegisExecutionOperationRequest.setAccountNo(accountNo);
	    selfRegisExecutionOperationRequest.setBranchCode(branchCode);
	    selfRegisExecutionOperationRequest.setContext(context);

	    RetrieveAllCustAcctOperationRequest retrieveAllCustAcctOperationRequest = new RetrieveAllCustAcctOperationRequest();

	    context.setCustomerId(scvId);
	    retrieveAllCustAcctOperationRequest.setContext(secondAuthenticationOperationRequest.getContext());
	    // Retrieve SCVID of Customer
	    RetrieveAllCustAcctOperationResponse retrieveAllCustAcctOperationResponse = retrieveAllCustAcctOperation
		    .retrieveCustInfo(retrieveAllCustAcctOperationRequest);
	    if (retrieveAllCustAcctOperationResponse != null && retrieveAllCustAcctOperationResponse.isSuccess()) {
		CustomerDTO custDto = retrieveAllCustAcctOperationResponse.getCustomer();
		if (custDto != null) {
		    secondAuthenticationOperationRequest.setSingleCustViewId(custDto.getCustomerID());
		    secondAuthenticationOperationRequest.setUserStatusInMCE(custDto.getUserStatusInMCE());

		}
	    }

	    secondAuthenticationOperationResponse = secondAuthOperation.verifyChallengeResponse(secondAuthenticationOperationRequest);

	    if (secondAuthenticationOperationResponse != null && secondAuthenticationOperationResponse.isSuccess()) {

	    	// TODO Auto-generated method stub: add code to retrieve customer card list and put the list of cards into process map
		setIntoProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, BMGProcessConstants.SECOND_AUTH_RESPONSE,
			secondAuthenticationOperationResponse.isSuccess());
	}
	} else {
	    secondAuthenticationOperationResponse = new SecondAuthenticationOperationResponse();
	    secondAuthenticationOperationResponse.setContext(secondAuthenticationOperationRequest.getContext());
	    getMessage(secondAuthenticationOperationResponse);
	    secondAuthenticationOperationResponse.setSuccess(false);
	}

	return (BMBBaseResponseModel) secondAuthJSONBldr.createJSONResponse(secondAuthenticationOperationResponse);
    }

    /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpRequest
     * @return SecondAuthenticationOperationRequest
     */
    private SecondAuthenticationOperationRequest makeRequest(HttpServletRequest request) {

	SecondAuthenticationOperationRequest secondAuthenticationOperationRequest = new SecondAuthenticationOperationRequest();

	Context context = createContext(request);

	String mobileNo = (String) getFromProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.MOBILE_NO);

	secondAuthenticationOperationRequest.setMobileNo(mobileNo);
	context.setMobilePhone(mobileNo);

	context.setActivityId(this.activityId);
	secondAuthenticationOperationRequest.setContext(context);

	return secondAuthenticationOperationRequest;

    }

    /**
     * Getter for SecondAuthenticationOperation
     *
     *@param none
     *@return SecondAuthenticationOperation
     */
    public SecondAuthenticationOperation getSecondAuthOperation() {
	return secondAuthOperation;
    }

    /**
     * Setter for SecondAuthenticationOperation
     *
     * @param SecondAuthenticationOperation
     * @return void
     */
    public void setSecondAuthOperation(SecondAuthenticationOperation secondAuthOperation) {
	this.secondAuthOperation = secondAuthOperation;
    }

    /**
     * Getter for BMBJSONBuilder
     *
     *@param none
     *@return BMBJSONBuilder
     */
    public BMBJSONBuilder getSecondAuthJSONBldr() {
	return secondAuthJSONBldr;
    }

    /**
     * Setter for BMBJSONBuilder
     *
     * @param BMBJSONBuilder
     * @return void
     */
    public void setSecondAuthJSONBldr(BMBJSONBuilder secondAuthJSONBldr) {
	this.secondAuthJSONBldr = secondAuthJSONBldr;
    }

    /**
     * Setter for fundTransferURL
     *
     * @param fundTransferURL
     * @return void
     */
    public void setFundTransferURL(String fundTransferURL) {
	this.fundTransferURL = fundTransferURL;
    }

    /**
     * Getter for fundTransferURL
     *
     *@param none
     *@return fundTransferURL
     */
    public String getFundTransferURL() {
	return fundTransferURL;
    }

    /**
     * Getter for billPaymentURL
     *
     *@param none
     *@return billPaymentURL
     */
    public String getBillPaymentURL() {
	return billPaymentURL;
    }

    /**
     * Setter for billPaymentURL
     *
     * @param billPaymentURL
     * @return void
     */
    public void setBillPaymentURL(String billPaymentURL) {
	this.billPaymentURL = billPaymentURL;
    }

    /**
     * Getter for internationalURL
     *
     *@param none
     *@return internationalURL
     */
    public String getInternationalURL() {
	return internationalURL;
    }

    /**
     * Setter for internationalURL
     *
     * @param internationalURL
     * @return void
     */
    public void setInternationalURL(String internationalURL) {
	this.internationalURL = internationalURL;
    }

    /**
     * Getter for externalURL
     *
     *@param none
     *@return externalURL
     */
    public String getExternalURL() {
	return externalURL;
    }

    /**
     * Setter for externalURL
     *
     * @param externalURL
     * @return void
     */
    public void setExternalURL(String externalURL) {
	this.externalURL = externalURL;
    }

    /**
     * Getter for selfRegistrationURL
     *
     *@param none
     *@return selfRegistrationURL
     */
    public String getSelfRegistrationURL() {
	return selfRegistrationURL;
    }

    /**
     * Setter for selfRegistrationURL
     *
     * @param selfRegistrationURL
     * @return void
     */
    public void setSelfRegistrationURL(String selfRegistrationURL) {
	this.selfRegistrationURL = selfRegistrationURL;
    }

    /**
     * Gets the retrieve all cust acct operation.
     *
     * @return the RetrieveAllCustAcctOperation
     */
    public RetrieveAllCustAcctOperation getRetrieveAllCustAcctOperation() {
	return retrieveAllCustAcctOperation;
    }

    /**
     * Sets values for RetrieveAllCustAcctOperation.
     *
     * @param retrieveAllCustAcctOperation
     *            the retrieve all cust acct operation
     */
    public void setRetrieveAllCustAcctOperation(RetrieveAllCustAcctOperation retrieveAllCustAcctOperation) {
	this.retrieveAllCustAcctOperation = retrieveAllCustAcctOperation;
    }

    /**
     * Getter for activityId
     *
     *@param none
     *@return activityId
     */
    public String getActivityId() {
	return activityId;
    }

    /**
     * Setter for activityId
     *
     * @param activityId
     * @return void
     */
    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Getter for SelfRegistrationExecutionOperation
     *
     *@param none
     *@return SelfRegistrationExecutionOperation
     */
    public SelfRegistrationExecutionOperation getSelfRegistrationExecutionOperation() {
	return selfRegistrationExecutionOperation;
    }

    /**
     * Setter for SelfRegistrationExecutionOperation
     *
     * @param SelfRegistrationExecutionOperation
     * @return void
     */
    public void setSelfRegistrationExecutionOperation(SelfRegistrationExecutionOperation selfRegistrationExecutionOperation) {
	this.selfRegistrationExecutionOperation = selfRegistrationExecutionOperation;
    }



}
