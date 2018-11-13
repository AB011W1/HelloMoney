package com.barclays.ussd.bmg.requesDecisionMaker;

import com.barclays.ussd.svc.context.USSDBaseRequest;

public class PseudoCallRequestImpl implements CallBMGAccordingRequestInter {
    public String callBMGClient(USSDBaseRequest ussdBaseRequest, boolean demoModeFlag, String businessId, String countryCode) {
	String response = "";
	// Stack<Map<String,String>> bmgResponseStack = ussdSessionMgmt.getUserTransactionDetails().getBmgResponseStack();
	return response;
    }
}
