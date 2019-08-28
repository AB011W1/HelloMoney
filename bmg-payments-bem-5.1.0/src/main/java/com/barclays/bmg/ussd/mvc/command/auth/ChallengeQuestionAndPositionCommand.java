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
package com.barclays.bmg.ussd.mvc.command.auth;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionCommand.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionCommand {

    /**
     * The instance variable for questionList of type String
     */
    private String questionList;

    /**
     * Getter for questionList
     * 
     *@param none
     *@return questionList
     */
    public String getQuestionList() {
	return questionList;
    }

    /**
     * Setter for questionList
     * 
     * @param questionList
     * @return void
     */
    public void setQuestionList(String questionList) {
	this.questionList = questionList;
    }

    String mobileNumber;

    public String getMobileNumber() {
	return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    /* Will be part of session not command object */
    /*
     * private String mobileNumber; private String singleCustViewId;
     */

    /**
     * @return the mobileNumber
     */
    /*
     * public String getMobileNumber() { return mobileNumber; }
     *//**
     * @param mobileNumber
     *            the mobileNumber to set
     */
    /*
     * public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
     *//**
     * @return the singleCustViewId
     */
    /*
     * public String getSingleCustViewId() { return singleCustViewId; }
     *//**
     * @param singleCustViewId
     *            the singleCustViewId to set
     */
    /*
     * public void setSingleCustViewId(String singleCustViewId) { this.singleCustViewId = singleCustViewId; }
     */

}
