package com.barclays.ussd.bmg.user.migration;

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

public class UserMigrationDOBRequestBuilder implements BmgBaseRequestBuilder {
    @Autowired
    private UssdMenuBuilder ussdMenuBuilder;

    private static final String MOBILE_NUMBER = "mobileNumber";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	// set the default country, language and business id to the user profile
	ussdSessionMgmt.getUserProfile().setCountryCode(ussdSessionMgmt.getCountryCode());
	ussdSessionMgmt.getUserProfile().setBusinessId(ussdSessionMgmt.getBusinessId());
	List<TwoFactorQuestion> selfRegQuestList = ussdMenuBuilder.getSelfRegQuesList(ussdSessionMgmt.getUserProfile().getCountryCode(),
		ussdSessionMgmt.getUserProfile().getBusinessId());
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	int twoFAattemptCount = ussdSessionMgmt.getTwoFAattemptCount();
	if (twoFAattemptCount > USSDConstants.MAX_TWO_FACT_ATTEMPTS - 1) {
	    throw new USSDBlockingException(USSDExceptions.USSD_FAILED_2FA_LOGOFF.getBmgCode());
	}

	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>(5);
	}
	if (selfRegQuestList != null && !selfRegQuestList.isEmpty()) {
	    // TwoFactorQuestion twoFactQuestion = questList.get(USSDUtils.generateRandomNumber(questList.size()));
	    StringBuffer selfRegQuesions = concateQuestions(selfRegQuestList);

	    requestParamMap.put(USSDInputParamsEnum.USER_MIGRATION_DOB.getParamName(), selfRegQuesions.toString());
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	} else {
	    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(MOBILE_NUMBER, requestBuilderParamsDTO.getMsisdnNo());

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
