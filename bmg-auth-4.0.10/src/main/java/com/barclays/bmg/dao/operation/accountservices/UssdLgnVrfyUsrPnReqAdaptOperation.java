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
 * Package name is com.barclays.bmg.dao.operation.accountservices
 * /
 */
package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.VerifyPIN.VerifyPINRequest;
import com.barclays.bmg.dao.accountservices.adapter.UssdLgnVrfyUsrPnHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.UssdLgnVrfyUsrPnPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnReqAdaptOperation.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>May 30, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnReqAdaptOperation created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnReqAdaptOperation {
    /**
     * The instance variable named "ussdLgnVrfyUsrPnHeaderAdapter" is created.
     */
    private UssdLgnVrfyUsrPnHeaderAdapter ussdLgnVrfyUsrPnHeaderAdapter;

    /**
     * The instance variable named "ussdLgnVrfyUsrPnPayloadAdapter" is created.
     */
    private UssdLgnVrfyUsrPnPayloadAdapter ussdLgnVrfyUsrPnPayloadAdapter;

    /**
     * The method is written for Adapt request.
     * 
     * @param workContext
     *            the work context
     * @return the Object's object
     */
    public VerifyPINRequest adaptRequest(WorkContext workContext) {
	VerifyPINRequest verifyPINRequest = new VerifyPINRequest();
	verifyPINRequest.setRequestHeader(ussdLgnVrfyUsrPnHeaderAdapter.buildAuthReqHeader(workContext));
	verifyPINRequest.setVerifyPIN(ussdLgnVrfyUsrPnPayloadAdapter.adaptPayLoad(workContext));
	return verifyPINRequest;
    }

    /**
     * Gets the ussd lgn vrfy usr pn header adapter.
     * 
     * @return the UssdLgnVrfyUsrPnHeaderAdapter
     */
    public UssdLgnVrfyUsrPnHeaderAdapter getUssdLgnVrfyUsrPnHeaderAdapter() {
	return ussdLgnVrfyUsrPnHeaderAdapter;
    }

    /**
     * Sets values for UssdLgnVrfyUsrPnHeaderAdapter.
     * 
     * @param ussdLgnVrfyUsrPnHeaderAdapter
     *            the ussd lgn vrfy usr pn header adapter
     */
    public void setUssdLgnVrfyUsrPnHeaderAdapter(UssdLgnVrfyUsrPnHeaderAdapter ussdLgnVrfyUsrPnHeaderAdapter) {
	this.ussdLgnVrfyUsrPnHeaderAdapter = ussdLgnVrfyUsrPnHeaderAdapter;
    }

    /**
     * Gets the ussd lgn vrfy usr pn payload adapter.
     * 
     * @return the UssdLgnVrfyUsrPnPayloadAdapter
     */
    public UssdLgnVrfyUsrPnPayloadAdapter getUssdLgnVrfyUsrPnPayloadAdapter() {
	return ussdLgnVrfyUsrPnPayloadAdapter;
    }

    /**
     * Sets values for UssdLgnVrfyUsrPnPayloadAdapter.
     * 
     * @param ussdLgnVrfyUsrPnPayloadAdapter
     *            the ussd lgn vrfy usr pn payload adapter
     */
    public void setUssdLgnVrfyUsrPnPayloadAdapter(UssdLgnVrfyUsrPnPayloadAdapter ussdLgnVrfyUsrPnPayloadAdapter) {
	this.ussdLgnVrfyUsrPnPayloadAdapter = ussdLgnVrfyUsrPnPayloadAdapter;
    }
}
