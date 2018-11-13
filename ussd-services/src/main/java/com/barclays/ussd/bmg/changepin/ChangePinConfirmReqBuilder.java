package com.barclays.ussd.bmg.changepin;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;

public class ChangePinConfirmReqBuilder implements BmgBaseRequestBuilder {
    private static final String MOBILE_NO = "mobileNo";
    private static final String USER_STATUS_IN_MCE = "usrStatInMCE";
    //Forgot Pin Change
    private static final String FORGOT_PIN_FLOW_FLAG = "forgotPinFlag";

    /** The Constant LOGGER. */

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>(5);
	    requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap);
	}

	Map<String, String> requestParamMap = new HashMap<String, String>();
	//Forgot Pin Change
	String tranDataId=requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
		requestParamMap.put(FORGOT_PIN_FLOW_FLAG,"FORGOTPIN");
		userInputMap.put("oldPass","1234");
	}
	requestParamMap.put(USER_STATUS_IN_MCE,requestBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getUsrSta());
	requestParamMap.putAll(userInputMap);
	requestParamMap.put(MOBILE_NO, requestBuilderParamsDTO.getUssdSessionMgmt().getMsisdnNumber());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);



	request.setRequestParamMap(requestParamMap);

	return request;
    }
}
