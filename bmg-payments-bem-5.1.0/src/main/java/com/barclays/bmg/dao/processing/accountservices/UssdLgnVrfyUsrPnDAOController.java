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
package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.UssdLgnVrfyUsrPnReqAdaptOperation;
import com.barclays.bmg.dao.operation.accountservices.UssdLgnVrfyUsrPnResAdaptOperation;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnDAOController.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>May 30, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnDAOController created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnDAOController implements Controller {
    /**
     * The instance variable named "ussdLgnVrfyUsrPnReqAdaptOperation" is created.
     */
    private UssdLgnVrfyUsrPnReqAdaptOperation ussdLgnVrfyUsrPnReqAdaptOperation;

    /**
     * The instance variable named "ussdLgnVrfyUsrPnTransmissionOperation" is created.
     */
    private TransmissionOperation ussdLgnVrfyUsrPnTransmissionOperation;
    /**
     * The instance variable named "ussdLgnVrfyUsrPnResAdaptOperation" is created.
     */
    private UssdLgnVrfyUsrPnResAdaptOperation ussdLgnVrfyUsrPnResAdaptOperation;

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.dao.core.processing.Controller#handleRequest(com.barclays.bmg.dao.core.context.WorkContext)
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	Object obj = ussdLgnVrfyUsrPnReqAdaptOperation.adaptRequest(workContext);
	Object obj1 = ussdLgnVrfyUsrPnTransmissionOperation.sendAndReceive(obj);
	return ussdLgnVrfyUsrPnResAdaptOperation.adaptResponse(workContext, obj1);
    }

    /**
     * @return the ussdLgnVrfyUsrPnReqAdaptOperation
     */
    public UssdLgnVrfyUsrPnReqAdaptOperation getUssdLgnVrfyUsrPnReqAdaptOperation() {
	return ussdLgnVrfyUsrPnReqAdaptOperation;
    }

    /**
	 *
	 */
    public void setUssdLgnVrfyUsrPnReqAdaptOperation(UssdLgnVrfyUsrPnReqAdaptOperation ussdLgnVrfyUsrPnReqAdaptOperation) {
	this.ussdLgnVrfyUsrPnReqAdaptOperation = ussdLgnVrfyUsrPnReqAdaptOperation;
    }

    /**
     * @return the ussdLgnVrfyUsrPnTransmissionOperation
     */
    public TransmissionOperation getUssdLgnVrfyUsrPnTransmissionOperation() {
	return ussdLgnVrfyUsrPnTransmissionOperation;
    }

    /**
	 *
	 */
    public void setUssdLgnVrfyUsrPnTransmissionOperation(TransmissionOperation ussdLgnVrfyUsrPnTransmissionOperation) {
	this.ussdLgnVrfyUsrPnTransmissionOperation = ussdLgnVrfyUsrPnTransmissionOperation;
    }

    /**
     * @return the ussdLgnVrfyUsrPnResAdaptOperation
     */
    public UssdLgnVrfyUsrPnResAdaptOperation getUssdLgnVrfyUsrPnResAdaptOperation() {
	return ussdLgnVrfyUsrPnResAdaptOperation;
    }

    /**
	 *
	 */
    public void setUssdLgnVrfyUsrPnResAdaptOperation(UssdLgnVrfyUsrPnResAdaptOperation ussdLgnVrfyUsrPnResAdaptOperation) {
	this.ussdLgnVrfyUsrPnResAdaptOperation = ussdLgnVrfyUsrPnResAdaptOperation;
    }
}