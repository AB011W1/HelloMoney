package com.barclays.bmg.dao.operation.accountservices.ssa;

import com.barclays.bem.SSAMakeBillPayment.SSAMakeBillPaymentRequest;
import com.barclays.bmg.dao.accountservices.adapter.ssa.PayBillHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ssa.PayBillPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class PayBillReqAdptOperation {
	private PayBillHeaderAdapter payBillHeaderAdapter;
	private PayBillPayloadAdapter payBillPayloadAdapter;

	public SSAMakeBillPaymentRequest adaptRequestForPayBill(WorkContext workContext){
		SSAMakeBillPaymentRequest request = new SSAMakeBillPaymentRequest();
		request.setRequestHeader(payBillHeaderAdapter.buildRequestHeader(workContext));
		request.setBillPaymentInfo(payBillPayloadAdapter.adaptPayload(workContext));
		return request;
	}

	public PayBillHeaderAdapter getPayBillHeaderAdapter() {
		return payBillHeaderAdapter;
	}

	public void setPayBillHeaderAdapter(PayBillHeaderAdapter payBillHeaderAdapter) {
		this.payBillHeaderAdapter = payBillHeaderAdapter;
	}

	public PayBillPayloadAdapter getPayBillPayloadAdapter() {
		return payBillPayloadAdapter;
	}

	public void setPayBillPayloadAdapter(PayBillPayloadAdapter payBillPayloadAdapter) {
		this.payBillPayloadAdapter = payBillPayloadAdapter;
	}

}
