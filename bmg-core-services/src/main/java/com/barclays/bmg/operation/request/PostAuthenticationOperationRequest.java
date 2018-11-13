package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

public class PostAuthenticationOperationRequest extends RequestContext {
    private String mobilePhone;

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

}
