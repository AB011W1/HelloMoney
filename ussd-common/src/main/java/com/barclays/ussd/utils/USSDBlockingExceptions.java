/**
 *
 */
package com.barclays.ussd.utils;

// TODO: Auto-generated Javadoc
/**
 * The Enum USSDBlockingExceptions.
 * 
 * @author E20040458
 */
public enum USSDBlockingExceptions {

	/** The parser error. */
	PARSER_ERROR("USSD_BE_00001"),

	/** The xpath error. */
	XPATH_ERROR("USSD_BE_00002"),

	AUTHENTICATION_ERROR("USSD_AUTH_FAIL");

	/** The error code. */
	private String errorCode;

	/**
	 * Instantiates a new uSSD blocking exceptions.
	 * 
	 * @param errorCode
	 *            the error code
	 */
	private USSDBlockingExceptions(final String errorCode) {
		setErrorCode(errorCode);
	}

	/**
	 * Sets the error code.
	 * 
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error code.
	 * 
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

}
