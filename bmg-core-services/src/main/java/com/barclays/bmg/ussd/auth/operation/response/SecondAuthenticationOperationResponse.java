/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.ussd.auth.operation.response;

import com.barclays.bmg.context.ResponseContext;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SecondAuthenticationOperationResponse extends ResponseContext {



	/**
	 *
	 */
    private static final long serialVersionUID = 5304900216171962702L;

    private String questionId;

    private String serviceResponse;


    public String getQuestionId() {
	return questionId;
    }

    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

    public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }
}
