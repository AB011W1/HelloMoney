package com.barclays.bmg.chequebook.mvc.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.chequebook.mvc.command.ChequeBookRequestValidationCommand;
import com.barclays.bmg.constants.ChequeBookResponseCodeConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;

public class ChequeBookRequestValidationCommandValidator implements Validator{

	private List<String> chequeBookTypeCountryList;

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return ChequeBookRequestValidationCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		//ChequeBookRequestValidationCommand command = (ChequeBookRequestValidationCommand)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actNo",
						ResponseIdConstants.CHEQUE_BOOK_CONFIRM +
						ResponseIdConstants.HYPHEN_SEPERATOR +
						ChequeBookResponseCodeConstant.CHEQUE_ACT_NO_EMPTY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bkSize",
				ResponseIdConstants.CHEQUE_BOOK_CONFIRM +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				ChequeBookResponseCodeConstant.CHEQUE_BOOK_SIZE_EMPTY);

		if(chequeBookTypeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bkTyp",
				ResponseIdConstants.CHEQUE_BOOK_CONFIRM +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				ChequeBookResponseCodeConstant.CHEQUE_BOOK_TYPE_EMPTY);
		}



	}

	public List<String> getChequeBookTypeCountryList() {
		return chequeBookTypeCountryList;
	}

	public void setChequeBookTypeCountryList(List<String> chequeBookTypeCountryList) {
		this.chequeBookTypeCountryList = chequeBookTypeCountryList;
	}

}
