package com.barclays.bmg.service.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class CreditCardAccountDetailsServiceRequest extends RequestContext {

    private String accountNo;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

}
