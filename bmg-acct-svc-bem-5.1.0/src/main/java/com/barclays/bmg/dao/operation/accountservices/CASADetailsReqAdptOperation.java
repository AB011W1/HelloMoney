package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveCASAAcctDetails.RetrieveCASAAccountDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.CASADetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.CASADetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CASADetailsReqAdptOperation {

    private CASADetailsHeaderAdapter casaDetailsHeaderAdapter;

    private CASADetailsPayloadAdapter casaDetailsPayloadAdapter;

    public RetrieveCASAAccountDetailsRequest adaptRequestForCASADetails(WorkContext workContext) {

	RetrieveCASAAccountDetailsRequest casaDetailsRequest = new RetrieveCASAAccountDetailsRequest();

	casaDetailsRequest.setRequestHeader(casaDetailsHeaderAdapter.buildCASADetailsHeader(workContext));

	casaDetailsPayloadAdapter.adaptPayLoad(workContext, casaDetailsRequest);

	return casaDetailsRequest;
    }

    public void setCasaDetailsHeaderAdapter(CASADetailsHeaderAdapter casaDetailsHeaderAdapter) {
	this.casaDetailsHeaderAdapter = casaDetailsHeaderAdapter;
    }

    public void setCasaDetailsPayloadAdapter(CASADetailsPayloadAdapter casaDetailsPayloadAdapter) {
	this.casaDetailsPayloadAdapter = casaDetailsPayloadAdapter;
    }

}
