package com.barclays.ussd.bmg.airtime.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class AirtimeTopUpBillerListReqBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getParamName(), USSDConstants.AIRTIME_PAY);
	
	//Data bundle change
	String transNodeId;
	if(null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails()  && 
			null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap() &&
			null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("TransNodeId"))
	{
		transNodeId = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("TransNodeId");
		if(transNodeId.equals("ussd0.10GHBRB"))
			requestParamMap.put(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getParamName(), "DB");
	}
	
	
	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
