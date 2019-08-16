package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.dao.accountservices.adapter.ValidateUserHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ValidateUserPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.grcb.mcfe.ssc.authentication.ValidateUserByLoginNameIgnoreCase.ValidateUserByLoginNameIgnoreCaseRequest;

public class ValidateUserReqAdptOperation {

    private ValidateUserHeaderAdapter hostMessageHeaderAdapter;

    private ValidateUserPayloadAdapter validateUserPayloadAdapter;

    public ValidateUserByLoginNameIgnoreCaseRequest adaptRequestForValidateUser(WorkContext workContext) {

	// /AuthenticationVerifyRequest request = new AuthenticationVerifyRequest();
	ValidateUserByLoginNameIgnoreCaseRequest request = new ValidateUserByLoginNameIgnoreCaseRequest();
	request.setRequestHeader(hostMessageHeaderAdapter.buildAuthReqHeader(workContext));
	request.setRequest(validateUserPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public ValidateUserHeaderAdapter getHostMessageHeaderAdapter() {
	return hostMessageHeaderAdapter;
    }

    public void setHostMessageHeaderAdapter(ValidateUserHeaderAdapter hostMessageHeaderAdapter) {
	this.hostMessageHeaderAdapter = hostMessageHeaderAdapter;
    }

    public ValidateUserPayloadAdapter getValidateUserPayloadAdapter() {
	return validateUserPayloadAdapter;
    }

    public void setValidateUserPayloadAdapter(ValidateUserPayloadAdapter validateUserPayloadAdapter) {
	this.validateUserPayloadAdapter = validateUserPayloadAdapter;
    }

}
