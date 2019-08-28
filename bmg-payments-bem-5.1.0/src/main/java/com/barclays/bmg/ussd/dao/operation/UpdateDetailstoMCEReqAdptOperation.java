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
import com.barclays.bem.HMUpdateCustomer.UpdateHMCustomerRequestType;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.UpdateDetailstoMCEHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.UpdateDetailstoMCEPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UpdateDetailstoMCEReqAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>November 22, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20045924
 * 
 */
public class UpdateDetailstoMCEReqAdptOperation {
    /**
     * The instance variable for updateDetailstoMCEHeaderAdapter of type UpdateDetailstoMCEHeaderAdapter
     */
    private UpdateDetailstoMCEHeaderAdapter updateDetailstoMCEHeaderAdapter;
    /**
     * The instance variable for updateDetailstoMCEPayloadAdapter of type UpdateDetailstoMCEPayloadAdapter
     */
    private UpdateDetailstoMCEPayloadAdapter updateDetailstoMCEPayloadAdapter;

    /**
     * Setter for UpdateDetailstoMCEHeaderAdapter
     * 
     * @param UpdateDetailstoMCEHeaderAdapter
     * @return void
     */
    public void setUpdateDetailstoMCEHeaderAdapter(UpdateDetailstoMCEHeaderAdapter updateDetailstoMCEHeaderAdapter) {
	this.updateDetailstoMCEHeaderAdapter = updateDetailstoMCEHeaderAdapter;
    }

    /**
     * Setter for UpdateDetailstoMCEPayloadAdapter
     * 
     * @param UpdateDetailstoMCEPayloadAdapter
     * @return void
     */
    public void setUpdateDetailstoMCEPayloadAdapter(UpdateDetailstoMCEPayloadAdapter updateDetailstoMCEPayloadAdapter) {
	this.updateDetailstoMCEPayloadAdapter = updateDetailstoMCEPayloadAdapter;
    }

    /**
     * This method adaptRequestForRegistrationExecution has the purpose to adapt the request for registration execution.
     * 
     * @param WorkContext
     * @return SelfRegistrationInitServiceRequest
     */
    public final UpdateHMCustomerRequestType adaptRequestForRegistrationExecution(final WorkContext context) {

	UpdateHMCustomerRequestType request = new UpdateHMCustomerRequestType();
	request.setRequestHeader(updateDetailstoMCEHeaderAdapter.buildMCEHeader(context));
	request.setUpdateCustomerRequestInfo(updateDetailstoMCEPayloadAdapter.adaptPayload(context));
	BEMReqHeader bemRequestFromHeader = request.getRequestHeader();
	RoutingIndicator routingIndicator = new RoutingIndicator();
	routingIndicator.setIndicator("MCE");
	bemRequestFromHeader.setRoutingIndicator(routingIndicator);
	request.setRequestHeader(bemRequestFromHeader);

	return request;
    }
}
