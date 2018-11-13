package com.barclays.bmg.ussd.auth.service.request;

import com.barclays.bmg.context.RequestContext;

public class ReissuePinServiceRequest extends RequestContext {

    private String customerId;

    private String mobileNumber;

    private String prefLang;

    public String getPrefLang() {
	return prefLang;
    }

    public void setPrefLang(String prefLang) {
	this.prefLang = prefLang;
    }

    public String getCustomerId() {
	return customerId;
    }

    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    public String getMobileNumber() {
	return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

}
