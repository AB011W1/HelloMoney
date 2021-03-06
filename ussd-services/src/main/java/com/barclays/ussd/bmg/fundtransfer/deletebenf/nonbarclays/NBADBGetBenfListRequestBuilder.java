package com.barclays.ussd.bmg.fundtransfer.deletebenf.nonbarclays;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;

public class NBADBGetBenfListRequestBuilder implements BmgBaseRequestBuilder {
    private static final String DEL_BENF = "delBenf";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.NON_BARC_DEL_BENF_SERVICE_NAME.getParamName(), DEL_BENF);
	request.setRequestParamMap(requestParamMap);
	return request;
    }
}