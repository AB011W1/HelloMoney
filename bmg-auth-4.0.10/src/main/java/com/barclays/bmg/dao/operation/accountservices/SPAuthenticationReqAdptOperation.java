package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.accountservices.adapter.AuthenticationHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.SPAuthenticationPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.AuthenticationVerifyRequest;

public class SPAuthenticationReqAdptOperation {

    private AuthenticationHeaderAdapter hostMessageHeaderAdapter;

    private SPAuthenticationPayloadAdapter spAuthenticationPayloadAdapter;

    public AuthenticationVerifyRequest adaptRequestForSPAuthentication(WorkContext workContext) {

	AuthenticationVerifyRequest request = new AuthenticationVerifyRequest();

	request.setRequestHeader(hostMessageHeaderAdapter.buildAuthReqHeader(workContext));
	request.setRequest(spAuthenticationPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public AuthenticationHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setHostMessageHeaderAdapter(AuthenticationHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public SPAuthenticationPayloadAdapter getSpAuthenticationPayloadAdapter() {
	return spAuthenticationPayloadAdapter;
    }

    public void setSpAuthenticationPayloadAdapter(SPAuthenticationPayloadAdapter spAuthenticationPayloadAdapter) {
	this.spAuthenticationPayloadAdapter = spAuthenticationPayloadAdapter;
    }

}
