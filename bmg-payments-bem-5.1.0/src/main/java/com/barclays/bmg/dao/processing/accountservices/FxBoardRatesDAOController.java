package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.FxBoardRatesReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.FxBoardRatesRespAdptOperation;


public class FxBoardRatesDAOController implements Controller {

	private FxBoardRatesReqAdptOperation fxBoardRatesReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private FxBoardRatesRespAdptOperation fxBoardRatesRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = fxBoardRatesReqAdptOperation.adaptRequestForFxRate(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = fxBoardRatesRespAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}
	public FxBoardRatesReqAdptOperation getFxBoardRatesReqAdptOperation() {
		return fxBoardRatesReqAdptOperation;
	}
	public void setFxBoardRatesReqAdptOperation(
			FxBoardRatesReqAdptOperation fxBoardRatesReqAdptOperation) {
		this.fxBoardRatesReqAdptOperation = fxBoardRatesReqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public FxBoardRatesRespAdptOperation getFxBoardRatesRespAdptOperation() {
		return fxBoardRatesRespAdptOperation;
	}
	public void setFxBoardRatesRespAdptOperation(
			FxBoardRatesRespAdptOperation fxBoardRatesRespAdptOperation) {
		this.fxBoardRatesRespAdptOperation = fxBoardRatesRespAdptOperation;
	}


}
