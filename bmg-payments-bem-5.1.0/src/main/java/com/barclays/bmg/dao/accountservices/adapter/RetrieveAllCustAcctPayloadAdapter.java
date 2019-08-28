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
 * Package name is com.barclays.bmg.dao.accountservices.adapter
 * /
 */
package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveIndividualCustAcctBasic.IndividualCustomerInfo;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-acct-svc-bem-5.1.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 15, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctPayloadAdapter created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctPayloadAdapter {
    private static final String MST = "MST";

    /**
     * The method is written for Adapt pay load.
     * 
     * @param workContext
     *            the work context
     * @return the Object's object
     */
    public IndividualCustomerInfo adaptPayLoad(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	RetrieveAllCustAcctServiceRequest serRequest = (RetrieveAllCustAcctServiceRequest) args[0];
	Context context = serRequest.getContext();

	IndividualCustomerInfo info = new IndividualCustomerInfo();
	if (context.getCustomerId() != null) {
	    info.setCustomerNumber(context.getCustomerId());
	} else {
	    info.setCustomerNumber(context.getMobilePhone());
	}
	info.setRecordIndicator(MST);

	return info;
    }
}