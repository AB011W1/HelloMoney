package com.barclays.bmg.mvc.validator.fundtransfer.international;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.ExternalFundTransferCommand;

public class InternationalFTFormSubmissionCommandValidator implements Validator {

	private static final String AED="AED";
	@Override
	public boolean supports(Class clazz) {

		return ExternalFundTransferCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnAmt",
				ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_EMPTY);
		ExternalFundTransferCommand command = (ExternalFundTransferCommand) target;
		String amount = command.getTxnAmt();
		if(amount!=null){
			if(!amount.matches("^\\d+$|^\\d*\\.\\d{1,2}$")){
				errors
				.reject(ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT + ResponseIdConstants.HYPHEN_SEPERATOR
						+BillPaymentResponseCodeConstants.INVALID_BILL_PAY_AMT);
			}
		}


		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "chDesc",
				ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.EXTERNAL_CHARGE_DESC_EMPTY);


		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curr",
				ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.EXTERNAL_CURRENCY_EMPTY);


	/*	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rem1",
				ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.EXTERNAL_REMITTANCE_PART1_EMPTY);
*/
/*		String currency = command.getCurr();
		if(AED.equals(currency)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payRson",
					ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT
					+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.EXTERNAL_PAYEE_REASON_DTLS_EMPTY);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payDtls",
					ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT
					+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.EXTERNAL_PAYEE_REASON_DTLS_EMPTY);
		}*/

	}

}
