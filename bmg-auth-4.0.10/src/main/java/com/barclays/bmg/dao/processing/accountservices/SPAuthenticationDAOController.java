package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.SPAuthenticationReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.SPAuthenticationResAdptOperation;

public class SPAuthenticationDAOController implements Controller {

    private SPAuthenticationReqAdptOperation spAuthenticationReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private SPAuthenticationResAdptOperation spAuthenticationResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = spAuthenticationReqAdptOperation.adaptRequestForSPAuthentication(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = spAuthenticationResAdptOperation.adaptResponseForSPAuthentication(workContext, obj1);

	return obj2;
    }

    public SPAuthenticationReqAdptOperation getSpAuthenticationReqAdptOperation() {
	return spAuthenticationReqAdptOperation;
    }

    public void setSpAuthenticationReqAdptOperation(SPAuthenticationReqAdptOperation spAuthenticationReqAdptOperation) {
	this.spAuthenticationReqAdptOperation = spAuthenticationReqAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public SPAuthenticationResAdptOperation getSpAuthenticationResAdptOperation() {
	return spAuthenticationResAdptOperation;
    }

    public void setSpAuthenticationResAdptOperation(SPAuthenticationResAdptOperation spAuthenticationResAdptOperation) {
	this.spAuthenticationResAdptOperation = spAuthenticationResAdptOperation;
    }

}
