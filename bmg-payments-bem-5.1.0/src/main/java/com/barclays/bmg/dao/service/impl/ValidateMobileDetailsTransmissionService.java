package com.barclays.bmg.dao.service.impl;


import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.IndividualCustomerManagement.IndividualCustomerManagement_PortType;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicRequest;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicResponse;
import com.barclays.bem.RetrieveMobileDetails.RetrieveMobileDetailsRequest;
import com.barclays.bem.RetrieveMobileDetails.RetrieveMobileDetailsResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ValidateMobileDetailsTransmissionService implements TransmissionService {

	private IndividualCustomerManagement_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(ValidateMobileDetailsTransmissionService.class);
    
    //TODO service request and response
	@Override
	public Object sendAndReceive(Object object) throws Exception {
		// TODO Auto-generated method stub
		RetrieveMobileDetailsRequest request = (RetrieveMobileDetailsRequest) object;

		RetrieveMobileDetailsResponse response = null;
		try {
		    response = remoteService.retrieveMobileDetails(request);
		} catch (RemoteException e) {
		    LOGGER.error("Error in BMB data access", e);
		    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
		} catch (Error e) {
		    LOGGER.error("Error in BMB data access", e);
		    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
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
