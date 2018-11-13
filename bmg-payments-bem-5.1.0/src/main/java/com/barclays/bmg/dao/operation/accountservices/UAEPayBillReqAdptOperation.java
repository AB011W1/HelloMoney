package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.UAEMakeBillPayment.UAEMakeBillPaymentRequest;
import com.barclays.bmg.dao.accountservices.adapter.UAEPayBillHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.UAEPayBillPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;


public class UAEPayBillReqAdptOperation {

	private UAEPayBillHeaderAdapter uaePayBillHeaderAdapter;
	private UAEPayBillPayloadAdapter uaePayBillPayloadAdapter;

	public UAEMakeBillPaymentRequest adaptRequestForPayBill(WorkContext workContext){
		UAEMakeBillPaymentRequest uaeMakeBillPaymentRequest = new UAEMakeBillPaymentRequest();
		uaeMakeBillPaymentRequest.setRequestHeader(uaePayBillHeaderAdapter.buildRequestHeader(workContext));
		uaeMakeBillPaymentRequest.setBillPaymentInfo(uaePayBillPayloadAdapter.adaptPayload(workContext));
		return uaeMakeBillPaymentRequest;
	}
	public UAEPayBillHeaderAdapter getUaePayBillHeaderAdapter() {
		return uaePayBillHeaderAdapter;
	}
	public void setUaePayBillHeaderAdapter(
			UAEPayBillHeaderAdapter uaePayBillHeaderAdapter) {
		this.uaePayBillHeaderAdapter = uaePayBillHeaderAdapter;
	}
	public UAEPayBillPayloadAdapter getUaePayBillPayloadAdapter() {
		return uaePayBillPayloadAdapter;
	}
	public void setUaePayBillPayloadAdapter(
			UAEPayBillPayloadAdapter uaePayBillPayloadAdapter) {
		this.uaePayBillPayloadAdapter = uaePayBillPayloadAdapter;
	}
}
