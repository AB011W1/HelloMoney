package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class ApplyTDOperationRequest extends RequestContext {

    private String acctNo;

    public String getAcctNo() {
	return acctNo;
    }

    public void setAcctNo(String acctNo) {
	this.acctNo = acctNo;
    }

}
