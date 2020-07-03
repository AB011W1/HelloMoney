/**
 * DelBillersValidateRequest.java
 */
package com.barclays.ussd.bmg.airtime.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

/**
 * @author BTCI
 *
 */
public class AirtimeTopUpBenfDtlsReqBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = null;
	if (requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap() == null) {
	    userInputMap = new HashMap<String, String>();
	    requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap);
	}

	userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	String userInput = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getParamName());
	String payeeId = "";
	if (requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() != null
		&& requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getTranId()) != null) {
	    List<Beneficiery> lstBenef = (List<Beneficiery>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getTranId());
	    if (!lstBenef.isEmpty()) {
		Beneficiery bene = lstBenef.get(Integer.parseInt(userInput) - 1);
		payeeId = bene.getPayId();
	    }
	}
	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getParamName(), payeeId);
	requestParamMap.put("BP_AT_WT", USSDConstants.AIRTIME_PAY);
	
	//Data Bundle change
		String transNodeId;
		if(null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails()  && 
				null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap() &&
				null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("TransNodeId"))
		{
			transNodeId = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("TransNodeId");
			if(transNodeId.equals("ussd0.10GHBRB"))
				requestParamMap.put("BP_AT_WT", "DB");
		}
	
	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
