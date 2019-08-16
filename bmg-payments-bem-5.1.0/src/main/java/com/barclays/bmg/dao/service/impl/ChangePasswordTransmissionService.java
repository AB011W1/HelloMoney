package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.PinManagement.PinManagement_PortType;
import com.barclays.bem.UpdatePIN.UpdatePINRequest;
import com.barclays.bem.UpdatePIN.UpdatePINResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ChangePasswordTransmissionService extends AbstractAuthTransmissionService {

    private PinManagement_PortType remoteService;

    public PinManagement_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(PinManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }

    @Override
    public Object sendAndReceive(Object object) throws Exception {
	UpdatePINRequest request = (UpdatePINRequest) object;

	UpdatePINResponse response = null;

	try {
	    response = remoteService.updatePin(request);
	} catch (RemoteException e) {
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}
	return response;
    }

}
