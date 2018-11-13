package com.barclays.ussd.bmg.factory.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

public class NonBMGRequestBuilder implements BmgBaseRequestBuilder
{
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO)
	{
		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> requestParamMap = new HashMap<String, String>();

		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());

		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		request.setSerVer(USSDConstants.BMG_SERVICE_VERSION_VALUE);

		requestParamMap.put(USSDConstants.HEADER_ID_PARAM_NAME, requestBuilderParamsDTO.getHeaderId());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		request.setRequestParamMap(requestParamMap);

		return request;
	}
}
