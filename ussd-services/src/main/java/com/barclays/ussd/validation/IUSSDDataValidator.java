package com.barclays.ussd.validation;

import com.barclays.ussd.exception.USSDNonBlockingException;

public interface IUSSDDataValidator {
	boolean validate(String data) throws USSDNonBlockingException;

}
