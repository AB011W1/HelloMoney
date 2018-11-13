package com.barclays.ussd.bmg.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class KitsDeregisterSubmitRequestBuilder implements BmgBaseRequestBuilder {
	@SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {


		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

		//Mobile Number input
		requestParamMap.put("mobileNo",requestBuilderParamsDTO.getMsisdnNo());

		String primaryFlag=(String)txSessions.get("primaryFlag");
    	if("Yes".equals(primaryFlag))
    	{
    		requestParamMap.put("primaryFlag","true");
    	}else if("No".equals(primaryFlag))
    	{
    		requestParamMap.put("primaryFlag","false");
    	}
		requestParamMap.put("accountNo",(String)txSessions.get("accountNumber"));


    	//Activity Id input
		requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "KITS_DEREGISTRATION");
		request.setRequestParamMap(requestParamMap);
		return request;


    }
}
