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

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bem.HMCreateCustomer.CreateHMCustomerRequestType;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.AddDetailstoMCEHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.AddDetailstoMCEPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>AddDetailstoMCEReqAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class AddDetailstoMCEReqAdptOperation {
    /**
     * The instance variable for addDetailstoMCEHeaderAdapter of type AddDetailstoMCEHeaderAdapter
     */
    private AddDetailstoMCEHeaderAdapter addDetailstoMCEHeaderAdapter;
    /**
     * The instance variable for addDetailstoMCEPayloadAdapter of type AddDetailstoMCEPayloadAdapter
     */
    private AddDetailstoMCEPayloadAdapter addDetailstoMCEPayloadAdapter;

    /**
     * Setter for AddDetailstoMCEHeaderAdapter
     * 
     * @param AddDetailstoMCEHeaderAdapter
     * @return void
     */
    public void setAddDetailstoMCEHeaderAdapter(AddDetailstoMCEHeaderAdapter addDetailstoMCEHeaderAdapter) {
	this.addDetailstoMCEHeaderAdapter = addDetailstoMCEHeaderAdapter;
    }

    /**
     * Setter for AddDetailstoMCEPayloadAdapter
     * 
     * @param AddDetailstoMCEPayloadAdapter
     * @return void
     */
    public void setAddDetailstoMCEPayloadAdapter(AddDetailstoMCEPayloadAdapter addDetailstoMCEPayloadAdapter) {
	this.addDetailstoMCEPayloadAdapter = addDetailstoMCEPayloadAdapter;
    }

    /**
     * This method adaptRequestForRegistrationExecution has the purpose to adapt the request for registration execution.
     * 
     * @param WorkContext
     * @return SelfRegistrationInitServiceRequest
     */
    public final CreateHMCustomerRequestType adaptRequestForRegistrationExecution(final WorkContext context) {

	CreateHMCustomerRequestType request = new CreateHMCustomerRequestType();
	request.setRequestHeader(addDetailstoMCEHeaderAdapter.buildMCEHeader(context));
	request.setHMCustomerInfo(addDetailstoMCEPayloadAdapter.adaptPayload(context));
	BEMReqHeader bemRequestFromHeader = request.getRequestHeader();
	RoutingIndicator routingIndicator = new RoutingIndicator();
	routingIndicator.setIndicator("MCE");
	bemRequestFromHeader.setRoutingIndicator(routingIndicator);
	request.setRequestHeader(bemRequestFromHeader);

	return request;
    }
}
