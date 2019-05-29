package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveBillDetails.RetrieveBillDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveBillDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveBillDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrieveBillDetailsReqAdptOperation {

	private RetrieveBillDetailsHeaderAdapter retrieveBillDetailsHeaderAdapter;
	private RetrieveBillDetailsPayloadAdapter retrieveBillDetailsPayloadAdapter;

	public RetrieveBillDetailsRequest adaptRequestForRetrieveBillDetails(WorkContext workContext){
		RetrieveBillDetailsRequest request = new RetrieveBillDetailsRequest();
		request.setRequestHeader(retrieveBillDetailsHeaderAdapter.buildRequestHeader(workContext));
		request.setBillInquiryInfo(retrieveBillDetailsPayloadAdapter.adaptPayload(workContext));
		return request;
	}

	/**
	 * @return the retrieveBillDetailsHeaderAdapter
	 */
	public RetrieveBillDetailsHeaderAdapter getRetrieveBillDetailsHeaderAdapter() {
		return retrieveBillDetailsHeaderAdapter;
	}

	/**
	 * @param retrieveBillDetailsHeaderAdapter the retrieveBillDetailsHeaderAdapter to set
	 */
	public void setRetrieveBillDetailsHeaderAdapter(
			RetrieveBillDetailsHeaderAdapter retrieveBillDetailsHeaderAdapter) {
		this.retrieveBillDetailsHeaderAdapter = retrieveBillDetailsHeaderAdapter;
	}

	/**
	 * @return the retrieveBillDetailsPayloadAdapter
	 */
	public RetrieveBillDetailsPayloadAdapter getRetrieveBillDetailsPayloadAdapter() {
		return retrieveBillDetailsPayloadAdapter;
	}

	/**
	 * @param retrieveBillDetailsPayloadAdapter the retrieveBillDetailsPayloadAdapter to set
	 */
	public void setRetrieveBillDetailsPayloadAdapter(
			RetrieveBillDetailsPayloadAdapter retrieveBillDetailsPayloadAdapter) {
		this.retrieveBillDetailsPayloadAdapter = retrieveBillDetailsPayloadAdapter;
	}

}
