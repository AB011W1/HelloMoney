package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class CASADetailsOperationRequest extends RequestContext {
    private String accountNo;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

}
