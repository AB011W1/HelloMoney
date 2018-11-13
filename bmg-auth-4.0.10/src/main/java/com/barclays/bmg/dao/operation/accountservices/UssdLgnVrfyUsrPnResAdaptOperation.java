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
 * Package name is com.barclays.bmg.dao.operation.accountservices
 * /
 */
package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.VerifyPIN.VerifyPINResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.UssdLgnVrfyUsrPnServiceRequest;
import com.barclays.bmg.service.response.UssdLgnVrfyUsrPnServiceResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnResAdaptOperation.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>May 30, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnResAdaptOperation created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnResAdaptOperation extends AbstractResAdptOperation {
    /**
     * The method is written for Adapt response.
     * 
     * @param workContext
     *            the work context
     * @param obj
     *            the obj
     * @return the UssdLgnVrfyUsrPnServiceResponse's object
     */
    public UssdLgnVrfyUsrPnServiceResponse adaptResponse(WorkContext workContext, Object obj) {
	UssdLgnVrfyUsrPnServiceResponse response = new UssdLgnVrfyUsrPnServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	UssdLgnVrfyUsrPnServiceRequest ussdLgnVrfyUsrPnServiceRequest = (UssdLgnVrfyUsrPnServiceRequest) args[0];
	response.setContext(ussdLgnVrfyUsrPnServiceRequest.getContext());

	VerifyPINResponse bemResponse = (VerifyPINResponse) obj;
	if (bemResponse != null) {
	    response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
	    response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());

	    // TransactionResponseStatus status = bemResponse.getTransactionResponseStatus();

	    String resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde)) {
		response.setSuccess(true);
		return response;
	    } else {
		response.setSuccess(false);
		response.setResCde(resCde);
	    }

	    /*
	     * if(checkResponseHeader(bemResponse.getResponseHeader())) { // response.setSuccess(status.getTransactionStaus());
	     * response.setSuccess(true); return response; } else { response.setSuccess(false); }
	     */
	} else {
	    response.setSuccess(false);
	}
	return response;
    }
}