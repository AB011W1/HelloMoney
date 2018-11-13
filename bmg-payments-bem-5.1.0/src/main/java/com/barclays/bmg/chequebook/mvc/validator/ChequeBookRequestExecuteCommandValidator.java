package com.barclays.bmg.chequebook.mvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.chequebook.mvc.command.ChequeBookRequestExecuteCommand;
import com.barclays.bmg.constants.ChequeBookResponseCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;

public class ChequeBookRequestExecuteCommandValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return ChequeBookRequestExecuteCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "txnRefNo",
				ResponseIdConstants.CHEQUE_BOOK_EXECUTE +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				ChequeBookResponseCodeConstant.CHEQUE_REF_NO_EMPTY);


	}

}
