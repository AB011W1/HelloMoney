package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class ScriptResourceServiceRequest extends RequestContext {
    private String scriptKey;

    public String getScriptKey() {
	return scriptKey;
    }

    public void setScriptKey(String scriptKey) {
	this.scriptKey = scriptKey;
    }
}
