package com.barclays.bmg.ussd.auth.service.response;

import com.barclays.bmg.context.ResponseContext;

public class RegisterCustomerToProductServiceResponse extends ResponseContext {

	private String serviceResponse;

	public String getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

}
