/**
 * USSDException.java
 */
package com.barclays.ussd.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDBlockingException.
 * 
 * @author BTCI
 */
public class USSDBlockingException extends Exception {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** errCode. */
    private String errCode;

    /** errMsg. */
    private String errMsg;

    /**
     * Gets the err code.
     * 
     * @return the errCode
     */
    public String getErrCode() {
	return errCode;
    }

    /**
     * Sets the err code.
     * 
     * @param errCode
     *            the errCode to set
     */
    public void setErrCode(final String errCode) {
	this.errCode = errCode;
    }

    /**
     * Gets the err msg.
     * 
     * @return the errMsg
     */
    public String getErrMsg() {
	return errMsg;
    }

    /**
     * Sets the err msg.
     * 
     * @param errMsg
     *            the errMsg to set
     */
    public void setErrMsg(final String errMsg) {
	this.errMsg = errMsg;
    }

    /**
     * Instantiates a new uSSD blocking exception.
     * 
     * @param errorCode
     *            the error code
     * @param errMsg
     *            the err msg
     */
    public USSDBlockingException(final String errorCode, final String errMsg) {
	errCode = errorCode;
	this.errMsg = errMsg;
    }

    /**
     * Instantiates a new uSSD blocking exception.
     * 
     * @param errCode
     *            the err code
     */
    public USSDBlockingException(final String errCode) {
	this.errCode = errCode;
    }
}
