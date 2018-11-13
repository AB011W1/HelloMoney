package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;

public class RetreivePayeeListOperationRequest extends RequestContext {

    private String payGrp;

    public String getPayGrp() {
	return payGrp;
    }

    public void setPayGrp(String payGrp) {
	this.payGrp = payGrp;
    }
}
