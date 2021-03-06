package com.barclays.ussd.bmg.groupwallet.requests;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class GroupWalletOptionsListRequestBuilder implements BmgBaseRequestBuilder{

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		// TODO Auto-generated method stub
		USSDBaseRequest request = new USSDBaseRequest();
    	Map<String, String> requestParamMap = new HashMap<String, String>();
    	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
    	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "PMT_MW_GW");
    	request.setRequestParamMap(requestParamMap);
    	return request;
	}

}
