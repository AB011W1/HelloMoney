package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class MutualFundDetailsOperationRequest extends RequestContext {

    private String asssetClass;

    public String getAsssetClass() {
	return asssetClass;
    }

    public void setAsssetClass(String asssetClass) {
	this.asssetClass = asssetClass;
    }

}
