package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveIndividualCustBySCVID.RetrieveIndividualCustomerBySCVIDRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveIndCustBySCVIDHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveIndCustBySCVIDPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrieveIndCustBySCVIDReqAdptOperation {

    private RetrieveIndCustBySCVIDHeaderAdapter retrieveIndCustBySCVIDHeaderAdapter;
    private RetrieveIndCustBySCVIDPayloadAdapter retrieveIndCustBySCVIDPayloadAdapter;

    public void setRetrieveIndCustBySCVIDHeaderAdapter(RetrieveIndCustBySCVIDHeaderAdapter retrieveIndCustBySCVIDHeaderAdapter) {
	this.retrieveIndCustBySCVIDHeaderAdapter = retrieveIndCustBySCVIDHeaderAdapter;
    }

    public void setRetrieveIndCustBySCVIDPayloadAdapter(RetrieveIndCustBySCVIDPayloadAdapter retrieveIndCustBySCVIDPayloadAdapter) {
	this.retrieveIndCustBySCVIDPayloadAdapter = retrieveIndCustBySCVIDPayloadAdapter;
    }

    public RetrieveIndividualCustomerBySCVIDRequest adaptRequest(WorkContext workContext) {

	RetrieveIndividualCustomerBySCVIDRequest request = new RetrieveIndividualCustomerBySCVIDRequest();
	request.setRequestHeader(retrieveIndCustBySCVIDHeaderAdapter.buildBemReqHeader(workContext));
	request.setIndividuaCustomerSearchInfo(retrieveIndCustBySCVIDPayloadAdapter.adaptPayLoad(workContext));
	return request;
    }

}
