package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.TransactionHistoryResponseCodeConstants;
import com.barclays.bmg.mvc.command.billpayment.ViewTxnHistoryDetailsCommand;

public class ViewTxnHistoryDetailsCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {

		return ViewTxnHistoryDetailsCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"transactionRefNo",
				ResponseIdConstants.VIEW_TXN_HISTORY_DETAILS
				+ ResponseIdConstants.HYPHEN_SEPERATOR + TransactionHistoryResponseCodeConstants.TXN_REF_NO_EMPTY);


	}

}
