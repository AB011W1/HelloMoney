package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.SQAAuthenticationChallengeReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.SQAAuthenticationChallengeResAdptOperation;

public class SQAAuthenticationChallengeDAOController implements Controller {

    private SQAAuthenticationChallengeReqAdptOperation sqaAuthenticationChallengeReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private SQAAuthenticationChallengeResAdptOperation sqaAuthenticationChallengeResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = sqaAuthenticationChallengeReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = sqaAuthenticationChallengeResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public SQAAuthenticationChallengeReqAdptOperation getSqaAuthenticationChallengeReqAdptOperation() {
	return sqaAuthenticationChallengeReqAdptOperation;
    }

    public void setSqaAuthenticationChallengeReqAdptOperation(SQAAuthenticationChallengeReqAdptOperation sqaAuthenticationChallengeReqAdptOperation) {
	this.sqaAuthenticationChallengeReqAdptOperation = sqaAuthenticationChallengeReqAdptOperation;
    }

    public SQAAuthenticationChallengeResAdptOperation getSqaAuthenticationChallengeResAdptOperation() {
	return sqaAuthenticationChallengeResAdptOperation;
    }

    public void setSqaAuthenticationChallengeResAdptOperation(SQAAuthenticationChallengeResAdptOperation sqaAuthenticationChallengeResAdptOperation) {
	this.sqaAuthenticationChallengeResAdptOperation = sqaAuthenticationChallengeResAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
