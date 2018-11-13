package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ChangePasswordReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ChangePasswordResAdptOperation;

public class ChangePasswordDAOController implements Controller {

    private ChangePasswordReqAdptOperation changePasswordReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private ChangePasswordResAdptOperation changePasswordResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	Object obj = changePasswordReqAdptOperation.adaptRequestForChangePassword(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	Object obj2 = changePasswordResAdptOperation.adaptResponseChangePassword(workContext, obj1);
	return obj2;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public ChangePasswordReqAdptOperation getChangePasswordReqAdptOperation() {
	return changePasswordReqAdptOperation;
    }

    public void setChangePasswordReqAdptOperation(ChangePasswordReqAdptOperation changePasswordReqAdptOperation) {
	this.changePasswordReqAdptOperation = changePasswordReqAdptOperation;
    }

    public ChangePasswordResAdptOperation getChangePasswordResAdptOperation() {
	return changePasswordResAdptOperation;
    }

    public void setChangePasswordResAdptOperation(ChangePasswordResAdptOperation changePasswordResAdptOperation) {
	this.changePasswordResAdptOperation = changePasswordResAdptOperation;
    }

}
