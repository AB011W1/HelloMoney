package com.barclays.ussd.auth.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerMobileRegAcct.
 */
public class CustomerMobileRegAcct {
	
	/** The account number. */
	private String accountNumber;
	
	/** The branch code. */
	private String branchCode;
	
	/** The primary indicator. */
	private boolean primaryIndicator;

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
     * Checks if is primary indicator.
     * 
     * @return true, if is primary indicator
     */
	public boolean isPrimaryIndicator() {
		return primaryIndicator;
	}

	/**
     * Sets the primary indicator.
     * 
     * @param primaryIndicator
     *            the new primary indicator
     */
	public void setPrimaryIndicator(boolean primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}
}
