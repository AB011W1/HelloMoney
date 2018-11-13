package com.barclays.bmg.dao.service.bankdraft.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountRequest;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class PurchaseBankDraftTransmissionService implements
		TransmissionService {

	protected FinancialTransactionProcessing_PortType remoteService;

	public FinancialTransactionProcessing_PortType getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(
			FinancialTransactionProcessing_PortType remoteService) {
		this.remoteService = remoteService;
	}

	@Override
	public Object sendAndReceive(Object object) throws Exception {
		// TODO Auto-generated method stub

		MakeDomesticDemandDraftByAccountRequest accountRequest = (MakeDomesticDemandDraftByAccountRequest) object;

		MakeDomesticDemandDraftByAccountResponse accountResponse = null;

		try {
			accountResponse = remoteService
					.makeDomesticDemandDraftByAccount(accountRequest);
		} catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return accountResponse;
	}

}
