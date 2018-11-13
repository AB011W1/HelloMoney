package com.barclays.ussd.bmg.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class AuthenticateUserRequestBuilder implements BmgBaseRequestBuilder {
    private static final String PIN_STATUS_IN_CRYPTO = "pinStatInCrypto";
    private static final String USER_STATUS_IN_MCE = "usrStatInMCE";
    private static final String CASA_ACCOUNT_TYPE = "CH";
    private static final Logger LOGGER = Logger.getLogger(AuthenticateUserRequestBuilder.class);

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("Authenticating the user!");
	}

	USSDBaseRequest request = new USSDBaseRequest();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	ussdSessionMgmt.setFirstLoginOldPin(userInputMap.get(USSDInputParamsEnum.USER_AUTHENTICATION.getParamName()));

	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.putAll(userInputMap);
	requestParamMap.put(USSDInputParamsEnum.USER_AUTHENTICATION_USER_NAME.getParamName(), requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USER_STATUS_IN_MCE, ussdSessionMgmt.getUserProfile().getUsrSta());
	requestParamMap.put(PIN_STATUS_IN_CRYPTO, ussdSessionMgmt.getUserProfile().getPinSta());
	requestParamMap.put(USSDInputParamsEnum.RETRIVE_ACCOUNT_TYPE.getParamName(), CASA_ACCOUNT_TYPE);

	request.setRequestParamMap(requestParamMap);

	return request;

    }
}
