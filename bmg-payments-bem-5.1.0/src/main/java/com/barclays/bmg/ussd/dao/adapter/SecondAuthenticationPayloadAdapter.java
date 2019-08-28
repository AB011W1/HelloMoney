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
package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.Questionnaire.QuestionData;
import com.barclays.bem.Questionnaire.QuestionDataGroup;
import com.barclays.bem.Questionnaire.QuestionResponses;
import com.barclays.bem.UpdateIndividualCustQuestionnaireStatus.QuestionnaireDetailsType;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.QuesWithPosDTO;
import com.barclays.bmg.ussd.auth.service.request.SecondAuthenticationServiceRequest;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationPayloadAdapter {

    /**
     * @param WorkContext
     * @return QuestionnaireDetailsType
     */
    public QuestionnaireDetailsType adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	SecondAuthenticationServiceRequest request = (SecondAuthenticationServiceRequest) args[0];
	QuestionnaireDetailsType questionnaireDetailsType = new QuestionnaireDetailsType();

	QuesWithPosDTO quesWithPosDTO[] = request.getQuestionWithPositions();

	QuestionData questionData[] = new QuestionData[quesWithPosDTO.length];
	QuestionDataGroup questionDataGroup = new QuestionDataGroup();

	for (int i = 0; i < quesWithPosDTO.length; i++) {

	    if (!quesWithPosDTO[i].getQuesId().equals("") && !quesWithPosDTO[i].getAnsPos1().equals("") && !quesWithPosDTO[i].getAnsPos2().equals("")
		    && !quesWithPosDTO[i].getChallengeId().equals("")) {

		questionData[i] = new QuestionData();
		QuestionResponses questionResponses[] = new QuestionResponses[1];
		questionResponses[0] = new QuestionResponses();
		questionData[i].setQuestionId(quesWithPosDTO[i].getQuesId());
		questionResponses[0].setResponseId(quesWithPosDTO[i].getChallengeId());
		String positions = quesWithPosDTO[i].getAnsPos1() + "," + quesWithPosDTO[i].getAnsPos2();
		questionResponses[0].setText(positions);
		questionData[i].setQuestionResponses(questionResponses);

	    }

	}

	questionDataGroup.setQuestionData(questionData);

	if (request.getUserStatusInMCE() != null) {
	    questionnaireDetailsType.setCustomerNumber(request.getSingleCustViewId());
	} else {
	    questionnaireDetailsType.setMobileNumber(request.getMobileNumber());
	}

	questionnaireDetailsType.setQuestionDataGroup(questionDataGroup);

	return questionnaireDetailsType;
    }

}
