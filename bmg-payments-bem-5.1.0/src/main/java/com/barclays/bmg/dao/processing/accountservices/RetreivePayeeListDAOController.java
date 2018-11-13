package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreivePayeeListReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreivePayeeListRespAdptOperation;

public class RetreivePayeeListDAOController implements Controller{

	private RetreivePayeeListReqAdptOperation retreivePayeeListReqAdptOperation;
	private  TransmissionOperation transmissionOperation;
	private RetreivePayeeListRespAdptOperation retreivePayeeListRespAdptOperation;

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = retreivePayeeListReqAdptOperation.adaptRequestForIndvPayeeList(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = retreivePayeeListRespAdptOperation.adaptResponse(workContext, obj1);


		return obj2;
	}

	public RetreivePayeeListReqAdptOperation getRetreivePayeeListReqAdptOperation() {
		return retreivePayeeListReqAdptOperation;
	}

	public void setRetreivePayeeListReqAdptOperation(
			RetreivePayeeListReqAdptOperation retreivePayeeListReqAdptOperation) {
		this.retreivePayeeListReqAdptOperation = retreivePayeeListReqAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}

	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

	public RetreivePayeeListRespAdptOperation getRetreivePayeeListRespAdptOperation() {
		return retreivePayeeListRespAdptOperation;
	}

	public void setRetreivePayeeListRespAdptOperation(
			RetreivePayeeListRespAdptOperation retreivePayeeListRespAdptOperation) {
		this.retreivePayeeListRespAdptOperation = retreivePayeeListRespAdptOperation;
	}

}
