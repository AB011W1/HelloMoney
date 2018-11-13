package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.MakeBillPayment.MakeBillPaymentRequest;
import com.barclays.bem.MakeBillPayment.MakeBillPaymentResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class MakePayBillTransmissionService implements TransmissionService {

	private FinancialTransactionProcessing_PortType remoteService;

	@Override
	public Object sendAndReceive(Object object) throws Exception {
		MakeBillPaymentRequest request = (MakeBillPaymentRequest) object;

		MakeBillPaymentResponse response = null;
		try {
			response = remoteService.makeBillPayment(request);
		} catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;
	}

	public FinancialTransactionProcessing_PortType getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(
			FinancialTransactionProcessing_PortType remoteService) {
		this.remoteService = remoteService;
	}

}
