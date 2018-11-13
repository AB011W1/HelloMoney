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

import java.util.List;

import com.barclays.bmg.context.RequestContext;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionServiceRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionServiceRequest extends RequestContext {
    private String txnRefNo;
    private String mobileNumber;
    private String singleCustViewId;

    private String questionKey;
    private String secondAuthGroupId;

    private String businessId;

    private String systemId;

    private List<String> quesIdList;

    private String questionCount;

    private String[] questionId;

    private String userStatusInMCE;

    public String getUserStatusInMCE() {
	return userStatusInMCE;
    }

    public void setUserStatusInMCE(String userStatusInMCE) {
	this.userStatusInMCE = userStatusInMCE;
    }

    public String[] getQuestionId() {
	String[] str = questionId;
	return str;
    }

    public void setQuestionId(String[] questionId) {
	String[] str = questionId;
	this.questionId = str;
    }

    public String getQuestionCount() {
	return questionCount;
    }

    public void setQuestionCount(String questionCount) {
	this.questionCount = questionCount;
    }

    public List<String> getQuesIdList() {
	return quesIdList;
    }

    public void setQuesIdList(List<String> quesIdList) {
	this.quesIdList = quesIdList;
    }

    public String getQuestionKey() {
	return questionKey;
    }

    public void setQuestionKey(String questionKey) {
	this.questionKey = questionKey;
    }

    public String getSecondAuthGroupId() {
	return secondAuthGroupId;
    }

    public void setSecondAuthGroupId(String secondAuthGroupId) {
	this.secondAuthGroupId = secondAuthGroupId;
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

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

}
