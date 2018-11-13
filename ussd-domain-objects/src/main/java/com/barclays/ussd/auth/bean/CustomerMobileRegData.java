package com.barclays.ussd.auth.bean;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerMobileRegData.
 */
public class CustomerMobileRegData {
	
	/** The customer scv id. */
	private String customerScvID;
	
	/** The customer full name. */
	private String customerFullName;
	
	/** The customer mobile number. */
	private String customerMobileNumber;
	
	/** The customer mobile reg status. */
	private String customerMobileRegStatus;
	
	/** The language. */
	private String language;
	
	/** The pin status. */
	private String pinStatus;
	
	/** The pin sub status. */
	private String pinSubStatus;
	
	/** The pin error count. */
	private String pinErrorCount;
	
	/** The customer mobile reg acct list. */
	List<CustomerMobileRegAcct> customerMobileRegAcctList;

	/**
     * Gets the customer scv id.
     * 
     * @return the customer scv id
     */
	public String getCustomerScvID() {
		return customerScvID;
	}

	/**
     * Sets the customer scv id.
     * 
     * @param customerScvID
     *            the new customer scv id
     */
	public void setCustomerScvID(String customerScvID) {
		this.customerScvID = customerScvID;
	}

	/**
     * Gets the customer full name.
     * 
     * @return the customer full name
     */
	public String getCustomerFullName() {
		return customerFullName;
	}

	/**
     * Sets the customer full name.
     * 
     * @param customerFullName
     *            the new customer full name
     */
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	/**
     * Gets the customer mobile number.
     * 
     * @return the customer mobile number
     */
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	/**
     * Sets the customer mobile number.
     * 
     * @param customerMobileNumber
     *            the new customer mobile number
     */
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	/**
     * Gets the customer mobile reg status.
     * 
     * @return the customer mobile reg status
     */
	public String getCustomerMobileRegStatus() {
		return customerMobileRegStatus;
	}

	/**
     * Sets the customer mobile reg status.
     * 
     * @param customerMobileRegStatus
     *            the new customer mobile reg status
     */
	public void setCustomerMobileRegStatus(String customerMobileRegStatus) {
		this.customerMobileRegStatus = customerMobileRegStatus;
	}

	/**
     * Gets the language.
     * 
     * @return the language
     */
	public String getLanguage() {
		return language;
	}

	/**
     * Sets the language.
     * 
     * @param language
     *            the new language
     */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
     * Gets the pin status.
     * 
     * @return the pin status
     */
	public String getPinStatus() {
		return pinStatus;
	}

	/**
     * Sets the pin status.
     * 
     * @param pinStatus
     *            the new pin status
     */
	public void setPinStatus(String pinStatus) {
		this.pinStatus = pinStatus;
	}

	/**
     * Gets the pin sub status.
     * 
     * @return the pin sub status
     */
	public String getPinSubStatus() {
		return pinSubStatus;
	}

	/**
     * Sets the pin sub status.
     * 
     * @param pinSubStatus
     *            the new pin sub status
     */
	public void setPinSubStatus(String pinSubStatus) {
		this.pinSubStatus = pinSubStatus;
	}

	/**
     * Gets the pin error count.
     * 
     * @return the pin error count
     */
	public String getPinErrorCount() {
		return pinErrorCount;
	}

	/**
     * Sets the pin error count.
     * 
     * @param pinErrorCount
     *            the new pin error count
     */
	public void setPinErrorCount(String pinErrorCount) {
		this.pinErrorCount = pinErrorCount;
	}

	/**
     * Gets the customer mobile reg acct list.
     * 
     * @return the customer mobile reg acct list
     */
	public List<CustomerMobileRegAcct> getCustomerMobileRegAcctList() {
		return customerMobileRegAcctList;
	}

	/**
     * Sets the customer mobile reg acct list.
     * 
     * @param customerMobileRegAcctList
     *            the new customer mobile reg acct list
     */
	public void setCustomerMobileRegAcctList(List<CustomerMobileRegAcct> customerMobileRegAcctList) {
		this.customerMobileRegAcctList = customerMobileRegAcctList;
	}
}
