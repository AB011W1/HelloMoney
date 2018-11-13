package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class SelfRegisterExecuteRequest implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	if (StringUtils.isEmpty(ussdSessionMgmt.getUnregCustLangPref())) {
	    ussdSessionMgmt.setUnregCustLangPref("EN"); // set default language as EN. Added where we will not have preferred language selection
	}
	requestParamMap.put(USSDInputParamsEnum.SELFREG_LANGUAGE.getParamName(), ussdSessionMgmt.getUnregCustLangPref());
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }

}
