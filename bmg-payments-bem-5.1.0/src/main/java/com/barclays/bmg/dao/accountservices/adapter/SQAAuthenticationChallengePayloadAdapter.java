package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.Request;
import com.barclays.grcb.mcfe.ssc.authentication.entity.AuthenticationType;

public class SQAAuthenticationChallengePayloadAdapter {

    public Request adaptPayLoad(WorkContext workContext) {
	Request requestBody = new Request();

	requestBody.setAuthenticationType(AuthenticationType.SQA);

	return requestBody;
    }
}
