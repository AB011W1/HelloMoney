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
import com.barclays.bmg.ussd.dao.operation.SelfRegistrationExecutionReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.SelfRegistrationExecutionResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SelfRegistrationExecutionDAOController implements Controller {

    /**
     * The instance variable for selfRegistrationExecutionReqAdptOperation of type SelfRegistrationExecutionReqAdptOperation
     */
    private SelfRegistrationExecutionReqAdptOperation selfRegistrationExecutionReqAdptOperation;
    /**
     * The instance variable for selfRegistrationExecutionResAdptOperation of type SelfRegistrationExecutionResAdptOperation
     */
    private SelfRegistrationExecutionResAdptOperation selfRegistrationExecutionResAdptOperation;
    private TransmissionOperation transmissionOperation;

    /**
     * Overrides handleRequest method of Controller
     * 
     * @param WorkContext
     * @return Object
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = selfRegistrationExecutionReqAdptOperation.adaptRequestForRegistrationExecution(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = selfRegistrationExecutionResAdptOperation.adaptResponseForRegistrationExecution(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for SelfRegistrationExecutionReqAdptOperation
     * 
     *@param none
     *@return SelfRegistrationExecutionReqAdptOperation
     */
    public SelfRegistrationExecutionReqAdptOperation getSelfRegistrationExecutionReqAdptOperation() {
	return selfRegistrationExecutionReqAdptOperation;
    }

    /**
     * Setter for SelfRegistrationExecutionReqAdptOperation
     * 
     * @param SelfRegistrationExecutionReqAdptOperation
     * @return void
     */
    public void setSelfRegistrationExecutionReqAdptOperation(SelfRegistrationExecutionReqAdptOperation selfRegistrationExecutionReqAdptOperation) {
	this.selfRegistrationExecutionReqAdptOperation = selfRegistrationExecutionReqAdptOperation;
    }

    /**
     * Getter for SelfRegistrationExecutionResAdptOperation
     * 
     *@param none
     *@return SelfRegistrationExecutionResAdptOperation
     */
    public SelfRegistrationExecutionResAdptOperation getSelfRegistrationExecutionResAdptOperation() {
	return selfRegistrationExecutionResAdptOperation;
    }

    /**
     * Setter for SelfRegistrationExecutionResAdptOperation
     * 
     * @param SelfRegistrationExecutionResAdptOperation
     * @return void
     */
    public void setSelfRegistrationExecutionResAdptOperation(SelfRegistrationExecutionResAdptOperation selfRegistrationExecutionResAdptOperation) {
	this.selfRegistrationExecutionResAdptOperation = selfRegistrationExecutionResAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
