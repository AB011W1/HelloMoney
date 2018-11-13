package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

/**
 * Authentication service request
 * 
 * @author e20026338
 * 
 */
public class ValidateUserServiceRequest extends RequestContext {
    private String userID;
    private String userName;
    private String password;
    private String tnxPassword;
    private String passwordType;
    private String perferredLanguage;
    private String customerId;

    public java.lang.String getUserID() {
	return userID;
    }

    public void setUserID(java.lang.String userID) {
	this.userID = userID;
    }

    public java.lang.String getUserName() {
	return userName;
    }

    public void setUserName(java.lang.String userName) {
	this.userName = userName;
    }

    public java.lang.String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getTnxPassword() {
	return tnxPassword;
    }

    public void setTnxPassword(String tnxPassword) {
	this.tnxPassword = tnxPassword;
    }

    public String getPasswordType() {
	return passwordType;
    }

    public void setPasswordType(String passwordType) {
	this.passwordType = passwordType;
    }

    public String getPerferredLanguage() {
	return perferredLanguage;
    }

    public void setPerferredLanguage(String perferredLanguage) {
	this.perferredLanguage = perferredLanguage;
    }

    public String getCustomerId() {
	return customerId;
    }

    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

}
