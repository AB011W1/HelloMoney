package com.barclays.ussd.validation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.barclays.ussd.exception.USSDNonBlockingException;

public class USSDLengthValidator implements IUSSDDataValidator {
    private String maxLength;

    public USSDLengthValidator(final String maxLength) {
	super();
	this.maxLength = maxLength;
    }

    @Override
    public boolean validate(final String data) throws USSDNonBlockingException {
	boolean result = true;
	if (StringUtils.isNotEmpty(maxLength) && NumberUtils.isDigits(maxLength)) {
	    if (data.length() > Integer.parseInt(maxLength)) {
		// TODO implement logging
		throw new USSDNonBlockingException();
	    }
	} else {
	    result = false;
	}

	return result;
    }
}
