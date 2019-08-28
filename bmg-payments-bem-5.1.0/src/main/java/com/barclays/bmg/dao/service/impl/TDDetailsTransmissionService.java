package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.AccountManagement.AccountManagement_PortType;
import com.barclays.bem.RetrieveTimeDepositDetails.RetrieveTimeDepositDetailsRequest;
import com.barclays.bem.RetrieveTimeDepositDetails.RetrieveTimeDepositDetailsResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;

public class TDDetailsTransmissionService extends AbstractTransmissionService {

    private AccountManagement_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(TDDetailsTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveTimeDepositDetailsRequest request = (RetrieveTimeDepositDetailsRequest) object;

	RetrieveTimeDepositDetailsResponse response = null;
	try {
	    response = remoteService.retrieveTimeDepositDetails(request);
	} catch (RemoteException e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}

	return response;
    }

    public AccountManagement_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(AccountManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }

}
