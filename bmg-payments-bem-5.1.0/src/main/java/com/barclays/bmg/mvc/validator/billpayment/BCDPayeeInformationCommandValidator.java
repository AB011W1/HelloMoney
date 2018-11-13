package com.barclays.bmg.mvc.validator.billpayment;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.BCDPayeeInformationCommand;

public class BCDPayeeInformationCommandValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

	return BCDPayeeInformationCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	BCDPayeeInformationCommand command = (BCDPayeeInformationCommand) target;

	if (command.getPayId() != null && command.getPayId().isEmpty() && command.getToActNo() != null && command.getToActNo().isEmpty())
	    errors.reject(ResponseIdConstants.RETRIEVE_BCD_PAYEE_INFORMATION_RESPONSE_ID + ResponseIdConstants.HYPHEN_SEPERATOR
		    + BillPaymentResponseCodeConstants.PAYMENT_BENEFICIARY_ID_EMPTY);

    }

}
