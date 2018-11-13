package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class MessageResourceServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -8131602440868672149L;
    private String messageDesc;

    public String getMessageDesc() {
	return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
	this.messageDesc = messageDesc;
    }
}
