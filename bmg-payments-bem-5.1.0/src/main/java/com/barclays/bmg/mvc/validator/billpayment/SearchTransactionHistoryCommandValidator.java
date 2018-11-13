package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.TransactionHistoryResponseCodeConstants;
import com.barclays.bmg.mvc.command.billpayment.SearchTransactionHistoryCommand;

public class SearchTransactionHistoryCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {

		return SearchTransactionHistoryCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"transactionType",
				ResponseIdConstants.SEARCH_TRANSACTION_HISTORY
				+ ResponseIdConstants.HYPHEN_SEPERATOR + TransactionHistoryResponseCodeConstants.TXN_TYPE_EMPTY);

	}

}
