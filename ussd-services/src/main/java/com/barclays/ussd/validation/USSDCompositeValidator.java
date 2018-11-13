package com.barclays.ussd.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.ussd.exception.USSDNonBlockingException;

public class USSDCompositeValidator implements IUSSDDataValidator {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(USSDCompositeValidator.class);

    private List<IUSSDDataValidator> validators;

    public List<IUSSDDataValidator> getValidators() {
	return validators;
    }

    public void setValidators(final List<IUSSDDataValidator> validators) {
	this.validators = validators;
    }

    public USSDCompositeValidator(final IUSSDDataValidator... dataValidators) {
	super();
	validators = new ArrayList<IUSSDDataValidator>();
	for (IUSSDDataValidator validatorObj : dataValidators) {
	    this.validators.add(validatorObj);
	}
    }

    public boolean validate(final String data) throws USSDNonBlockingException {
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("Performing validations on the user inputs...");
	}
	for (IUSSDDataValidator validatorObj : this.validators) {
	    validatorObj.validate(data);
	}
	return true;
    }
}
