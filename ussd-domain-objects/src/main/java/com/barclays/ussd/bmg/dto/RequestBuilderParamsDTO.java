package com.barclays.ussd.bmg.dto;

import com.barclays.ussd.auth.bean.USSDSessionManagement;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestBuilderParamsDTO.
 */
public class RequestBuilderParamsDTO {

    /** The bmg op code. */
    private String bmgOpCode;

    /** The msisdn no. */
    private String msisdnNo;

    /** The user input. */
    private String userInput;

    /** The header id. */
    private String headerId;

    /** The ussd session mgmt. */
    private USSDSessionManagement ussdSessionMgmt;

    /**
     * Gets the bmg op code.
     * 
     * @return the bmg op code
     */
    public String getBmgOpCode() {
	return bmgOpCode;
    }

    /**
     * Sets the bmg op code.
     * 
     * @param bmgOpCode
     *            the new bmg op code
     */
    public void setBmgOpCode(String bmgOpCode) {
	this.bmgOpCode = bmgOpCode;
    }

    /**
     * Gets the msisdn no.
     * 
     * @return the msisdn no
     */
    public String getMsisdnNo() {
	return msisdnNo;
    }

    /**
     * Sets the msisdn no.
     * 
     * @param msisdnNo
     *            the new msisdn no
     */
    public void setMsisdnNo(String msisdnNo) {
	this.msisdnNo = msisdnNo;
    }

    /**
     * Gets the user input.
     * 
     * @return the user input
     */
    public String getUserInput() {
	return userInput;
    }

    /**
     * Sets the user input.
     * 
     * @param userInput
     *            the new user input
     */
    public void setUserInput(String userInput) {
	this.userInput = userInput;
    }

    /**
     * Gets the header id.
     * 
     * @return the header id
     */
    public String getHeaderId() {
	return headerId;
    }

    /**
     * Sets the header id.
     * 
     * @param headerId
     *            the new header id
     */
    public void setHeaderId(String headerId) {
	this.headerId = headerId;
    }

    /**
     * Gets the ussd session mgmt.
     * 
     * @return the ussd session mgmt
     */
    public USSDSessionManagement getUssdSessionMgmt() {
	return ussdSessionMgmt;
    }

    /**
     * Sets the ussd session mgmt.
     * 
     * @param ussdSessionMgmt
     *            the new ussd session mgmt
     */
    public void setUssdSessionMgmt(USSDSessionManagement ussdSessionMgmt) {
	this.ussdSessionMgmt = ussdSessionMgmt;
    }
}
