package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.grcb.mcfe.ssc.AuthenticationService.AuthenticationService_PortType;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.AuthenticationVerifyRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.AuthenticationVerifyResponse;

public class SPAuthenticationTransmissionService extends AbstractAuthTransmissionService {

    private AuthenticationService_PortType remoteService;

    public AuthenticationService_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(AuthenticationService_PortType remoteService) {
	this.remoteService = remoteService;
    }

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	AuthenticationVerifyRequest request = (AuthenticationVerifyRequest) object;

	AuthenticationVerifyResponse response = null;
	try {
	    response = remoteService.authenticationVerify(request);
	} catch (RemoteException e) {
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}

	convertException(response.getResponseHeader());

	return response;
    }

}
