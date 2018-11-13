package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.DeleteBeneficiaryCommand;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryCommandValidator implements Validator {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return DeleteBeneficiaryCommand.class.isAssignableFrom(clazz);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"payeeId",
						ResponseIdConstants.DELETE_BENEFICIARY
								+ ResponseIdConstants.HYPHEN_SEPERATOR
								+ BeneficiaryResponseCodeConstants.BENEFICIARY_ID_EMPTY);

	}

}
