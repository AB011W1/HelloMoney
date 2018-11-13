package com.barclays.ussd.bmg.user.migration;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class UserMigrationVerifyDOBRequestBuilder implements BmgBaseRequestBuilder {
    private static final int SECOND_ANSWER = 1;
    private static final int FIRST_ANSWER = 0;
    private static final String DATE_OF_BIRTH = "Q5";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	int twoFAattemptCount = ussdSessionMgmt.getTwoFAattemptCount();
	if (twoFAattemptCount > USSDConstants.MAX_TWO_FACT_ATTEMPTS - 1) {
	    throw new USSDBlockingException(USSDExceptions.USSD_FAILED_2FA_LOGOFF.getBmgCode());
	} else {
	    twoFAattemptCount++;
	    ussdSessionMgmt.setTwoFAattemptCount(twoFAattemptCount);
	}

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	String userInput = userInputMap.get(USSDInputParamsEnum.USER_MIGRATION_DOB.getParamName());

	StringBuffer questString = new StringBuffer();
	questString.append(DATE_OF_BIRTH);
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(userInput.charAt(FIRST_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(userInput.charAt(SECOND_ANSWER));
	String challengeID = "_"
		+ String.valueOf(ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.USER_MIGRATION_CHALLENGE_ID.getParamName()));
	requestParamMap.put(USSDInputParamsEnum.USER_MIGRATION_VERIFY_DOB.getParamName(), questString.append(challengeID).toString());
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }
}