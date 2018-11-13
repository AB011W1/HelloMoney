package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.IndividualCustomerManagement.IndividualCustomerManagement_PortType;
import com.barclays.bem.RetrieveIndividualCustQuestionnaire.RetrieveIndividualCustQuestionnaireRequest;
import com.barclays.bem.RetrieveIndividualCustQuestionnaire.RetrieveIndividualCustQuestionnaireResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ChallengeQuestionAndPositionTransmissionService extends AbstractTransmissionService {
    // ChallengeQuestionAndPositionTransmissionService
    private IndividualCustomerManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveIndividualCustQuestionnaireRequest request = (RetrieveIndividualCustQuestionnaireRequest) object;

	RetrieveIndividualCustQuestionnaireResponse response = null;
	try {
	    response = remoteService.retrieveCustomerQuestionnaire(request);

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
