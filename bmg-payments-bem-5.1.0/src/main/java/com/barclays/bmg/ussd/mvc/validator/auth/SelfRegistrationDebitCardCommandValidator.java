package com.barclays.bmg.ussd.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationDebitCardCommand;

public class SelfRegistrationDebitCardCommandValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
	return SelfRegistrationDebitCardCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SQA", ResponseIdConstants.SECOND_AUTH_INIT_RESPONSE_ID
	//	+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.SECOND_AUTHCOMMAND_POSITION1_EMPTY);
    }

}
