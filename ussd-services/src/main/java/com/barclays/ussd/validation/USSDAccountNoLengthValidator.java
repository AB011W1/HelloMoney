package com.barclays.ussd.validation;

import com.barclays.ussd.exception.USSDNonBlockingException;

public class USSDAccountNoLengthValidator implements IUSSDDataValidator {
    private int accountNoLength;

    public USSDAccountNoLengthValidator(final String accountNoLength) {
	super();
	if (accountNoLength != null) {
	    this.accountNoLength = Integer.valueOf(accountNoLength);
	}
    }

    @Override
    public boolean validate(final String data) throws USSDNonBlockingException {
	if (data != null && Integer.valueOf(data) == accountNoLength) {
	    // if (StringUtils.endsWithIgnoreCase(data, accountNoLength)) {
	    return true;
	} else {
	    throw new USSDNonBlockingException();
	}
    }
}
