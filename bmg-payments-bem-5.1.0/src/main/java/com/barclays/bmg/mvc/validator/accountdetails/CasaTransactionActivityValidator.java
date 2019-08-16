package com.barclays.bmg.mvc.validator.accountdetails;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.accountdetails.CasaTransactionActivityCommand;

public class CasaTransactionActivityValidator implements Validator {

    public boolean supports(Class clazz) {
	return CasaTransactionActivityCommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
	String respID = ResponseIdConstants.CASA_ACTIVITY_RESPONSE_ID + "-";

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actNo", respID + AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_ACCOUNT_NO_EMPTY);

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "days", respID + AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_EMPTY_BIL_DATE);

	CasaTransactionActivityCommand command = (CasaTransactionActivityCommand) target;

	String txDays = command.getDays();

	if (txDays != null && !txDays.equals("")) {
	    if (!"currentperiod".equalsIgnoreCase(txDays)) {
		if (!txDays.matches("\\d*")) {
		    errors.reject(respID + AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_TXN_DAY);
		}
	    }
	}

    }

}
