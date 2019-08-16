package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrevCASAAcctTranActivityReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrevCASAAcctTranActivityRespAdptOperation;

public class RetrevCASAAcctTranActivityDAOController implements Controller {
	RetrevCASAAcctTranActivityReqAdptOperation retrevCASAAcctTranActivityReqAdptOperation;
	TransmissionOperation retrevCASAAcctTranActivityTransmissionOperation;
	RetrevCASAAcctTranActivityRespAdptOperation retrevCASAAcctTranActivityRespAdptOperation;

	public RetrevCASAAcctTranActivityReqAdptOperation getRetrevCASAAcctTranActivityReqAdptOperation() {
		return retrevCASAAcctTranActivityReqAdptOperation;
	}

	public void setRetrevCASAAcctTranActivityReqAdptOperation(
			RetrevCASAAcctTranActivityReqAdptOperation retrevCASAAcctTranActivityReqAdptOperation) {
		this.retrevCASAAcctTranActivityReqAdptOperation = retrevCASAAcctTranActivityReqAdptOperation;
	}

	public TransmissionOperation getRetrevCASAAcctTranActivityTransmissionOperation() {
		return retrevCASAAcctTranActivityTransmissionOperation;
	}

	public void setRetrevCASAAcctTranActivityTransmissionOperation(
			TransmissionOperation retrevCASAAcctTranActivityTransmissionOperation) {
		this.retrevCASAAcctTranActivityTransmissionOperation = retrevCASAAcctTranActivityTransmissionOperation;
	}

	public RetrevCASAAcctTranActivityRespAdptOperation getRetrevCASAAcctTranActivityRespAdptOperation() {
		return retrevCASAAcctTranActivityRespAdptOperation;
	}

	public void setRetrevCASAAcctTranActivityRespAdptOperation(
			RetrevCASAAcctTranActivityRespAdptOperation retrevCASAAcctTranActivityRespAdptOperation) {
		this.retrevCASAAcctTranActivityRespAdptOperation = retrevCASAAcctTranActivityRespAdptOperation;
	}

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		// TODO Auto-generated method stub

		Object obj = retrevCASAAcctTranActivityReqAdptOperation.adaptRequest(workContext);

		Object obj1 = retrevCASAAcctTranActivityTransmissionOperation.sendAndReceive(obj);

		Object obj2 = retrevCASAAcctTranActivityRespAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}

}
