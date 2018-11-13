package com.barclays.ussd.bmg.changepin;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class FirstLoginChangePinConfirmReqBuilder implements BmgBaseRequestBuilder {
    private static final String MOBILE_NO = "mobileNo";
    private static final String USER_STATUS_IN_MCE = "usrStatInMCE";
    private static final String CASA_ACCOUNT_TYPE = "CH";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FirstLoginChangePinConfirmReqBuilder.class);

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	userInputMap.put(MOBILE_NO, requestBuilderParamsDTO.getUssdSessionMgmt().getMsisdnNumber());

	Map<String, String> requestParamMap = new HashMap<String, String>();
	userInputMap.put(USSDInputParamsEnum.FIRST_LOGIN_CHNG_PIN_OLD_PASS.getParamName(), ussdSessionMgmt.getFirstLoginOldPin());
	requestParamMap.putAll(userInputMap);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	requestParamMap.put(USER_STATUS_IN_MCE, ussdSessionMgmt.getUserProfile().getUsrSta());
	requestParamMap.put(USSDInputParamsEnum.RETRIVE_ACCOUNT_TYPE.getParamName(), CASA_ACCOUNT_TYPE);

	request.setRequestParamMap(requestParamMap);

	return request;
    }
}
