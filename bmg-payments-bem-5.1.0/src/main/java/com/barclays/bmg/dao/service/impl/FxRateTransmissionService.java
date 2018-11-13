package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.RetrieveFXRate.RetrieveFXRateRequest;
import com.barclays.bem.RetrieveFXRate.RetrieveFXRateResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class FxRateTransmissionService implements TransmissionService {

    private FinancialTransactionProcessing_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveFXRateRequest request = (RetrieveFXRateRequest) object;
	RetrieveFXRateResponse response = null;

	try {
	    response = remoteService.retrieveFXRate(request);
	} catch (RemoteException e) {
	    
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}

	return response;
    }

    public FinancialTransactionProcessing_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(FinancialTransactionProcessing_PortType remoteService) {
	this.remoteService = remoteService;
    }

}
