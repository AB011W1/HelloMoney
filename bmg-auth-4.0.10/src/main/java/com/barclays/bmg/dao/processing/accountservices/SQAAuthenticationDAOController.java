package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.SQAAuthenticationReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.SQAAuthenticationResAdptOperation;

public class SQAAuthenticationDAOController implements Controller {

    private SQAAuthenticationReqAdptOperation sqaAuthenticationReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private SQAAuthenticationResAdptOperation sqaAuthenticationResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = sqaAuthenticationReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = sqaAuthenticationResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public SQAAuthenticationReqAdptOperation getSqaAuthenticationReqAdptOperation() {
	return sqaAuthenticationReqAdptOperation;
    }

    public void setSqaAuthenticationReqAdptOperation(SQAAuthenticationReqAdptOperation sqaAuthenticationReqAdptOperation) {
	this.sqaAuthenticationReqAdptOperation = sqaAuthenticationReqAdptOperation;
    }

    public SQAAuthenticationResAdptOperation getSqaAuthenticationResAdptOperation() {
	return sqaAuthenticationResAdptOperation;
    }

    public void setSqaAuthenticationResAdptOperation(SQAAuthenticationResAdptOperation sqaAuthenticationResAdptOperation) {
	this.sqaAuthenticationResAdptOperation = sqaAuthenticationResAdptOperation;
    }

}
