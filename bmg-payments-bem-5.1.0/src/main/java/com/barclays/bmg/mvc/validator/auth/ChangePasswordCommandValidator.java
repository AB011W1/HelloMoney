package com.barclays.bmg.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.auth.ChangePasswordCommand;

public class ChangePasswordCommandValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
	// TODO Auto-generated method stub
	return ChangePasswordCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {
	String respID = ResponseIdConstants.CHANGE_USERNAME_PASSWORD + "-";

	ValidationUtils.rejectIfEmptyOrWhitespace(e, "oldPass", respID + AuthResponseCodeConstants.PASSWORD_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(e, "newPass", respID + AuthResponseCodeConstants.PASSWORD_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(e, "confNewPass", respID + AuthResponseCodeConstants.PASSWORD_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(e, "mobileNo", respID + AuthResponseCodeConstants.MOBILE_NO_EMPTY);

    }

}
