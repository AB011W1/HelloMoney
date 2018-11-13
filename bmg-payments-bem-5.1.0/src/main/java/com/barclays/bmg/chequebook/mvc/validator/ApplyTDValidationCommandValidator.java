package com.barclays.bmg.chequebook.mvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.ApplyTDConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.ApplyTDSourceAccountDetailsCommand;

public class ApplyTDValidationCommandValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return ApplyTDSourceAccountDetailsCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"actNo",ResponseIdConstants.APPLY_TD_VALIDATIONOPERATION
				+ ResponseIdConstants.HYPHEN_SEPERATOR + ApplyTDConstants.ACC_NUM_EMPTY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"depositAmount",ResponseIdConstants.APPLY_TD_VALIDATIONOPERATION
				+ ResponseIdConstants.HYPHEN_SEPERATOR + ApplyTDConstants.DEPOIST_AMOUNT_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"tenureMonths",ResponseIdConstants.APPLY_TD_VALIDATIONOPERATION
				+ ResponseIdConstants.HYPHEN_SEPERATOR +ApplyTDConstants.TENURTERM_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"tenureDays",ResponseIdConstants.APPLY_TD_VALIDATIONOPERATION
				+ ResponseIdConstants.HYPHEN_SEPERATOR +ApplyTDConstants.TENURTERM_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"productId",ResponseIdConstants.APPLY_TD_VALIDATIONOPERATION
				+ ResponseIdConstants.HYPHEN_SEPERATOR + ApplyTDConstants.PRODUCT_ID_EMPTY);

	}

}
