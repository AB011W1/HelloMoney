package com.barclays.ussd.bmg.requesDecisionMaker;

import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

public class NonBMGAccordingRequestImpl implements CallBMGAccordingRequestInter {
    public String callBMGClient(USSDBaseRequest ussdBaseRequest, boolean demoModeFlag, String businessId, String countryCode) {
	String response = null;
	response = ussdBaseRequest.getRequestParamMap().get(USSDConstants.HEADER_ID_PARAM_NAME);
	return response;
    }

}
