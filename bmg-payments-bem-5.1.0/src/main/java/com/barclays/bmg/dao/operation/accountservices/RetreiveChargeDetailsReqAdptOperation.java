package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveChargeDetails.RetrieveChargeDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveChargeDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveChargeDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetreiveChargeDetailsReqAdptOperation {
	private RetreiveChargeDetailsHeaderAdapter retreiveChargeDetailsHeaderAdapter;
	private RetreiveChargeDetailsPayloadAdapter retreiveChargeDetailsPayloadAdapter;
	public RetrieveChargeDetailsRequest adaptRequestForRetreiveChargeDetails(WorkContext workContext){

		RetrieveChargeDetailsRequest request = new RetrieveChargeDetailsRequest();
		request.setRequestHeader(retreiveChargeDetailsHeaderAdapter.buildRequestHeader(workContext));
		request.setRetrieveCharge(retreiveChargeDetailsPayloadAdapter.adaptPayload(workContext));
		return request;
	}
	public RetreiveChargeDetailsHeaderAdapter getRetreiveChargeDetailsHeaderAdapter() {
		return retreiveChargeDetailsHeaderAdapter;
	}
	public void setRetreiveChargeDetailsHeaderAdapter(
			RetreiveChargeDetailsHeaderAdapter retreiveChargeDetailsHeaderAdapter) {
		this.retreiveChargeDetailsHeaderAdapter = retreiveChargeDetailsHeaderAdapter;
	}
	public RetreiveChargeDetailsPayloadAdapter getRetreiveChargeDetailsPayloadAdapter() {
		return retreiveChargeDetailsPayloadAdapter;
	}
	public void setRetreiveChargeDetailsPayloadAdapter(
			RetreiveChargeDetailsPayloadAdapter retreiveChargeDetailsPayloadAdapter) {
		this.retreiveChargeDetailsPayloadAdapter = retreiveChargeDetailsPayloadAdapter;
	}

}
