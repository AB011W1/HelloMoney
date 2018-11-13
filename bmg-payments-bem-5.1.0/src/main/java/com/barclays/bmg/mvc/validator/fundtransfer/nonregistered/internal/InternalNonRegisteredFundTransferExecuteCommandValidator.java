package com.barclays.bmg.mvc.validator.fundtransfer.nonregistered.internal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.fundtransfer.nonregistered.internal.InternalNonRegisteredFundTransferCommand;

public class InternalNonRegisteredFundTransferExecuteCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return InternalNonRegisteredFundTransferCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo",
				ResponseIdConstants.INTERNAL_NON_REGISTERED_FUND_TRANSFER_DETAILS_DONE
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFEREXECUTECOMMAND_TXREFKEY_EMPTY);

	}



}
