package com.barclays.bmg.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.auth.OTPCommand;

public class OTPCommandValidator implements Validator {

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {

	return OTPCommand.class.isAssignableFrom(clazz);

    }

    public void validate(Object obj, Errors e) {
	String respID = ResponseIdConstants.OTP_RESPONSE_ID + "-";

	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(e, "otp", "otpcommand.otp.empty");
	 */

	ValidationUtils.rejectIfEmptyOrWhitespace(e, "otp", respID + AuthResponseCodeConstants.OTP_EMPTY);

    }
}
