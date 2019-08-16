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

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.QuesWithPosDTO;
import com.barclays.bmg.operation.BMBPaymentsOperation;
import com.barclays.bmg.ussd.auth.operation.request.SecondAuthenticationOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SecondAuthenticationOperationResponse;
import com.barclays.bmg.ussd.auth.service.request.SecondAuthenticationServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SecondAuthenticationServiceResponse;
import com.barclays.bmg.ussd.service.SecondAuthenticationService;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationOperation extends BMBPaymentsOperation {

    /**
     * The instance variable for secondAuthService of type SecondAuthenticationService
     */
    private SecondAuthenticationService secondAuthService;

    /**
     * This method verify has the purpose to verify the answers to Challenge Questions
     * 
     * @param SecondAuthenticationOperationRequest
     * @return SecondAuthenticationOperationResponse
     */
    public SecondAuthenticationOperationResponse verifyChallengeResponse(SecondAuthenticationOperationRequest secondAuthOperationRequest) {

	SecondAuthenticationServiceRequest secondAuthServiceRequest = buildSecondAuthServiceRequest(secondAuthOperationRequest);

	SecondAuthenticationOperationResponse secondAuthOperationResponse = new SecondAuthenticationOperationResponse();

	SecondAuthenticationServiceResponse secondAuthServiceResponse = secondAuthService.verifyChallengeResponse(secondAuthServiceRequest);

	secondAuthOperationResponse.setContext(secondAuthServiceRequest.getContext());
	secondAuthOperationResponse.setServiceResponse(secondAuthServiceResponse.getServiceResponse());
	secondAuthOperationResponse.setResCde(secondAuthOperationResponse.getServiceResponse());
	secondAuthOperationResponse.setSuccess(secondAuthServiceResponse.isSuccess());

	/**
	 * Get message for error
	 */
	if (!secondAuthOperationResponse.isSuccess()) {
	    getMessage(secondAuthOperationResponse);
	}

	return secondAuthOperationResponse;

    }

    /**
     * This method buildSecondAuthServiceRequest has the purpose to build second auth service request.
     * 
     * @param SecondAuthenticationOperationRequest
     * @return SecondAuthenticationServiceRequest
     */
    private SecondAuthenticationServiceRequest buildSecondAuthServiceRequest(SecondAuthenticationOperationRequest secondAuthOperationRequest) {

	SecondAuthenticationServiceRequest secondAuthServiceRequest = new SecondAuthenticationServiceRequest();
	String quesAndPos = secondAuthOperationRequest.getQuestionWithPositions();
	String splitted1[] = quesAndPos.split("\\|");

	QuesWithPosDTO[] quesWithPosDTO = new QuesWithPosDTO[splitted1.length];

	int count = 0;
	while (count < splitted1.length) {

	    String splitted2[] = splitted1[count].split("_");
	    quesWithPosDTO[count] = new QuesWithPosDTO("", "", "", "");
	    ;

	    if (splitted2.length == 4) {
		quesWithPosDTO[count].setQuesId(splitted2[0]);
		quesWithPosDTO[count].setAnsPos1(splitted2[1]);
		quesWithPosDTO[count].setAnsPos2(splitted2[2]);
		quesWithPosDTO[count].setChallengeId(splitted2[3]);
	    }

	    count++;

	}

	Context context = secondAuthOperationRequest.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	secondAuthServiceRequest.setContext(context);
	secondAuthServiceRequest.setMobileNumber(secondAuthOperationRequest.getMobileNo());
	secondAuthServiceRequest.setSingleCustViewId(secondAuthOperationRequest.getSingleCustViewId());
	secondAuthServiceRequest.setUserStatusInMCE(secondAuthOperationRequest.getUserStatusInMCE());
	secondAuthServiceRequest.setQuestionWithPositions(quesWithPosDTO);

	return secondAuthServiceRequest;
    }

    /**
     * Getter for SecondAuthenticationService
     * 
     *@param none
     *@return SecondAuthenticationService
     */
    public SecondAuthenticationService getSecondAuthService() {
	return secondAuthService;
    }

    /**
     * Setter for SecondAuthenticationService
     * 
     * @param SecondAuthenticationService
     * @return void
     */
    public void setSecondAuthService(SecondAuthenticationService secondAuthService) {
	this.secondAuthService = secondAuthService;
    }

}
