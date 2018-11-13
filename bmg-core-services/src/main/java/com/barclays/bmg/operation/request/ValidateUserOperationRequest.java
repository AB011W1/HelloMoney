package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

/**
 * SP Authentication Operation Request
 * 
 * @author e20026338
 * 
 */

public class ValidateUserOperationRequest extends RequestContext {

    private String username;

    public ValidateUserOperationRequest() {

    }

    public ValidateUserOperationRequest(String username, String password) {
	this.username = username;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }
}
