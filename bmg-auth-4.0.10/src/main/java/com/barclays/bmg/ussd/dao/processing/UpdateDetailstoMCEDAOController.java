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
import com.barclays.bmg.ussd.dao.operation.UpdateDetailstoMCEReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.UpdateDetailstoMCEResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UpdateDetailstoMCEDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>November 22, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20045924
 * 
 */

public class UpdateDetailstoMCEDAOController implements Controller {

    /**
     * The instance variable for updateDetailstoMCEReqAdptOperation of type UpdateDetailstoMCEReqAdptOperation
     */
    private UpdateDetailstoMCEReqAdptOperation updateDetailstoMCEReqAdptOperation;
    /**
     * The instance variable for updateDetailstoMCEResAdptOperation of type UpdateDetailstoMCEResAdptOperation
     */
    private UpdateDetailstoMCEResAdptOperation updateDetailstoMCEResAdptOperation;
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

	Object obj = updateDetailstoMCEReqAdptOperation.adaptRequestForRegistrationExecution(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = updateDetailstoMCEResAdptOperation.adaptResponseForMCE(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for UpdateDetailstoMCEReqAdptOperation
     * 
     *@param none
     *@return UpdateDetailstoMCEReqAdptOperation
     */
    public UpdateDetailstoMCEReqAdptOperation getUpdateDetailstoMCEReqAdptOperation() {
	return updateDetailstoMCEReqAdptOperation;
    }

    /**
     * Setter for UpdateDetailstoMCEReqAdptOperation
     * 
     * @param UpdateDetailstoMCEReqAdptOperation
     * @return void
     */
    public void setUpdateDetailstoMCEReqAdptOperation(UpdateDetailstoMCEReqAdptOperation updateDetailstoMCEReqAdptOperation) {
	this.updateDetailstoMCEReqAdptOperation = updateDetailstoMCEReqAdptOperation;
    }

    /**
     * Getter for UpdateDetailstoMCEResAdptOperation
     * 
     *@param none
     *@return UpdateDetailstoMCEResAdptOperation
     */
    public UpdateDetailstoMCEResAdptOperation getUpdateDetailstoMCEResAdptOperation() {
	return updateDetailstoMCEResAdptOperation;
    }

    /**
     * Setter for UpdateDetailstoMCEResAdptOperation
     * 
     * @param UpdateDetailstoMCEResAdptOperation
     * @return void
     */
    public void setUpdateDetailstoMCEResAdptOperation(UpdateDetailstoMCEResAdptOperation updateDetailstoMCEResAdptOperation) {
	this.updateDetailstoMCEResAdptOperation = updateDetailstoMCEResAdptOperation;
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
