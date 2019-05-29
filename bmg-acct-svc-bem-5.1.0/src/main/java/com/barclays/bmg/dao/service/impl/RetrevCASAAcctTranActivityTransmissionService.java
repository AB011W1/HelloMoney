package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.AccountReporting.AccountReporting_PortType;
import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityRequest;
import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetrevCASAAcctTranActivityTransmissionService extends
		AbstractTransmissionService {
	private AccountReporting_PortType remoteService;

	public AccountReporting_PortType getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(
			AccountReporting_PortType remoteService) {
		this.remoteService = remoteService;
	}

	@Override
	public Object sendAndReceive(Object object) throws Exception {
		RetrieveCASAAccountTransactionActivityRequest request = (RetrieveCASAAccountTransactionActivityRequest) object;

		RetrieveCASAAccountTransactionActivityResponse response = null;
		try {
			response = remoteService.retrieveCASAAcctTransactionActivity(request);
		} catch (RemoteException e) {

			throw new BMBDataAccessException(
					ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;

	}
}
