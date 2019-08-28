package com.barclays.bmg.operation;

import com.barclays.bmg.operation.request.PostAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.PostAuthenticationOperationResponse;
import com.barclays.bmg.service.PostAuthenticationService;
import com.barclays.bmg.service.request.PostAuthenticationServiceRequest;
import com.barclays.bmg.service.response.PostAuthenticationServiceResponse;

/**
 * post authentication operation
 * 
 * @author e20026338
 * 
 */
public class PostAuthenticationOperation {

    PostAuthenticationService postAuthenticationService;

    public PostAuthenticationOperationResponse afterLoginSuccess(PostAuthenticationOperationRequest request) {

	/**
	 * Prepare request
	 */
	PostAuthenticationServiceRequest postAuthenticationServiceRequest = new PostAuthenticationServiceRequest();

	postAuthenticationServiceRequest.setContext(request.getContext());
	postAuthenticationServiceRequest.setMobilePhone(request.getMobilePhone());

	/**
	 * Call service
	 */
	PostAuthenticationServiceResponse postAuthenticationServiceResponse = postAuthenticationService
		.afterLoginSuccess(postAuthenticationServiceRequest);

	/**
	 * Prepare response
	 */
	PostAuthenticationOperationResponse postAuthenticationOperationResponse = new PostAuthenticationOperationResponse();

	postAuthenticationOperationResponse.setContext(postAuthenticationServiceResponse.getContext());
	postAuthenticationOperationResponse.setSuccess(postAuthenticationServiceResponse.isSuccess());
	postAuthenticationOperationResponse.setLastLoginTime(postAuthenticationServiceResponse.getLastLoginTime());

	return postAuthenticationOperationResponse;
    }

    public PostAuthenticationService getPostAuthenticationService() {
	return postAuthenticationService;
    }

    public void setPostAuthenticationService(PostAuthenticationService postAuthenticationService) {
	this.postAuthenticationService = postAuthenticationService;
    }

}
