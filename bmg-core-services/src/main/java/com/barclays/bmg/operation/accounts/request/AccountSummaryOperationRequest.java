package com.barclays.bmg.operation.accounts.request;

import com.barclays.bmg.context.RequestContext;

public class AccountSummaryOperationRequest extends RequestContext {

    private String accountType;

    private String activityIDParam;

    public String getAccountType() {
	return accountType;
    }

    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }

    public String getActivityIDParam() {
	return activityIDParam;
    }

    public void setActivityIDParam(String activityIDParam) {
	this.activityIDParam = activityIDParam;
    }

}
