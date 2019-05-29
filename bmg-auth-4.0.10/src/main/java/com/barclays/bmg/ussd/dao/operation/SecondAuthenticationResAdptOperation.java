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

import java.util.Map;

import com.barclays.bem.UpdateIndividualCustQuestionnaireStatus.UpdateIndividualCustQuestionnaireStatusResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.ussd.auth.service.request.SecondAuthenticationServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SecondAuthenticationServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationResAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationResAdptOperation extends AbstractResAdptOperationAcct {

    private boolean isCryptoCall(WorkContext workContext) throws Exception {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	SecondAuthenticationServiceRequest secondAuthenticationServiceRequest = (SecondAuthenticationServiceRequest) args[0];
	Context context = secondAuthenticationServiceRequest.getContext();
	Map<String, Object> contextMap = context.getContextMap();
	String cryptoCall = (String) contextMap.get("SQAEnabled");
	if (cryptoCall.equalsIgnoreCase("y")) {
	    return true;
	}
	return false;

    }

    /**
     * This method adaptResponseforAuthentication has the purpose to adapt the response for authentication.
     * 
     * @param WorkContext
     * @param Object
     * @return SecondAuthenticationServiceResponse
     */
    public SecondAuthenticationServiceResponse adaptResponseforAuthentication(WorkContext workContext, Object obj) {

	SecondAuthenticationServiceResponse response = new SecondAuthenticationServiceResponse();
	UpdateIndividualCustQuestionnaireStatusResponse bemResponse = (UpdateIndividualCustQuestionnaireStatusResponse) obj;

	// aDDED FOR THER CRYPTO stuB
	// try {
	// if (isCryptoCall(workContext)) {
	// response.setServiceResponse("RES115");
	// response.setSuccess(true);
	// response.setResCde("00000");
	// return response;
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// 
	// }
	if (bemResponse != null) {

	    String resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde)) {

		response.setServiceResponse(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
		response.setSuccess(true);
		response.setResCde(resCde);

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
