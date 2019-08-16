package com.barclays.bmg.operation;

import com.barclays.bmg.dto.SecretQuestionDTO;
import com.barclays.bmg.operation.request.SQAAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.SQAAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.service.AuthenticationChallengeService;
import com.barclays.bmg.service.AuthenticationService;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;

/**
 * SQA Authentication Operation
 * 
 * @author e20026338
 * 
 */
public class SQAAuthenticationOperation extends BMBCommonOperation {

    AuthenticationChallengeService authenticationChallengeService;
    AuthenticationService authenticationService;

    /**
     * Verify SQA challenge
     * 
     * @param request
     * @return
     */
    public SQAAuthenticationOperationResponse verify(SQAAuthenticationOperationRequest request) {
	AuthenticationServiceRequest authenticationServiceRequest = new AuthenticationServiceRequest();
	/**
	 * Prepare request
	 */

	authenticationServiceRequest.setContext(request.getContext());

	authenticationServiceRequest.setQuestionId(request.getQuestionId());
	authenticationServiceRequest.setSqa(request.getSqa());

	/**
	 * Call service
	 */
	AuthenticationServiceResponse authenticationServiceResponse = authenticationService.verify(authenticationServiceRequest);

	/**
	 * Prepare response
	 */
	SQAAuthenticationOperationResponse sqaAuthenticationOperationResponse = new SQAAuthenticationOperationResponse();
	sqaAuthenticationOperationResponse.setContext(authenticationServiceResponse.getContext());

	boolean authenticated = authenticationServiceResponse.isSuccess();
	String resCde = authenticationServiceResponse.getResCde();
	String resMsg = authenticationServiceResponse.getResMsg();

	sqaAuthenticationOperationResponse.setSuccess(authenticated);
	sqaAuthenticationOperationResponse.setResCde(resCde);
	sqaAuthenticationOperationResponse.setResMsg(resMsg);

	/**
	 * Get message for corresponding code
	 */
	if (!authenticated) {
	    getMessage(sqaAuthenticationOperationResponse);
	}

	return sqaAuthenticationOperationResponse;
    }

    /**
     * generate SQA challenge
     * 
     * @param request
     * @return
     */
    public SQAGenerateAuthenticationOperationResponse generate(SQAGenerateAuthenticationOperationRequest request) {
	AuthenticationServiceRequest authenticationServiceRequest = new AuthenticationServiceRequest();

	/**
	 * Prepare request
	 */
	authenticationServiceRequest.setContext(request.getContext());

	/**
	 * Call service
	 */
	AuthenticationServiceResponse authenticationServiceResponse = authenticationChallengeService.retrieveChallenge(authenticationServiceRequest);

	/**
	 * Prepare response
	 */
	SQAGenerateAuthenticationOperationResponse sqaAuthenticationOperationResponse = new SQAGenerateAuthenticationOperationResponse();

	sqaAuthenticationOperationResponse.setContext(authenticationServiceResponse.getContext());
	SecretQuestionDTO secretQuestionDTO = (SecretQuestionDTO) authenticationServiceResponse.getAuthenticationDTO().getChallengeId();

	String sqaResponse = (String) authenticationServiceResponse.getContext().getValue("sqaResponse");

	sqaAuthenticationOperationResponse.setSuccess(Boolean.valueOf(sqaResponse));

	sqaAuthenticationOperationResponse.setSuccess(authenticationServiceResponse.getAuthenticationDTO().isAuthenticated());
	sqaAuthenticationOperationResponse.setQuestion(secretQuestionDTO.getMessageValue());
	sqaAuthenticationOperationResponse.setQuestionId(secretQuestionDTO.getQuestionId());

	return sqaAuthenticationOperationResponse;
    }

    public AuthenticationChallengeService getAuthenticationChallengeService() {
	return authenticationChallengeService;
    }

    public void setAuthenticationChallengeService(AuthenticationChallengeService authenticationChallengeService) {
	this.authenticationChallengeService = authenticationChallengeService;
    }

    public AuthenticationService getAuthenticationService() {
	return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
	this.authenticationService = authenticationService;
    }

}
