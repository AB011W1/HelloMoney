package com.barclays.bmg.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.auth.SQACommand;

public class SQACommandValidator implements Validator {

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
	return SQACommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object obj, Errors e) {
	String respID = ResponseIdConstants.SQA_RESPONSE_ID + "-";

	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(e, "sqa", "sqacommand.sqa.empty");
	 */

	ValidationUtils.rejectIfEmptyOrWhitespace(e, "sqa", respID + AuthResponseCodeConstants.SQA_EMPTY);

    }

}
