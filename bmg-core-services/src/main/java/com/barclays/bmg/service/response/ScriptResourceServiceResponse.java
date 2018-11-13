package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class ScriptResourceServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 5035470635740398190L;
    private String scriptValue;

    public String getScriptValue() {
	return scriptValue;
    }

    public void setScriptValue(String scriptValue) {
	this.scriptValue = scriptValue;
    }
}
