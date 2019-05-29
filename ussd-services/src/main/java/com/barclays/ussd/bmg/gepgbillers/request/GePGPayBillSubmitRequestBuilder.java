/**
 * PayBillConfirm.java
 */
package com.barclays.ussd.bmg.gepgbillers.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI This class displays final confirmation screen
 *
 */
public class GePGPayBillSubmitRequestBuilder implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

		String txnRefNo = (String) txSessions.get(USSDInputParamsEnum.GePG_PAY_BILLS_SUBMIT.getParamName());
		requestParamMap.put(USSDInputParamsEnum.GePG_PAY_BILLS_SUBMIT.getParamName(), txnRefNo);

		ussdBaseRequest.setRequestParamMap(requestParamMap);
		return ussdBaseRequest;
	}

}