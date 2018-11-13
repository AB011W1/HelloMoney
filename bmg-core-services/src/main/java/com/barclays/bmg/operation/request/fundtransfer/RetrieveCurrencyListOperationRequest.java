package com.barclays.bmg.operation.request.fundtransfer;

import com.barclays.bmg.context.RequestContext;

public class RetrieveCurrencyListOperationRequest extends RequestContext {

    String paramKey;
    String activityId;

    public String getParamKey() {
	return paramKey;
    }

    public void setParamKey(String paramKey) {
	this.paramKey = paramKey;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

}
