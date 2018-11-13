package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.UAERetrieveBillDetails.UAERetrieveBillDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.InqueryBillHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.InqueryBillPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class InqueryBillReqAdptOperation {

	private InqueryBillHeaderAdapter inqueryBillHeaderAdapter;
	private InqueryBillPayloadAdapter inqueryBillPayloadAdapter;

	public UAERetrieveBillDetailsRequest adaptRequestForInqueryBill(WorkContext workContext){

		UAERetrieveBillDetailsRequest request = new UAERetrieveBillDetailsRequest();
		request.setRequestHeader(inqueryBillHeaderAdapter.buildRequestHeader(workContext));
		request.setBillInquiryInfo(inqueryBillPayloadAdapter.adaptPayload(workContext));

		return request;
	}

	public InqueryBillHeaderAdapter getInqueryBillHeaderAdapter() {
		return inqueryBillHeaderAdapter;
	}

	public void setInqueryBillHeaderAdapter(
			InqueryBillHeaderAdapter inqueryBillHeaderAdapter) {
		this.inqueryBillHeaderAdapter = inqueryBillHeaderAdapter;
	}

	public InqueryBillPayloadAdapter getInqueryBillPayloadAdapter() {
		return inqueryBillPayloadAdapter;
	}

	public void setInqueryBillPayloadAdapter(
			InqueryBillPayloadAdapter inqueryBillPayloadAdapter) {
		this.inqueryBillPayloadAdapter = inqueryBillPayloadAdapter;
	}
}
