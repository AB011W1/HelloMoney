package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.AccountReporting.AccountReporting_PortType;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityRequest;
import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.exception.BMBDataAccessException;

public class CASAAccountActivityTransmissionService extends AbstractTransmissionService {

    private AccountReporting_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(CASAAccountActivityTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveCASAAccountTransactionActivityRequest request = (RetrieveCASAAccountTransactionActivityRequest) object;

	RetrieveCASAAccountTransactionActivityResponse response = null;
	try {
	    response = remoteService.retrieveCASAAcctTransactionActivity(request);

	} catch (RemoteException e) {
	    LOGGER.error("Error in BMB data access", e);
	    // throw new BMBDataAccessException(AccountServiceResponseCodeConstant.CASA_ACTIVITY_SERVICE_EXCEPTION,
	    // "Remote Service Failed, please contact administrator");
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(AccountReporting_PortType remoteService) {
	this.remoteService = remoteService;
    }

}
