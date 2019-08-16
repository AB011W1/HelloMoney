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
import com.barclays.bmg.ussd.dao.operation.SMSDetailsReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.SMSDetailsResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SMSDetailsDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>July 22, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SMSDetailsDAOController implements Controller {

    /**
     * The instance variable for smsDetailsReqAdptOperation of type SMSDetailsReqAdptOperation
     */
    private SMSDetailsReqAdptOperation smsDetailsReqAdptOperation;
    /**
     * The instance variable for smsDetailsResAdptOperation of type SMSDetailsResAdptOperation
     */
    private SMSDetailsResAdptOperation smsDetailsResAdptOperation;
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

	Object obj = smsDetailsReqAdptOperation.adaptRequestForSMSDetails(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = smsDetailsResAdptOperation.adaptResponseForSMSDetails(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for SMSDetailsReqAdptOperation
     * 
     *@param none
     *@return SMSDetailsReqAdptOperation
     */
    public SMSDetailsReqAdptOperation getSmsDetailsReqAdptOperation() {
	return smsDetailsReqAdptOperation;
    }

    /**
     * Setter for SMSDetailsReqAdptOperation
     * 
     * @param SMSDetailsReqAdptOperation
     * @return void
     */
    public void setSmsDetailsReqAdptOperation(SMSDetailsReqAdptOperation smsDetailsReqAdptOperation) {
	this.smsDetailsReqAdptOperation = smsDetailsReqAdptOperation;
    }

    /**
     * Getter for SMSDetailsResAdptOperation
     * 
     *@param none
     *@return SMSDetailsResAdptOperation
     */
    public SMSDetailsResAdptOperation getSmsDetailsResAdptOperation() {
	return smsDetailsResAdptOperation;
    }

    /**
     * Setter for SMSDetailsResAdptOperation
     * 
     * @param SMSDetailsResAdptOperation
     * @return void
     */
    public void setSmsDetailsResAdptOperation(SMSDetailsResAdptOperation smsDetailsResAdptOperation) {
	this.smsDetailsResAdptOperation = smsDetailsResAdptOperation;
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
