package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;

public class OneTimeBillPayOperationRequest extends RequestContext {

    private String billerId;
    private String billerType;

    public String getBillerType() {
		return billerType;
	}

	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}

	public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

}
