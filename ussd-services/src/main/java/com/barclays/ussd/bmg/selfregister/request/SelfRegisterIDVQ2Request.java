package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class SelfRegisterIDVQ2Request implements BmgBaseRequestBuilder {

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	/*
	 * String userInput = requestBuilderParamsDTO.getUserInput(); if (StringUtils.isNotBlank(userInput)) { Map<String, String> userInputMap = new
	 * HashMap<String, String>(6); userInputMap.put(USSDInputParamsEnum.SELFREG_QUESTION_ONE.getTranId(), userInput);
	 * requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap); }
	 */
	return ussdBaseRequest;
    }

}
