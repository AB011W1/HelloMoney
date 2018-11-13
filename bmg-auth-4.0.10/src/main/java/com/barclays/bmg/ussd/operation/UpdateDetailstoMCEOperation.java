package com.barclays.bmg.ussd.operation;

import org.springframework.beans.BeanUtils;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.ussd.auth.operation.request.UpdateDetailstoMCEOpRequest;
import com.barclays.bmg.ussd.auth.operation.response.UpdateDetailstoMCEOperationResponse;
import com.barclays.bmg.ussd.auth.service.request.UpdateDetailstoMCEServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.UpdateDetailstoMCEServiceResponse;
import com.barclays.bmg.ussd.service.UpdateDetailstoMCEService;

public class UpdateDetailstoMCEOperation extends BMBCommonOperation {

    private UpdateDetailstoMCEService updateDetailstoMCEService;

    public UpdateDetailstoMCEOperationResponse updateDetailsToMCE(UpdateDetailstoMCEOpRequest updateDetailstoMCEOpRequest) {
	UpdateDetailstoMCEServiceRequest updateDetailstoMCEServiceRequest = new UpdateDetailstoMCEServiceRequest();
	BeanUtils.copyProperties(updateDetailstoMCEOpRequest, updateDetailstoMCEServiceRequest);
	Context context = updateDetailstoMCEOpRequest.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	UpdateDetailstoMCEOperationResponse operationResponse = new UpdateDetailstoMCEOperationResponse();
	UpdateDetailstoMCEServiceResponse resp = updateDetailstoMCEService.updateDetailsToMCE(updateDetailstoMCEServiceRequest);
	operationResponse.setSuccess(resp.isSuccess());
	if (!operationResponse.isSuccess()) {
	    getMessage(operationResponse);
	}
	operationResponse.setContext(context);
	return operationResponse;
    }

    public UpdateDetailstoMCEService getUpdateDetailstoMCEService() {
	return updateDetailstoMCEService;
    }

    public void setUpdateDetailstoMCEService(UpdateDetailstoMCEService updateDetailstoMCEService) {
	this.updateDetailstoMCEService = updateDetailstoMCEService;
    }
}
