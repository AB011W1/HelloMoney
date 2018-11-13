package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class GhipsSaveBenefSubmitRequestBuilder implements BmgBaseRequestBuilder {

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO) {
		USSDBaseRequest request = new USSDBaseRequest();
		String txnRefNo = (String) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_SUBMIT.getTranId());
		Map<String, String> requestParamMap = new HashMap<String, String>();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_SUBMIT.getParamName(), txnRefNo);
		request.setRequestParamMap(requestParamMap);
		return request;
	}
}
