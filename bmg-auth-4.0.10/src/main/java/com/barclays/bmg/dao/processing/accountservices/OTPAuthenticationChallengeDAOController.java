package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.OTPAuthenticationChallengeReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.OTPAuthenticationChallengeResAdptOperation;

public class OTPAuthenticationChallengeDAOController implements Controller {

    private OTPAuthenticationChallengeReqAdptOperation otpAuthenticationChallengeReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private OTPAuthenticationChallengeResAdptOperation otpAuthenticationChallengeResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = otpAuthenticationChallengeReqAdptOperation.adaptRequestForSPAuthentication(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = otpAuthenticationChallengeResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public OTPAuthenticationChallengeReqAdptOperation getOtpAuthenticationChallengeReqAdptOperation() {
	return otpAuthenticationChallengeReqAdptOperation;
    }

    public void setOtpAuthenticationChallengeReqAdptOperation(OTPAuthenticationChallengeReqAdptOperation otpAuthenticationChallengeReqAdptOperation) {
	this.otpAuthenticationChallengeReqAdptOperation = otpAuthenticationChallengeReqAdptOperation;
    }

    public OTPAuthenticationChallengeResAdptOperation getOtpAuthenticationChallengeResAdptOperation() {
	return otpAuthenticationChallengeResAdptOperation;
    }

    public void setOtpAuthenticationChallengeResAdptOperation(OTPAuthenticationChallengeResAdptOperation otpAuthenticationChallengeResAdptOperation) {
	this.otpAuthenticationChallengeResAdptOperation = otpAuthenticationChallengeResAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
