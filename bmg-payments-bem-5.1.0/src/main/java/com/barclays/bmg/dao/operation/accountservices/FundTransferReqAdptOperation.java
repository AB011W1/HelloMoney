package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferRequest;
import com.barclays.bmg.dao.accountservices.adapter.FundTransferExecutionHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.FundTransferExecutionPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class FundTransferReqAdptOperation {

	private FundTransferExecutionHeaderAdapter fundTransferExecutionHeaderAdapter;
	private FundTransferExecutionPayloadAdapter fundTransferExecutionPayloadAdapter;

	public MakeDomesticFundTransferRequest adaptRequestForFundTransfer(WorkContext workContext){

		MakeDomesticFundTransferRequest fundTransferRequest = new MakeDomesticFundTransferRequest();

		fundTransferRequest.setRequestHeader(
				fundTransferExecutionHeaderAdapter.buildFundTransferRequestHeader(workContext));

		fundTransferRequest.setDomesticFundTransferInfo(
				fundTransferExecutionPayloadAdapter.adaptPayload(workContext));

		return fundTransferRequest;
	}

	public FundTransferExecutionHeaderAdapter getFundTransferExecutionHeaderAdapter() {
		return fundTransferExecutionHeaderAdapter;
	}

	public void setFundTransferExecutionHeaderAdapter(
			FundTransferExecutionHeaderAdapter fundTransferExecutionHeaderAdapter) {
		this.fundTransferExecutionHeaderAdapter = fundTransferExecutionHeaderAdapter;
	}

	public FundTransferExecutionPayloadAdapter getFundTransferExecutionPayloadAdapter() {
		return fundTransferExecutionPayloadAdapter;
	}

	public void setFundTransferExecutionPayloadAdapter(
			FundTransferExecutionPayloadAdapter fundTransferExecutionPayloadAdapter) {
		this.fundTransferExecutionPayloadAdapter = fundTransferExecutionPayloadAdapter;
	}
}
