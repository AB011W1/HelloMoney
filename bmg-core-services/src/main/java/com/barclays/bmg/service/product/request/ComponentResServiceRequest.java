package com.barclays.bmg.service.product.request;

import com.barclays.bmg.context.RequestContext;

public class ComponentResServiceRequest extends RequestContext {

    private String componentKey;
    private String screenId;

    public String getComponentKey() {
	return componentKey;
    }

    public void setComponentKey(String componentKey) {
	this.componentKey = componentKey;
    }

    public String getScreenId() {
	return screenId;
    }

    public void setScreenId(String screenId) {
	this.screenId = screenId;
    }

}
