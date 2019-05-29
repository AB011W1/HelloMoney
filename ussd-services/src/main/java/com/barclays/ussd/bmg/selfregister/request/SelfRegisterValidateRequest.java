package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bean.TwoFactorQuestion;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

public class SelfRegisterValidateRequest implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }

    /*
     * This method concatenates the questions & answers with underscore as a separator
     */
    private StringBuffer concateQuesAndAnswer(List<TwoFactorQuestion> questList, Map<String, String> userInputMap) {
	return null;}
}
