package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.accountservices.adapter.AuthenticationHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.OTPAuthenticationChallengePayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.AuthenticationChallengeRequest;

public class OTPAuthenticationChallengeReqAdptOperation {

    private AuthenticationHeaderAdapter hostMessageHeaderAdapter;

    private OTPAuthenticationChallengePayloadAdapter otpAuthenticationChallengePayloadAdapter;

    public AuthenticationChallengeRequest adaptRequestForSPAuthentication(WorkContext workContext) {

	AuthenticationChallengeRequest request = new AuthenticationChallengeRequest();

	request.setRequestHeader(hostMessageHeaderAdapter.buildAuthReqHeader(workContext));
	request.setRequest(otpAuthenticationChallengePayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public AuthenticationHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setHostMessageHeaderAdapter(AuthenticationHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public OTPAuthenticationChallengePayloadAdapter getOtpAuthenticationChallengePayloadAdapter() {
	return otpAuthenticationChallengePayloadAdapter;
    }

    public void setOtpAuthenticationChallengePayloadAdapter(OTPAuthenticationChallengePayloadAdapter otpAuthenticationChallengePayloadAdapter) {
	this.otpAuthenticationChallengePayloadAdapter = otpAuthenticationChallengePayloadAdapter;
    }

}
