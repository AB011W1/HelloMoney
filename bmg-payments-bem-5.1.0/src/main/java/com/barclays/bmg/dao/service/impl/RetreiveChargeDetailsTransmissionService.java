package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.RetrieveChargeDetails.RetrieveChargeDetailsRequest;
import com.barclays.bem.RetrieveChargeDetails.RetrieveChargeDetailsResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetreiveChargeDetailsTransmissionService implements TransmissionService{

	private FinancialTransactionProcessing_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		RetrieveChargeDetailsRequest request = (RetrieveChargeDetailsRequest) object;
		RetrieveChargeDetailsResponse response = null;
		try{
			response = remoteService.retrieveChargeDetails(request);
		}catch (RemoteException e) {
			
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
