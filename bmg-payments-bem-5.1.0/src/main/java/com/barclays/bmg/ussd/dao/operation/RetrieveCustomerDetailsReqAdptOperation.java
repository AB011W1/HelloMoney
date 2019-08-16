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

import com.barclays.bem.RetrieveIndividualCustInformation.RetrieveIndividualCustomerInformationRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.RetrieveCustomerDetailsHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.RetrieveCustomerDetailsPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>RetrieveCustomerDetailsReqAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * **********************************************************
 * 
 * @author E20043104
 * 
 */
public class RetrieveCustomerDetailsReqAdptOperation {

    /**
     * The instance variable for selfRegistrationExecutionHeaderAdapter of type SelfRegistrationExecutionHeaderAdapter
     */
    private RetrieveCustomerDetailsHeaderAdapter retrieveCustomerDetailsHeaderAdapter;
    /**
     * The instance variable for selfRegistrationExecutionPayloadAdapter of type SelfRegistrationExecutionPayloadAdapter
     */
    private RetrieveCustomerDetailsPayloadAdapter retrieveCustomerDetailsPayloadAdapter;

    /**
     * Setter for RetrieveCustomerDetailsHeaderAdapter
     * 
     * @param RetrieveCustomerDetailsHeaderAdapter
     * @return void
     */
    public void setRetrieveCustomerDetailsHeaderAdapter(RetrieveCustomerDetailsHeaderAdapter retrieveCustomerDetailsHeaderAdapter) {
	this.retrieveCustomerDetailsHeaderAdapter = retrieveCustomerDetailsHeaderAdapter;
    }

    /**
     * Setter for RetrieveCustomerDetailsPayloadAdapter
     * 
     * @param RetrieveCustomerDetailsPayloadAdapter
     * @return void
     */
    public void setRetrieveCustomerDetailsPayloadAdapter(RetrieveCustomerDetailsPayloadAdapter retrieveCustomerDetailsPayloadAdapter) {
	this.retrieveCustomerDetailsPayloadAdapter = retrieveCustomerDetailsPayloadAdapter;
    }

    /**
     * This method adaptRequestForDetails has the purpose to adapt the request for retrieving details.
     * 
     * @param WorkContext
     * @return RetrieveIndividualCustomerInformationRequest
     */
    public final RetrieveIndividualCustomerInformationRequest adaptRequestForDetails(final WorkContext context) {

	RetrieveIndividualCustomerInformationRequest request = new RetrieveIndividualCustomerInformationRequest();
	request.setRequestHeader(retrieveCustomerDetailsHeaderAdapter.buildDetailsReqHeader(context));
	request.setIndividualCustomerInfo(retrieveCustomerDetailsPayloadAdapter.adaptPayload(context));

	return request;
    }

}
