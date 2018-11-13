package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;

public class OTPAuthenticationOperationResponse extends ResponseContext {

    private String otpPrefix;
    private String otpHeaderLine1;
    private String otpHeaderLine2;
    private String otpFooter;
    private boolean success;

    public String getOtpPrefix() {
	return otpPrefix;
    }

    public void setOtpPrefix(String otpPrefix) {
	this.otpPrefix = otpPrefix;
    }

    public String getOtpHeaderLine1() {
	return otpHeaderLine1;
    }

    public void setOtpHeaderLine1(String otpHeaderLine1) {
	this.otpHeaderLine1 = otpHeaderLine1;
    }

    public String getOtpHeaderLine2() {
	return otpHeaderLine2;
    }

    public void setOtpHeaderLine2(String otpHeaderLine2) {
	this.otpHeaderLine2 = otpHeaderLine2;
    }

    public String getOtpFooter() {
	return otpFooter;
    }

    public void setOtpFooter(String otpFooter) {
	this.otpFooter = otpFooter;
    }

    @Override
    public boolean isSuccess() {
	return success;
    }

    @Override
    public void setSuccess(boolean success) {
	this.success = success;
    }

}
