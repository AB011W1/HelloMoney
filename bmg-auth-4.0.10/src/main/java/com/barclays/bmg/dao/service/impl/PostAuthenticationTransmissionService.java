package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.grcb.mcfe.ssc.AuthenticationService.AuthenticationService_PortType;
import com.barclays.grcb.mcfe.ssc.authentication.InitialAfterLoginSucess.InitialAfterLoginSuccessRequest;
import com.barclays.grcb.mcfe.ssc.authentication.InitialAfterLoginSucess.InitialAfterLoginSuccessResponse;

public class PostAuthenticationTransmissionService implements TransmissionService {

    private AuthenticationService_PortType remoteService;

    public AuthenticationService_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(AuthenticationService_PortType remoteService) {
	this.remoteService = remoteService;
    }

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	InitialAfterLoginSuccessRequest request = (InitialAfterLoginSuccessRequest) object;

	InitialAfterLoginSuccessResponse response = null;
	try {
	    response = remoteService.initialAfterLoginSuccess(request);
	} catch (RemoteException e) {
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}

	return response;
    }

}
