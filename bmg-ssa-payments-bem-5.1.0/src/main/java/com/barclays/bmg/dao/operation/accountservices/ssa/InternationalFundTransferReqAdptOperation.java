package com.barclays.bmg.dao.operation.accountservices.ssa;

import com.barclays.bem.MakeInternationalFundTransfer.MakeInternationalFundTransferRequest;
import com.barclays.bmg.dao.accountservices.adapter.ssa.InternationalFundTransferHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ssa.InternationalFundTransferPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class InternationalFundTransferReqAdptOperation {
	private InternationalFundTransferHeaderAdapter internationalFundTransferHeaderAdapter;
	private InternationalFundTransferPayloadAdapter internationalFundTransferPayloadAdapter;

	public MakeInternationalFundTransferRequest adaptRequestForInternationalFundTransfer(WorkContext workContext){

		MakeInternationalFundTransferRequest request = new MakeInternationalFundTransferRequest();
		request.setRequestHeader(internationalFundTransferHeaderAdapter.buildRequestHeader(workContext));
		request.setInternationalFundTransferInfo(internationalFundTransferPayloadAdapter.adaptPayload(workContext));
		return request;
	}

	public InternationalFundTransferHeaderAdapter getInternationalFundTransferHeaderAdapter() {
		return internationalFundTransferHeaderAdapter;
	}

	public void setInternationalFundTransferHeaderAdapter(
			InternationalFundTransferHeaderAdapter internationalFundTransferHeaderAdapter) {
		this.internationalFundTransferHeaderAdapter = internationalFundTransferHeaderAdapter;
	}

	public InternationalFundTransferPayloadAdapter getInternationalFundTransferPayloadAdapter() {
		return internationalFundTransferPayloadAdapter;
	}

	public void setInternationalFundTransferPayloadAdapter(
			InternationalFundTransferPayloadAdapter internationalFundTransferPayloadAdapter) {
		this.internationalFundTransferPayloadAdapter = internationalFundTransferPayloadAdapter;
	}

}
