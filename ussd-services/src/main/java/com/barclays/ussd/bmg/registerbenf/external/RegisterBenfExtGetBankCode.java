package com.barclays.ussd.bmg.registerbenf.external;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class RegisterBenfExtGetBankCode implements BmgBaseRequestBuilder {
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		if (userInputMap == null) {
			userInputMap = new HashMap<String, String>(5);
			requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap);
		}
		//userInputMap.put(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE.getParamName(), requestBuilderParamsDTO.getUserInput());
		/*requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put(
				USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE.getParamName(), requestBuilderParamsDTO.getUserInput());*/
		return null;
	}
}
