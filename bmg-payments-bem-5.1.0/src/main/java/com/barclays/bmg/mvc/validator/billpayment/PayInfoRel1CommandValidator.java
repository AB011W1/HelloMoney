package com.barclays.bmg.mvc.validator.billpayment;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.mvc.command.billpayment.PayInfoCommandRel1;

public class PayInfoRel1CommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return PayInfoCommandRel1.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		PayInfoCommandRel1 command = (PayInfoCommandRel1) target;
		boolean valid = true;
		if(StringUtils.isEmpty(command.getPayId()) && StringUtils.isEmpty(command.getToActNo())){
			valid = false;
		}
		if(!StringUtils.isEmpty(command.getPayId()) && !StringUtils.isEmpty(command.getToActNo())){
			valid = false;
		}

		if(!valid){
			errors
			.reject(ResponseIdConstants.RETRIEVE_BP_PAYEE_INFORMATION_RESPONSE_ID
					+ ResponseIdConstants.HYPHEN_SEPERATOR + BillPaymentResponseCodeConstants.PAYMENT_BENEFICIARY_ID_EMPTY);
		}
	}

}
