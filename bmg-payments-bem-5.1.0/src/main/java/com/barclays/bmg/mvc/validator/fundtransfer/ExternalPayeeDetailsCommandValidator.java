package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.ExternalPayeeDetailsCommand;

public class ExternalPayeeDetailsCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return ExternalPayeeDetailsCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"payId",
				ResponseIdConstants.EXTERNAL_FT_PAYEE_DETAILS
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_BENEFICIARY_ID_EMPTY);
	}

}