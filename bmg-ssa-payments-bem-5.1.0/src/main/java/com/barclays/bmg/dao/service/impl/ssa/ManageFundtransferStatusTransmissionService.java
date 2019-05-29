package com.barclays.bmg.dao.service.impl.ssa;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.ManageFundTransferStatus.ManageFundTransferStatusRequest;
import com.barclays.bem.ManageFundTransferStatus.ManageFundTransferStatusResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ManageFundtransferStatusTransmissionService implements
		TransmissionService {
	private FinancialTransactionProcessing_PortType remoteService;

	@Override
	public Object sendAndReceive(Object object) throws Exception {
		// TODO Auto-generated method stub

		ManageFundTransferStatusRequest request = (ManageFundTransferStatusRequest)object;

		ManageFundTransferStatusResponse response = null;
		try{
			response = remoteService.manageFundTransferStatus(request);
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
