package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ReportProblemReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ReportProblemRespAdptOperation;

/**
 * @author E20027734
 *
 */
public class ReportProblemDAOController implements Controller {

	private ReportProblemReqAdptOperation reqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private ReportProblemRespAdptOperation resAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		// TODO Auto-generated method stub
		Object obj = reqAdptOperation.adaptRequestForRequestProblem(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = resAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}
	public ReportProblemReqAdptOperation getReqAdptOperation() {
		return reqAdptOperation;
	}
	public void setReqAdptOperation(ReportProblemReqAdptOperation reqAdptOperation) {
		this.reqAdptOperation = reqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public ReportProblemRespAdptOperation getResAdptOperation() {
		return resAdptOperation;
	}
	public void setResAdptOperation(ReportProblemRespAdptOperation resAdptOperation) {
		this.resAdptOperation = resAdptOperation;
	}

}
