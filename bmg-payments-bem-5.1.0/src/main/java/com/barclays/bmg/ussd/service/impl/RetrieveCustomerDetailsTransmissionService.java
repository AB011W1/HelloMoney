package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.IndividualCustomerManagement.IndividualCustomerManagement_PortType;
import com.barclays.bem.RetrieveIndividualCustInformation.RetrieveIndividualCustomerInformationRequest;
import com.barclays.bem.RetrieveIndividualCustInformation.RetrieveIndividualCustomerInformationResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetrieveCustomerDetailsTransmissionService extends AbstractTransmissionService {

    private IndividualCustomerManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveIndividualCustomerInformationRequest request = (RetrieveIndividualCustomerInformationRequest) object;

	RetrieveIndividualCustomerInformationResponse response = null;
	try {
	    response = remoteService.retrieveIndividualCustInformation(request);

	} catch (RemoteException e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	// super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(IndividualCustomerManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}
