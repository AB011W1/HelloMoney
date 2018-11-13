package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrIndvCustByCCReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrIndvCustByCCRespAdptOperation;

public class RetreiveIndvCustInfoByCCDAOController implements Controller {

	private RetrIndvCustByCCReqAdptOperation retrIndvCustByCCReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private RetrIndvCustByCCRespAdptOperation retrIndvCustByCCRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = retrIndvCustByCCReqAdptOperation.adaptRequestForIndvPayeeList(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = retrIndvCustByCCRespAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}
	public RetrIndvCustByCCReqAdptOperation getRetrIndvCustByCCReqAdptOperation() {
		return retrIndvCustByCCReqAdptOperation;
	}
	public void setRetrIndvCustByCCReqAdptOperation(
			RetrIndvCustByCCReqAdptOperation retrIndvCustByCCReqAdptOperation) {
		this.retrIndvCustByCCReqAdptOperation = retrIndvCustByCCReqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public RetrIndvCustByCCRespAdptOperation getRetrIndvCustByCCRespAdptOperation() {
		return retrIndvCustByCCRespAdptOperation;
	}
	public void setRetrIndvCustByCCRespAdptOperation(
			RetrIndvCustByCCRespAdptOperation retrIndvCustByCCRespAdptOperation) {
		this.retrIndvCustByCCRespAdptOperation = retrIndvCustByCCRespAdptOperation;
	}

}
