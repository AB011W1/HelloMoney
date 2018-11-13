package com.barclays.ussd.exception;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDNonBlockingException.
 */
public class USSDNonBlockingException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** The error code. */
    private String errorCode;

    /** The error msg. */
    private String errorMsg;

    /** The page error parameters. */
    private List<String> errorParams;

    //CR-86 Back Flow changes
    /**Back Flow parameters */
    private boolean isBackFlow;
    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
	return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode
     *            the new error code
     */
    public void setErrorCode(final String errorCode) {
	this.errorCode = errorCode;
    }

    /**
     * Gets the error msg.
     *
     * @return the error msg
     */
    public String getErrorMsg() {
	return errorMsg;
    }

    /**
     * Sets the error msg.
     *
     * @param errorMsg
     *            the new error msg
     */
    public void setErrorMsg(final String errorMsg) {
	this.errorMsg = errorMsg;
    }

    /**
     * Instantiates a new uSSD non blocking exception.
     */
    public USSDNonBlockingException() {

    }

    /**
     * Instantiates a new uSSD non blocking exception.
     *
     * @param errorCode
     *            the error code
     */
    public USSDNonBlockingException(final String errorCode) {
	this.errorCode = errorCode;
    }

    public USSDNonBlockingException(final String errorCode, final String errorMsg) {
	this.errorCode = errorCode;
	this.errorMsg = errorMsg;
    }

    /**
     * @return the errorParams
     */
    public List<String> getErrorParams() {
	return errorParams;
    }

    /**
     * @param errorParams
     *            the errorParams to set
     */
    public void setErrorParams(List<String> errorParams) {
	this.errorParams = errorParams;
    }

    public void addErrorParam(String errorParam) {
	if (this.errorParams == null) {
	    this.errorParams = new ArrayList<String>(5);
	}
	this.errorParams.add(errorParam);
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    //CR-86 Back flow changes
	public void setBackFlow(boolean isBackFlow) {
		this.isBackFlow = isBackFlow;
	}

	//CR-86 Back flow changes
	public boolean isBackFlow() {
		return isBackFlow;
	}

}
