package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.PostAuthenticationServiceRequest;
import com.barclays.bmg.service.response.PostAuthenticationServiceResponse;
import com.barclays.grcb.mcfe.ssc.authentication.InitialAfterLoginSucess.InitialAfterLoginSuccessResponse;

public class PostAuthenticationResAdptOperation {

    public PostAuthenticationServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	InitialAfterLoginSuccessResponse response = (InitialAfterLoginSuccessResponse) obj;

	/**
	 * prepare authentication service response
	 */
	PostAuthenticationServiceResponse authenticationServiceResponse = new PostAuthenticationServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	PostAuthenticationServiceRequest authenticationServiceRequest = (PostAuthenticationServiceRequest) args[0];

	authenticationServiceResponse.setContext(authenticationServiceRequest.getContext());

	if (response.getLoginDetails() != null && response.getLoginDetails().getLastLoginTime() != null) {
	    authenticationServiceResponse.setLastLoginTime(response.getLoginDetails().getLastLoginTime().getTime());
	}

	if (response.getResponseHeader() != null && response.getResponseHeader().getServiceResStatus() != null) {
	    if ("00000".equals(response.getResponseHeader().getServiceResStatus().getServiceResCode())) {
		authenticationServiceResponse.setSuccess(true);

	    } else {
		authenticationServiceResponse.setSuccess(false);
	    }

	}

	/**
	 * Response header coming null in response setting success true.
	 */
	if (response.getResponseHeader() == null) {
	    authenticationServiceResponse.setSuccess(true);
	}

	return authenticationServiceResponse;
    }

}
