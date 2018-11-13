package com.barclays.ussd.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.LongValidator;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;

public class USSDBackFlowValidator {
private String errorCode;

	public String getErrorCode() {
	return errorCode;
}

	public boolean validateAmount(String data) throws USSDNonBlockingException {
		char zero = '0';
		if(StringUtils.isEmpty(data) || !LongValidator.getInstance().isValid(data) || isNotDigits(data.toCharArray())){
			throw new USSDNonBlockingException();
		} else if(data.length() > 1 && zero == data.charAt(0)){
			throw new USSDNonBlockingException();
		}

		return true;
	}

	public boolean validateMobileNumberCashSend(String data) throws USSDNonBlockingException {
		if (!StringUtils.isNumeric(data)) {
			throw new USSDNonBlockingException();
		}
		return true;
	}

	public boolean validateCashSendATMPin(String data, USSDSessionManagement ussdSessionMgmt) throws USSDNonBlockingException {
		if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("ZMBRB")){
			if (data == null || StringUtils.isEmpty(data) || data.length() != 6 || isNotDigits(data.toCharArray())) {
				throw new USSDNonBlockingException();
			}
		} else {
			if (data == null || StringUtils.isEmpty(data) || data.length() != 4 || isNotDigits(data.toCharArray())) {
				throw new USSDNonBlockingException();
			}
		}

		return true;
	}

	public boolean validateConfirmationMwalletAirtime(String data) throws USSDNonBlockingException {
		if (StringUtils.isEmpty(data) || !IntegerValidator.getInstance().isValid(data)
				|| !USSDConstants.CONFIRM_ACTION_OPTION_CODE.equalsIgnoreCase(data.trim())) {
			throw new USSDNonBlockingException();
		}
		return true;
	}
	public boolean validateAccountNumber(String data) throws USSDNonBlockingException {

		if (StringUtils.isEmpty(data) || !StringUtils.isNumeric(data)) {
			throw new USSDNonBlockingException();
		}
		return true;
	}
	public boolean validateNIBNumber(String data) throws USSDNonBlockingException {

		if (data.equalsIgnoreCase("*")) {
    	 	return true;
    	}
    	else if (StringUtils.isEmpty(data) || !StringUtils.isNumeric(data)) {
    	    errorCode=USSDExceptions.USSD_INVALID_NIB_NO.getUssdErrorCode();
    	    throw new USSDNonBlockingException();
    	}else if(data.length()==21)
    		return true;
    	else if(data.length()!=21){
    		errorCode=USSDExceptions.USSD_NIB_ERROR.getUssdErrorCode();
    		throw new USSDNonBlockingException();
    	}
		return true;
	}

	private static boolean isNotDigits(char[] sequence) {
		boolean isNotDigits = false;
		int sequenceLength = sequence.length;
		for (int i = 0; i < sequenceLength; i++) {
			if (!Character.isDigit(sequence[i])) {
				isNotDigits = true;
				break;
			}

		}
		return isNotDigits;
	}


}
