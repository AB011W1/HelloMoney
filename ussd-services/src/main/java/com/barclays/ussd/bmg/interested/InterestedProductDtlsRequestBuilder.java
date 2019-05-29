package com.barclays.ussd.bmg.interested;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class InterestedProductDtlsRequestBuilder implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	// requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), EXTRA_INSTALLMENT_OFFER_ACTIVITY_ID);
	requestParamMap.put(USSDInputParamsEnum.BUSINESS_ID.getParamName(), requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
	// requestParamMap.put(USSDInputParamsEnum.AUDIT_REQUIRED.getParamName(), "Y");
	// requestParamMap.put(USSDInputParamsEnum.SELECTED_RESTAURANT_NAME.getParamName(), selectePartnerName);

	request.setRequestParamMap(requestParamMap);
	return request;
    }
}