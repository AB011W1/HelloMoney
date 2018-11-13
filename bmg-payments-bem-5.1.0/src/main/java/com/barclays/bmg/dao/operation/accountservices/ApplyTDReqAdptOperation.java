package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.AddProblem.AddProblemRequest;
import com.barclays.bmg.dao.accountservices.adapter.ApplyTDHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ApplyTDPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ApplyTDReqAdptOperation {

	private ApplyTDHeaderAdapter applyTDHeaderAdapter;
	private ApplyTDPayloadAdapter applyTDPayloadAdapter;

	public AddProblemRequest adaptRequestForApplyTD(WorkContext workContext){

		AddProblemRequest request = new AddProblemRequest();
		request.setRequestHeader(applyTDHeaderAdapter.buildRequestHeader(workContext));
		//request.getRequestHeader().getCustomerContext()

		request.setRequest(applyTDPayloadAdapter.adaptPayload(workContext));
		return request;
	}

	public ApplyTDHeaderAdapter getApplyTDHeaderAdapter() {
		return applyTDHeaderAdapter;
	}

	public void setApplyTDHeaderAdapter(ApplyTDHeaderAdapter applyTDHeaderAdapter) {
		this.applyTDHeaderAdapter = applyTDHeaderAdapter;
	}

	public ApplyTDPayloadAdapter getApplyTDPayloadAdapter() {
		return applyTDPayloadAdapter;
	}

	public void setApplyTDPayloadAdapter(ApplyTDPayloadAdapter applyTDPayloadAdapter) {
		this.applyTDPayloadAdapter = applyTDPayloadAdapter;
	}


}
