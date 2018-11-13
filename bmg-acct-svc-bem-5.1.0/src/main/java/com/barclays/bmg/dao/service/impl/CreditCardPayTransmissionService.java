package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.MakeCreditCardPayment.MakeCreditCardPaymentRequest;
import com.barclays.bem.MakeCreditCardPayment.MakeCreditCardPaymentResponse;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class CreditCardPayTransmissionService implements TransmissionService {
    private FinancialTransactionProcessing_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(CreditCardPayTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	MakeCreditCardPaymentRequest request = (MakeCreditCardPaymentRequest) object;

	MakeCreditCardPaymentResponse response = null;
	try {
	    response = remoteService.makeCreditCardPayment(request);
	} catch (RemoteException e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(".");
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
