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
package com.barclays.bmg.ussd.dao.processing;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveCustomerAccountListReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveCustomerAccountListResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>RetrieveCustomerAccountListDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class RetrieveCustomerAccountListDAOController implements Controller {

    /**
     * The instance variable for retrieveCustomerAccountListReqAdptOperation of type RetrieveCustomerAccountListReqAdptOperation
     */
    private RetrieveCustomerAccountListReqAdptOperation retrieveCustomerAccountListReqAdptOperation;

    /**
     * The instance variable for retrieveCustomerAccountListResAdptOperation of type RetrieveCustomerAccountListResAdptOperation
     */
    private RetrieveCustomerAccountListResAdptOperation retrieveCustomerAccountListResAdptOperation;

    /**
     * The instance variable for transmissionOperation of type TransmissionOperation
     */
    private TransmissionOperation transmissionOperation;

    /**
     * Overrides handleRequest method of Controller
     * 
     * @param WorkContext
     * @return Object
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = retrieveCustomerAccountListReqAdptOperation.adaptRequestForAccountList(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = retrieveCustomerAccountListResAdptOperation.adaptResponseForAccountList(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for RetrieveCustomerAccountListReqAdptOperation
     * 
     *@param none
     *@return RetrieveCustomerAccountListReqAdptOperation
     */
    public RetrieveCustomerAccountListReqAdptOperation getRetrieveCustomerAccountListReqAdptOperation() {
	return retrieveCustomerAccountListReqAdptOperation;
    }

    /**
     * Setter for RetrieveCustomerAccountListReqAdptOperation
     * 
     * @param RetrieveCustomerAccountListReqAdptOperation
     * @return void
     */
    public void setRetrieveCustomerAccountListReqAdptOperation(RetrieveCustomerAccountListReqAdptOperation retrieveCustomerAccountListReqAdptOperation) {
	this.retrieveCustomerAccountListReqAdptOperation = retrieveCustomerAccountListReqAdptOperation;
    }

    /**
     * Getter for RetrieveCustomerAccountListResAdptOperation
     * 
     *@param none
     *@return RetrieveCustomerAccountListResAdptOperation
     */
    public RetrieveCustomerAccountListResAdptOperation getRetrieveCustomerAccountListResAdptOperation() {
	return retrieveCustomerAccountListResAdptOperation;
    }

    /**
     * Setter for RetrieveCustomerAccountListResAdptOperation
     * 
     * @param RetrieveCustomerAccountListResAdptOperation
     * @return void
     */
    public void setRetrieveCustomerAccountListResAdptOperation(RetrieveCustomerAccountListResAdptOperation retrieveCustomerAccountListResAdptOperation) {
	this.retrieveCustomerAccountListResAdptOperation = retrieveCustomerAccountListResAdptOperation;
    }

    /**
     * Getter for TransmissionOperation
     * 
     *@param none
     *@return TransmissionOperation
     */
    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    /**
     * Setter for TransmissionOperation
     * 
     * @param TransmissionOperation
     * @return void
     */
    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
