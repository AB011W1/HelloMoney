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
package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.VerifyPIN.VerifyPIN;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.UssdLgnVrfyUsrPnServiceRequest;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnPayloadAdapter.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>May 30, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnPayloadAdapter created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnPayloadAdapter {
    /**
     * The method is written for Adapt pay load.
     * 
     * @param workContext
     *            the work context
     * @return the Object's object
     */
    public VerifyPIN adaptPayLoad(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	UssdLgnVrfyUsrPnServiceRequest serRequest = (UssdLgnVrfyUsrPnServiceRequest) args[0];
	VerifyPIN verifyPin = new VerifyPIN();
	verifyPin.setMobileNumber(serRequest.getUsrNam());
	verifyPin.setFullPIN(serRequest.getPass());
	return verifyPin;
    }
}