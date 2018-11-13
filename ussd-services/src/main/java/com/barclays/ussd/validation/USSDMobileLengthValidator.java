package com.barclays.ussd.validation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDExceptions;

public class USSDMobileLengthValidator implements IUSSDDataValidator {

    private String maxLength;

    public USSDMobileLengthValidator(final String maxLength) {
	super();
	this.maxLength = maxLength;
    }

    @Override
    public boolean validate(final String data) throws USSDNonBlockingException {
	boolean result = true;
	String tenDigitZero = "0000000000";
	String nineDigit = "000000000";
	String eightDigit = "00000000";
	int maxLen = 0;
	if (StringUtils.isNotEmpty(maxLength) && NumberUtils.isDigits(maxLength)) {
	    maxLen = Integer.parseInt(maxLength);
	    if (data.length() == maxLen + 1) {
		if (!data.startsWith("0")) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
		} else if (data.equalsIgnoreCase(tenDigitZero)) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
		} else if (data.equalsIgnoreCase(nineDigit)) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
		} else {
		    result = false;
		}
	    } else if (data.length() != maxLen) {
		throw new USSDNonBlockingException(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
	    } else if (data.equalsIgnoreCase(nineDigit)) {
		throw new USSDNonBlockingException(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
	    }else if (data.equalsIgnoreCase(eightDigit)) {
		throw new USSDNonBlockingException(USSDExceptions.USSD_MOBILE_NUMBER_INVALID.getUssdErrorCode());
		}
	} else {
	    result = false;
	}

	return result;
    }

}
