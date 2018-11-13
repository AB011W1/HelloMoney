package com.barclays.bmg.cashsend.dao.controller;

import com.barclays.bmg.cashsend.dao.operation.CashSendEncryptionReqAdptOperation;
import com.barclays.bmg.cashsend.dao.operation.CashSendEncryptionResAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;

public class CashSendEncryptionDAOController implements Controller {

    private CashSendEncryptionReqAdptOperation cashSendEncryptionReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CashSendEncryptionResAdptOperation cashSendEncryptionResAdptOperation;

    public CashSendEncryptionReqAdptOperation getCashSendEncryptionReqAdptOperation() {
		return cashSendEncryptionReqAdptOperation;
	}

	public void setCashSendEncryptionReqAdptOperation(
			CashSendEncryptionReqAdptOperation cashSendEncryptionReqAdptOperation) {
		this.cashSendEncryptionReqAdptOperation = cashSendEncryptionReqAdptOperation;
	}

	public CashSendEncryptionResAdptOperation getCashSendEncryptionResAdptOperation() {
		return cashSendEncryptionResAdptOperation;
	}

	public void setCashSendEncryptionResAdptOperation(
			CashSendEncryptionResAdptOperation cashSendEncryptionResAdptOperation) {
		this.cashSendEncryptionResAdptOperation = cashSendEncryptionResAdptOperation;
	}

	@Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = cashSendEncryptionReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = cashSendEncryptionResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
