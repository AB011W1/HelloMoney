package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.json.response.BMBPayloadData;

public class SelfRegistrationAccountValidationOperationJSONResponseModel extends BMBPayloadData {

    private String serviceResponse;

    public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }

}