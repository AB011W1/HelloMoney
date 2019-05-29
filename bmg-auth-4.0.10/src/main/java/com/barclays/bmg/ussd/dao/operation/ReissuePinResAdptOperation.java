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

import com.barclays.bem.CreatePIN.CreatePINResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.ussd.auth.service.response.ReissuePinServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ReissuePinResAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class ReissuePinResAdptOperation extends AbstractResAdptOperationAcct {
    /**
     * This method adaptResponseForReissuePin has the purpose to adapt response for creation of pin.
     * 
     * @param WorkContext
     * @param Object
     * @return ReissuePinServiceResponse
     */
    public ReissuePinServiceResponse adaptResponseForReissuePin(WorkContext workContext, Object obj) {

	ReissuePinServiceResponse response = new ReissuePinServiceResponse();
	CreatePINResponse bemResponse = (CreatePINResponse) obj;

	if (bemResponse != null) {

	    String resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde)) {

		response.setServiceResponse(resCde);
		response.setSuccess(true);
		response.setPin(bemResponse.getFullPIN());

	    } else {

		response.setServiceResponse(resCde);
		response.setSuccess(false);

	    }
	} else {
	    response.setSuccess(false);
	}

	return response;
    }
}
