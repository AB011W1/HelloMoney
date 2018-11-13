package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;

public class GhipsSaveBenefOtherBankBenefInfoRequestBuilder implements BmgBaseRequestBuilder {

	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
		USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
		List<BeneData> payeeList = (List<BeneData>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_TO_AC.getTranId());
		String payId="";
		if (payeeList != null && !payeeList.isEmpty()) {
			Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
			BeneData beneData=payeeList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_TO_AC.getParamName()))-1);
			payId=beneData.getPayId();
		}

		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_TO_AC.getParamName(), payId);
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		ussdBaseRequest.setRequestParamMap(requestParamMap);
		return ussdBaseRequest;
	}
}
