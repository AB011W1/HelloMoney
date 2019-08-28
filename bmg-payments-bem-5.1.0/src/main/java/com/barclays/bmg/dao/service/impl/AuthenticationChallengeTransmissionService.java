package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.grcb.mcfe.ssc.AuthenticationService.AuthenticationService_PortType;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.AuthenticationChallengeRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.AuthenticationChallengeResponse;

public class AuthenticationChallengeTransmissionService extends AbstractAuthTransmissionService {

    private AuthenticationService_PortType remoteService;

    public AuthenticationService_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(AuthenticationService_PortType remoteService) {
	this.remoteService = remoteService;
    }

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	AuthenticationChallengeRequest request = (AuthenticationChallengeRequest) object;

	AuthenticationChallengeResponse response = null;
	try {
	    response = remoteService.authenticationChallenge(request);
	} catch (RemoteException e) {
	     // throw new BMBDataAccessException("ATH00101");
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);

	}

	convertException(response.getResponseHeader());

	return response;
    }

}
