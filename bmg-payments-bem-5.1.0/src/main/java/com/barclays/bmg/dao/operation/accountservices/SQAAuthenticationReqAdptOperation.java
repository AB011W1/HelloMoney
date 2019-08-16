package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.accountservices.adapter.AuthenticationHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.SQAAuthenticationPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.AuthenticationVerifyRequest;

public class SQAAuthenticationReqAdptOperation {

    private AuthenticationHeaderAdapter hostMessageHeaderAdapter;

    private SQAAuthenticationPayloadAdapter sqaAuthenticationPayloadAdapter;

    public AuthenticationVerifyRequest adaptRequest(WorkContext workContext) {

	AuthenticationVerifyRequest request = new AuthenticationVerifyRequest();

	request.setRequestHeader(hostMessageHeaderAdapter.buildAuthReqHeader(workContext));
	request.setRequest(sqaAuthenticationPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public AuthenticationHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setHostMessageHeaderAdapter(AuthenticationHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public SQAAuthenticationPayloadAdapter getSqaAuthenticationPayloadAdapter() {
	return sqaAuthenticationPayloadAdapter;
    }

    public void setSqaAuthenticationPayloadAdapter(SQAAuthenticationPayloadAdapter sqaAuthenticationPayloadAdapter) {
	this.sqaAuthenticationPayloadAdapter = sqaAuthenticationPayloadAdapter;
    }

}
