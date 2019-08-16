package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.TDDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.TDDetailsRespAdptOperation;

public class TDDetailsDAOController implements Controller {
    private TDDetailsReqAdptOperation tdDetailsReqAdptOperation;
    private TransmissionOperation transmissionOperation;
    private TDDetailsRespAdptOperation tdDetailsRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = tdDetailsReqAdptOperation.adaptRequestForInqueryBill(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	Object obj2 = tdDetailsRespAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public TDDetailsReqAdptOperation getTdDetailsReqAdptOperation() {
	return tdDetailsReqAdptOperation;
    }

    public void setTdDetailsReqAdptOperation(TDDetailsReqAdptOperation tdDetailsReqAdptOperation) {
	this.tdDetailsReqAdptOperation = tdDetailsReqAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public TDDetailsRespAdptOperation getTdDetailsRespAdptOperation() {
	return tdDetailsRespAdptOperation;
    }

    public void setTdDetailsRespAdptOperation(TDDetailsRespAdptOperation tdDetailsRespAdptOperation) {
	this.tdDetailsRespAdptOperation = tdDetailsRespAdptOperation;
    }

}
