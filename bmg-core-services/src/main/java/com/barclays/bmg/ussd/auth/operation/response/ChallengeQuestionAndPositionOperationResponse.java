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
package com.barclays.bmg.ussd.auth.operation.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.QuesWithPosDTO;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionOperationResponse.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b>
 * </br> ************************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 8711683942384033843L;
    private String challengeId;
    private String questionId;
    private String questionPos1;
    private String questionPos2;
    private QuesWithPosDTO[] questionWithPositions;

    public QuesWithPosDTO[] getQuestionWithPositions() {
	QuesWithPosDTO[] que = questionWithPositions;
	return que;
    }

    public void setQuestionWithPositions(QuesWithPosDTO[] questionWithPositions) {
	QuesWithPosDTO[] que = questionWithPositions;
	this.questionWithPositions = que;
    }

    /**
     * @return the challengeId
     */
    public String getChallengeId() {
	return challengeId;
    }

    /**
     * @param challengeId
     *            the challengeId to set
     */
    public void setChallengeId(String challengeId) {
	this.challengeId = challengeId;
    }

    /**
     * @return the questionId
     */
    public String getQuestionId() {
	return questionId;
    }

    /**
     * @param questionId
     *            the questionId to set
     */
    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

    /**
     * @return the questionPos1
     */
    public String getQuestionPos1() {
	return questionPos1;
    }

    /**
     * @param questionPos1
     *            the questionPos1 to set
     */
    public void setQuestionPos1(String questionPos1) {
	this.questionPos1 = questionPos1;
    }

    /**
     * @return the questionPos2
     */
    public String getQuestionPos2() {
	return questionPos2;
    }

    /**
     * @param questionPos2
     *            the questionPos2 to set
     */
    public void setQuestionPos2(String questionPos2) {
	this.questionPos2 = questionPos2;
    }
}