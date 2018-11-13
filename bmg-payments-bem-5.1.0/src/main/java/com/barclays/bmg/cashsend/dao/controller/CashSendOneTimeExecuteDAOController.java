package com.barclays.bmg.cashsend.dao.controller;

import com.barclays.bmg.cashsend.dao.operation.CashSendOneTimeExecuteReqAdptOperation;
import com.barclays.bmg.cashsend.dao.operation.CashSendOneTimeExecuteResAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;

public class CashSendOneTimeExecuteDAOController implements Controller {

    private CashSendOneTimeExecuteReqAdptOperation cashSendOneTimeExecuteReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CashSendOneTimeExecuteResAdptOperation cashSendOneTimeExecuteResAdptOperation;


    public CashSendOneTimeExecuteReqAdptOperation getCashSendOneTimeExecuteReqAdptOperation() {
        return cashSendOneTimeExecuteReqAdptOperation;
    }

    public void setCashSendOneTimeExecuteReqAdptOperation(CashSendOneTimeExecuteReqAdptOperation cashSendOneTimeExecuteReqAdptOperation) {
        this.cashSendOneTimeExecuteReqAdptOperation = cashSendOneTimeExecuteReqAdptOperation;
    }

    public CashSendOneTimeExecuteResAdptOperation getCashSendOneTimeExecuteResAdptOperation() {
        return cashSendOneTimeExecuteResAdptOperation;
    }

    public void setCashSendOneTimeExecuteResAdptOperation(CashSendOneTimeExecuteResAdptOperation cashSendOneTimeExecuteResAdptOperation) {
        this.cashSendOneTimeExecuteResAdptOperation = cashSendOneTimeExecuteResAdptOperation;
    }

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = cashSendOneTimeExecuteReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = cashSendOneTimeExecuteResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
