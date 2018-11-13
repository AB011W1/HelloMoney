package com.barclays.bmg.ussd.auth.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;

public class SMSDetailsServiceRequest extends RequestContext {

    private String event;

    private String priority;
    private String prefLang;

    public String getPrefLang() {
	return prefLang;
    }

    public void setPrefLang(String prefLang) {
	this.prefLang = prefLang;
    }

    private RequestContext parentRequest;

    private String dynamicFields;

    public String getDynamicFields() {
	return dynamicFields;
    }

    public void setDynamicFields(String dynamicFields) {
	this.dynamicFields = dynamicFields;
    }

    public RequestContext getParentRequest() {
	return parentRequest;
    }

    public void setParentRequest(RequestContext parentRequest) {
	this.parentRequest = parentRequest;
    }

    private ResponseContext parentResponse;

    public ResponseContext getParentResponse() {
	return parentResponse;
    }

    public void setParentResponse(ResponseContext parentResponse) {
	this.parentResponse = parentResponse;
    }

    public String getEvent() {
	return event;
    }

    public void setEvent(String event) {
	this.event = event;
    }

    public String getPriority() {
	return priority;
    }

    public void setPriority(String priority) {
	this.priority = priority;
    }

}
