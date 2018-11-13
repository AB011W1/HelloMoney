package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationQuestion;

public class SelfRegisterChallengeAnsRequest implements BmgBaseRequestBuilder {
    private static final int SECOND_ANSWER = 1;
    private static final int FIRST_ANSWER = 0;
    private static final String DATE_OF_BIRTH = "Q5";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
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
	// String userInput = userInputMap.get(USSDInputParamsEnum.SELFREG_ANSWER_ONE.getParamName());
	String userInput = userInputMap.get(USSDInputParamsEnum.SELFREG_GET_QUESTION.getParamName());

	StringBuffer questString = new StringBuffer();

	//Self Registration CR for Kenya: New Question to ask National ID digits
	if(USSDConstants.BUSINESS_ID_KEBRB.equals(ussdSessionMgmt.getBusinessId())){

		SelfRegistrationQuestion selfRegistrationQuestion = (SelfRegistrationQuestion) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.SELFREG_GET_QUESTION.getTranId());
		questString.append(selfRegistrationQuestion.getQuesId());
	}else{
		questString.append(DATE_OF_BIRTH);
	}

	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(userInput.charAt(FIRST_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(userInput.charAt(SECOND_ANSWER));
	String challengeID = "_" + String.valueOf(ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.SELFREG_CHALLENGE_ID.getParamName()));
	requestParamMap.put(USSDInputParamsEnum.SELFREG_ANSWER_ONE.getParamName(), questString.append(challengeID).toString());
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }
}
