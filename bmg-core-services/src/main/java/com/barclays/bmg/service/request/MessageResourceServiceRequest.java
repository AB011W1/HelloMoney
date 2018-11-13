package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class MessageResourceServiceRequest extends RequestContext {
    private String messageKey;

    public String getMessageKey() {
	return messageKey;
    }

    public void setMessageKey(String messageKey) {
	this.messageKey = messageKey;
    }
}
