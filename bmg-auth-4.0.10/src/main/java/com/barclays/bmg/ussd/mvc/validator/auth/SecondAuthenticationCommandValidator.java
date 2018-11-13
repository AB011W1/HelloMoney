package com.barclays.bmg.ussd.mvc.validator.auth;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.ussd.mvc.command.auth.SecondAuthenticationCommand;

public class SecondAuthenticationCommandValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
	return SecondAuthenticationCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo", ResponseIdConstants.FUND_TRANSFER_SECONDAUTH_INIT_RESPONSE_ID +
	 * ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.TRANSACTION_SECOND_AUTHCOMMAND_TXREFKEY_EMPTY);
	 */

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SQA", ResponseIdConstants.SECOND_AUTH_INIT_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.SECOND_AUTHCOMMAND_POSITION1_EMPTY);

	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position2", ResponseIdConstants.FUND_TRANSFER_SECONDAUTH_INIT_RESPONSE_ID +
	 * ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.TRANSACTION_SECOND_AUTHCOMMAND_POSITION2_EMPTY);
	 */

    }

}
