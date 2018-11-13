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

public class GhipsSaveBenefEnterAmtRequestBuilder implements BmgBaseRequestBuilder {

	private static final String FT_BENF = "fundtxBenf";

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
	throws USSDNonBlockingException, USSDBlockingException {

		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		String branchInfo = (String) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
				USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_BENEF_INFO.getTranId());
		if(branchInfo!=null){
			String strArr[]=branchInfo.split("-");
			requestParamMap.put(USSDInputParamsEnum.SELFREG_BRANCH.getParamName(), strArr[0]);
			requestParamMap.put(USSDInputParamsEnum.SELFREG_ACCOUNT.getParamName(), strArr[1]);
		}
		requestParamMap.put(USSDInputParamsEnum.SELFREG_MOBILE.getParamName(), requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDInputParamsEnum.EXT_BANK_FT_SERVICE_NAME.getParamName(), FT_BENF);
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setRequestParamMap(requestParamMap);
		return request;
	}

}
