package com.barclays.bmg.ussd.auth.operation.response;

import com.barclays.bmg.context.ResponseContext;


/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>SelfRegistrationAccountValidationOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>Jun 22, 2015</b> </br>
 * ***********************************************************
 *
 *
 *
 */
public class SelfRegistrationAccountValidationOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 5111658142863696798L;
    private String serviceResponse;

    public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }

}
