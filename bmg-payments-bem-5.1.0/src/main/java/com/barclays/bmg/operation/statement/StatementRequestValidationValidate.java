package com.barclays.bmg.operation.statement;

import java.util.Calendar;

import com.barclays.bmg.constants.StatementRequestConstants;
import com.barclays.bmg.constants.StatementResponseCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.mvc.command.statement.StatmentRequestValidateCommand;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.response.statement.StatmentValidateOperationResponse;
import com.barclays.bmg.utils.DateTimeUtil;
import com.barclays.bmg.utils.DateUtils;
public class StatementRequestValidationValidate extends BMBPaymentsOperation{


	public void dateValidation(Context context, StatmentRequestValidateCommand statmentRequestValidateCommand,
				ResponseContext... responseContexts){

		StatmentValidateOperationResponse statementExecuteOperationResponse = (StatmentValidateOperationResponse) responseContexts[0];;

	Calendar fromCalendar =  DateUtils.parseStringToCalendar(statmentRequestValidateCommand.getFromDate(), StatementRequestConstants.SIMPLE_DATE_FORMAT);

	Calendar toCalendar =  DateUtils.parseStringToCalendar(statmentRequestValidateCommand.getToDate(), StatementRequestConstants.SIMPLE_DATE_FORMAT);

	Calendar currentcaldate = DateTimeUtil.getCurrentBusinessCalendar(context);

	statementExecuteOperationResponse.setContext(context);
	if(!isBeforeDate(fromCalendar, toCalendar)){

		statementExecuteOperationResponse.setResCde(StatementResponseCodeConstant.STATEMENT_FROM_DATE_GRATER_TO_DATE);
		statementExecuteOperationResponse.setSuccess(false);
		getMessage(statementExecuteOperationResponse);

		/*errors.reject(ResponseIdConstants.STATMENT_REQUEST_VALIDATE +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				StatementResponseCodeConstant.STATEMENT_FROM_DATE_GRATER_TO_DATE);*/



	}
	if(!isBeforeDate(fromCalendar, currentcaldate) || !isBeforeDate(toCalendar, currentcaldate) ){

		statementExecuteOperationResponse.setResCde(StatementResponseCodeConstant.STATEMENT_FROM_OR_TO_DATE_GRATER_CURRENT_DATE);
		statementExecuteOperationResponse.setSuccess(false);
		getMessage(statementExecuteOperationResponse);
		/*
		errors.reject(ResponseIdConstants.STATMENT_REQUEST_VALIDATE +
				ResponseIdConstants.HYPHEN_SEPERATOR +
				StatementResponseCodeConstant.STATEMENT_FROM_OR_TO_DATE_GRATER_CURRENT_DATE);*/
	}
	}

	private boolean isBeforeDate(Calendar fromCal, Calendar toCal){

		return fromCal.before(toCal);
		}



}
