package com.barclays.ussd.validation;

import org.apache.commons.validator.GenericValidator;

import com.barclays.ussd.exception.USSDNonBlockingException;

public class USSDMinMaxRangeValidator implements IUSSDDataValidator {
    private String minLimit;
    private String maxLimit;

    public USSDMinMaxRangeValidator(final String minLimit, final String maxLimit) {
	super();
	this.minLimit = minLimit;
	this.maxLimit = maxLimit;
    }

    @Override
    public boolean validate(final String data) throws USSDNonBlockingException {
	if (GenericValidator.isInRange(Long.parseLong(data), Long.parseLong(minLimit), Long.parseLong(maxLimit))) {
	    return true;
	} else {
	    throw new USSDNonBlockingException();
	}
    }
}
