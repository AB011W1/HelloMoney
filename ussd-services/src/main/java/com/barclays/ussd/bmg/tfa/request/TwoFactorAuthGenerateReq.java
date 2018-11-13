package com.barclays.ussd.bmg.tfa.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.TwoFactorQuestion;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdMenuBuilder;

public class TwoFactorAuthGenerateReq implements BmgBaseRequestBuilder {

    private static final String TWO_FACT_PARAM_MOBILE_NUMBER = "mobileNumber";
    @Autowired
    private UssdMenuBuilder ussdMenuBuilder;

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(TWO_FACT_PARAM_MOBILE_NUMBER, requestBuilderParamsDTO.getMsisdnNo());
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();

	int twoFAattemptCount = ussdSessionMgmt.getTwoFAattemptCount();
	if (twoFAattemptCount > USSDConstants.MAX_TWO_FACT_ATTEMPTS - 1) {
	    throw new USSDBlockingException(USSDExceptions.USSD_FAILED_2FA_LOGOFF.getBmgCode());
	}

	TwoFactorQuestion twoFactQuestion = (TwoFactorQuestion) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.TWO_FACTOR_INIT.getTranId());

	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>(5);
	}
	// Send all the question to the CRYPTO
	if (twoFactQuestion != null) {
	    userInputMap.put(USSDInputParamsEnum.TWO_FACTOR_GENERATE.getParamName(), twoFactQuestion.getQuesId());
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	} else {
	    throw new USSDBlockingException(USSDExceptions.USSD_TWO_FACT_EXCEP_FAIL.getUssdErrorCode());
	}

	requestParamMap.put(USSDInputParamsEnum.TWO_FACTOR_GENERATE.getParamName(), userInputMap.get(USSDInputParamsEnum.TWO_FACTOR_GENERATE
		.getParamName()));
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;

    }

    /* This method concatenates the questions with underscore as a separator */
    private StringBuffer concateQuestions(List<TwoFactorQuestion> questList) {
	StringBuffer questString = new StringBuffer();
	for (TwoFactorQuestion ques : questList) {
	    if (questString.length() != 0) {
		questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	    }
	    questString.append(ques.getQuesId());
	}
	return questString;
    }

}
