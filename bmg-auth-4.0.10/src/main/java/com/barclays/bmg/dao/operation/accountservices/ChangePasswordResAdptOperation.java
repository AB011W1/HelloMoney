package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.UpdatePIN.UpdatePINResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.PasswordChangeServiceResponse;

public class ChangePasswordResAdptOperation extends AbstractResAdptOperation {

    public PasswordChangeServiceResponse adaptResponseChangePassword(WorkContext workContext, Object obj) throws Exception {

	PasswordChangeServiceResponse response = new PasswordChangeServiceResponse();

	UpdatePINResponse bemResponse = (UpdatePINResponse) obj;

	if (bemResponse != null) {
	    if (null != bemResponse.getTransactionResponseStatus()) {
		response.setTxnRefNumber(bemResponse.getTransactionResponseStatus().getTransactionReferenceNumber());
	    }

	    response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
	    response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());

	    if (checkResponseHeader(bemResponse.getResponseHeader())) {
		response.setSuccess(true);
		return response;
	    }
	}
	response.setSuccess(false);
	return response;

    }
}
