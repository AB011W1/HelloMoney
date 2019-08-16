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

import com.barclays.bem.AddProspect.AddProspectRequest;
import com.barclays.bem.AddProspect.AddProspectResponse;
import com.barclays.bem.ProspectManagement.ProspectManagement_PortType;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class AddProspectTransmissionService implements TransmissionService {
    private ProspectManagement_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(AddProspectTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	AddProspectRequest request = (AddProspectRequest) object;

	AddProspectResponse response = null;

	try {
	    response = remoteService.addProspect(request);
	} catch (RemoteException e) {
	    LOGGER.error("BMB data access exception", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    LOGGER.error("BMB data access exception", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}
	return response;
    }

    public ProspectManagement_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(ProspectManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }

}