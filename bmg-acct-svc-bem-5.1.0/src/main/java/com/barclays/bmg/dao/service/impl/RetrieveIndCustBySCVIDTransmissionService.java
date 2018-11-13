/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
/**
 * Package name is com.barclays.bmg.dao.service.impl
 * /
 */
package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.IndividualCustomerManagement.IndividualCustomerManagement_PortType;
import com.barclays.bem.RetrieveIndividualCustBySCVID.RetrieveIndividualCustomerBySCVIDRequest;
import com.barclays.bem.RetrieveIndividualCustBySCVID.RetrieveIndividualCustomerBySCVIDResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetrieveIndCustBySCVIDTransmissionService implements TransmissionService {
    private IndividualCustomerManagement_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(RetrieveIndCustBySCVIDTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveIndividualCustomerBySCVIDRequest request = (RetrieveIndividualCustomerBySCVIDRequest) object;

	RetrieveIndividualCustomerBySCVIDResponse response = null;
	try {
	    response = remoteService.retrieveIndividualCustBySCVID(request);
	} catch (RemoteException e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}
	return response;
    }

    /**
     * @return the remoteService
     */
    public IndividualCustomerManagement_PortType getRemoteService() {
	return remoteService;
    }

    /**
	 *
	 */
    public void setRemoteService(IndividualCustomerManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }
}