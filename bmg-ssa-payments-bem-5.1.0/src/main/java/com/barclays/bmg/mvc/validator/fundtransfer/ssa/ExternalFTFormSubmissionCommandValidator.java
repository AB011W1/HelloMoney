package com.barclays.bmg.mvc.validator.fundtransfer.ssa;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.ExternalFundTransferCommand;

public class ExternalFTFormSubmissionCommandValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

	return ExternalFundTransferCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt", ResponseIdConstants.EXTERNAL_FUND_TRANSFER_FORM_SUBMIT
		+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_EMPTY);
	ExternalFundTransferCommand command = (ExternalFundTransferCommand) target;
	String amount = command.getTxnAmt();
	if (amount != null) {
	    if (!amount.matches("^\\d+$|^\\d*\\.\\d{1,2}$")) {
		errors.reject(ResponseIdConstants.EXTERNAL_FUND_TRANSFER_FORM_SUBMIT + ResponseIdConstants.HYPHEN_SEPERATOR
			+ BillPaymentResponseCodeConstants.INVALID_BILL_PAY_AMT);
	    }
	}

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curr", ResponseIdConstants.EXTERNAL_FUND_TRANSFER_FORM_SUBMIT
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.EXTERNAL_CURRENCY_EMPTY);

	command.setRem1(command.getTxnNot());

    }

}
