package com.barclays.bmg.mvc.validator.accountdetails;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.accountdetails.AccountDetailsCommand;

public class AccountDetailsValidator implements Validator {

    public boolean supports(Class clazz) {
	return AccountDetailsCommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
	String respID = ResponseIdConstants.CASA_DETAILS_RESPONSE_ID + "-";

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actNo", respID + AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_ACCOUNT_NO_EMPTY);

    }

}
