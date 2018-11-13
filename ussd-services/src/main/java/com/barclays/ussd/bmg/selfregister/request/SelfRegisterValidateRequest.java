package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.utils.UssdMenuBuilder;

public class SelfRegisterValidateRequest implements BmgBaseRequestBuilder {

    private static final int SECOND_ANSWER = 1;
    private static final int FIRST_ANSWER = 0;
    @Autowired
    UssdMenuBuilder ussdMenuBuilder;

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	String userInput = requestBuilderParamsDTO.getUserInput();
	if (StringUtils.isNotBlank(userInput)) {
	    Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	//    userInputMap.put(USSDInputParamsEnum.SELFREG_QUESTION_FOUR.getTranId(), userInput);
	}

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	List<TwoFactorQuestion> questList = ussdMenuBuilder.getTwoFactQuesList(ussdSessionMgmt.getUserProfile().getCountryCode(), ussdSessionMgmt
		.getUserProfile().getBusinessId());

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	if (questList != null && !questList.isEmpty()) {
	    StringBuffer queLst = concateQuesAndAnswer(questList, userInputMap);
	   // requestParamMap.put(USSDInputParamsEnum.SELFREG_VALIDATION.getParamName(), queLst.toString());
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_IDV_FAILED.getUssdErrorCode());
	}

	return ussdBaseRequest;
    }

    /*
     * This method concatenates the questions & answers with underscore as a separator
     */
    private StringBuffer concateQuesAndAnswer(List<TwoFactorQuestion> questList, Map<String, String> userInputMap) {/*
	String ans1 = userInputMap.get(USSDInputParamsEnum.SELFREG_QUESTION_ONE.getTranId());
	String ans2 = userInputMap.get(USSDInputParamsEnum.SELFREG_QUESTION_TWO.getTranId());
	String ans3 = userInputMap.get(USSDInputParamsEnum.SELFREG_QUESTION_THREE.getTranId());
	String ans4 = userInputMap.get(USSDInputParamsEnum.SELFREG_QUESTION_FOUR.getTranId());

	StringBuffer questString = new StringBuffer();

	TwoFactorQuestion ques1 = questList.get(USSDConstants.FIRST_QUESTION_INDEX);
	TwoFactorQuestion ques2 = questList.get(USSDConstants.SECOND_QUESTION_INDEX);
	TwoFactorQuestion ques3 = questList.get(USSDConstants.THIRD_QUESTION_INDEX);
	TwoFactorQuestion ques4 = questList.get(USSDConstants.FOURTH_QUESTION_INDEX);

	questString.append(ques1.getQuesId());
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans1.charAt(FIRST_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans1.charAt(SECOND_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ques1.getChallengeId());
	questString.append(USSDConstants.PIPE_SEPERATOR);

	questString.append(ques2.getQuesId());
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans2.charAt(FIRST_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans2.charAt(SECOND_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ques2.getChallengeId());
	questString.append(USSDConstants.PIPE_SEPERATOR);

	questString.append(ques3.getQuesId());
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans3.charAt(FIRST_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans3.charAt(SECOND_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ques3.getChallengeId());
	questString.append(USSDConstants.PIPE_SEPERATOR);

	questString.append(ques4.getQuesId());
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans4.charAt(FIRST_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ans4.charAt(SECOND_ANSWER));
	questString.append(USSDConstants.UNDERSCORE_SEPERATOR);
	questString.append(ques4.getChallengeId());

	return questString;
    */return null;}
}
