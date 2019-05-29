package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.mvc.command.billpayment.PayeeListCommand;
import com.barclays.bmg.mvc.command.billpayment.RetrieveBillDetailsCommand;

public class RetrieveBillDetailsCommandValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
    	return RetrieveBillDetailsCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	/*
	 * ValidationUtils.rejectIfEmptyOrWhitespace(errors,"payGrp", ResponseIdConstants.RETRIEVE_BP_PAYEE_LIST_RESPONSE_ID +
	 * ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYEE_GROUP_EMPTY);
	 */
    }

}
