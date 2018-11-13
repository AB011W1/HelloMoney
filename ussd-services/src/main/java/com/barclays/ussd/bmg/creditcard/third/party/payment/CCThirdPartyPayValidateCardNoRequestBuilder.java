package com.barclays.ussd.bmg.creditcard.third.party.payment;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class CCThirdPartyPayValidateCardNoRequestBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings( {})
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_CC_NO.getParamName(), userInputMap
		.get(USSDInputParamsEnum.THIRD_PARTY_CC_PYMT_CC_NO.getParamName()));
	request.setRequestParamMap(requestParamMap);

	return request;
    }
}
