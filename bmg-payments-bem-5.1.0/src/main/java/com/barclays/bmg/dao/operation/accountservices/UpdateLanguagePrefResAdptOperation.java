package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.HMUpdateCustomer.UpdateHMCustomerResponseType;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.UpdateLanguagePrefServiceResponse;

public class UpdateLanguagePrefResAdptOperation extends AbstractResAdptOperationAcct {

    public UpdateLanguagePrefServiceResponse adaptResponse(WorkContext workContext, Object obj) throws Exception {
	UpdateLanguagePrefServiceResponse response = new UpdateLanguagePrefServiceResponse();

	UpdateHMCustomerResponseType bemResponse = (UpdateHMCustomerResponseType) obj;

	if (bemResponse != null) {

	    if (checkResponseHeader(bemResponse.getResponseHeader())) {
		response.setSuccess(true);
		return response;
	    }
	}
	response.setSuccess(false);
	return response;
    }
}
