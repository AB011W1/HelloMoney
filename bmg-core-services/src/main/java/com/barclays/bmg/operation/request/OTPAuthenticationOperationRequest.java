package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

public class OTPAuthenticationOperationRequest extends RequestContext {

    private String otp;
    private String mobilePhone;
    private String challengeId;

    public String getOtp() {
	return otp;
    }

    public void setOtp(String otp) {
	this.otp = otp;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getChallengeId() {
	return challengeId;
    }

    public void setChallengeId(String challengeId) {
	this.challengeId = challengeId;
    }

}
