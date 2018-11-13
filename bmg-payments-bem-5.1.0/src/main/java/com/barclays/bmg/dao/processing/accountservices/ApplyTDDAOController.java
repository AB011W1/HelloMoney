package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ApplyTDReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ApplyTDRespAdptOperation;

/**
 * @author E20027734
 *
 */
public class ApplyTDDAOController implements Controller {

	private ApplyTDReqAdptOperation reqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private ApplyTDRespAdptOperation resAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		// TODO Auto-generated method stub
		Object obj = reqAdptOperation.adaptRequestForApplyTD(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = resAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}
	public ApplyTDReqAdptOperation getReqAdptOperation() {
		return reqAdptOperation;
	}
	public void setReqAdptOperation(ApplyTDReqAdptOperation reqAdptOperation) {
		this.reqAdptOperation = reqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public ApplyTDRespAdptOperation getResAdptOperation() {
		return resAdptOperation;
	}
	public void setResAdptOperation(ApplyTDRespAdptOperation resAdptOperation) {
		this.resAdptOperation = resAdptOperation;
	}

}
