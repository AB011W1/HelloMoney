package com.barclays.bmg.mvc.validator.USSDAuthentication;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.mvc.command.USSDAuthentication.AuthChangeRequestCommand;

public class AuthChangeRequestCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return AuthChangeRequestCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

}
