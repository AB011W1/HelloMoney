package com.barclays.ussd.bmg.chequeBook.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class ChequeBookExecuteRequestBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	List<String> txnRefNo = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.CHECK_BK_VALIDATE.getTranId());
	String txRefNo = StringUtils.EMPTY;
	if (txnRefNo != null && !txnRefNo.isEmpty()) {
	    txRefNo = txnRefNo.get(0);
	}
	Map<String, String> requestParamMap = new HashMap<String, String>(3);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode()); // Cheque boook Validation
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.CHECK_BK_CONFIRM.getParamName(), txRefNo);
	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
