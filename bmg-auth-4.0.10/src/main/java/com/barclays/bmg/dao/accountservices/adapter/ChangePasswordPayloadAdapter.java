package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.UpdatePIN.UpdatePIN;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.PasswordChangeServiceRequest;

public class ChangePasswordPayloadAdapter {

    public UpdatePIN adaptPayLoad(WorkContext workContext) {
	UpdatePIN requestBody = new UpdatePIN();
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	PasswordChangeServiceRequest passwordResetServiceRequest = (PasswordChangeServiceRequest) args[0];
	Context context = passwordResetServiceRequest.getContext();

	requestBody.setCustomerId(context.getCustomerId());
	requestBody.setMobileNumber(context.getMobilePhone());
	requestBody.setOldPIN(passwordResetServiceRequest.getOldPassword());
	requestBody.setNewPIN(passwordResetServiceRequest.getNewPassword());

	return requestBody;
    }

}
