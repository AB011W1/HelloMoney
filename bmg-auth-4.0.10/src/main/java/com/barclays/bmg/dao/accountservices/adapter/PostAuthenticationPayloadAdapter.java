package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.PostAuthenticationServiceRequest;
import com.barclays.grcb.mcfe.ssc.authentication.InitialAfterLoginSucess.LoginInitialRequest;
import com.barclays.grcb.mcfe.ssc.authentication.entity.LoginInitialBody;

public class PostAuthenticationPayloadAdapter {

    public LoginInitialRequest adaptPayLoad(WorkContext workContext) {
	LoginInitialRequest loginInitialRequest = new LoginInitialRequest();

	LoginInitialBody requestBody = new LoginInitialBody();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	PostAuthenticationServiceRequest authenticationServiceRequest = (PostAuthenticationServiceRequest) args[0];

	requestBody.setCustomerMobileNumber(authenticationServiceRequest.getMobilePhone());

	loginInitialRequest.setLoginInitialBody(requestBody);

	return loginInitialRequest;
    }
}
