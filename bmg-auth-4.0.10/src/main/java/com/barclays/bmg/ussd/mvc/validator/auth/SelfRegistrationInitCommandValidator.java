package com.barclays.bmg.ussd.mvc.validator.auth;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationInitCommand;

public class SelfRegistrationInitCommandValidator implements Validator {

    // START BRANCH CODE VALIDATION #UMESH
    private List<String> branchCodeCountryList;

    public List<String> getBranchCodeCountryList() {
	return branchCodeCountryList;
    }

    public void setBranchCodeCountryList(List<String> branchCodeCountryList) {
	this.branchCodeCountryList = branchCodeCountryList;
    }

    // END BRANCH CODE VALIDATION #UMESH

    @Override
    public boolean supports(Class clazz) {
	return SelfRegistrationInitCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNo", ResponseIdConstants.SECOND_AUTH_INIT_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.SECOND_AUTHCOMMAND_POSITION1_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountNo", ResponseIdConstants.SECOND_AUTH_INIT_RESPONSE_ID
		+ ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.SECOND_AUTHCOMMAND_POSITION2_EMPTY);

	// START BRANCH CODE VALIDATION #UMESH
	if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchCode", ResponseIdConstants.SECOND_AUTH_INIT_RESPONSE_ID
		    + ResponseIdConstants.HYPHEN_SEPERATOR + FundTransferResponseCodeConstants.SECOND_AUTHCOMMAND_TXREFKEY_EMPTY);

	}
	// END BRANCH CODE VALIDATION #UMESH
    }
}