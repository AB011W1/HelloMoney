package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.RetrieveBillDetails.RetrieveBillDetailsRequest;
import com.barclays.bem.RetrieveBillDetails.RetrieveBillDetailsResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetrieveBillDetailsTransmissionService implements TransmissionService{

	private FinancialTransactionProcessing_PortType remoteService;

	@Override
	public Object sendAndReceive(Object object) throws Exception {

		RetrieveBillDetailsRequest request = (RetrieveBillDetailsRequest) object;
		RetrieveBillDetailsResponse response = null;
		try{
			response = remoteService.retrieveBillDetails(request);
		}catch (RemoteException e) {

			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}

		return response;
	}

	/**
	 * @return the remoteService
	 */
	public FinancialTransactionProcessing_PortType getRemoteService() {
		return remoteService;
	}

	/**
	 * @param remoteService the remoteService to set
	 */
	public void setRemoteService(
			FinancialTransactionProcessing_PortType remoteService) {
		this.remoteService = remoteService;
	}
}
