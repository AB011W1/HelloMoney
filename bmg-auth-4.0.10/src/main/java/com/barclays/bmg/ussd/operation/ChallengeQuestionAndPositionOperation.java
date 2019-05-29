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

import org.apache.log4j.Logger;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.ussd.auth.operation.request.ChallengeQuestionAndPositionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.ChallengeQuestionAndPositionOperationResponse;
import com.barclays.bmg.ussd.auth.service.request.ChallengeQuestionAndPositionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.ChallengeQuestionAndPositionServiceResponse;
import com.barclays.bmg.ussd.service.ChallengeQuestionAndPositionService;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class ChallengeQuestionAndPositionOperation extends BMBCommonOperation {

    private static final Logger logger = Logger.getLogger(ChallengeQuestionAndPositionOperation.class);
    /**
     * The instance variable for challengeQuestionAndPositionService of type ChallengeQuestionAndPositionService
     */
    private ChallengeQuestionAndPositionService challengeQuestionAndPositionService;

    /**
     * Getter for ChallengeQuestionAndPositionService
     *
     *@param none
     *@return ChallengeQuestionAndPositionService
     */
    public ChallengeQuestionAndPositionService getChallengeQuestionAndPositionService() {
	return challengeQuestionAndPositionService;
    }

    /**
     * Setter for ChallengeQuestionAndPositionService
     *
     * @param ChallengeQuestionAndPositionService
     * @return void
     */
    public void setChallengeQuestionAndPositionService(ChallengeQuestionAndPositionService challengeQuestionAndPositionService) {
	this.challengeQuestionAndPositionService = challengeQuestionAndPositionService;
    }

    /**
     * This method getChallengeQuestions has the purpose to pass operation request and service request parameters for the purpose of making call to
     * bem service.
     *
     * @param ChallengeQuestionAndPositionOperationRequest
     * @return ChallengeQuestionAndPositionOperationResponse
     */
    public ChallengeQuestionAndPositionOperationResponse getChallengeQuestionPositions(
	    ChallengeQuestionAndPositionOperationRequest challengeQuesAndPosOperRequest) {

	logger.info("into ChallengeQuesWithPosOperationResponse.ChallengeQuesWithPosOperationRequest");
	Context context = challengeQuesAndPosOperRequest.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	ChallengeQuestionAndPositionServiceRequest serviceRequest = getChallengeQuesServiceRequest(challengeQuesAndPosOperRequest);
	serviceRequest.setContext(context);

	ChallengeQuestionAndPositionOperationResponse challengeQuesWithPosOperationResponse = getChallengeQuestionRespFromService(serviceRequest);

	challengeQuesWithPosOperationResponse.setContext(context);

	if (!challengeQuesWithPosOperationResponse.isSuccess()) {
	    getMessage(challengeQuesWithPosOperationResponse);
	}

	logger.info("Exit ChallengeQuesWithPosOperationResponse.ChallengeQuesWithPosOperationRequest");
	return challengeQuesWithPosOperationResponse;
    }

    /**
     * This method getChallengeQuesServiceRequest has the purpose to construct service request from the operation request.
     *
     * @param ChallengeQuestionAndPositionOperationRequest
     * @return ChallengeQuestionAndPositionServiceRequest
     */
    private ChallengeQuestionAndPositionServiceRequest getChallengeQuesServiceRequest(
	    ChallengeQuestionAndPositionOperationRequest challengeQuesRequest) {

	ChallengeQuestionAndPositionServiceRequest serviceRequest = new ChallengeQuestionAndPositionServiceRequest();

	serviceRequest.setTxnRefNo(challengeQuesRequest.getTxnRefNo());
	serviceRequest.setMobileNumber(challengeQuesRequest.getMobileNumber());
	serviceRequest.setSingleCustViewId(challengeQuesRequest.getSingleCustViewId());
	serviceRequest.setUserStatusInMCE(challengeQuesRequest.getUserStatusInMCE());
	serviceRequest.setContext(challengeQuesRequest.getContext());
	serviceRequest.setBusinessId(challengeQuesRequest.getContext().getBusinessId());
	serviceRequest.setSystemId(challengeQuesRequest.getContext().getSystemId());

	String quesIds[] = challengeQuesRequest.getQuesIdString().split("_");
	serviceRequest.setQuestionId(quesIds);

	return serviceRequest;
    }

    /**
     * This method getChallengeQuestionRespFromService has the purpose to fetch the positions to challenge questions ,to be shown to the user.
     *
     * @param ChallengeQuestionAndPositionServiceRequest
     * @return ChallengeQuestionAndPositionOperationResponse
     */
    private ChallengeQuestionAndPositionOperationResponse getChallengeQuestionRespFromService(
	    ChallengeQuestionAndPositionServiceRequest serviceRequest) {

	ChallengeQuestionAndPositionOperationResponse challengeQuesWithPosOperationResponse = new ChallengeQuestionAndPositionOperationResponse();
	ChallengeQuestionAndPositionServiceResponse serviceResponse = challengeQuestionAndPositionService.getChallengePositions(serviceRequest);

	challengeQuesWithPosOperationResponse.setQuestionWithPositions(serviceResponse.getQuestionWithPositions());
	challengeQuesWithPosOperationResponse.setSuccess(serviceResponse.isSuccess());

	return challengeQuesWithPosOperationResponse;

    }

}
