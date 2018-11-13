package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.FxRateReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.FxRateRespAdptOperation;


public class FxRateDAOController implements Controller {

	private FxRateReqAdptOperation fxRateReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private FxRateRespAdptOperation fxRateRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = fxRateReqAdptOperation.adaptRequestForFxRate(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = fxRateRespAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}
	public FxRateReqAdptOperation getFxRateReqAdptOperation() {
		return fxRateReqAdptOperation;
	}
	public void setFxRateReqAdptOperation(
			FxRateReqAdptOperation fxRateReqAdptOperation) {
		this.fxRateReqAdptOperation = fxRateReqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public FxRateRespAdptOperation getFxRateRespAdptOperation() {
		return fxRateRespAdptOperation;
	}
	public void setFxRateRespAdptOperation(
			FxRateRespAdptOperation fxRateRespAdptOperation) {
		this.fxRateRespAdptOperation = fxRateRespAdptOperation;
	}

}
