package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.AccountManagement.AccountManagement_PortType;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCreditCardAcctDetails.RetrieveCreditCardAccountDetailsRequest;
import com.barclays.bem.RetrieveCreditCardAcctDetails.RetrieveCreditCardAccountDetailsResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.exception.BMBDataAccessException;

public class CreditCardAccountDetailsTransmissionService extends AbstractTransmissionService {

    private AccountManagement_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(CreditCardAccountDetailsTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveCreditCardAccountDetailsRequest request = (RetrieveCreditCardAccountDetailsRequest) object;

	RetrieveCreditCardAccountDetailsResponse response = null;

	try {
	    response = remoteService.retrieveCreditCardAcctDetails(request);

	} catch (RemoteException e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(AccountManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }

}
