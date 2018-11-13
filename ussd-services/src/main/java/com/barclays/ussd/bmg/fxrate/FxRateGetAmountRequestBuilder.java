package com.barclays.ussd.bmg.fxrate;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class FxRateGetAmountRequestBuilder implements BmgBaseRequestBuilder {

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>();
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	}
	if (requestBuilderParamsDTO.getUserInput() != null) {
	    userInputMap.put(USSDInputParamsEnum.FX_RATE_GET_CURR.getTranId(), requestBuilderParamsDTO.getUserInput());
	}

	return new USSDBaseRequest();
    }

}
