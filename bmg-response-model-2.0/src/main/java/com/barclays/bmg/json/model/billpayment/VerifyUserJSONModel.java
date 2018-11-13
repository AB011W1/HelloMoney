package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.json.response.BMBPayloadData;

public class VerifyUserJSONModel extends BMBPayloadData implements Serializable {
    private static final long serialVersionUID = -5436515665377036572L;
    private String status;
    private String mobilePhone;
    private String preferredLanguage;
    private String userId;
    private String countryCode;
    private String businessID;
    private String cryptoStatusCode;
    private String cryptoPinStatus;
    private String userStatusInMCE;

    public void setBusinessID(String businessID) {
	this.businessID = businessID;
    }

    public String getBusinessID() {
	return businessID;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getPreferredLanguage() {
	return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
	this.preferredLanguage = preferredLanguage;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public VerifyUserJSONModel() {

    }

    public String getCryptoStatusCode() {
	return cryptoStatusCode;
    }

    public void setCryptoStatusCode(String cryptoStatusCode) {
	this.cryptoStatusCode = cryptoStatusCode;
    }

    public String getCryptoPinStatus() {
	return cryptoPinStatus;
    }

    public void setCryptoPinStatus(String cryptoPinStatus) {
	this.cryptoPinStatus = cryptoPinStatus;
    }

    public String getUserStatusInMCE() {
	return userStatusInMCE;
    }

    public void setUserStatusInMCE(String userStatusInMCE) {
	this.userStatusInMCE = userStatusInMCE;
    }

}
