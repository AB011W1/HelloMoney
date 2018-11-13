package com.barclays.bmg.operation.request.intrates;

import com.barclays.bmg.context.RequestContext;

public class InterestRatesOperationRequest extends RequestContext {

    private String categoryCode;

    public String getCategoryCode() {
	return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
	this.categoryCode = categoryCode;
    }

}
