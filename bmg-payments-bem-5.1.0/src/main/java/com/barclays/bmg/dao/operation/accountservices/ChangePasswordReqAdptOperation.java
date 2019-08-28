package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.UpdatePIN.UpdatePINRequest;
import com.barclays.bmg.dao.accountservices.adapter.ChangePasswordHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ChangePasswordPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ChangePasswordReqAdptOperation {

    private ChangePasswordHeaderAdapter hostMessageHeaderAdapter;

    private ChangePasswordPayloadAdapter resetPasswordPayloadAdapter;

    public UpdatePINRequest adaptRequestForChangePassword(WorkContext workContext) {
	UpdatePINRequest request = new UpdatePINRequest();
	request.setRequestHeader(hostMessageHeaderAdapter.buildRequestHeader(workContext));
	request.setUpdatePIN(resetPasswordPayloadAdapter.adaptPayLoad(workContext));
	return request;
    }

    public void setHostMessageHeaderAdapter(ChangePasswordHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public ChangePasswordHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setResetPasswordPayloadAdapter(ChangePasswordPayloadAdapter resetPasswordPayloadAdapter) {
	this.resetPasswordPayloadAdapter = resetPasswordPayloadAdapter;
    }

    public ChangePasswordPayloadAdapter getResetPasswordPayloadAdapter() {
	return resetPasswordPayloadAdapter;
    }

}
