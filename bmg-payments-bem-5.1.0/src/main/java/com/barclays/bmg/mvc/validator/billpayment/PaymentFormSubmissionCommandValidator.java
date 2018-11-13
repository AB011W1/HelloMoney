package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.PaymentFormSubmissionCommand;

public class PaymentFormSubmissionCommandValidator implements Validator{

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {

		return PaymentFormSubmissionCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amt",
				ResponseIdConstants.BP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_EMPTY);
		ValidationUtils.rejectIfEmpty(errors,"frActNo",
				ResponseIdConstants.BP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_FROM_ACCOUNT_EMPTY);
		PaymentFormSubmissionCommand command = (PaymentFormSubmissionCommand) target;
		String amount = command.getAmt();
		if(amount!=null){
			if(!amount.matches("^\\d+$|^\\d*\\.\\d{1,2}$")){
				errors
				.reject(ResponseIdConstants.BP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID+ ResponseIdConstants.HYPHEN_SEPERATOR
						+BillPaymentResponseCodeConstants.INVALID_BILL_PAY_AMT);
			}
		}

	}

}
