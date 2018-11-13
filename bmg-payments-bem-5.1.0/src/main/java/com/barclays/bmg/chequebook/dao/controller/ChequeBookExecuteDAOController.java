package com.barclays.bmg.chequebook.dao.controller;

import com.barclays.bmg.chequebook.dao.operation.ChequeBookExecuteReqAdptOperation;
import com.barclays.bmg.chequebook.dao.operation.ChequeBookExecuteResAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;

public class ChequeBookExecuteDAOController implements Controller{

	private ChequeBookExecuteReqAdptOperation chequeBookExecuteReqAdptOperation;

	private TransmissionOperation transmissionOperation;

	private ChequeBookExecuteResAdptOperation chequeBookExecuteResAdptOperation;

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = chequeBookExecuteReqAdptOperation.adaptRequest(workContext);

		Object obj1 = transmissionOperation.sendAndReceive(obj);

		Object obj2 = chequeBookExecuteResAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}

	public ChequeBookExecuteReqAdptOperation getChequeBookExecuteReqAdptOperation() {
		return chequeBookExecuteReqAdptOperation;
	}

	public void setChequeBookExecuteReqAdptOperation(
			ChequeBookExecuteReqAdptOperation chequeBookExecuteReqAdptOperation) {
		this.chequeBookExecuteReqAdptOperation = chequeBookExecuteReqAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}

	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

	public ChequeBookExecuteResAdptOperation getChequeBookExecuteResAdptOperation() {
		return chequeBookExecuteResAdptOperation;
	}

	public void setChequeBookExecuteResAdptOperation(
			ChequeBookExecuteResAdptOperation chequeBookExecuteResAdptOperation) {
		this.chequeBookExecuteResAdptOperation = chequeBookExecuteResAdptOperation;
	}

}
