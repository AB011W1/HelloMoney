package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.fundtransfer.internal.InternalFTDetailsCommand;

public class InternalFTDetailsCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {

		return InternalFTDetailsCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "payId",
				ResponseIdConstants.INTERNAL_FUND_TRANSFER_DETAILS_RESPONSE_ID
				+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.FUNDTRANSFERDATACOMMAND_TARGETACCOUNT_EMPTY);

	}

}
