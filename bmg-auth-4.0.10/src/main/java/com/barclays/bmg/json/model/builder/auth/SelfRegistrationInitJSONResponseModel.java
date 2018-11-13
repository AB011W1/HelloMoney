package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.json.response.BMBPayloadData;

public class SelfRegistrationInitJSONResponseModel extends BMBPayloadData {

    private String serviceResponse;
    private String fullName;

    public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


}
