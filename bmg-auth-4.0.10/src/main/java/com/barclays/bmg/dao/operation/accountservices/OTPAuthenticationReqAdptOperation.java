package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.accountservices.adapter.AuthenticationHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.OTPAuthenticationPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.AuthenticationVerifyRequest;

public class OTPAuthenticationReqAdptOperation {

    private AuthenticationHeaderAdapter hostMessageHeaderAdapter;

    private OTPAuthenticationPayloadAdapter otpAuthenticationPayloadAdapter;

    public AuthenticationVerifyRequest adaptRequest(WorkContext workContext) {

	AuthenticationVerifyRequest request = new AuthenticationVerifyRequest();

	request.setRequestHeader(hostMessageHeaderAdapter.buildAuthReqHeader(workContext));
	request.setRequest(otpAuthenticationPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public AuthenticationHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setHostMessageHeaderAdapter(AuthenticationHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public OTPAuthenticationPayloadAdapter getOtpAuthenticationPayloadAdapter() {
	return otpAuthenticationPayloadAdapter;
    }

    public void setOtpAuthenticationPayloadAdapter(OTPAuthenticationPayloadAdapter otpAuthenticationPayloadAdapter) {
	this.otpAuthenticationPayloadAdapter = otpAuthenticationPayloadAdapter;
    }

}
