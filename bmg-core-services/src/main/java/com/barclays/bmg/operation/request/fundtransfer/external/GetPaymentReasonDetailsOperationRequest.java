package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;

public class GetPaymentReasonDetailsOperationRequest extends RequestContext {

    private String payRsonKey;

    public String getPayRsonKey() {
	return payRsonKey;
    }

    public void setPayRsonKey(String payRsonKey) {
	this.payRsonKey = payRsonKey;
    }

}
