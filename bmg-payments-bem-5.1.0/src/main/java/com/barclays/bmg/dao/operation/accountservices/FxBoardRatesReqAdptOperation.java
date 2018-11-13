package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveFXBoardRates.RetrieveFXBoardRatesRequest;
import com.barclays.bmg.dao.accountservices.adapter.FxBoardRatesHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.FxBoardRatesPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;



public class FxBoardRatesReqAdptOperation {

	private FxBoardRatesHeaderAdapter fxBoardRatesHeaderAdapter;
	private FxBoardRatesPayloadAdapter fxBoardRatesPayloadAdapter;

	public RetrieveFXBoardRatesRequest adaptRequestForFxRate(WorkContext workContext){

		RetrieveFXBoardRatesRequest request = new RetrieveFXBoardRatesRequest();
		request.setRequestHeader(fxBoardRatesHeaderAdapter.buildRequestHeader(workContext));
		request.setFxRateRequestInfo(fxBoardRatesPayloadAdapter.adaptPayload(workContext));

		return request;
	}

	public FxBoardRatesHeaderAdapter getFxBoardRatesHeaderAdapter() {
		return fxBoardRatesHeaderAdapter;
	}

	public void setFxBoardRatesHeaderAdapter(
			FxBoardRatesHeaderAdapter fxBoardRatesHeaderAdapter) {
		this.fxBoardRatesHeaderAdapter = fxBoardRatesHeaderAdapter;
	}

	public FxBoardRatesPayloadAdapter getFxBoardRatesPayloadAdapter() {
		return fxBoardRatesPayloadAdapter;
	}

	public void setFxBoardRatesPayloadAdapter(
			FxBoardRatesPayloadAdapter fxBoardRatesPayloadAdapter) {
		this.fxBoardRatesPayloadAdapter = fxBoardRatesPayloadAdapter;
	}



}
