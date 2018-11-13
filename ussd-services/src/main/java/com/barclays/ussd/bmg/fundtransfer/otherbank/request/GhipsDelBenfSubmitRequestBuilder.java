package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTBeneficiary;

public class GhipsDelBenfSubmitRequestBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	String payeeIdInput = userInputMap.get(USSDInputParamsEnum.GHIPS_DELETE_BENEF_PAYEE.getParamName());
	List<OBAFTBeneficiary> bnfLst = (List<OBAFTBeneficiary>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.GHIPS_DELETE_BENEF_PAYEE.getTranId());
	String payeeId = bnfLst.get(Integer.parseInt(payeeIdInput) - 1).getPayId();
	userInputMap.clear();
	userInputMap.put(USSDInputParamsEnum.GHIPS_DELETE_BENEF_CONFIRM.getParamName(), payeeId);

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.putAll(userInputMap);
	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
