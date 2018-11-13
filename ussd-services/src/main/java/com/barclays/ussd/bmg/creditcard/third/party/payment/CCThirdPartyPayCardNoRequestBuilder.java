package com.barclays.ussd.bmg.creditcard.third.party.payment;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class CCThirdPartyPayCardNoRequestBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings( {})
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>(5);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	}
	return null;
    }
}
