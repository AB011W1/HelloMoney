package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.ViewBillPaymentDetails.ViewBillPaymentDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.ViewBillPaymentDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ViewBillPaymentDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ViewBillPaymentDetailsReqAdptOperation {

	private ViewBillPaymentDetailsHeaderAdapter viewBillPaymentDetailsHeaderAdapter;
	private ViewBillPaymentDetailsPayloadAdapter viewBillPaymentDetailsPayloadAdapter;


	public void setViewBillPaymentDetailsHeaderAdapter(
			ViewBillPaymentDetailsHeaderAdapter viewBillPaymentDetailsHeaderAdapter) {
		this.viewBillPaymentDetailsHeaderAdapter = viewBillPaymentDetailsHeaderAdapter;
	}


	public void setViewBillPaymentDetailsPayloadAdapter(
			ViewBillPaymentDetailsPayloadAdapter viewBillPaymentDetailsPayloadAdapter) {
		this.viewBillPaymentDetailsPayloadAdapter = viewBillPaymentDetailsPayloadAdapter;
	}

	public ViewBillPaymentDetailsRequest adaptRequestforViewBillPaymentDetails(WorkContext context){

		ViewBillPaymentDetailsRequest request = new ViewBillPaymentDetailsRequest();
		request.setRequestHeader(viewBillPaymentDetailsHeaderAdapter.buildRequestHeader(context));
		request.setViewBillPaymentInfo(viewBillPaymentDetailsPayloadAdapter.adaptPayload(context));
		return request;
	}

}
