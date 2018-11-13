package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;

public class RetrievePayeeListOperationRequest extends RequestContext {

    private String payGrp;

    public String getPayGrp() {
	return payGrp;
    }

    public void setPayGrp(String payGrp) {
	this.payGrp = payGrp;
    }

}
