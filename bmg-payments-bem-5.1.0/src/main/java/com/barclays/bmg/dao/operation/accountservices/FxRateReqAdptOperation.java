package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveFXRate.RetrieveFXRateRequest;
import com.barclays.bmg.dao.accountservices.adapter.FxRateHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.FxRatePayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;



public class FxRateReqAdptOperation {

	private FxRateHeaderAdapter fxRateHeaderAdapter;
	private FxRatePayloadAdapter fxRatePayloadAdapter;

	public RetrieveFXRateRequest adaptRequestForFxRate(WorkContext workContext){

		RetrieveFXRateRequest request = new RetrieveFXRateRequest();
		request.setRequestHeader(fxRateHeaderAdapter.buildRequestHeader(workContext));
		request.setFxRateRequestInfo(fxRatePayloadAdapter.adaptPayload(workContext));

		return request;
	}

	public FxRateHeaderAdapter getFxRateHeaderAdapter() {
		return fxRateHeaderAdapter;
	}

	public void setFxRateHeaderAdapter(FxRateHeaderAdapter fxRateHeaderAdapter) {
		this.fxRateHeaderAdapter = fxRateHeaderAdapter;
	}

	public FxRatePayloadAdapter getFxRatePayloadAdapter() {
		return fxRatePayloadAdapter;
	}

	public void setFxRatePayloadAdapter(FxRatePayloadAdapter fxRatePayloadAdapter) {
		this.fxRatePayloadAdapter = fxRatePayloadAdapter;
	}

}
