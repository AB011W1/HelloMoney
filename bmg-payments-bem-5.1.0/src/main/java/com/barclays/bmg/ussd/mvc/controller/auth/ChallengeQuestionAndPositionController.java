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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.ussd.auth.operation.request.ChallengeQuestionAndPositionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.request.SelfRegistrationExecutionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.ChallengeQuestionAndPositionOperationResponse;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationExecutionOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.ChallengeQuestionAndPositionCommand;
import com.barclays.bmg.ussd.operation.ChallengeQuestionAndPositionOperation;
import com.barclays.bmg.ussd.operation.SelfRegistrationExecutionOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class ChallengeQuestionAndPositionController extends BMBAbstractCommandController {

    // private static final BMBLog logger = BMBLogUtility.getLogger(ChallengeQuestionAndPositionController.class);

    /**
     * The instance variable for challengeQuestionAndPositionJSONBldr of type BMBJSONBuilder
     */
    private BMBJSONBuilder challengeQuestionAndPositionJSONBldr;

    /**
     * The instance variable for challengeQuestionAndPositionOperation of type ChallengeQuestionAndPositionOperation
     */
    private ChallengeQuestionAndPositionOperation challengeQuestionAndPositionOperation;

    /**
     * The instance variable named "retrieveAllCustAcctOperation" is created.
     */
    private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;

    /**
     * The instance variable for selfRegistrationExecutionOperation of type SelfRegistrationExecutionOperation
     */
    private SelfRegistrationExecutionOperation selfRegistrationExecutionOperation;

    /**
     * The instance variable for activityId of type String
     */
    private String activityId;

    /**
     * The instance variable for verifyChallengeURL of type String
     */
    private String verifyChallengeURL;

    /**
     * Getter for BMBJSONBuilder
     *
     *@param none
     *@return BMBJSONBuilder
     */
    public BMBJSONBuilder getChallengeQuestionAndPositionJSONBldr() {
	return challengeQuestionAndPositionJSONBldr;
    }

    /**
     * Setter for BMBJSONBuilder
     *
     * @param BMBJSONBuilder
     * @return void
     */
    public void setChallengeQuestionAndPositionJSONBldr(BMBJSONBuilder challengeQuestionAndPositionJSONBldr) {
	this.challengeQuestionAndPositionJSONBldr = challengeQuestionAndPositionJSONBldr;
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

    /**
     * Getter for ChallengeQuestionAndPositionOperation
     *
     *@param none
     *@return ChallengeQuestionAndPositionOperation
     */
    public ChallengeQuestionAndPositionOperation getChallengeQuestionAndPositionOperation() {
	return challengeQuestionAndPositionOperation;
    }

    /**
     * Setter for ChallengeQuestionAndPositionOperation
     *
     * @param ChallengeQuestionAndPositionOperation
     * @return void
     */
    public void setChallengeQuestionAndPositionOperation(ChallengeQuestionAndPositionOperation challengeQuestionAndPositionOperation) {
	this.challengeQuestionAndPositionOperation = challengeQuestionAndPositionOperation;
    }

    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	// logger.info("into Start ChallengeQuesWithPosController.handle1");

	ChallengeQuestionAndPositionOperationRequest challengeQuesOperRequest = makeRequest(request);
	ChallengeQuestionAndPositionOperationResponse challengeQuesOperResponse;
	ChallengeQuestionAndPositionCommand challengeQuesWithPosCommand = (ChallengeQuestionAndPositionCommand) command;

	if (challengeQuesWithPosCommand != null) {

	    challengeQuesOperRequest.setQuesIdString(challengeQuesWithPosCommand.getQuestionList());
	    challengeQuesOperRequest.setMobileNumber(challengeQuesWithPosCommand.getMobileNumber());
	    setIntoProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.MOBILE_NO, challengeQuesOperRequest
		    .getMobileNumber());

	    String accountNo = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.ACCOUNT_NO);
	    String branchCode = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.BRANCH_CODE);
	 // Start Bmg call added to minimize response time for confirm screen
	    String scvId = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.SCV_ID);
	 // End Bmg call added to minimize response time for confirm screen
	    challengeQuesOperRequest.setAccountNo(accountNo);
	    challengeQuesOperRequest.setBranch(branchCode);

	    String mobileNo = challengeQuesWithPosCommand.getMobileNumber();
	    Context context = challengeQuesOperRequest.getContext();
	    context.setMobilePhone(mobileNo);
	    challengeQuesOperRequest.setContext(context);

	    SelfRegistrationExecutionOperationRequest selfRegisExecutionOperationRequest = new SelfRegistrationExecutionOperationRequest();

	    selfRegisExecutionOperationRequest.setMobileNo(mobileNo);
	    selfRegisExecutionOperationRequest.setAccountNo(accountNo);
	    selfRegisExecutionOperationRequest.setBranchCode(branchCode);
	    selfRegisExecutionOperationRequest.setContext(context);
	    RetrieveAllCustAcctOperationRequest retrieveAllCustAcctOperationRequest = new RetrieveAllCustAcctOperationRequest();
	 // Start Bmg call added to minimize response time for confirm screen
	    context.setCustomerId(scvId);
	 // End Bmg call added to minimize response time for confirm screen
	    retrieveAllCustAcctOperationRequest.setContext(context);
	    // Retrieve SCVID of Customer
	    RetrieveAllCustAcctOperationResponse retrieveAllCustAcctOperationResponse = retrieveAllCustAcctOperation
		    .retrieveCustInfo(retrieveAllCustAcctOperationRequest);
	    if (retrieveAllCustAcctOperationResponse != null && retrieveAllCustAcctOperationResponse.isSuccess()) {
		CustomerDTO custDto = retrieveAllCustAcctOperationResponse.getCustomer();
		if (custDto != null) {
		    challengeQuesOperRequest.setSingleCustViewId(custDto.getCustomerID());
		    challengeQuesOperRequest.setUserStatusInMCE(custDto.getUserStatusInMCE());

		}
	    }
	    retrieveAllCustAcctOperationRequest.setContext(context);
	    challengeQuesOperResponse = challengeQuestionAndPositionOperation.getChallengeQuestionPositions(challengeQuesOperRequest);

	    if (challengeQuesOperResponse != null && challengeQuesOperResponse.getQuestionWithPositions() != null) {
		setIntoProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.SECOND_AUTH_QUESTION_ID_AND_POSITIONS,
			challengeQuesOperResponse.getQuestionWithPositions());
	    }

	    // logger.info("into End ChallengeQuesWithPosController.handle1");

	} else {
	    challengeQuesOperResponse = new ChallengeQuestionAndPositionOperationResponse();
	    challengeQuesOperResponse.setContext(challengeQuesOperResponse.getContext());
	    challengeQuesOperResponse.setSuccess(false);
	    getMessage(challengeQuesOperResponse);
	}

	// logger.info("into End ChallengeQuesWithPosController.handle1");
	return (BMBBaseResponseModel) challengeQuestionAndPositionJSONBldr.createJSONResponse(challengeQuesOperResponse);
    }

    /**
     * This method makeRequest has the purpose to construct operation request.
     *
     * @param HttpServletRequest
     * @return ChallengeQuestionAndPositionOperationRequest
     *
     */
    private ChallengeQuestionAndPositionOperationRequest makeRequest(HttpServletRequest httpRequest) {

	ChallengeQuestionAndPositionOperationRequest request = new ChallengeQuestionAndPositionOperationRequest();
	Context context = addContext(request, httpRequest);
	request.setContext(context);
	return request;

    }

    /**
     * This method addContext has the purpose to add context to operation request.
     *
     * @param ChallengeQuestionAndPositionOperationRequest
     * @param httpRequest
     * @return Context
     */
    private Context addContext(ChallengeQuestionAndPositionOperationRequest request, HttpServletRequest httpRequest) {

	Context context = createContext(httpRequest);
	Map<String, Object> processMap = getProcessMapFromSession(httpRequest);
	context.setActivityId(this.activityId);
	request.setContext(context);
	request.setMobileNumber((String) processMap.get(SessionConstant.SESSION_MOBILE_PHONE));
	request.setSingleCustViewId((String) processMap.get(SessionConstant.SESSION_USER_ID));
	String mobileNo = (String) getFromProcessMap(httpRequest, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.MOBILE_NO);
	request.setMobileNumber(mobileNo);
	return context;

    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Getter for verifyChallengeURL
     *
     *@param none
     *@return verifyChallengeURL
     */
    public String getVerifyChallengeURL() {
	return verifyChallengeURL;
    }

    /**
     * Setter for verifyChallengeURL
     *
     * @param verifyChallengeURL
     * @return void
     */
    public void setVerifyChallengeURL(String verifyChallengeURL) {
	this.verifyChallengeURL = verifyChallengeURL;
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
