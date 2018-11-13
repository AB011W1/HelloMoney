package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.IndividualCustomerManagement.IndividualCustomerManagement_PortType;
import com.barclays.bem.RetrieveIndividualCustByCCNumber.RetrieveIndividualCustomerByCCNumberRequest;
import com.barclays.bem.RetrieveIndividualCustByCCNumber.RetrieveIndividualCustomerByCCNumberResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetreiveIndvCustInfoByCCTransmissionService implements TransmissionService{

	private IndividualCustomerManagement_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		RetrieveIndividualCustomerByCCNumberRequest request = (RetrieveIndividualCustomerByCCNumberRequest)object;
		RetrieveIndividualCustomerByCCNumberResponse response = null;
		try{
			response =
				remoteService.retrieveIndividualCustByCCNumber(request);
		}catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}

		return response;
	}
	public IndividualCustomerManagement_PortType getRemoteService() {
		return remoteService;
	}
	public void setRemoteService(IndividualCustomerManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

}
