package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.AuthenticationDTO;

public class OTPGenerateAuthenticationOperationRequest extends RequestContext {

    private String mobilePhone;
    private String[] smsParams;
    private String customerId;
    private String smsTemplate;

    private AuthenticationDTO authenticationDTO;

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String[] getSmsParams() {
	String str[] = smsParams;
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

    public AuthenticationDTO getAuthenticationDTO() {
	return authenticationDTO;
    }

    public void setAuthenticationDTO(AuthenticationDTO authenticationDTO) {
	this.authenticationDTO = authenticationDTO;
    }

}
