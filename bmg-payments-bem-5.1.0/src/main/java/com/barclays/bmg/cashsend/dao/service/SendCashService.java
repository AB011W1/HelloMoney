package com.barclays.bmg.cashsend.dao.service;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.SendCash.SendCashRequest;
import com.barclays.bem.SendCash.SendCashResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class SendCashService extends AbstractTransmissionService {

    private FinancialTransactionProcessing_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {

	    SendCashRequest request = (SendCashRequest) object;

	    SendCashResponse response = null;
		try{
			response = remoteService.sendCash(request);
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
