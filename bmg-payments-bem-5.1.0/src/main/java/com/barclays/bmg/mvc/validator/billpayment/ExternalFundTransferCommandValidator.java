package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.ExternalFundTransferCommand;
import com.barclays.bmg.mvc.command.billpayment.PaymentFormSubmissionCommand;

public class ExternalFundTransferCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {

		return ExternalFundTransferCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt",
				ResponseIdConstants.EXTERNAL_FUND_TRANSFER_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_EMPTY);
		ValidationUtils.rejectIfEmpty(errors,"frmAct",
				ResponseIdConstants.EXTERNAL_FUND_TRANSFER_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_FROM_ACCOUNT_EMPTY);

		// Validation for chargedetails, currency and RemInfo.
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
