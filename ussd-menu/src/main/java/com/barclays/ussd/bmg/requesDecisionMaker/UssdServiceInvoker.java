package com.barclays.ussd.bmg.requesDecisionMaker;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.utils.USSDConstants;

public class UssdServiceInvoker {

    @Autowired
    CallBMGAccordingRequestImpl callBMGAccordingRequest;

    @Autowired
    NonBMGAccordingRequestImpl nonBMGAccordingRequestImpl;

    public CallBMGAccordingRequestInter getInvoker(String bmgOpCode) {
	CallBMGAccordingRequestInter callBMGAccordingRequestInter = null;
	if (bmgOpCode.equalsIgnoreCase(USSDConstants.BMG_CALL_NOT_REQUIRED)) {
	    callBMGAccordingRequestInter = nonBMGAccordingRequestImpl;
	} else {
	    callBMGAccordingRequestInter = callBMGAccordingRequest;
	}

	return callBMGAccordingRequestInter;
    }

    public void setCallBMGAccordingRequest(CallBMGAccordingRequestImpl callBMGAccordingRequest) {
	this.callBMGAccordingRequest = callBMGAccordingRequest;
    }

    public void setNonBMGAccordingRequestImpl(NonBMGAccordingRequestImpl nonBMGAccordingRequestImpl) {
	this.nonBMGAccordingRequestImpl = nonBMGAccordingRequestImpl;
    }

}
