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

import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinRequest;
import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinResponse;
import com.barclays.bem.PinManagement.PinManagement_PortType;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ThmEncryptPinTransmissionService implements TransmissionService {
    private PinManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	CreditCardATMPinRequest request = (CreditCardATMPinRequest) object;
	CreditCardATMPinResponse response = null;
	try {
	    response = remoteService.encryptCreditCardATMPin(request);
	} catch (RemoteException e) {
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}
	return response;
    }

    public PinManagement_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(PinManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}