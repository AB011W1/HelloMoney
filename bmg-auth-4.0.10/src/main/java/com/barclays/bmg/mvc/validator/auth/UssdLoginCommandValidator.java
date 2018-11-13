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
package com.barclays.bmg.mvc.validator.auth;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLoginCommandValidator.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>Jun 12, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLoginCommandValidator created using JRE 1.6.0_10
 */
public class UssdLoginCommandValidator extends LoginCommandValidator {
    /**
     * The instance variable named "respId" is created.
     */
    private String responseId;

    /**
     * @return the responseId
     */
    public String getResponseId() {
	return responseId;
    }

    /**
	 *
	 */
    public void setResponseId(String responseId) {
	super.respID = responseId;
	this.responseId = responseId;
    }
}