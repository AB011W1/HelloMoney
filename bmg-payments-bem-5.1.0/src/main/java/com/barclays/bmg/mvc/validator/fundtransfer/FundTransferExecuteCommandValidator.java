package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.fundtransfer.own.FundTransferExecuteCommand;

public class FundTransferExecuteCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return FundTransferExecuteCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo",
				ResponseIdConstants.OWN_FUND_TRANSFER_EXECUTE_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFEREXECUTECOMMAND_TXREFKEY_EMPTY);

	}

}
