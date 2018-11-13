package com.barclays.ussd.bmg.user.migration;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

public class UserMigrationSubmitRequestBuilder implements BmgBaseRequestBuilder {
    private static final String MOBILE_NO = "mobileNo";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.putAll(userInputMap);
	requestParamMap.put(MOBILE_NO, requestBuilderParamsDTO.getUssdSessionMgmt().getMsisdnNumber());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	request.setRequestParamMap(requestParamMap);

	return request;
    }
}