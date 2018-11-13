package com.barclays.bmg.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.auth.LoginCommand;

public class LoginCommandValidator implements Validator {
    public String respID = ResponseIdConstants.LOGIN_RESPONSE_ID + "-";

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
	return LoginCommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object obj, Errors e) {
	ValidationUtils.rejectIfEmptyOrWhitespace(e, "usrNam", respID + AuthResponseCodeConstants.USERNAME_EMPTY);
	ValidationUtils.rejectIfEmptyOrWhitespace(e, "pass", respID + AuthResponseCodeConstants.PASSWORD_EMPTY);
    }
}