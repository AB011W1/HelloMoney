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
import com.barclays.bmg.dao.operation.accountservices.ThmEncryptPinReqAdaptOperation;
import com.barclays.bmg.dao.operation.accountservices.ThmEncryptPinResAdaptOperation;

public class ThmEncryptPinDAOController implements Controller {
    private ThmEncryptPinReqAdaptOperation thmEncryptPinReqAdaptOperation;
    private TransmissionOperation thmEncryptPinTransmissionOperation;
    private ThmEncryptPinResAdaptOperation thmEncryptPinResAdaptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	Object obj = thmEncryptPinReqAdaptOperation.adaptRequest(workContext);
	Object obj1 = thmEncryptPinTransmissionOperation.sendAndReceive(obj);
	return thmEncryptPinResAdaptOperation.adaptResponse(workContext, obj1);
    }

    public ThmEncryptPinReqAdaptOperation getThmEncryptPinReqAdaptOperation() {
	return thmEncryptPinReqAdaptOperation;
    }

    public void setThmEncryptPinReqAdaptOperation(ThmEncryptPinReqAdaptOperation thmEncryptPinReqAdaptOperation) {
	this.thmEncryptPinReqAdaptOperation = thmEncryptPinReqAdaptOperation;
    }

    public TransmissionOperation getThmEncryptPinTransmissionOperation() {
	return thmEncryptPinTransmissionOperation;
    }

    public void setThmEncryptPinTransmissionOperation(TransmissionOperation thmEncryptPinTransmissionOperation) {
	this.thmEncryptPinTransmissionOperation = thmEncryptPinTransmissionOperation;
    }

    public ThmEncryptPinResAdaptOperation getThmEncryptPinResAdaptOperation() {
	return thmEncryptPinResAdaptOperation;
    }

    public void setThmEncryptPinResAdaptOperation(ThmEncryptPinResAdaptOperation thmEncryptPinResAdaptOperation) {
	this.thmEncryptPinResAdaptOperation = thmEncryptPinResAdaptOperation;
    }

}