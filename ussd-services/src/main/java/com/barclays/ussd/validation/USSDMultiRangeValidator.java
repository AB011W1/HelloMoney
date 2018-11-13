package com.barclays.ussd.validation;

import com.barclays.ussd.exception.USSDNonBlockingException;

public class USSDMultiRangeValidator implements IUSSDDataValidator {
    private String multiAmtSndr;

    public USSDMultiRangeValidator(final String multiAmtSndr) {
	super();
	this.multiAmtSndr = multiAmtSndr;

    }

    @Override
    public boolean validate(final String data) throws USSDNonBlockingException {

	Long result = (Long.parseLong(data)) % (Long.parseLong(multiAmtSndr));

	if (result == 0) {
	    return true;
	} else {
	    throw new USSDNonBlockingException();
	}
    }
}
