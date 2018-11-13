package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;

public class RetrievePayeeInfoOperationRequest extends RequestContext {

    private String payId;
    private String payGrp;

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
    }

    public String getPayGrp() {
	return payGrp;
    }

    public void setPayGrp(String payGrp) {
	this.payGrp = payGrp;
    }

}
