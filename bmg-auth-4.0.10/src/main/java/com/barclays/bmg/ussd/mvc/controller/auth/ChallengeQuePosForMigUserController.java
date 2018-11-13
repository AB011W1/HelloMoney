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

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.ussd.auth.operation.request.ChallengeQuestionAndPositionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.ChallengeQuestionAndPositionOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.ChallengeQuestionAndPositionCommand;
import com.barclays.bmg.ussd.operation.ChallengeQuestionAndPositionOperation;

public class ChallengeQuePosForMigUserController extends BMBAbstractCommandController {

    private static final Logger LOGGER = Logger.getLogger(ChallengeQuePosForMigUserController.class);

    private BMBJSONBuilder challengeQuestionAndPositionJSONBldr;

    private ChallengeQuestionAndPositionOperation challengeQuestionAndPositionOperation;

    private String activityId;

    private String verifyChallengeURL;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	LOGGER.debug(" End ChallengeQuePosForMigUserController.handle1");

	ChallengeQuestionAndPositionOperationResponse challengeQuesOperResponse;
	ChallengeQuestionAndPositionCommand challengeQuesWithPosCommand = (ChallengeQuestionAndPositionCommand) command;

	if (challengeQuesWithPosCommand != null) {
	    ChallengeQuestionAndPositionOperationRequest challengeQuesOperRequest = makeRequest(request, challengeQuesWithPosCommand);

	    challengeQuesOperResponse = challengeQuestionAndPositionOperation.getChallengeQuestionPositions(challengeQuesOperRequest);

	    if (challengeQuesOperResponse != null && challengeQuesOperResponse.getQuestionWithPositions() != null) {

		// Put QuePosition and MobileNumber in session
		setIntoProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.SECOND_AUTH_QUESTION_ID_AND_POSITIONS,
			challengeQuesOperResponse.getQuestionWithPositions());
		setIntoProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.MOBILE_NO, challengeQuesWithPosCommand
			.getMobileNumber());
	    }
	} else {
	    challengeQuesOperResponse = new ChallengeQuestionAndPositionOperationResponse();
	    challengeQuesOperResponse.setContext(challengeQuesOperResponse.getContext());
	    challengeQuesOperResponse.setSuccess(false);
	    getMessage(challengeQuesOperResponse);
	}
	LOGGER.debug(" End ChallengeQuePosForMigUserController.handle1");
	return (BMBBaseResponseModel) challengeQuestionAndPositionJSONBldr.createJSONResponse(challengeQuesOperResponse);
    }

    private ChallengeQuestionAndPositionOperationRequest makeRequest(HttpServletRequest httpRequest, ChallengeQuestionAndPositionCommand inputCommand) {

	ChallengeQuestionAndPositionOperationRequest challengeQuesOperRequest = new ChallengeQuestionAndPositionOperationRequest();
	Context context = addContext(challengeQuesOperRequest, httpRequest);

	// Get Account Number ,BranchCode,SCV_ID from Session
	String accountNo = (String) getFromProcessMap(httpRequest, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.ACCOUNT_NO);
	String branchCode = (String) getFromProcessMap(httpRequest, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.BRANCH_CODE);
	String scvid = (String) getFromProcessMap(httpRequest, BMGProcessConstants.VERIFY_MIGRATED_USER,
		RequestConstants.VERIFY_MIGRAGTED_USER_SCV_ID);

	// Create ChallengeOperationRequest
	challengeQuesOperRequest.setSingleCustViewId(scvid);
	challengeQuesOperRequest.setAccountNo(accountNo);
	challengeQuesOperRequest.setBranch(branchCode);
	challengeQuesOperRequest.setUserStatusInMCE(BMGProcessConstants.USER_MIGRATED_STATUS_IN_MCE);
	challengeQuesOperRequest.setQuesIdString(inputCommand.getQuestionList());
	challengeQuesOperRequest.setMobileNumber(inputCommand.getMobileNumber());

	context.setMobilePhone(inputCommand.getMobileNumber());
	challengeQuesOperRequest.setContext(context);

	return challengeQuesOperRequest;
    }

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

    public String getVerifyChallengeURL() {
	return verifyChallengeURL;
    }

    public void setVerifyChallengeURL(String verifyChallengeURL) {
	this.verifyChallengeURL = verifyChallengeURL;
    }

    public BMBJSONBuilder getChallengeQuestionAndPositionJSONBldr() {
	return challengeQuestionAndPositionJSONBldr;
    }

    public void setChallengeQuestionAndPositionJSONBldr(BMBJSONBuilder challengeQuestionAndPositionJSONBldr) {
	this.challengeQuestionAndPositionJSONBldr = challengeQuestionAndPositionJSONBldr;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public ChallengeQuestionAndPositionOperation getChallengeQuestionAndPositionOperation() {
	return challengeQuestionAndPositionOperation;
    }

    public void setChallengeQuestionAndPositionOperation(ChallengeQuestionAndPositionOperation challengeQuestionAndPositionOperation) {
	this.challengeQuestionAndPositionOperation = challengeQuestionAndPositionOperation;
    }

}
