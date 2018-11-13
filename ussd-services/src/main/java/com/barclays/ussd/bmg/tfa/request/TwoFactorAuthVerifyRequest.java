package com.barclays.ussd.bmg.tfa.request;

import java.util.HashMap;
import java.util.Map;

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

public class TwoFactorAuthVerifyRequest implements BmgBaseRequestBuilder {
    private static final String TWO_FACT_PARAM_MOBILE_NUMBER = "mobileNumber";

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
	} else {
	    twoFAattemptCount++;
	    ussdSessionMgmt.setTwoFAattemptCount(twoFAattemptCount);
	}

	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	String userInputAnswer = requestBuilderParamsDTO.getUserInput();

	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>(5);
	}
	userInputMap.put(USSDInputParamsEnum.TWO_FACTOR_VERIFY.getParamName(), getQuestionListInBMGFormat(ussdSessionMgmt, userInputAnswer));

	requestParamMap.put(USSDInputParamsEnum.TWO_FACTOR_VERIFY.getParamName(), userInputMap.get(USSDInputParamsEnum.TWO_FACTOR_VERIFY
		.getParamName()));
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }

    private String getQuestionListInBMGFormat(USSDSessionManagement ussdSessionMgmt, String userInputAnswer) {
	// SQA=QuestionID1_Ans1_ Ans 2_ChallengId1|QuestionID2_ Ans 1_ Ans 2_ChallengId2
	// E.g. SQA=517_1_4_54674|511_1_4_54684
	StringBuffer sqa = new StringBuffer();

	TwoFactorQuestion twoFactorQn = (TwoFactorQuestion) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.TWO_FACTOR_GENERATE.getTranId());
	if (twoFactorQn != null) {
	    sqa.append(twoFactorQn.getQuesId());
	    sqa.append(USSDConstants.UNDERSCORE_SEPERATOR);
	    sqa.append(userInputAnswer.charAt(0));
	    sqa.append(USSDConstants.UNDERSCORE_SEPERATOR);
	    sqa.append(userInputAnswer.charAt(1));
	    sqa.append(USSDConstants.UNDERSCORE_SEPERATOR);
	    sqa.append(twoFactorQn.getChallengeId());
	}
	return sqa.toString();
    }

}
