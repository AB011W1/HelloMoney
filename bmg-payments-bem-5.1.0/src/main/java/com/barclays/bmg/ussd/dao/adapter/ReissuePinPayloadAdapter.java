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
package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.CreatePIN.CreatePIN;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.ussd.auth.service.request.ReissuePinServiceRequest;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ReissuePinPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */

public class ReissuePinPayloadAdapter {
    /**
     * This method adaptPayload has the purpose to adapt request for pin creation.
     * 
     * @param WorkContext
     * @return CreatePIN
     */
    public CreatePIN adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	ReissuePinServiceRequest request = (ReissuePinServiceRequest) args[0];
	CreatePIN createPIN = new CreatePIN();
	createPIN.setMobileNumber(request.getMobileNumber());
	createPIN.setPreferredLanguage(request.getPrefLang().toUpperCase());
	return createPIN;
    }
}
