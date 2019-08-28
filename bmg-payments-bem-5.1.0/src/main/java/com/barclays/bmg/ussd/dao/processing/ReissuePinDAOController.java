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
import com.barclays.bmg.ussd.dao.operation.ReissuePinReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.ReissuePinResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ReissuePinDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class ReissuePinDAOController implements Controller {

    /**
     * The instance variable for reissuePinReqAdptOperation of type ReissuePinReqAdptOperation
     */
    private ReissuePinReqAdptOperation reissuePinReqAdptOperation;
    /**
     * The instance variable for reissuePinResAdptOperation of type ReissuePinResAdptOperation
     */
    private ReissuePinResAdptOperation reissuePinResAdptOperation;
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

	Object obj = reissuePinReqAdptOperation.adaptRequestForReissuePin(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = reissuePinResAdptOperation.adaptResponseForReissuePin(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for ReissuePinReqAdptOperation
     * 
     *@param none
     *@return ReissuePinReqAdptOperation
     */
    public ReissuePinReqAdptOperation getReissuePinReqAdptOperation() {
	return reissuePinReqAdptOperation;
    }

    /**
     * Setter for ReissuePinReqAdptOperation
     * 
     * @param ReissuePinReqAdptOperation
     * @return void
     */
    public void setReissuePinReqAdptOperation(ReissuePinReqAdptOperation reissuePinReqAdptOperation) {
	this.reissuePinReqAdptOperation = reissuePinReqAdptOperation;
    }

    /**
     * Getter for ReissuePinResAdptOperation
     * 
     *@param none
     *@return ReissuePinResAdptOperation
     */
    public ReissuePinResAdptOperation getReissuePinResAdptOperation() {
	return reissuePinResAdptOperation;
    }

    /**
     * Setter for ReissuePinResAdptOperation
     * 
     * @param ReissuePinResAdptOperation
     * @return void
     */
    public void setReissuePinResAdptOperation(ReissuePinResAdptOperation reissuePinResAdptOperation) {
	this.reissuePinResAdptOperation = reissuePinResAdptOperation;
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
