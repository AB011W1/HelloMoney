package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

/**
 * Authentication service request
 * 
 * @author e20026338
 * 
 */
public class AuthenticationServiceRequest extends RequestContext {

    /*
     * Properties for SP
     */
    private String username;
    private String password;

    /*
     * Properties for OTP challenge
     */
    private String mobilePhone;
    private String[] smsParams;
    private String customerId;
    private String smsTemplate;

    /*
     * Properties for SQA
     */
    private String questionId;
    private String sqa;

    /*
     * Properties for otp
     */
    private String challengeId;
    private String otpPrefix;
    private String otp;

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

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String[] getSmsParams() {
	String[] str = smsParams;
	return str;
    }

    public void setSmsParams(String[] smsParams) {
	String[] str = smsParams;
	this.smsParams = str;
    }

    public String getCustomerId() {
	return customerId;
    }

    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    public String getSmsTemplate() {
	return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
	this.smsTemplate = smsTemplate;
    }

    public String getQuestionId() {
	return questionId;
    }

    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

    public String getSqa() {
	return sqa;
    }

    public void setSqa(String sqa) {
	this.sqa = sqa;
    }

    public String getChallengeId() {
	return challengeId;
    }

    public void setChallengeId(String challengeId) {
	this.challengeId = challengeId;
    }

    public String getOtpPrefix() {
	return otpPrefix;
    }

    public void setOtpPrefix(String otpPrefix) {
	this.otpPrefix = otpPrefix;
    }

    public String getOtp() {
	return otp;
    }

    public void setOtp(String otp) {
	this.otp = otp;
    }

}
