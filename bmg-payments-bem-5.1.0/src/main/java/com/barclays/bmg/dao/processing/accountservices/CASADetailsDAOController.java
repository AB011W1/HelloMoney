package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.CASADetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.CASADetailsRespAdptOperation;

public class CASADetailsDAOController implements Controller {

    private CASADetailsReqAdptOperation casaDetailsReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CASADetailsRespAdptOperation casaDetailsRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = casaDetailsReqAdptOperation.adaptRequestForCASADetails(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = casaDetailsRespAdptOperation.adaptResponseForCASADetails(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public CASADetailsReqAdptOperation getCasaDetailsReqAdptOperation() {
	return casaDetailsReqAdptOperation;
    }

    public void setCasaDetailsReqAdptOperation(CASADetailsReqAdptOperation casaDetailsReqAdptOperation) {
	this.casaDetailsReqAdptOperation = casaDetailsReqAdptOperation;
    }

    public CASADetailsRespAdptOperation getCasaDetailsRespAdptOperation() {
	return casaDetailsRespAdptOperation;
    }

    public void setCasaDetailsRespAdptOperation(CASADetailsRespAdptOperation casaDetailsRespAdptOperation) {
	this.casaDetailsRespAdptOperation = casaDetailsRespAdptOperation;
    }

}
