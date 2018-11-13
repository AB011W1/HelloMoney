package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class UrlBusinessMapServiceRequest extends RequestContext {
    private String url;
    private String businessId;

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

}
