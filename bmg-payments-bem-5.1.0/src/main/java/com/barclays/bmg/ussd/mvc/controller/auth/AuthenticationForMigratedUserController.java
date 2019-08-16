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

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.QuesWithPosDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.ussd.auth.operation.request.SecondAuthenticationOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SecondAuthenticationOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.SecondAuthenticationCommand;
import com.barclays.bmg.ussd.operation.SecondAuthenticationOperation;

public class AuthenticationForMigratedUserController extends BMBAbstractCommandController {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationForMigratedUserController.class);

    private String activityId;

    private SecondAuthenticationOperation secondAuthOperation;

    private BMBJSONBuilder secondAuthJSONBldr;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	LOGGER.debug(" Start AuthenticationForMigratedUserController.handle1");
	SecondAuthenticationOperationResponse secondAuthenticationOperationResponse = null;
	SecondAuthenticationCommand secondAuthCommand = (SecondAuthenticationCommand) command;

	if (secondAuthCommand != null && (secondAuthCommand.getSQA() != null)) {
	    SecondAuthenticationOperationRequest secondAuthenticationOperationRequest = makeRequest(request, secondAuthCommand);

	    // Verify Challenge
	    secondAuthenticationOperationResponse = secondAuthOperation.verifyChallengeResponse(secondAuthenticationOperationRequest);

	    if (secondAuthenticationOperationResponse != null && secondAuthenticationOperationResponse.isSuccess()) {
		setIntoProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, BMGProcessConstants.SECOND_AUTH_RESPONSE,
			secondAuthenticationOperationResponse.isSuccess());
	    }
	} else {
	    secondAuthenticationOperationResponse = new SecondAuthenticationOperationResponse();
	    secondAuthenticationOperationResponse.setContext(secondAuthenticationOperationResponse.getContext());
	    getMessage(secondAuthenticationOperationResponse);
	    secondAuthenticationOperationResponse.setSuccess(false);
	}
	LOGGER.debug(" End AuthenticationForMigratedUserController.handle1");
	return (BMBBaseResponseModel) secondAuthJSONBldr.createJSONResponse(secondAuthenticationOperationResponse);
    }

    private SecondAuthenticationOperationRequest makeRequest(HttpServletRequest request, SecondAuthenticationCommand secondAuthCommand) {

	SecondAuthenticationOperationRequest secondAuthenticationOperationRequest = new SecondAuthenticationOperationRequest();
	Context context = createContext(request);
	context.setActivityId(this.activityId);

	// Read Account Number BranchCode and SCVID ,CustoemerID,UserStatus from Session
	String accountNo = (String) getFromProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.ACCOUNT_NO);
	String branchCode = (String) getFromProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.BRANCH_CODE);
	String scvid = (String) getFromProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.VERIFY_MIGRAGTED_USER_SCV_ID);
	String mobileNo = (String) getFromProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.MOBILE_NO);
	// Read Que Position and Challenge
	QuesWithPosDTO quesAndPosFromChallenge[] = (QuesWithPosDTO[]) getFromProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION,
		RequestConstants.SECOND_AUTH_QUESTION_ID_AND_POSITIONS);
	secondAuthenticationOperationRequest.setContext(context);

	// Create secondAuthenticationOperationRequest
	secondAuthenticationOperationRequest.setMobileNo(mobileNo);
	secondAuthenticationOperationRequest.setQuestionWithPositions(secondAuthCommand.getSQA());
	secondAuthenticationOperationRequest.setQuesAndPosFromChallenge(quesAndPosFromChallenge);
	secondAuthenticationOperationRequest.setAccountNo(accountNo);
	secondAuthenticationOperationRequest.setBranch(branchCode);
	secondAuthenticationOperationRequest.getContext().setCustomerId(scvid);
	secondAuthenticationOperationRequest.setSingleCustViewId(scvid);
	secondAuthenticationOperationRequest.setUserStatusInMCE(BMGProcessConstants.USER_MIGRATED_STATUS_IN_MCE);
	context.setMobilePhone(mobileNo);

	return secondAuthenticationOperationRequest;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public SecondAuthenticationOperation getSecondAuthOperation() {
	return secondAuthOperation;
    }

    public void setSecondAuthOperation(SecondAuthenticationOperation secondAuthOperation) {
	this.secondAuthOperation = secondAuthOperation;
    }

    public BMBJSONBuilder getSecondAuthJSONBldr() {
	return secondAuthJSONBldr;
    }

    public void setSecondAuthJSONBldr(BMBJSONBuilder secondAuthJSONBldr) {
	this.secondAuthJSONBldr = secondAuthJSONBldr;
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

}
