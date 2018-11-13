/**
 *
 */
package com.barclays.ussd.exception;

// TODO: Auto-generated Javadoc
/**
 * The Enum USSDNonBlockingErrorCodes.
 * 
 * @author E20037164
 */
public enum USSDNonBlockingErrorCodes {

    /** The invalid back. */
    INVALID_BACK("USSD_ERR_00001"),

    /** The invalid home. */
    INVALID_HOME("USSD_ERR_00002"),

    /** The srvc not avlbl. */
    SRVC_NOT_AVLBL("USSD_SRVC_NT_AVLBL_00003"),

    /** The main acct nt found. */
    MAIN_ACCT_NT_FOUND("USSD_ERR_00003");

    /** The ussd error code. */
    private String ussdErrorCode;

    /**
     * Instantiates a new uSSD non blocking error codes.
     * 
     * @param errorCode
     *            the error code
     */
    private USSDNonBlockingErrorCodes(final String errorCode) {
	setUssdErrorCode(errorCode);
    }

    /**
     * Sets the ussd error code.
     * 
     * @param ussdErrorCode
     *            the ussdErrorCode to set
     */
    public void setUssdErrorCode(final String ussdErrorCode) {
	this.ussdErrorCode = ussdErrorCode;
    }

    /**
     * Gets the ussd error code.
     * 
     * @return the ussdErrorCode
     */
    public String getUssdErrorCode() {
	return ussdErrorCode;
    }

}
