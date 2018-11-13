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
package com.barclays.bmg.ussd.auth.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.QuesWithPosDTO;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationServiceRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationServiceRequest extends RequestContext {

    private String questionId;

    private String position1Answer;

    private String position2Answer;

    private String mobileNumber;

    private String userStatusInMCE;

    private String singleCustViewId;

    public String getUserStatusInMCE() {
	return userStatusInMCE;
    }

    public void setUserStatusInMCE(String userStatusInMCE) {
	this.userStatusInMCE = userStatusInMCE;
    }

    /**
     * @return the singleCustViewId
     */
    public String getSingleCustViewId() {
	return singleCustViewId;
    }

    /**
     * @param singleCustViewId
     *            the singleCustViewId to set
     */
    public void setSingleCustViewId(String singleCustViewId) {
	this.singleCustViewId = singleCustViewId;
    }

    public String getMobileNumber() {
	return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    private QuesWithPosDTO[] questionWithPositions;

    public QuesWithPosDTO[] getQuestionWithPositions() {
	QuesWithPosDTO[] que = questionWithPositions;
	return que;
    }

    public void setQuestionWithPositions(QuesWithPosDTO[] questionWithPositions) {
	QuesWithPosDTO[] que = questionWithPositions;
	this.questionWithPositions = que;
    }

    public String getQuestionId() {
	return questionId;
    }

    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

    public String getPosition1Answer() {
	return position1Answer;
    }

    public void setPosition1Answer(String position1Answer) {
	this.position1Answer = position1Answer;
    }

    public String getPosition2Answer() {
	return position2Answer;
    }

    public void setPosition2Answer(String position2Answer) {
	this.position2Answer = position2Answer;
    }

}
