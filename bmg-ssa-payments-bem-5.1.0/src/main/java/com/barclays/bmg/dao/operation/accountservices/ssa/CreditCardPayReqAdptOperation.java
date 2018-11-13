package com.barclays.bmg.dao.operation.accountservices.ssa;

import com.barclays.bem.MakeCreditCardPayment.MakeCreditCardPaymentRequest;
import com.barclays.bmg.dao.accountservices.adapter.ssa.CreditCardPayHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ssa.CreditCardPayPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardPayReqAdptOperation {

	private CreditCardPayHeaderAdapter creditCardPayHeaderAdapter;
	private CreditCardPayPayloadAdapter creditCardPayPayloadAdapter;
	public MakeCreditCardPaymentRequest adaptRequestForCCP(WorkContext workContext){

		MakeCreditCardPaymentRequest request = new MakeCreditCardPaymentRequest();
		request.setRequestHeader(creditCardPayHeaderAdapter.buildRequestHeader(workContext));
		request.setCreditCardPaymentInfo(creditCardPayPayloadAdapter.adaptPayload(workContext));

		return request;
	}
	public CreditCardPayHeaderAdapter getCreditCardPayHeaderAdapter() {
		return creditCardPayHeaderAdapter;
	}
	public void setCreditCardPayHeaderAdapter(
			CreditCardPayHeaderAdapter creditCardPayHeaderAdapter) {
		this.creditCardPayHeaderAdapter = creditCardPayHeaderAdapter;
	}
	public CreditCardPayPayloadAdapter getCreditCardPayPayloadAdapter() {
		return creditCardPayPayloadAdapter;
	}
	public void setCreditCardPayPayloadAdapter(
			CreditCardPayPayloadAdapter creditCardPayPayloadAdapter) {
		this.creditCardPayPayloadAdapter = creditCardPayPayloadAdapter;
	}

}
