package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class PasswordChangeServiceRequest extends RequestContext {

    private String oldPassword;
    private String newPassword;
    private String authType;

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

    public void setAuthType(String authType) {
	this.authType = authType;
    }

    public String getAuthType() {
	return authType;
    }

}
