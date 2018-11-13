package com.barclays.bmg.operation.request.fundtransfer;

import com.barclays.bmg.context.RequestContext;

public class InternalFTDetailsOperationRequest extends RequestContext {

    private String payId;

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
    }

}
