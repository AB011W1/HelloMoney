package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.MessageCodeConstant;
import com.barclays.bmg.mvc.command.billpayment.BillPaymentExecuteCommand;

public class BillPaymentExecuteCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return BillPaymentExecuteCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo",
				MessageCodeConstant.BILLPAYMENTEXECUTECOMMAND_TXREFKEY_EMPTY);
	}

}
