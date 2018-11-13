package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.mvc.command.billpayment.OneTimeBillPayInitCommand;

public class OneTimeBillPayInitCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return OneTimeBillPayInitCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billerId",
				BillPaymentResponseCodeConstants.BILLER_ID_EMPTY);


	}

}
