package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.AccountManagement.AccountManagement_PortType;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveIndividualCustAcctList.RetrieveIndividualCustomerAccountListRequest;
import com.barclays.bem.RetrieveIndividualCustAcctList.RetrieveIndividualCustomerAccountListResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.exception.BMBDataAccessException;

public class WSTransmissionService extends AbstractTransmissionService {

    private AccountManagement_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(WSTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveIndividualCustomerAccountListRequest request = (RetrieveIndividualCustomerAccountListRequest) object;

	RetrieveIndividualCustomerAccountListResponse response = null;
	try {
	    response = remoteService.retrieveIndividualCustAcctList(request);

	} catch (RemoteException e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	// super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(AccountManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}
