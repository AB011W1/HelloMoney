package com.barclays.bmg.mvc.validator.fundtransfer.international;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.ExternalPayeeDetailsCommand;

public class InternationalPayeeDetailsCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return ExternalPayeeDetailsCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"payId",
				ResponseIdConstants.INTERNATIONAL_FT_PAYEE_DETAILS
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_BENEFICIARY_ID_EMPTY);
	}

}