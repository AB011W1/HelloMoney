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
 * Package name is com.barclays.bmg.dao.processing.accountservices
 * /
 */
package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrieveAllCustAcctReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrieveAllCustAcctResAdptOperation;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-acct-svc-bem-5.1.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctDAOController.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 04, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctDAOController created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctDAOController implements Controller {

    /**
     * The instance variable named "retrieveAllCustAcctReqAdptOperation" is created.
     */
    private RetrieveAllCustAcctReqAdptOperation retrieveAllCustAcctReqAdptOperation;

    /**
     * The instance variable named "retrieveAllCustAcctTransmissionOperation" is created.
     */
    private TransmissionOperation retrieveAllCustAcctTransmissionOperation;

    /**
     * The instance variable named "retrieveAllCustAcctResAdptOperation" is created.
     */
    private RetrieveAllCustAcctResAdptOperation retrieveAllCustAcctResAdptOperation;

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.dao.core.processing.Controller#handleRequest(com.barclays.bmg.dao.core.context.WorkContext)
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	Object obj = retrieveAllCustAcctReqAdptOperation.adaptRequest(workContext);
	Object obj1 = retrieveAllCustAcctTransmissionOperation.sendAndReceive(obj);
	return retrieveAllCustAcctResAdptOperation.adaptResponse(workContext, obj1);
    }

    /**
     * Gets the retrieve all cust acct req adpt operation.
     * 
     * @return the RetrieveAllCustAcctReqAdptOperation
     */
    public RetrieveAllCustAcctReqAdptOperation getRetrieveAllCustAcctReqAdptOperation() {
	return retrieveAllCustAcctReqAdptOperation;
    }

    /**
     * Sets values for RetrieveAllCustAcctReqAdptOperation.
     * 
     * @param retrieveAllCustAcctReqAdptOperation
     *            the retrieve all cust acct req adpt operation
     */
    public void setRetrieveAllCustAcctReqAdptOperation(RetrieveAllCustAcctReqAdptOperation retrieveAllCustAcctReqAdptOperation) {
	this.retrieveAllCustAcctReqAdptOperation = retrieveAllCustAcctReqAdptOperation;
    }

    /**
     * Gets the retrieve all cust acct transmission operation.
     * 
     * @return the RetrieveAllCustAcctTransmissionOperation
     */
    public TransmissionOperation getRetrieveAllCustAcctTransmissionOperation() {
	return retrieveAllCustAcctTransmissionOperation;
    }

    /**
     * Sets values for RetrieveAllCustAcctTransmissionOperation.
     * 
     * @param retrieveAllCustAcctTransmissionOperation
     *            the retrieve all cust acct transmission operation
     */
    public void setRetrieveAllCustAcctTransmissionOperation(TransmissionOperation retrieveAllCustAcctTransmissionOperation) {
	this.retrieveAllCustAcctTransmissionOperation = retrieveAllCustAcctTransmissionOperation;
    }

    /**
     * Gets the retrieve all cust acct res adpt operation.
     * 
     * @return the RetrieveAllCustAcctResAdptOperation
     */
    public RetrieveAllCustAcctResAdptOperation getRetrieveAllCustAcctResAdptOperation() {
	return retrieveAllCustAcctResAdptOperation;
    }

    /**
     * Sets values for RetrieveAllCustAcctResAdptOperation.
     * 
     * @param retrieveAllCustAcctResAdptOperation
     *            the retrieve all cust acct res adpt operation
     */
    public void setRetrieveAllCustAcctResAdptOperation(RetrieveAllCustAcctResAdptOperation retrieveAllCustAcctResAdptOperation) {
	this.retrieveAllCustAcctResAdptOperation = retrieveAllCustAcctResAdptOperation;
    }
}