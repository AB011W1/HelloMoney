package com.barclays.ussd.validation;

import java.util.regex.Pattern;

import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDExceptions;

public class USSDAccountNoLengthValidator implements IUSSDDataValidator {
    private int accountNoLength;

    public USSDAccountNoLengthValidator(final String accountNoLength) {
	super();
	if (accountNoLength != null) {
	    this.accountNoLength = Integer.valueOf(accountNoLength);
	}
    }
    public USSDAccountNoLengthValidator() {
		// TODO Auto-generated constructor stub
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
    
    //Ghana changes for Data Bundle
    public boolean validatedatabundleacc(String validation, String userInput) throws USSDNonBlockingException {
	if (Pattern.matches(validation, userInput)) {
	    // if (StringUtils.endsWithIgnoreCase(data, accountNoLength)) {
	    return true;
	} else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_ACCT_NO.getUssdErrorCode());
	}
    }
}
