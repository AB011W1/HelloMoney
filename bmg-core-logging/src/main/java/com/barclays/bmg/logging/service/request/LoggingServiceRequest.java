package com.barclays.bmg.logging.service.request;

import com.barclays.bmg.context.RequestContext;

public class LoggingServiceRequest extends RequestContext {

    String level;
    String message;
    String className;

    public String getLevel() {
	return level;
    }

    public void setLevel(String level) {
	this.level = level;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getClassName() {
	return className;
    }

    public void setClassName(String className) {
	this.className = className;
    }

}
