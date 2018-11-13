package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.Request;
import com.barclays.grcb.mcfe.ssc.authentication.entity.AuthenticationType;
import com.barclays.grcb.mcfe.ssc.authentication.entity.StaticPasswordInfo;

public class SPAuthenticationPayloadAdapter {

    public Request adaptPayLoad(WorkContext workContext) {
	Request requestBody = new Request();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AuthenticationServiceRequest authenticationServiceRequest = (AuthenticationServiceRequest) args[0];

	requestBody.setAuthenticationType(AuthenticationType.SP);
	StaticPasswordInfo staticPassword = new StaticPasswordInfo();
	staticPassword.setUserName(authenticationServiceRequest.getUsername());
	staticPassword.setPassword(authenticationServiceRequest.getPassword());
	requestBody.setStaticPasswordInfo(staticPassword);

	return requestBody;
    }
}
