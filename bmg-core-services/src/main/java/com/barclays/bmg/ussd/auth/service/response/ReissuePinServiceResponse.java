package com.barclays.bmg.ussd.auth.service.response;

import com.barclays.bmg.context.ResponseContext;

public class ReissuePinServiceResponse extends ResponseContext {

    private String serviceResponse;

    private String pin;

    public String getPin() {
	return pin;
    }

    public void setPin(String pin) {
	this.pin = pin;
    }

    public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }

}
