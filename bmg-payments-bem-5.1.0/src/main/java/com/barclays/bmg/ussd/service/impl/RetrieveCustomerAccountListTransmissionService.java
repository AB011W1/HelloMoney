package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.AccountManagement.AccountManagement_PortType;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveIndividualCustAcctList.RetrieveIndividualCustomerAccountListRequest;
import com.barclays.bem.RetrieveIndividualCustAcctList.RetrieveIndividualCustomerAccountListResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetrieveCustomerAccountListTransmissionService extends AbstractTransmissionService {

    private AccountManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveIndividualCustomerAccountListRequest request = (RetrieveIndividualCustomerAccountListRequest) object;

	RetrieveIndividualCustomerAccountListResponse response = null;
	try {
	    response = remoteService.retrieveIndividualCustAcctList(request);

	} catch (RemoteException e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	// super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(AccountManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}
