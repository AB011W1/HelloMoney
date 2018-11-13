package com.barclays.ussd.bmg.requesDecisionMaker;

import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public interface CallBMGAccordingRequestInter {

    public String callBMGClient(USSDBaseRequest ussdBaseRequest, boolean demoModeFlag, String businessId, String countryCode)
	    throws USSDBlockingException, USSDNonBlockingException;
}
