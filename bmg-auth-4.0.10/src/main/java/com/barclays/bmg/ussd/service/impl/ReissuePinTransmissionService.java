package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.CreatePIN.CreatePINRequest;
import com.barclays.bem.CreatePIN.CreatePINResponse;
import com.barclays.bem.PinManagement.PinManagement_PortType;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ReissuePinTransmissionService extends AbstractTransmissionService {

    private PinManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	CreatePINRequest request = (CreatePINRequest) object;

	CreatePINResponse response = null;
	try {
	    response = remoteService.createPin(request);

	} catch (RemoteException e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	// super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(PinManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}
