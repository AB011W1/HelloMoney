package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.OTPAuthenticationReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.OTPAuthenticationResAdptOperation;

public class OTPAuthenticationDAOController implements Controller {

    private OTPAuthenticationReqAdptOperation otpAuthenticationReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private OTPAuthenticationResAdptOperation otpAuthenticationResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = otpAuthenticationReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = otpAuthenticationResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public OTPAuthenticationReqAdptOperation getOtpAuthenticationReqAdptOperation() {
	return otpAuthenticationReqAdptOperation;
    }

    public void setOtpAuthenticationReqAdptOperation(OTPAuthenticationReqAdptOperation otpAuthenticationReqAdptOperation) {
	this.otpAuthenticationReqAdptOperation = otpAuthenticationReqAdptOperation;
    }

    public OTPAuthenticationResAdptOperation getOtpAuthenticationResAdptOperation() {
	return otpAuthenticationResAdptOperation;
    }

    public void setOtpAuthenticationResAdptOperation(OTPAuthenticationResAdptOperation otpAuthenticationResAdptOperation) {
	this.otpAuthenticationResAdptOperation = otpAuthenticationResAdptOperation;
    }

}
