package com.barclays.ussd.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class BMGUserVerificationResponse.
 */
public class BMGUserVerificationResponse {
	
	/** The business id. */
	private String businessID;
	
	/** The mobile phone. */
	private String mobilePhone;
	
	/** The pref lang. */
	private String prefLang;
	
	/** The user id. */
	private String userId;
	
	/** The country code. */
	private String countryCode;

	/** Active user no not. */
	private String status;

	/**
     * Gets the business id.
     * 
     * @return the business id
     */
	public String getBusinessID() {
		return businessID;
	}

	/**
     * Sets the business id.
     * 
     * @param businessID
     *            the new business id
     */
	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	/**
     * Gets the mobile phone.
     * 
     * @return the mobile phone
     */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
     * Sets the mobile phone.
     * 
     * @param mobilePhone
     *            the new mobile phone
     */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
     * Gets the user id.
     * 
     * @return the user id
     */
	public String getUserId() {
		return userId;
	}

	/**
     * Sets the user id.
     * 
     * @param userId
     *            the new user id
     */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
     * Gets the country code.
     * 
     * @return the country code
     */
	public String getCountryCode() {
		return countryCode;
	}

	/**
     * Sets the country code.
     * 
     * @param countryCode
     *            the new country code
     */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
     * Gets the status.
     * 
     * @return the status
     */
	public String getStatus() {
		return status;
	}

	/**
     * Sets the status.
     * 
     * @param status
     *            the new status
     */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * Gets the pref lang.
     * 
     * @return the pref lang
     */
	public String getPrefLang() {
		return prefLang;
	}

	/**
     * Sets the pref lang.
     * 
     * @param prefLang
     *            the new pref lang
     */
	public void setPrefLang(String prefLang) {
		this.prefLang = prefLang;
	}
}
