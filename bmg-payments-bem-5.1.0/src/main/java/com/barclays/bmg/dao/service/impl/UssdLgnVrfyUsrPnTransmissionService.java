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
package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.PinManagement.PinManagement_PortType;
import com.barclays.bem.VerifyPIN.VerifyPINRequest;
import com.barclays.bem.VerifyPIN.VerifyPINResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnTransmissionService.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>May 30, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnTransmissionService created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnTransmissionService implements TransmissionService {
    private PinManagement_PortType remoteService;

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.dao.service.TransmissionService#sendAndReceive(java.lang.Object)
     */
    @Override
    public Object sendAndReceive(Object object) {
	VerifyPINRequest request = (VerifyPINRequest) object;
	VerifyPINResponse response = null;
	try {
	    response = remoteService.verifyPin(request);
	} catch (RemoteException e) {
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	   throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}
	return response;
    }

    /**
     * @return the remoteService
     */
    public PinManagement_PortType getRemoteService() {
	return remoteService;
    }

    /**
	 *
	 */
    public void setRemoteService(PinManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}