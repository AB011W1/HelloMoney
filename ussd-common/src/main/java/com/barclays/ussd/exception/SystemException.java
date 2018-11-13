package com.barclays.ussd.exception;

/**
 * This Exception is useful at the time of unknown exception
 * 
 */
public class SystemException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}

	public SystemException(String s) {
		super(s);
	}

	public SystemException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public SystemException(Throwable throwable) {
		super(throwable);
	}
}
