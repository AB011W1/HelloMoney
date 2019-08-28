package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.IndividualCustomerManagement.IndividualCustomerManagement_PortType;
import com.barclays.bem.UpdateIndividualCustQuestionnaireStatus.UpdateIndividualCustQuestionnaireStatusRequest;
import com.barclays.bem.UpdateIndividualCustQuestionnaireStatus.UpdateIndividualCustQuestionnaireStatusResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class SecondAuthenticationTransmissionService extends AbstractTransmissionService {

    private IndividualCustomerManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	UpdateIndividualCustQuestionnaireStatusRequest request = (UpdateIndividualCustQuestionnaireStatusRequest) object;

	UpdateIndividualCustQuestionnaireStatusResponse response = null;
	try {
	    response = remoteService.updateIndividualCustQuestionnaireStatus(request);

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
