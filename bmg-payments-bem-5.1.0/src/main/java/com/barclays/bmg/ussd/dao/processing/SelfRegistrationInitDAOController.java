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

/**
 * * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-auth-4.0.10</b>
 * </br>
 * The file name is  <b>SelfRegistrationInitDAOController.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 22, 2013</b>
 * </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.ussd.dao.operation.SelfRegistrationInitReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.SelfRegistrationInitResAdptOperation;

public class SelfRegistrationInitDAOController implements Controller {

    /**
     * The instance variable for selfRegistrationInitReqAdptOperation of type SelfRegistrationInitReqAdptOperation
     */
    private SelfRegistrationInitReqAdptOperation selfRegistrationInitReqAdptOperation;
    /**
     * The instance variable for selfRegistrationInitResAdptOperation of type SelfRegistrationInitResAdptOperation
     */
    private SelfRegistrationInitResAdptOperation selfRegistrationInitResAdptOperation;
    private TransmissionOperation transmissionOperation;

    /**
     * Overrides handleRequest method of Controller
     * 
     * @param WorkContext
     * @return Object
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = selfRegistrationInitReqAdptOperation.adaptRequestForRegistrationInitialization(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = selfRegistrationInitResAdptOperation.adaptResponseForRegistartionInitialization(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for SelfRegistrationInitReqAdptOperation
     * 
     *@param none
     *@return SelfRegistrationInitReqAdptOperation
     */
    public SelfRegistrationInitReqAdptOperation getSelfRegistrationInitReqAdptOperation() {
	return selfRegistrationInitReqAdptOperation;
    }

    /**
     * Setter for SelfRegistrationInitReqAdptOperation
     * 
     * @param SelfRegistrationInitReqAdptOperation
     * @return void
     */
    public void setSelfRegistrationInitReqAdptOperation(SelfRegistrationInitReqAdptOperation selfRegistrationInitReqAdptOperation) {
	this.selfRegistrationInitReqAdptOperation = selfRegistrationInitReqAdptOperation;
    }

    /**
     * Getter for SelfRegistrationInitResAdptOperation
     * 
     *@param none
     *@return SelfRegistrationInitResAdptOperation
     */
    public SelfRegistrationInitResAdptOperation getSelfRegistrationInitResAdptOperation() {
	return selfRegistrationInitResAdptOperation;
    }

    /**
     * Setter for SelfRegistrationInitResAdptOperation
     * 
     * @param SelfRegistrationInitResAdptOperation
     * @return void
     */
    public void setSelfRegistrationInitResAdptOperation(SelfRegistrationInitResAdptOperation selfRegistrationInitResAdptOperation) {
	this.selfRegistrationInitResAdptOperation = selfRegistrationInitResAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
