package com.barclays.bmg.mvc.validator.bankdraft;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.lookup.BranchLookUpCommand;

public class BranchLookUpValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return BranchLookUpCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object bean, Errors errors) {


		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brnNam",ResponseIdConstants.BANK_DRAFT_SECOND_STEP
				+ ResponseIdConstants.HYPHEN_SEPERATOR+
				FundTransferResponseCodeConstants.BRANCH_LOOKUP_EMPTY_BRANCH_NAME);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cty",ResponseIdConstants.BANK_DRAFT_SECOND_STEP
				+ ResponseIdConstants.HYPHEN_SEPERATOR+
				FundTransferResponseCodeConstants.BRANCH_LOOKUP_EMPTY_CITY_NAME);


	}

}
