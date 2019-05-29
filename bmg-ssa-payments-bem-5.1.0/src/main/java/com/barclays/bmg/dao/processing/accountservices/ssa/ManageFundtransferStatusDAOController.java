package com.barclays.bmg.dao.processing.accountservices.ssa;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ssa.ManageFundtransferStatusReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ssa.ManageFundtransferStatusRespAdptOperation;

public class ManageFundtransferStatusDAOController implements Controller {

	ManageFundtransferStatusReqAdptOperation manageFundtransferStatusReqAdptOperation;
	TransmissionOperation transmissionOperation;
	ManageFundtransferStatusRespAdptOperation manageFundtransferStatusRespAdptOperation;

	public ManageFundtransferStatusReqAdptOperation getManageFundtransferStatusReqAdptOperation() {
		return manageFundtransferStatusReqAdptOperation;
	}

	public void setManageFundtransferStatusReqAdptOperation(
			ManageFundtransferStatusReqAdptOperation manageFundtransferStatusReqAdptOperation) {
		this.manageFundtransferStatusReqAdptOperation = manageFundtransferStatusReqAdptOperation;
	}

	public TransmissionOperation gettransmissionOperation() {
		return transmissionOperation;
	}

	public void settransmissionOperation(
			TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

	public ManageFundtransferStatusRespAdptOperation getManageFundtransferStatusRespAdptOperation() {
		return manageFundtransferStatusRespAdptOperation;
	}

	public void setManageFundtransferStatusRespAdptOperation(
			ManageFundtransferStatusRespAdptOperation manageFundtransferStatusRespAdptOperation) {
		this.manageFundtransferStatusRespAdptOperation = manageFundtransferStatusRespAdptOperation;
	}


	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		Object obj = manageFundtransferStatusReqAdptOperation.adaptRequest(workContext);

		Object obj1 = transmissionOperation.sendAndReceive(obj);

		Object obj2 = manageFundtransferStatusRespAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}

}
