package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.accountservices.adapter.AuthenticationHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.SQAAuthenticationChallengePayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.AuthenticationChallengeRequest;

public class SQAAuthenticationChallengeReqAdptOperation {

    private AuthenticationHeaderAdapter hostMessageHeaderAdapter;

    private SQAAuthenticationChallengePayloadAdapter sqaAuthenticationChallengePayloadAdapter;

    public AuthenticationChallengeRequest adaptRequest(WorkContext workContext) {

	AuthenticationChallengeRequest request = new AuthenticationChallengeRequest();

	request.setRequestHeader(hostMessageHeaderAdapter.buildAuthReqHeader(workContext));
	request.setRequest(sqaAuthenticationChallengePayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public AuthenticationHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setHostMessageHeaderAdapter(AuthenticationHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public SQAAuthenticationChallengePayloadAdapter getSqaAuthenticationChallengePayloadAdapter() {
	return sqaAuthenticationChallengePayloadAdapter;
    }

    public void setSqaAuthenticationChallengePayloadAdapter(SQAAuthenticationChallengePayloadAdapter sqaAuthenticationChallengePayloadAdapter) {
	this.sqaAuthenticationChallengePayloadAdapter = sqaAuthenticationChallengePayloadAdapter;
    }

}
