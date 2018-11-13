package com.barclays.ussd.bmg.fundtransfer.ownfundtransfer.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class OwnFundTransferConfirmRequestBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();

	List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.OLAFT_VALIDATE.getTranId());

	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDInputParamsEnum.OLAFT_CONFIRM.getParamName(), txnRefNoLst.get(Integer
		.parseInt(requestBuilderParamsDTO.getUserInput()) - 1));

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
