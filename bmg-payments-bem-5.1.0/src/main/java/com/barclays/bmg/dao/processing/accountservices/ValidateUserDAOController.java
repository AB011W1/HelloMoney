package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ValidateUserReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ValidateUserResAdptOperation;

public class ValidateUserDAOController implements Controller {

    private ValidateUserReqAdptOperation validateUserReqAdptOperation;
    private ValidateUserResAdptOperation validateUserResAdptOperation;

    public ValidateUserReqAdptOperation getValidateUserReqAdptOperation() {
	return validateUserReqAdptOperation;
    }

    public void setValidateUserReqAdptOperation(ValidateUserReqAdptOperation validateUserReqAdptOperation) {
	this.validateUserReqAdptOperation = validateUserReqAdptOperation;
    }

    public void setValidateUserResAdptOperation(ValidateUserResAdptOperation validateUserResAdptOperation) {
	this.validateUserResAdptOperation = validateUserResAdptOperation;
    }

    private TransmissionOperation transmissionOperation1;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = validateUserReqAdptOperation.adaptRequestForValidateUser(workContext);

	Object obj1 = transmissionOperation1.sendAndReceive(obj);

	Object obj2 = validateUserResAdptOperation.adaptResponseForValidateUser(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation1() {
	return transmissionOperation1;
    }

    public void setTransmissionOperation1(TransmissionOperation transmissionOperation1) {
	this.transmissionOperation1 = transmissionOperation1;
    }

}
