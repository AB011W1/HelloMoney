package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreiveChargeDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreiveChargeDetailsRespAdptOperation;

public class RetreiveChargeDetailsDAOController implements Controller{

	private RetreiveChargeDetailsReqAdptOperation retreiveChargeDetailsReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private RetreiveChargeDetailsRespAdptOperation retreiveChargeDetailsRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = retreiveChargeDetailsReqAdptOperation.adaptRequestForRetreiveChargeDetails(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = retreiveChargeDetailsRespAdptOperation.adaptResponse(workContext, obj1);
		return obj2;
	}
	public RetreiveChargeDetailsReqAdptOperation getRetreiveChargeDetailsReqAdptOperation() {
		return retreiveChargeDetailsReqAdptOperation;
	}
	public void setRetreiveChargeDetailsReqAdptOperation(
			RetreiveChargeDetailsReqAdptOperation retreiveChargeDetailsReqAdptOperation) {
		this.retreiveChargeDetailsReqAdptOperation = retreiveChargeDetailsReqAdptOperation;
	}
	public RetreiveChargeDetailsRespAdptOperation getRetreiveChargeDetailsRespAdptOperation() {
		return retreiveChargeDetailsRespAdptOperation;
	}
	public void setRetreiveChargeDetailsRespAdptOperation(
			RetreiveChargeDetailsRespAdptOperation retreiveChargeDetailsRespAdptOperation) {
		this.retreiveChargeDetailsRespAdptOperation = retreiveChargeDetailsRespAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

}
