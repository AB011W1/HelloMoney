package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

public class ChangePasswordOperationRequest extends RequestContext {

    private String oldPassword;
    private String newPassword;
    private String authType;

    public String getAuthType() {
	return authType;
    }

    public void setAuthType(String authType) {
	this.authType = authType;
    }

    public String getOldPassword() {
	return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
	this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
	return newPassword;
    }

    public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
    }

}
