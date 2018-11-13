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
import com.barclays.bmg.ussd.dao.operation.AddDetailstoMCEReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.AddDetailstoMCEResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>AddDetailstoMCEDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */

public class AddDetailstoMCEDAOController implements Controller {

    /**
     * The instance variable for addDetailstoMCEReqAdptOperation of type AddDetailstoMCEReqAdptOperation
     */
    private AddDetailstoMCEReqAdptOperation addDetailstoMCEReqAdptOperation;
    /**
     * The instance variable for addDetailstoMCEResAdptOperation of type AddDetailstoMCEResAdptOperation
     */
    private AddDetailstoMCEResAdptOperation addDetailstoMCEResAdptOperation;
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

	Object obj = addDetailstoMCEReqAdptOperation.adaptRequestForRegistrationExecution(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = addDetailstoMCEResAdptOperation.adaptResponseForMCE(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for AddDetailstoMCEReqAdptOperation
     * 
     *@param none
     *@return AddDetailstoMCEReqAdptOperation
     */
    public AddDetailstoMCEReqAdptOperation getAddDetailstoMCEReqAdptOperation() {
	return addDetailstoMCEReqAdptOperation;
    }

    /**
     * Setter for AddDetailstoMCEReqAdptOperation
     * 
     * @param AddDetailstoMCEReqAdptOperation
     * @return void
     */
    public void setAddDetailstoMCEReqAdptOperation(AddDetailstoMCEReqAdptOperation addDetailstoMCEReqAdptOperation) {
	this.addDetailstoMCEReqAdptOperation = addDetailstoMCEReqAdptOperation;
    }

    /**
     * Getter for AddDetailstoMCEResAdptOperation
     * 
     *@param none
     *@return AddDetailstoMCEResAdptOperation
     */
    public AddDetailstoMCEResAdptOperation getAddDetailstoMCEResAdptOperation() {
	return addDetailstoMCEResAdptOperation;
    }

    /**
     * Setter for AddDetailstoMCEResAdptOperation
     * 
     * @param AddDetailstoMCEResAdptOperation
     * @return void
     */
    public void setAddDetailstoMCEResAdptOperation(AddDetailstoMCEResAdptOperation addDetailstoMCEResAdptOperation) {
	this.addDetailstoMCEResAdptOperation = addDetailstoMCEResAdptOperation;
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
