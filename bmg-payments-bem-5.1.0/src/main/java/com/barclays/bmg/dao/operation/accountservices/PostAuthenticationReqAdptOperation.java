package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.accountservices.adapter.PostAuthenticationHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.PostAuthenticationPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.InitialAfterLoginSucess.InitialAfterLoginSuccessRequest;

public class PostAuthenticationReqAdptOperation {

    private PostAuthenticationHeaderAdapter hostMessageHeaderAdapter;

    private PostAuthenticationPayloadAdapter postAuthenticationPayloadAdapter;

    public InitialAfterLoginSuccessRequest adaptRequest(WorkContext workContext) {

	InitialAfterLoginSuccessRequest request = new InitialAfterLoginSuccessRequest();

	request.setRequestHeader(hostMessageHeaderAdapter.buildAuthReqHeader(workContext));
	request.setLoginInitialRequest(postAuthenticationPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public PostAuthenticationHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setHostMessageHeaderAdapter(PostAuthenticationHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public PostAuthenticationPayloadAdapter getPostAuthenticationPayloadAdapter() {
	return postAuthenticationPayloadAdapter;
    }

    public void setPostAuthenticationPayloadAdapter(PostAuthenticationPayloadAdapter postAuthenticationPayloadAdapter) {
	this.postAuthenticationPayloadAdapter = postAuthenticationPayloadAdapter;
    }

}
