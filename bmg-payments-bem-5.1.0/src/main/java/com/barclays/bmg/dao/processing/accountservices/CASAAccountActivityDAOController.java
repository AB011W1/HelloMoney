package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.CASAAccountActivityReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.CASAAccountActivityRespAdptOperation;

public class CASAAccountActivityDAOController implements Controller {

    private CASAAccountActivityReqAdptOperation casaAccountActivityReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CASAAccountActivityRespAdptOperation casaAccountActivityRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = casaAccountActivityReqAdptOperation.adaptRequestForCASAAccountActivity(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = casaAccountActivityRespAdptOperation.adaptResponseForCASAAccountActivity(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public CASAAccountActivityReqAdptOperation getCasaAccountActivityReqAdptOperation() {
	return casaAccountActivityReqAdptOperation;
    }

    public void setCasaAccountActivityReqAdptOperation(CASAAccountActivityReqAdptOperation casaAccountActivityReqAdptOperation) {
	this.casaAccountActivityReqAdptOperation = casaAccountActivityReqAdptOperation;
    }

    public CASAAccountActivityRespAdptOperation getCasaAccountActivityRespAdptOperation() {
	return casaAccountActivityRespAdptOperation;
    }

    public void setCasaAccountActivityRespAdptOperation(CASAAccountActivityRespAdptOperation casaAccountActivityRespAdptOperation) {
	this.casaAccountActivityRespAdptOperation = casaAccountActivityRespAdptOperation;
    }

}
