package com.barclays.bmg.ussd.auth.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;

public class RetrieveCustomerDetailsServiceResponse extends ResponseContext {

    private List<String> mobileNumberList;

    public List<String> getMobileNumberList() {
	return mobileNumberList;
    }

    public void setMobileNumberList(List<String> mobileNumberList) {
	this.mobileNumberList = mobileNumberList;
    }

}
