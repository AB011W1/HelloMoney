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

import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveAllCustAcctHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveAllCustAcctPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-acct-svc-bem-5.1.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctReqAdptOperation.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 07, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctReqAdptOperation created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctReqAdptOperation {

    /**
     * The instance variable named "retrieveAllCustAcctHeaderAdapter" is created.
     */
    private RetrieveAllCustAcctHeaderAdapter retrieveAllCustAcctHeaderAdapter;

    /**
     * The instance variable named "retrieveAllCustAcctPayloadAdapter" is created.
     */
    private RetrieveAllCustAcctPayloadAdapter retrieveAllCustAcctPayloadAdapter;

    /**
     * The method is written for Adapt request.
     * 
     * @param workContext
     *            the work context
     * @return the Object's object
     */
    public RetrieveIndividualCustAcctBasicRequest adaptRequest(WorkContext workContext) {
	RetrieveIndividualCustAcctBasicRequest request = new RetrieveIndividualCustAcctBasicRequest();

	request.setRequestHeader(retrieveAllCustAcctHeaderAdapter.buildBemReqHeader(workContext));
	request.setIndividualCustomerInfo(retrieveAllCustAcctPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    /**
     * Gets the retrieve all cust acct header adapter.
     * 
     * @return the RetrieveAllCustAcctHeaderAdapter
     */
    public RetrieveAllCustAcctHeaderAdapter getRetrieveAllCustAcctHeaderAdapter() {
	return retrieveAllCustAcctHeaderAdapter;
    }

    /**
     * Sets values for RetrieveAllCustAcctHeaderAdapter.
     * 
     * @param retrieveAllCustAcctHeaderAdapter
     *            the retrieve all cust acct header adapter
     */
    public void setRetrieveAllCustAcctHeaderAdapter(RetrieveAllCustAcctHeaderAdapter retrieveAllCustAcctHeaderAdapter) {
	this.retrieveAllCustAcctHeaderAdapter = retrieveAllCustAcctHeaderAdapter;
    }

    /**
     * Gets the retrieve all cust acct payload adapter.
     * 
     * @return the RetrieveAllCustAcctPayloadAdapter
     */
    public RetrieveAllCustAcctPayloadAdapter getRetrieveAllCustAcctPayloadAdapter() {
	return retrieveAllCustAcctPayloadAdapter;
    }

    /**
     * Sets values for RetrieveAllCustAcctPayloadAdapter.
     * 
     * @param retrieveAllCustAcctPayloadAdapter
     *            the retrieve all cust acct payload adapter
     */
    public void setRetrieveAllCustAcctPayloadAdapter(RetrieveAllCustAcctPayloadAdapter retrieveAllCustAcctPayloadAdapter) {
	this.retrieveAllCustAcctPayloadAdapter = retrieveAllCustAcctPayloadAdapter;
    }
}