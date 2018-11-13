package com.barclays.bmg.mvc.validator.fundtransfer;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AddOrgBeneficiaryConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.AddorgBenefValidateCommand;

public class AddorgValidateCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return AddorgBenefValidateCommand.class.isAssignableFrom(clazz);
	}


	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"billerReferenceNum",
				ResponseIdConstants.ADD_ORG_VALIDATIONCONTROLLER_BENEFICIARY
				+ ResponseIdConstants.HYPHEN_SEPERATOR + AddOrgBeneficiaryConstants.BILLER_REFNUM_EMPTY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"billerNickName",
				ResponseIdConstants.ADD_ORG_VALIDATIONCONTROLLER_BENEFICIARY
				+ ResponseIdConstants.HYPHEN_SEPERATOR + AddOrgBeneficiaryConstants.BILLER_NICKNAME_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"billerId",
				ResponseIdConstants.ADD_ORG_VALIDATIONCONTROLLER_BENEFICIARY
				+ ResponseIdConstants.HYPHEN_SEPERATOR + AddOrgBeneficiaryConstants.BILLER_BILLERID_EMPTY);

	}

}
