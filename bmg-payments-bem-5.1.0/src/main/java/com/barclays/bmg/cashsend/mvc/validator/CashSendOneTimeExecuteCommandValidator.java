package com.barclays.bmg.cashsend.mvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.cashsend.mvc.command.CashSendOneTimeExecuteCommand;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;

public class CashSendOneTimeExecuteCommandValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
	return CashSendOneTimeExecuteCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo", ResponseIdConstants.CASH_SEND_VALIDATE_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.CASH_SEND_EMPTY_TXREFKEY_EMPTY);

    }

}
