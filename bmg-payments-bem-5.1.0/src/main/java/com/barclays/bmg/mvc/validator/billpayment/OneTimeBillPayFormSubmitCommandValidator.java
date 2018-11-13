package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.mvc.command.billpayment.OneTimeBillPayFormSubmitCommand;

public class OneTimeBillPayFormSubmitCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return OneTimeBillPayFormSubmitCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		OneTimeBillPayFormSubmitCommand oneTimeBillPayFormSubmitCommand =(OneTimeBillPayFormSubmitCommand)target;
		if(null == oneTimeBillPayFormSubmitCommand.getCreditcardNo())
		{		
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromActNumber",
				BillPaymentResponseCodeConstants.PAYMENT_FROM_ACCOUNT_EMPTY);
}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amt",
				BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billerRefNumber",
				BillPaymentResponseCodeConstants.BILLER_REF_NO_EMPTY);


	}

}
