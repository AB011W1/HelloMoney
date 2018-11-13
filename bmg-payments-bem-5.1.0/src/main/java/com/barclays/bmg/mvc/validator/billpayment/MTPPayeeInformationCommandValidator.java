package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.MTPPayeeInformationCommand;

public class MTPPayeeInformationCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {

		return MTPPayeeInformationCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"payId",
				ResponseIdConstants.RETRIEVE_MTP_PAYEE_INFORMATION_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_BENEFICIARY_ID_EMPTY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"paySer",
				ResponseIdConstants.RETRIEVE_MTP_PAYEE_INFORMATION_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.MTP_SERVICE_EMPTY);

	}

}
