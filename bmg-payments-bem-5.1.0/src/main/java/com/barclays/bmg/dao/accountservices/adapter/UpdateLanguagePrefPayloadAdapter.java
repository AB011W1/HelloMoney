package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.HMUpdateCustomer.UpdateCustomerRequestInfo_Type;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.UpdateLanguagePrefServiceRequest;

public class UpdateLanguagePrefPayloadAdapter {

    public UpdateCustomerRequestInfo_Type adaptPayLoad(WorkContext workContext) {

	UpdateCustomerRequestInfo_Type requestBody = new UpdateCustomerRequestInfo_Type();
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	UpdateLanguagePrefServiceRequest updateLanguagePrefServiceRequest = (UpdateLanguagePrefServiceRequest) args[0];
	Context context = updateLanguagePrefServiceRequest.getContext();

	requestBody.setCustomerID(context.getCustomerId());
	requestBody.setLanguagePreference(updateLanguagePrefServiceRequest.getPrefLang().toUpperCase());
	requestBody.setAutoAuthorize(true);
	requestBody.setUpdateType("2");

	return requestBody;

    }

}
