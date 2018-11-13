package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.addprospect.AddProspectReqAdptOperation;
import com.barclays.bmg.dao.operation.addprospect.AddProspectResAdptOperation;

public class AddProspectDAOController implements Controller {

    private AddProspectReqAdptOperation addProspectReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private AddProspectResAdptOperation addProspectResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	Object obj = addProspectReqAdptOperation.adaptRequest(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	return addProspectResAdptOperation.adaptResponse(workContext, obj1);
    }

    public AddProspectReqAdptOperation getAddProspectReqAdptOperation() {
	return addProspectReqAdptOperation;
    }

    public void setAddProspectReqAdptOperation(AddProspectReqAdptOperation addProspectReqAdptOperation) {
	this.addProspectReqAdptOperation = addProspectReqAdptOperation;
    }

    public AddProspectResAdptOperation getAddProspectResAdptOperation() {
	return addProspectResAdptOperation;
    }

    public void setAddProspectResAdptOperation(AddProspectResAdptOperation addProspectResAdptOperation) {
	this.addProspectResAdptOperation = addProspectResAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}