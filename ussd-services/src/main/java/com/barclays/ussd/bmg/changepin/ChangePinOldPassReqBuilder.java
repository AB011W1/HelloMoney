package com.barclays.ussd.bmg.changepin;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class ChangePinOldPassReqBuilder implements BmgBaseRequestBuilder
{
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO)
	{
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		if(userInputMap == null){
			userInputMap = new HashMap<String, String>(5);
			requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap);
		}
		return null;
	}
}
