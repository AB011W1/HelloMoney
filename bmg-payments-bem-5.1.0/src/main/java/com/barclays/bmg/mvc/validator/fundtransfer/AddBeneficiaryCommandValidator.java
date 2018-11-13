package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.AddBeneficiaryCommand;

public class AddBeneficiaryCommandValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return AddBeneficiaryCommand.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"txnRefNo",
				ResponseIdConstants.SEARCH_TRANSACTION_HISTORY
				+ ResponseIdConstants.HYPHEN_SEPERATOR + BeneficiaryResponseCodeConstants.BENEFICIARY_TXN_REF_NO_EMPTY);


	}

}
