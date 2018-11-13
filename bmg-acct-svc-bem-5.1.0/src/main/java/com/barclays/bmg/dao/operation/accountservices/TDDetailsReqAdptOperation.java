package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveTimeDepositDetails.RetrieveTimeDepositDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.TDDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.TDDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class TDDetailsReqAdptOperation {
    private TDDetailsHeaderAdapter tdDetailsHeaderAdapter;
    private TDDetailsPayloadAdapter tdDetailsPayloadAdapter;

    public RetrieveTimeDepositDetailsRequest adaptRequestForInqueryBill(WorkContext workContext) {
	RetrieveTimeDepositDetailsRequest request = new RetrieveTimeDepositDetailsRequest();
	request.setRequestHeader(tdDetailsHeaderAdapter.buildTDDetailsHeader(workContext));
	request.setTimeDepositInfo(tdDetailsPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public TDDetailsHeaderAdapter getTdDetailsHeaderAdapter() {
	return tdDetailsHeaderAdapter;
    }

    public void setTdDetailsHeaderAdapter(TDDetailsHeaderAdapter tdDetailsHeaderAdapter) {
	this.tdDetailsHeaderAdapter = tdDetailsHeaderAdapter;
    }

    public TDDetailsPayloadAdapter getTdDetailsPayloadAdapter() {
	return tdDetailsPayloadAdapter;
    }

    public void setTdDetailsPayloadAdapter(TDDetailsPayloadAdapter tdDetailsPayloadAdapter) {
	this.tdDetailsPayloadAdapter = tdDetailsPayloadAdapter;
    }

}
