package com.barclays.ussd.bmg.selfregister.request;

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

public class SelfRegisterGetQuestionRequest implements BmgBaseRequestBuilder {

    @Autowired
    UssdMenuBuilder ussdMenuBuilder;

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	List<TwoFactorQuestion> questList = ussdMenuBuilder.getTwoFactQuesList(ussdSessionMgmt.getUserProfile().getCountryCode(), ussdSessionMgmt
		.getUserProfile().getBusinessId());

	if (questList != null && !questList.isEmpty()) {
	    StringBuffer queLst = concateQuestions(questList);
	    requestParamMap.put(USSDInputParamsEnum.SELFREG_GET_QUESTION.getParamName(), queLst.toString());
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_IDV_FAILED.getUssdErrorCode());
	}

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

    /**
     * @return the ussdMenuBuilder
     */
    public UssdMenuBuilder getUssdMenuBuilder() {
	return ussdMenuBuilder;
    }

    /**
     * @param ussdMenuBuilder
     *            the ussdMenuBuilder to set
     */
    public void setUssdMenuBuilder(UssdMenuBuilder ussdMenuBuilder) {
	this.ussdMenuBuilder = ussdMenuBuilder;
    }

}
