package com.barclays.ussd.validation;

import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDExceptions;

public class USSDChangePasswordValidator implements IUSSDDataValidator {
    private final String newPwd;
    private final String reenteredPwd;

    public USSDChangePasswordValidator(final String newPassword, final String reenteredNewPassword) {
	super();
	this.newPwd = newPassword;
	this.reenteredPwd = reenteredNewPassword;
    }

    @Override
    public boolean validate(final String data) throws USSDNonBlockingException {
	if (newPwd.equalsIgnoreCase(reenteredPwd)) {
	    return true;
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.INPUT_INVALID_PASSWORD.getUssdErrorCode());
	}
    }

}
