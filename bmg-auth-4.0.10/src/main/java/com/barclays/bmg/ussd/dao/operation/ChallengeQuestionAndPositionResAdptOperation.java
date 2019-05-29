/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.ussd.dao.operation;

import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.bem.Questionnaire.QuestionData;
import com.barclays.bem.Questionnaire.QuestionDataGroup;
import com.barclays.bem.Questionnaire.QuestionResponses;
import com.barclays.bem.RetrieveIndividualCustQuestionnaire.Questionnaire;
import com.barclays.bem.RetrieveIndividualCustQuestionnaire.RetrieveDataResponse;
import com.barclays.bem.RetrieveIndividualCustQuestionnaire.RetrieveIndividualCustQuestionnaireResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.dto.QuesWithPosDTO;
import com.barclays.bmg.ussd.auth.service.request.ChallengeQuestionAndPositionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.ChallengeQuestionAndPositionServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionResAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b>
 * </br> **********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionResAdptOperation extends AbstractResAdptOperationAcct {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ChallengeQuestionAndPositionResAdptOperation.class);

    private boolean isCryptoCall(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	ChallengeQuestionAndPositionServiceRequest challengeQuestionAndPositionServiceRequest = (ChallengeQuestionAndPositionServiceRequest) args[0];
	Context context = challengeQuestionAndPositionServiceRequest.getContext();
	Map<String, Object> contextMap = context.getContextMap();
	String cryptoCall = (String) contextMap.get("SQAEnabled");
	LOGGER.debug("cryptoCall value Generate Challenge Question" + cryptoCall);
	if (cryptoCall.equalsIgnoreCase("y")) {
	    return true;
	}
	return false;

    }

    /**
     * This method adaptResponseforAnswer has the purpose to adapt response for fetching the answer positions.
     * 
     * @param WorkContext
     * @param Object
     * @return ChallengeQuestionAndPositionServiceResponse
     */
    public ChallengeQuestionAndPositionServiceResponse adaptResponseforAnswer(WorkContext workContext, Object obj) {

	ChallengeQuestionAndPositionServiceResponse response = new ChallengeQuestionAndPositionServiceResponse();
	RetrieveIndividualCustQuestionnaireResponse bemResponse = (RetrieveIndividualCustQuestionnaireResponse) obj;

	RetrieveDataResponse retrieveDataResponse = null;
	Questionnaire questionnaire = null;
	String resCde = "";

	// if (isCryptoCall(workContext)) {
	//
	// LOGGER.debug("Intering into Generate Challenge Question. In class ChallengeQuestion Adapter.");
	// LOGGER.debug("Crypto Stub Start for Generate Challenge Question");
	//
	// QuesWithPosDTO quesWithPosDTOArr[] = new QuesWithPosDTO[1];
	// quesWithPosDTOArr[0]= new QuesWithPosDTO("Q1","1","1","1");
	//
	// response.setQuestionWithPositions(quesWithPosDTOArr);
	// response.setSuccess(true);
	// response.setResCde("00000");
	// return response;
	// }

	if (bemResponse != null) {
	    retrieveDataResponse = bemResponse.getRetrieveDataResponse();
	    resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());
	}

	if (retrieveDataResponse != null) {
	    questionnaire = retrieveDataResponse.getQuestionnaire();
	}

	com.barclays.bem.Questionnaire.Questionnaire ques[];

	if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde) && questionnaire != null) {

	    ques = questionnaire.getQuestionnaire();

	    if (ques != null && ques.length != 0) {

		for (int i = 0; i < ques.length; i++) {

		    QuestionDataGroup questionDataGroup[] = ques[i].getQuestionDataGroup();

		    if (questionDataGroup != null && questionDataGroup.length != 0) {

			for (int j = 0; j < questionDataGroup.length; j++) {

			    QuestionData questionData[] = questionDataGroup[j].getQuestionData();

			    if (questionData != null && questionData.length != 0) {

				QuesWithPosDTO quesWithPosDTO[] = new QuesWithPosDTO[questionData.length];

				for (int k = 0; k < questionData.length; k++) {
				    QuestionResponses questionResponses[] = questionData[k].getQuestionResponses();

				    if (questionResponses != null && questionResponses.length != 0) {

					for (int m = 0; m < questionResponses.length; m++) {

					    quesWithPosDTO[k] = new QuesWithPosDTO("", "", "", "");

					    if (questionResponses[m].getText() != null && !questionResponses[m].getText().equals("")) {
						String challengePos[] = questionResponses[m].getText().split(",");
						if (challengePos.length == 2) {
						    quesWithPosDTO[k].setAnsPos1(challengePos[0]);
						    quesWithPosDTO[k].setAnsPos2(challengePos[1]);
						}
					    }

					    quesWithPosDTO[k].setChallengeId(questionResponses[m].getResponseId());
					    quesWithPosDTO[k].setQuesId(questionData[k].getQuestionId());

					}
				    }

				}
				response.setQuestionWithPositions(quesWithPosDTO);
			    }

			}
		    }
		}
	    }
	    response.setSuccess(true);
	    response.setResCde(resCde);
	} else {
	    response.setSuccess(false);
	    response.setResCde(resCde);
	}

	return response;
    }

}
