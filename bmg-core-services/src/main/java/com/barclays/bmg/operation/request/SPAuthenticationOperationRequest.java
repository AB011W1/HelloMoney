package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

/**
 * SP Authentication Operation Request
 * 
 * @author e20026338
 * 
 */

public class SPAuthenticationOperationRequest extends RequestContext {

    private String username;
    private String password;

    public SPAuthenticationOperationRequest() {

    }

    public SPAuthenticationOperationRequest(String username, String password) {

	// super();
	this.username = username;
	this.password = password;

    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
