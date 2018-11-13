package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.MakeBillPayment.MakeBillPaymentRequest;
import com.barclays.bmg.dao.accountservices.adapter.MakePayBillHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.MakePayBillPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class MakePayBillReqAdptOperation {

	private MakePayBillHeaderAdapter makePayBillHeaderAdapter;
	private MakePayBillPayloadAdapter makePayBillPayloadAdapter;

	public MakeBillPaymentRequest adaptRequestForPayBill(WorkContext workContext) {
		MakeBillPaymentRequest makeBillPaymentRequest = new MakeBillPaymentRequest();
		makeBillPaymentRequest.setRequestHeader(makePayBillHeaderAdapter
				.buildRequestHeader(workContext));
		makeBillPaymentRequest.setBillPaymentInfo(makePayBillPayloadAdapter
				.adaptPayload(workContext));
		return makeBillPaymentRequest;
	}

	public MakePayBillHeaderAdapter getMakePayBillHeaderAdapter() {
		return makePayBillHeaderAdapter;
	}

	public void setMakePayBillHeaderAdapter(
			MakePayBillHeaderAdapter makePayBillHeaderAdapter) {
		this.makePayBillHeaderAdapter = makePayBillHeaderAdapter;
	}

	public MakePayBillPayloadAdapter getMakePayBillPayloadAdapter() {
		return makePayBillPayloadAdapter;
	}

	public void setMakePayBillPayloadAdapter(
			MakePayBillPayloadAdapter makePayBillPayloadAdapter) {
		this.makePayBillPayloadAdapter = makePayBillPayloadAdapter;
	}

}
