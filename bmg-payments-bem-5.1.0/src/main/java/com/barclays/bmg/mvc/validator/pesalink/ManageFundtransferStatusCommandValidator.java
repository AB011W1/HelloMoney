package com.barclays.bmg.mvc.validator.pesalink;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.mvc.command.pesalink.ManageFundtransferStatusCommand;

public class ManageFundtransferStatusCommandValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ManageFundtransferStatusCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

	}

}
