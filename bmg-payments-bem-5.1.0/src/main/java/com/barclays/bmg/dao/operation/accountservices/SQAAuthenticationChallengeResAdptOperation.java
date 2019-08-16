package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bmg.constants.AuthServiceResponseCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.AuthenticationDTO;
import com.barclays.bmg.dto.SecretQuestionDTO;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationChallenge.AuthenticationChallengeResponse;
import com.barclays.grcb.mcfe.ssc.authentication.entity.SecretQuestion;

public class SQAAuthenticationChallengeResAdptOperation {

    public AuthenticationServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	AuthenticationChallengeResponse response = (AuthenticationChallengeResponse) obj;

	AuthenticationDTO authenticationDTO = new AuthenticationDTO();
	if (response.getResponseHeader().getServiceResStatus().getServiceResCode().equals("SEC00007")) {
	    authenticationDTO.setNeedResetToken(true);
	} else {
	    authenticationDTO.setNeedResetToken(false);
	    SecretQuestion secretQuestion = response.getSecretQuestion();
	    SecretQuestionDTO secretQuestionDTO = new SecretQuestionDTO();
	    secretQuestionDTO.setQuestionId(secretQuestion.getQuestionID());
	    secretQuestionDTO.setMessageValue(secretQuestion.getQuestionText());

	    authenticationDTO.setChallengeId(secretQuestionDTO);
	}

	/**
	 * prepare authentication service response
	 */
	AuthenticationServiceResponse authenticationServiceResponse = new AuthenticationServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AuthenticationServiceRequest authenticationServiceRequest = (AuthenticationServiceRequest) args[0];

	authenticationServiceResponse.setContext(authenticationServiceRequest.getContext());

	if (AuthServiceResponseCodeConstant.SUCCESS_CODE.equals(response.getResponseHeader().getServiceResStatus().getServiceResCode())) {
	    authenticationDTO.setAuthenticated(true);
	} else {
	    authenticationDTO.setAuthenticated(false);
	}

	authenticationServiceResponse.setAuthenticationDTO(authenticationDTO);

	return authenticationServiceResponse;
    }

}
