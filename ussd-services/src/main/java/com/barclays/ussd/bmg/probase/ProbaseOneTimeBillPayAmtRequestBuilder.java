package com.barclays.ussd.bmg.probase;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

public class ProbaseOneTimeBillPayAmtRequestBuilder implements BmgBaseRequestBuilder{
	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {

		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		BillersListDO biller = (BillersListDO) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDConstants.PROBASE_BILLER_INFO);

		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

		if (null != userInputMap) {
			requestParamMap.put(USSDConstants.GEPG_BMG_CONTROL_NUMBER_PARAM_NAME, userInputMap.get("billerRefNumber"));
		}
		if(null != biller){
				requestParamMap.put(USSDConstants.GEPG_BMG_BILLER_ID_PARAM_NAME, biller.getBillerId());
				requestParamMap.put(USSDConstants.GEPG_BMG_BILLER_NM_PARAM_NAME, biller.getBillerNm());
		}
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		request.setRequestParamMap(requestParamMap);

		return request;
	}

}
