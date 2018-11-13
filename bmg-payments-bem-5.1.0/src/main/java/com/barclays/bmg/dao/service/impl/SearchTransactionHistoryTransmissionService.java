package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.SearchFundsTransferHistory.SearchFundsTransferHistoryRequest;
import com.barclays.bem.SearchFundsTransferHistory.SearchFundsTransferHistoryResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class SearchTransactionHistoryTransmissionService implements TransmissionService  {

	private FinancialTransactionProcessing_PortType remoteService;

	public void setRemoteService(
			FinancialTransactionProcessing_PortType remoteService) {
		this.remoteService = remoteService;
	}

	@Override
	public Object sendAndReceive(Object object) throws Exception {

		SearchFundsTransferHistoryRequest request = (SearchFundsTransferHistoryRequest)object;
		SearchFundsTransferHistoryResponse response = null;

		try{
			response = remoteService.searchFundsTransferHistory(request);
		}catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;
	}

}
