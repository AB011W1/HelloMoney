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
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.ussd.auth.operation.request.SelfRegistrationInitOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationInitOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationInitCommand;
import com.barclays.bmg.ussd.operation.SelfRegistrationInitOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationInitController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SelfRegistrationInitController extends BMBAbstractCommandController {

    /**
     * The instance variable for selfRegistrationInitOperation of type SelfRegistrationInitOperation
     */
    private SelfRegistrationInitOperation selfRegistrationInitOperation;

    /**
     * The instance variable for selfRegistrationInitJSONBldr of type BMBJSONBuilder
     */
    private BMBJSONBuilder selfRegistrationInitJSONBldr;

    /**
     * The instance variable for generateChallengeURL of type String
     */
    private String generateChallengeURL;

    /**
     * The instance variable for activityId of type String
     */
    private String activityId;

    /**
     * Getter for ActivityId
     *
     *@param none
     *@return ActivityId
     */
    public String getActivityId() {
	return activityId;
    }

    /**
     * Setter for ActivityId
     *
     * @param ActivityId
     * @return void
     */
    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	setFirstStep(request);

	SelfRegistrationInitCommand selfRegistrationInitCommand = (SelfRegistrationInitCommand) command;

	SelfRegistrationInitOperationRequest selfRegisInitOperationRequest = makeRequest(request);
	selfRegisInitOperationRequest.setMobileNo(selfRegistrationInitCommand.getMobileNo());
	selfRegisInitOperationRequest.setAccountNo(selfRegistrationInitCommand.getAccountNo());
	selfRegisInitOperationRequest.setBranchCode(selfRegistrationInitCommand.getBranchCode());

	Context context = selfRegisInitOperationRequest.getContext();
	context.setMobilePhone(selfRegistrationInitCommand.getMobileNo());

	selfRegisInitOperationRequest.setContext(context);

	setIntoProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.MOBILE_NO, selfRegistrationInitCommand.getMobileNo());
	setIntoProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.ACCOUNT_NO, selfRegistrationInitCommand
		.getAccountNo());
	setIntoProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.BRANCH_CODE, selfRegistrationInitCommand
		.getBranchCode());
	setIntoProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.SELF_REG_INIT_FLOW_ID,
		BMGProcessConstants.SELF_REG_INIT_FLOW_ID);

	// Create Customer in Crypto
	SelfRegistrationInitOperationResponse selfRegisInitOperationResponse = selfRegistrationInitOperation
		.createCustomerInCrypto(selfRegisInitOperationRequest);

	return (BMBBaseResponseModel) selfRegistrationInitJSONBldr.createJSONResponse(selfRegisInitOperationResponse);

    }

    /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpServletRequest
     * @return SelfRegistrationInitOperationRequest
     */
    private SelfRegistrationInitOperationRequest makeRequest(HttpServletRequest request) {
	SelfRegistrationInitOperationRequest selfRegisInitOperationRequest = new SelfRegistrationInitOperationRequest();

	Context context = createContext(request);
	context.setActivityId(getActivityId());

	selfRegisInitOperationRequest.setContext(context);

	return selfRegisInitOperationRequest;

    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#getActivityId(java.lang.Object)
     */
    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Getter for SelfRegistrationInitOperation
     *
     *@param none
     *@return SelfRegistrationInitOperation
     */
    public SelfRegistrationInitOperation getSelfRegistrationInitOperation() {
	return selfRegistrationInitOperation;
    }

    /**
     * Setter for SelfRegistrationInitOperation
     *
     * @param SelfRegistrationInitOperation
     * @return void
     */
    public void setSelfRegistrationInitOperation(SelfRegistrationInitOperation selfRegistrationInitOperation) {
	this.selfRegistrationInitOperation = selfRegistrationInitOperation;
    }

    /**
     * Getter for BMBJSONBuilder
     *
     *@param none
     *@return BMBJSONBuilder
     */
    public BMBJSONBuilder getSelfRegistrationInitJSONBldr() {
	return selfRegistrationInitJSONBldr;
    }

    /**
     * Setter for BMBJSONBuilder
     *
     * @param BMBJSONBuilder
     * @return void
     */
    public void setSelfRegistrationInitJSONBldr(BMBJSONBuilder selfRegistrationInitJSONBldr) {
	this.selfRegistrationInitJSONBldr = selfRegistrationInitJSONBldr;
    }

    /**
     * Getter for GenerateChallengeURL
     *
     *@param none
     *@return GenerateChallengeURL
     */
    public String getGenerateChallengeURL() {
	return generateChallengeURL;
    }

    /**
     * Setter for GenerateChallengeURL
     *
     * @param GenerateChallengeURL
     * @return void
     */
    public void setGenerateChallengeURL(String generateChallengeURL) {
	this.generateChallengeURL = generateChallengeURL;
    }

}
