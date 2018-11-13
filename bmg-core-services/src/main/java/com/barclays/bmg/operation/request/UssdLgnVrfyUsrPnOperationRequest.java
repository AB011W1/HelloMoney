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
/**
 * Package name is com.barclays.bmg.operation.request
 * /
 */
package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnOperationRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 15, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnOperationRequest created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnOperationRequest extends RequestContext {

    /**
     * The instance variable named "usrNam" is created.
     */
    private String usrNam;

    /**
     * The instance variable named "pass" is created.
     */
    private String pass;

    /**
     * Gets the usr nam.
     * 
     * @return the UsrNam
     */
    public String getUsrNam() {
	return usrNam;
    }

    /**
     * Sets values for UsrNam.
     * 
     * @param usrNam
     *            the usr nam
     */
    public void setUsrNam(String usrNam) {
	this.usrNam = usrNam;
    }

    /**
     * Gets the pass.
     * 
     * @return the Pass
     */
    public String getPass() {
	return pass;
    }

    /**
     * Sets values for Pass.
     * 
     * @param pass
     *            the pass
     */
    public void setPass(String pass) {
	this.pass = pass;
    }
}