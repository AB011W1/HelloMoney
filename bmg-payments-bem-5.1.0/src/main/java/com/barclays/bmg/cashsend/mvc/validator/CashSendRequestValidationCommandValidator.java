package com.barclays.bmg.cashsend.mvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.cashsend.mvc.command.CashSendRequestValidationCommand;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;

public class CashSendRequestValidationCommandValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
	return CashSendRequestValidationCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actNo", ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CASH_SEND_ACCOUNT_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curr", ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CASH_SEND_EMPTY_CURRENCY);

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt", ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CASH_SEND_TXAMOUNT_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vPin", ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CASH_SEND_EMPTY_VPIN);

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNo", ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CASH_SEND_EMPTY_MOBILE_NO);

    }

}
