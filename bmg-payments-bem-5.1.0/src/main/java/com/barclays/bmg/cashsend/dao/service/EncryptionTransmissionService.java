package com.barclays.bmg.cashsend.dao.service;

import java.rmi.RemoteException;

import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinRequest;
import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinResponse;
import com.barclays.bem.PinManagement.PinManagement_PortType;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class EncryptionTransmissionService extends AbstractTransmissionService {

    private PinManagement_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		CreditCardATMPinRequest request = (CreditCardATMPinRequest) object;

	    CreditCardATMPinResponse response = null;
		try{
			response = remoteService.encryptCreditCardATMPin(request);
		}catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;
	}
	public PinManagement_PortType getRemoteService() {
		return remoteService;
	}
	public void setRemoteService(
			PinManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

}
