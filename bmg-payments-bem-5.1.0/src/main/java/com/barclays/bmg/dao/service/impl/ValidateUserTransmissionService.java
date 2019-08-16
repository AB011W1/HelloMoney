package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.grcb.mcfe.ssc.AuthenticationService.AuthenticationService_PortType;
import com.barclays.grcb.mcfe.ssc.authentication.ValidateUserByLoginNameIgnoreCase.ValidateUserByLoginNameIgnoreCaseRequest;
import com.barclays.grcb.mcfe.ssc.authentication.ValidateUserByLoginNameIgnoreCase.ValidateUserByLoginNameIgnoreCaseResponse;

public class ValidateUserTransmissionService extends AbstractAuthTransmissionService {

    private AuthenticationService_PortType remoteService;

    public AuthenticationService_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(AuthenticationService_PortType remoteService) {
	this.remoteService = remoteService;
    }

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	ValidateUserByLoginNameIgnoreCaseRequest request = (ValidateUserByLoginNameIgnoreCaseRequest) object;

	ValidateUserByLoginNameIgnoreCaseResponse response = null;
	try {
	    response = remoteService.validateUserByLoginNameIgnoreCase(request);
	    // Start: Temporary kept for testing
	    /*
	     * if (response != null){ throw new RemoteException(); }
	     */
	    // End: Temporary kept for testing
	} catch (RemoteException e) {

	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}

	convertException(response.getResponseHeader());

	return response;
    }

}
