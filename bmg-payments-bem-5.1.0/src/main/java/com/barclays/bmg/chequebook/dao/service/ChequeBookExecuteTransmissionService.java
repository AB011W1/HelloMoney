package com.barclays.bmg.chequebook.dao.service;

import java.rmi.RemoteException;

import com.barclays.bem.CheckBookManagement.CheckBookManagement_PortType;
import com.barclays.bmg.chequebook.dao.bem.request.ChequeBookBEMServiceRequest;
import com.barclays.bmg.chequebook.dao.bem.response.ChequeBookBEMServiceResponse;
import com.barclays.bmg.constants.ChequeBookResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ChequeBookExecuteTransmissionService extends AbstractTransmissionService{

	 private CheckBookManagement_PortType remoteService;

	@Override
	public Object sendAndReceive(Object object) throws Exception {

		ChequeBookBEMServiceRequest request = (ChequeBookBEMServiceRequest)object;

		ChequeBookBEMServiceResponse response = new ChequeBookBEMServiceResponse();

		try {
			remoteService.addCheckBookRequest(request.getBemReqHeader()
														, request.getCheckBook()
														, request.getBemResHeaderHolder()
														, request.getTransactionResponseStatusHolder());
			response.setBemReqHeader(request.getBemReqHeader());
			response.setBemResHeaderHolder(request.getBemResHeaderHolder());
			response.setCheckBook(request.getCheckBook());
			response.setTransactionResponseStatusHolder(request.getTransactionResponseStatusHolder());

		} catch (RemoteException e) {
			
			throw new BMBDataAccessException(ChequeBookResponseCodeConstant.CHEQUE_REMOTE_EXCEPTION);
		}
		return response;
	}

	public CheckBookManagement_PortType getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(CheckBookManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

}
