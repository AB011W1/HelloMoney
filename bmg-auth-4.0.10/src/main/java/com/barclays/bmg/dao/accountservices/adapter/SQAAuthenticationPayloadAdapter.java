package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationVerify.Request;
import com.barclays.grcb.mcfe.ssc.authentication.entity.AuthenticationType;
import com.barclays.grcb.mcfe.ssc.authentication.entity.SQAInfo;
import com.barclays.grcb.mcfe.ssc.authentication.entity.SecretQuestion;

public class SQAAuthenticationPayloadAdapter {

    public Request adaptPayLoad(WorkContext workContext) {
	Request requestBody = new Request();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AuthenticationServiceRequest authenticationServiceRequest = (AuthenticationServiceRequest) args[0];

	requestBody.setAuthenticationType(AuthenticationType.SQA);
	SQAInfo sqaInfo = new SQAInfo();
	SecretQuestion question = new SecretQuestion();

	question.setQuestionID(authenticationServiceRequest.getQuestionId());
	sqaInfo.setQuestion(question);
	sqaInfo.setAnswer(authenticationServiceRequest.getSqa());
	requestBody.setSQAInfo(sqaInfo);

	return requestBody;
    }
}
