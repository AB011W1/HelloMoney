package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferRequest;
import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class FundTransferExecutionTransmissionService implements TransmissionService{

	private FinancialTransactionProcessing_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		MakeDomesticFundTransferRequest request = (MakeDomesticFundTransferRequest) object;

		MakeDomesticFundTransferResponse response = null;
		try{
			response = remoteService.makeDomesticFundTransfer(request);
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
