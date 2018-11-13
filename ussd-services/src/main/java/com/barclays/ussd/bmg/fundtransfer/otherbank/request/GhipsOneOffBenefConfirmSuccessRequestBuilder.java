package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class GhipsOneOffBenefConfirmSuccessRequestBuilder implements BmgBaseRequestBuilder {

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		USSDBaseRequest request = new USSDBaseRequest();
		String txnRefNo = String.valueOf(requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.GHIPS_FUND_TRANSFER_NICK_NAME_SUBMIT.getTranId()));
		Map<String, String> requestParamMap = new HashMap<String, String>();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDInputParamsEnum.GHIPS_FUND_TRANSFER_NICK_NAME_SUBMIT.getParamName(), txnRefNo);
		request.setRequestParamMap(requestParamMap);
		return request;
	}

}
