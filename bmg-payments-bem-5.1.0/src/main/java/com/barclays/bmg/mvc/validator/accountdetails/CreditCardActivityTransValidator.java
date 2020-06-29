package com.barclays.bmg.mvc.validator.accountdetails;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardActivityTransCommand;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardStmtTransCommand;

public class CreditCardActivityTransValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CreditCardActivityTransCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		String respID = ResponseIdConstants.CREDIT_CARD_STMT_TXNS_RESPONSE_ID + "-";

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actNo",
				respID + AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_ACCOUNT_NO_EMPTY);

	}

}