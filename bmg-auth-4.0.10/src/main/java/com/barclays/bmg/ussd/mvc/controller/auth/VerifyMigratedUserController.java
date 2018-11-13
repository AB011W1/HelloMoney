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
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.ussd.auth.operation.request.VerifyMigratedUserOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.VerifyMigratedUserOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.VerifyMigratedUserCommand;
import com.barclays.bmg.ussd.operation.VerifyMigratedUserOperation;

public class VerifyMigratedUserController extends BMBAbstractCommandController {

    private static final Logger LOGGER = Logger.getLogger(VerifyMigratedUserController.class);

    private String activityId;
    private BMBJSONBuilder verifyMigratedUserJSONBldr;
    private VerifyMigratedUserOperation verifyMigratedUserOperation;

    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	LOGGER.debug(" Start VerifyMigratedUserController.handle1");
	VerifyMigratedUserOperationResponse verifyMigratedUserOperationResponse = null;
	VerifyMigratedUserCommand inputCommand = (VerifyMigratedUserCommand) command;

	if (inputCommand != null) {
	    setFirstStep(request);
	    VerifyMigratedUserOperationRequest verifyMigratedUserOperationRequest = makeRequest(request, inputCommand);
	    putRequestDataInSession(request, verifyMigratedUserOperationRequest);

	    verifyMigratedUserOperationResponse = verifyMigratedUserOperation.validateCustomer(verifyMigratedUserOperationRequest);

	    if (verifyMigratedUserOperationResponse.isSuccess()) {
		setIntoProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.VERIFY_MIGRAGTED_USER_SCV_ID,
			verifyMigratedUserOperationResponse.getScvid());
	    }
	} else {
	    verifyMigratedUserOperationResponse = new VerifyMigratedUserOperationResponse();
	    verifyMigratedUserOperationResponse.setContext(verifyMigratedUserOperationResponse.getContext());
	    verifyMigratedUserOperationResponse.setSuccess(false);
	    getMessage(verifyMigratedUserOperationResponse);
	}

	LOGGER.debug(" End VerifyMigratedUserControllers.handle1");
	return (BMBBaseResponseModel) verifyMigratedUserJSONBldr.createJSONResponse(verifyMigratedUserOperationResponse);
    }

    private VerifyMigratedUserOperationRequest makeRequest(HttpServletRequest request, VerifyMigratedUserCommand inputCommand) {

	VerifyMigratedUserOperationRequest verifyMigratedUserOperationRequest = new VerifyMigratedUserOperationRequest();
	Context context = createContext(request);
	context.setActivityId(getActivityId());

	verifyMigratedUserOperationRequest.setMobileNo(inputCommand.getMobileNo());
	verifyMigratedUserOperationRequest.setAccountNo(inputCommand.getAccountNo());
	verifyMigratedUserOperationRequest.setBranchCode(inputCommand.getBranchCode());
	context.setMobilePhone(inputCommand.getMobileNo());
	verifyMigratedUserOperationRequest.setContext(context);
	return verifyMigratedUserOperationRequest;
    }

    private void putRequestDataInSession(HttpServletRequest request, VerifyMigratedUserOperationRequest verifyMigratedUserOperationRequest) {
	setIntoProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.MOBILE_NO, verifyMigratedUserOperationRequest
		.getMobileNo());
	setIntoProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.ACCOUNT_NO, verifyMigratedUserOperationRequest
		.getAccountNo());
	setIntoProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.BRANCH_CODE, verifyMigratedUserOperationRequest
		.getBranchCode());
	setIntoProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.VERIFY_MIGRAGTED_USER_FLOW_ID,
		BMGProcessConstants.VERIFY_MIGRATED_USER_FLOW_ID);
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public BMBJSONBuilder getVerifyMigratedUserJSONBldr() {
	return verifyMigratedUserJSONBldr;
    }

    public void setVerifyMigratedUserJSONBldr(BMBJSONBuilder verifyMigratedUserJSONBldr) {
	this.verifyMigratedUserJSONBldr = verifyMigratedUserJSONBldr;
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    public VerifyMigratedUserOperation getVerifyMigratedUserOperation() {
	return verifyMigratedUserOperation;
    }

    public void setVerifyMigratedUserOperation(VerifyMigratedUserOperation verifyMigratedUserOperation) {
	this.verifyMigratedUserOperation = verifyMigratedUserOperation;
    }

}
