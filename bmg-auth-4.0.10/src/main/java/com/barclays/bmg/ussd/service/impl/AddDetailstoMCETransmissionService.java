package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.HMCreateCustomer.CreateHMCustomerRequestType;
import com.barclays.bem.HMCreateCustomer.CreateHMCustomerResponseType;
import com.barclays.bem.HMCustomerManagement.HelloMoneyCustomerManagement_PortType;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class AddDetailstoMCETransmissionService extends AbstractTransmissionService {

    private HelloMoneyCustomerManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	CreateHMCustomerRequestType request = (CreateHMCustomerRequestType) object;

	CreateHMCustomerResponseType response = null;
	try {
	    response = remoteService.createHelloMoneyCustomer(request);

	} catch (RemoteException e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	// super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(HelloMoneyCustomerManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}
