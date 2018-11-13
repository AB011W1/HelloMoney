package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class RetreiveIndvCustInfoServiceRequest extends RequestContext {

    private String creditCardNo;

    public String getCreditCardNo() {
	return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
	this.creditCardNo = creditCardNo;
    }
}
