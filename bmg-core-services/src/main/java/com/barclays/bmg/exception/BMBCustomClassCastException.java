package com.barclays.bmg.exception;

public class BMBCustomClassCastException extends ClassCastException {

    public BMBCustomClassCastException(String string) {
	super(string);
    }

    public BMBCustomClassCastException() {
	super();
    }

    /**
	 *
	 */
    private static final long serialVersionUID = -7945871943587081456L;

}
