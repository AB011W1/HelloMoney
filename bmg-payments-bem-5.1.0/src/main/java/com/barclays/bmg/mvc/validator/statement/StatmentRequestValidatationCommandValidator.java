package com.barclays.bmg.mvc.validator.statement;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.StatementResponseCodeConstant;
import com.barclays.bmg.mvc.command.statement.StatmentRequestValidateCommand;

public class StatmentRequestValidatationCommandValidator implements Validator{


	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return StatmentRequestValidateCommand.class.isAssignableFrom(clazz);
	}


	@Override
	public void validate(Object target, Errors errors) {



		/*StatmentRequestValidateCommand requestValidateCommand = (StatmentRequestValidateCommand) target;

		Calendar fromCalendar =  DateUtils.parseStringToCalendar(requestValidateCommand.getFromDate(), StatementRequestConstants.SIMPLE_DATE_FORMAT);

		Calendar toCalendar =  DateUtils.parseStringToCalendar(requestValidateCommand.getToDate(), StatementRequestConstants.SIMPLE_DATE_FORMAT);

		Calendar currentcaldate = DateTimeUtil.getCurrentBusinessCalendar(requestValidateCommand.getContext());*/

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actNo",
				ResponseIdConstants.STATMENT_REQUEST_VALIDATE +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				StatementResponseCodeConstant.STATEMENT_ACCOUNT_NUMBER_NO_EMPTY);


	   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromDate",
				ResponseIdConstants.STATMENT_REQUEST_VALIDATE +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				StatementResponseCodeConstant.STATEMENT_FROMDATE_NO_EMPTY);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDate",
				ResponseIdConstants.STATMENT_REQUEST_VALIDATE +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				StatementResponseCodeConstant.STATEMENT_FROMTO_NO_EMPTY);

		/*if(!isBeforeDate(fromCalendar, toCalendar)){
			errors.reject(ResponseIdConstants.STATMENT_REQUEST_VALIDATE +
					ResponseIdConstants.HYPHEN_SEPERATOR +
					StatementResponseCodeConstant.STATEMENT_FROM_DATE_GRATER_TO_DATE);
		}
		if(!isBeforeDate(fromCalendar, currentcaldate) || !isBeforeDate(toCalendar, currentcaldate) ){
			errors.reject(ResponseIdConstants.STATMENT_REQUEST_VALIDATE +
					ResponseIdConstants.HYPHEN_SEPERATOR +
					StatementResponseCodeConstant.STATEMENT_FROM_OR_TO_DATE_GRATER_CURRENT_DATE);
		}*/


	}


private boolean isBeforeDate(Calendar fromCal, Calendar toCal){

	return fromCal.before(toCal);
}

}
