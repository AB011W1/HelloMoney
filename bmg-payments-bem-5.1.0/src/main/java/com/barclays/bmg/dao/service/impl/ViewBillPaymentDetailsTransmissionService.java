package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.ViewBillPaymentDetails.ViewBillPaymentDetailsRequest;
import com.barclays.bem.ViewBillPaymentDetails.ViewBillPaymentDetailsResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ViewBillPaymentDetailsTransmissionService implements TransmissionService  {

	private FinancialTransactionProcessing_PortType remoteService;

	public void setRemoteService(
			FinancialTransactionProcessing_PortType remoteService) {
		this.remoteService = remoteService;
	}

	@Override
	public Object sendAndReceive(Object object) throws Exception {

		ViewBillPaymentDetailsRequest request = (ViewBillPaymentDetailsRequest)object;
		ViewBillPaymentDetailsResponse response = null;
		try{
			response = remoteService.viewBillPaymentDetails(request);
		}catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;
	}

}
