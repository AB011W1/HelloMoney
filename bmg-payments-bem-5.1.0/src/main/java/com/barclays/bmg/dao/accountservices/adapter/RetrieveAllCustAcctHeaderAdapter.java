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

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-acct-svc-bem-5.1.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctHeaderAdapter.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 07, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class RetrieveAllCustAcctHeaderAdapter created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctHeaderAdapter extends AbstractReqAdptOperation {
	//Added as to remove extra CAS call-Prod issue
	private static final String OPCD = "OP0202";

    /**
     * The method is written for Builds the auth req header.
     *
     * @param workContext
     *            the work context
     * @return the Object's object
     */
    public BEMReqHeader buildBemReqHeader(WorkContext workContext) {
    	//Added as to remove extra CAS call-Prod issue
    	BEMReqHeader reqHeader = super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_RTRV_IND_CUST_ACCT_LST);
    	DAOContext daoContext = (DAOContext) workContext;
    	Object[] args = daoContext.getArguments();
    	RequestContext request = (RequestContext) args[0];
    	Context context = request.getContext();
    	if(context.getOpCde()!=null){
    		if(OPCD.equals(context.getOpCde())){
    			RoutingIndicator routingIndicator = new RoutingIndicator();
    			routingIndicator.setIndicator("MCE");
    			reqHeader.setRoutingIndicator(routingIndicator);
    		}
    	}
    	//Added so as to solve defect#2048 and CAS call reduction prod issue
    	if (context.getCustomerId() != null) {
   			RoutingIndicator routingIndicator = new RoutingIndicator();
			routingIndicator.setIndicator("MCE");
			reqHeader.setRoutingIndicator(routingIndicator);

    	}
    	return reqHeader;
    }
}