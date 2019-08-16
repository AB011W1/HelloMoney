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

import com.barclays.bem.HMCreateCustomer.CreateHMCustomerRequestType;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.SelfRegistrationInitHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.SelfRegistrationInitPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationInitReqAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SelfRegistrationInitReqAdptOperation {

    /**
     * The instance variable for selfRegistrationInitHeaderAdapter of type SelfRegistrationInitHeaderAdapter
     */
    private SelfRegistrationInitHeaderAdapter selfRegistrationInitHeaderAdapter;
    /**
     * The instance variable for selfRegistrationInitPayloadAdapter of type SelfRegistrationInitPayloadAdapter
     */
    private SelfRegistrationInitPayloadAdapter selfRegistrationInitPayloadAdapter;

    /**
     * Setter for SelfRegistrationInitHeaderAdapter
     * 
     * @param SelfRegistrationInitHeaderAdapter
     * @return void
     */
    public void setSelfRegistrationInitHeaderAdapter(SelfRegistrationInitHeaderAdapter selfRegistrationInitHeaderAdapter) {
	this.selfRegistrationInitHeaderAdapter = selfRegistrationInitHeaderAdapter;
    }

    /**
     * Setter for SelfRegistrationInitPayloadAdapter
     * 
     * @param SelfRegistrationInitPayloadAdapter
     * @return void
     */
    public void setSelfRegistrationInitPayloadAdapter(SelfRegistrationInitPayloadAdapter selfRegistrationInitPayloadAdapter) {
	this.selfRegistrationInitPayloadAdapter = selfRegistrationInitPayloadAdapter;
    }

    /**
     * This method adaptRequestForRegistrationInitialization has the purpose to adapt request for registration initialization.
     * 
     * @param WorkContext
     * @return SelfRegistrationInitServiceRequest
     */
    public final CreateHMCustomerRequestType adaptRequestForRegistrationInitialization(final WorkContext context) {

	CreateHMCustomerRequestType createHMCustomerRequestType = new CreateHMCustomerRequestType();
	createHMCustomerRequestType.setRequestHeader(selfRegistrationInitHeaderAdapter.buildAuthReqHeader(context));
	createHMCustomerRequestType.setHMCustomerInfo(selfRegistrationInitPayloadAdapter.adaptPayload(context));
	return createHMCustomerRequestType;
    }

}
