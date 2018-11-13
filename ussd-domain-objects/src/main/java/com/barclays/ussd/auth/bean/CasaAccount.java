package com.barclays.ussd.auth.bean;

import java.io.Serializable;

/**
 * The Class CasaAccount.
 */
public class CasaAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The account number. */
    private String accountNumber;

    /** The masked account number. */
    private String maskedAccountNumber;

    /** The branch code. */
    private String branchCode;

    /** The primary indicator. */
    private String primaryIndicator;

    private String accStatus;

    private String curr;

    /**
     * Gets the account number.
     * 
     * @return the account number
     */
    public String getAccountNumber() {
	return accountNumber;
    }

    /**
     * Sets the account number.
     * 
     * @param accountNumber
     *            the new account number
     */
    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    /**
     * Gets the branch code.
     * 
     * @return the branch code
     */
    public String getBranchCode() {
	return branchCode;
    }

    /**
     * Sets the branch code.
     * 
     * @param branchCode
     *            the new branch code
     */
    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    /**
     * Gets the masked account number.
     * 
     * @return the maskedAccountNumber
     */
    public String getMaskedAccountNumber() {
	return maskedAccountNumber;
    }

    /**
     * Sets the masked account number.
     * 
     * @param maskedAccountNumber
     *            the maskedAccountNumber to set
     */
    public void setMaskedAccountNumber(String maskedAccountNumber) {
	this.maskedAccountNumber = maskedAccountNumber;
    }

    /**
     * Gets the primary indicator.
     * 
     * @return the primary indicator
     */
    public String getPrimaryIndicator() {
	return primaryIndicator;
    }

    /**
     * Sets the primary indicator.
     * 
     * @param primaryIndicator
     *            the new primary indicator
     */
    public void setPrimaryIndicator(String primaryIndicator) {
	this.primaryIndicator = primaryIndicator;
    }

    public String getAccStatus() {
	return accStatus;
    }

    public void setAccStatus(String accStatus) {
	this.accStatus = accStatus;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }
}
