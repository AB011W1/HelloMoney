package com.barclays.bmg.exception;

public class BMBDataAccessException extends RuntimeException {

    /**
	 *
	 */
    private static final long serialVersionUID = 978805378809457137L;

    private String errorCode;
    private String errorSource;
    private String txnRefNo;

    public BMBDataAccessException() {
	super();
    }

    public BMBDataAccessException(String errorCode) {
	super();
	this.errorCode = errorCode;
    }

    public BMBDataAccessException(String errorCode, String message) {
	super(message);
	this.errorCode = errorCode;
    }

    public BMBDataAccessException(String errorCode, String message, String txnRefNo) {
	super(message);
	this.errorCode = errorCode;
	this.txnRefNo = txnRefNo;
    }

    public String getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

    public String getErrorSource() {
	return errorSource;
    }

    public void setErrorSource(String errorSource) {
	this.errorSource = errorSource;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

}
