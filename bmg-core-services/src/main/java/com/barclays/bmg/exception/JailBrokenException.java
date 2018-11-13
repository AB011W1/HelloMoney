package com.barclays.bmg.exception;

public class JailBrokenException extends RuntimeException {

    private static final long serialVersionUID = -2413518685213457796L;

    private String errorCode;

    public JailBrokenException() {
	super();
    }

    public JailBrokenException(String errorCode) {
	super();
	this.errorCode = errorCode;
    }

    public String getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

}
