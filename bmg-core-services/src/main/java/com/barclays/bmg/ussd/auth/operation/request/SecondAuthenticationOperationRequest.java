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

package com.barclays.bmg.ussd.auth.operation.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.QuesWithPosDTO;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationOperationRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationOperationRequest extends RequestContext {

    private String questionWithPositions;

    private QuesWithPosDTO[] quesAndPosFromChallenge;

    private String mobileNo;

    private String userStatusInMCE;

    private String singleCustViewId;

    private String accountNo;

    private String branch;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public String getBranch() {
	return branch;
    }

    public void setBranch(String branch) {
	this.branch = branch;
    }

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

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    public String getQuestionWithPositions() {
	return questionWithPositions;
    }

    public void setQuestionWithPositions(String questionWithPositions) {
	this.questionWithPositions = questionWithPositions;
    }

    public QuesWithPosDTO[] getQuesAndPosFromChallenge() {
	return quesAndPosFromChallenge;
    }

    public void setQuesAndPosFromChallenge(QuesWithPosDTO[] quesAndPosFromChallenge) {
	this.quesAndPosFromChallenge = quesAndPosFromChallenge;
    }

}
