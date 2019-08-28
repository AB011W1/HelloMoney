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
package com.barclays.bmg.ussd.dao.operation;

import com.barclays.bem.HMCreateCustomer.CreateHMCustomerResponseType;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationInitServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationInitResAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SelfRegistrationInitResAdptOperation extends AbstractResAdptOperationAcct {

    /**
     * This method adaptResponseForRegistartionInitialization has the purpose to adapt response for registration initialization.
     * 
     * @param WorkContext
     * @param Object
     * @return SelfRegistrationInitServiceResponse
     */
    public SelfRegistrationInitServiceResponse adaptResponseForRegistartionInitialization(WorkContext workContext, Object obj) {

	SelfRegistrationInitServiceResponse response = new SelfRegistrationInitServiceResponse();
	CreateHMCustomerResponseType bemResponse = (CreateHMCustomerResponseType) obj;

	if (bemResponse != null) {

	    String resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde)) {

		response.setServiceResponse(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
		response.setResCde(resCde);
		response.setSuccess(true);

	    } else {
		response.setServiceResponse(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
		response.setSuccess(false);
		response.setResCde(resCde);
	    }

	} else {
	    response.setSuccess(false);
	}

	return response;
    }

}
