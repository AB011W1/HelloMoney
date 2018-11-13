package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.HMCustomerManagement.HelloMoneyCustomerManagement_PortType;
import com.barclays.bem.HMUpdateCustomer.UpdateHMCustomerRequestType;
import com.barclays.bem.HMUpdateCustomer.UpdateHMCustomerResponseType;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;

public class UpdateLanguagePrefTransmissionService extends AbstractAuthTransmissionService {

    private HelloMoneyCustomerManagement_PortType remoteService;

    public void setRemoteService(HelloMoneyCustomerManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	UpdateHMCustomerRequestType request = (UpdateHMCustomerRequestType) object;
	UpdateHMCustomerResponseType response = null;
	try {
	    response = remoteService.updateHelloMoneyCustomer(request);
	} catch (RemoteException e) {
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}
	return response;
    }

}
