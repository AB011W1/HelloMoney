package com.barclays.ussd.bmg.fundtransfer.ownfundtransfer.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

public class RetrieveSrcAccountListRequestBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);

	/*
	 * USSDBaseRequest request = new USSDBaseRequest(); Map<String, String> userInputMap =
	 * requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap(); if(userInputMap == null){ userInputMap = new
	 * HashMap<String, String>(5); requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap); }
	 * requestBuilderParamsDTO
	 * .getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName(),
	 * requestBuilderParamsDTO.getUserInput());
	 */
	return request;
    }
}
