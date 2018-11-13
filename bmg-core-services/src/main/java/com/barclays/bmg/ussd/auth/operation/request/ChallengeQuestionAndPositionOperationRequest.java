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

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionOperationRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b>
 * </br> ************************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionOperationRequest extends RequestContext {

    private String txnRefNo;
    private String mobileNumber;
    private String singleCustViewId;
    private String quesIdString;
    private String userStatusInMCE;
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

    public String getQuesIdString() {
	return quesIdString;
    }

    public void setQuesIdString(String quesIdString) {
	this.quesIdString = quesIdString;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
	return mobileNumber;
    }

    /**
     * @param mobileNumber
     *            the mobileNumber to set
     */
    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
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

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

}
