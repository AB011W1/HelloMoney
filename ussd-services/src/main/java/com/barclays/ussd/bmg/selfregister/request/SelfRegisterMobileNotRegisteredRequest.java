package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class SelfRegisterMobileNotRegisteredRequest implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>();
	}
	/*userInputMap.put(USSDInputParamsEnum.SELFREG_MOBILE.getParamName(), requestBuilderParamsDTO.getMsisdnNo());*/
	requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap);
	return new USSDBaseRequest();
    }
}
