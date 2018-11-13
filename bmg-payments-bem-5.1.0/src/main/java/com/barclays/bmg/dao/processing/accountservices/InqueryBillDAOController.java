package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.InqueryBillReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.InqueryBillRespAdptOperation;

public class InqueryBillDAOController implements Controller {

	private InqueryBillReqAdptOperation inqueryBillReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private InqueryBillRespAdptOperation inqueryBillRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = inqueryBillReqAdptOperation.adaptRequestForInqueryBill(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = inqueryBillRespAdptOperation.adaptResponse(workContext, obj1);
		return obj2;
	}
	public InqueryBillReqAdptOperation getInqueryBillReqAdptOperation() {
		return inqueryBillReqAdptOperation;
	}
	public void setInqueryBillReqAdptOperation(
			InqueryBillReqAdptOperation inqueryBillReqAdptOperation) {
		this.inqueryBillReqAdptOperation = inqueryBillReqAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public InqueryBillRespAdptOperation getInqueryBillRespAdptOperation() {
		return inqueryBillRespAdptOperation;
	}
	public void setInqueryBillRespAdptOperation(
			InqueryBillRespAdptOperation inqueryBillRespAdptOperation) {
		this.inqueryBillRespAdptOperation = inqueryBillRespAdptOperation;
	}

}
