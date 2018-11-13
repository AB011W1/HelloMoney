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
import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicRequest;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-acct-svc-bem-5.1.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctTransmissionService.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 04, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctTransmissionService created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctTransmissionService implements TransmissionService {
    private IndividualCustomerManagement_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(RetrieveAllCustAcctTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveIndividualCustAcctBasicRequest request = (RetrieveIndividualCustAcctBasicRequest) object;

	RetrieveIndividualCustAcctBasicResponse response = null;
	try {
	    response = remoteService.retrieveIndividualCustAndAcctBasicDetails(request);
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