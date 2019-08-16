package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.HMUpdateCustomer.UpdateHMCustomerRequestType;
import com.barclays.bmg.dao.accountservices.adapter.UpdateLanguagePrefHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.UpdateLanguagePrefPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class UpdateLanguagePrefReqAdptOperation {

    private UpdateLanguagePrefHeaderAdapter updateLanguagePrefHeaderAdapter;
    private UpdateLanguagePrefPayloadAdapter updateLanguagePrefPayloadAdapter;

    public void setUpdateLanguagePrefHeaderAdapter(UpdateLanguagePrefHeaderAdapter updateLanguagePrefHeaderAdapter) {
	this.updateLanguagePrefHeaderAdapter = updateLanguagePrefHeaderAdapter;
    }

    public void setUpdateLanguagePrefPayloadAdapter(UpdateLanguagePrefPayloadAdapter updateLanguagePrefPayloadAdapter) {
	this.updateLanguagePrefPayloadAdapter = updateLanguagePrefPayloadAdapter;
    }

    /**
     * @param context
     * @return DeleteIndividualCustomerBeneficiaryRequest
     */
    public final Object adaptRequest(final WorkContext context) {

	UpdateHMCustomerRequestType request = new UpdateHMCustomerRequestType();
	request.setRequestHeader(updateLanguagePrefHeaderAdapter.buildRequestHeader(context));
	request.setUpdateCustomerRequestInfo(updateLanguagePrefPayloadAdapter.adaptPayLoad(context));
	return request;
    }

}
