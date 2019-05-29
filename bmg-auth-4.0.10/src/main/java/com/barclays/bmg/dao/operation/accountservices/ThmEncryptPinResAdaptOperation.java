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

import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.TacticalHelloMoneyEncryptPinServiceRequest;
import com.barclays.bmg.service.response.TacticalHelloMoneyEncryptPinServiceResponse;

public class ThmEncryptPinResAdaptOperation extends AbstractResAdptOperationAcct {

    public TacticalHelloMoneyEncryptPinServiceResponse adaptResponse(WorkContext workContext, Object obj) {
	TacticalHelloMoneyEncryptPinServiceResponse response = new TacticalHelloMoneyEncryptPinServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	TacticalHelloMoneyEncryptPinServiceRequest thmEncryptPinServiceRequest = (TacticalHelloMoneyEncryptPinServiceRequest) args[0];
	response.setContext(thmEncryptPinServiceRequest.getContext());

	CreditCardATMPinResponse bemResponse = (CreditCardATMPinResponse) obj;
	if (bemResponse != null) {
	    response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
	    response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());

	    String resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde)) {
		response.setSuccess(true);
		response.setEncryptedPin(bemResponse.getEncryptPinReponses().getEncryptPin());
		return response;
	    } else {
		response.setSuccess(false);
		response.setResCde(resCde);
	    }
	} else {
	    response.setSuccess(false);
	}
	return response;
    }
}