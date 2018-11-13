package com.barclays.ussd.bmg.login;

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

public class VerifyUserRequestBuilder implements BmgBaseRequestBuilder {

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
	// set the default country, language and business id to the user profile
	ussdSessionMgmt.getUserProfile().setCountryCode(ussdSessionMgmt.getCountryCode());
	ussdSessionMgmt.getUserProfile().setBusinessId(ussdSessionMgmt.getBusinessId());

	List<TwoFactorQuestion> selfRegQuestList = ussdMenuBuilder.getSelfRegQuesList(ussdSessionMgmt.getUserProfile().getCountryCode(),
		ussdSessionMgmt.getUserProfile().getBusinessId());

	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>(5);
	}
	// Send all the question to the CRYPTO
	if (selfRegQuestList != null && !selfRegQuestList.isEmpty()) {
	    StringBuffer selfRegQuesions = concateQuestions(selfRegQuestList);
	    requestParamMap.put(USSDInputParamsEnum.USER_VERIFY_QUESTION.getParamName(), selfRegQuesions.toString());
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	} else {
	    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}
	// if (1 == 1) {
	// throw new USSDBlockingException(USSDExceptions.USSD_MOBILE_NOT_REG.getBmgCode());
	// }
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
