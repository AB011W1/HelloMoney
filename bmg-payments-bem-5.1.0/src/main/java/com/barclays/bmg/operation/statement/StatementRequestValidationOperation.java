package com.barclays.bmg.operation.statement;

import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.statement.StatementValidationOperationRequest;
import com.barclays.bmg.operation.response.statement.StatmentValidateOperationResponse;

public class StatementRequestValidationOperation extends BMBCommonOperation {


	// validate the dates
	// 1. both date can't be same
	// 2. from date is less than Todate
	// 3. FromDate and ToDate are less than the current date
	// 4. both dates are past date not future dates

	public StatmentValidateOperationResponse statementValidate(
					StatementValidationOperationRequest request) {

		StatmentValidateOperationResponse  response = new StatmentValidateOperationResponse();

		//Long fromDateLong = Long.valueOf(request.getFromDate());

		Long toDateLong = Long.valueOf(request.getToDate());

		/*if(!DateUtils.isSameDate(fromDateLong, toDateLong)){
			response.setSuccess(false);
			response.setResCde(StatementResponseCodeConstant.STATEMENT_DATE_SAME);
			getMessage(response);
		}else */
		if (isToDateValid(toDateLong)) {

		}


return response;


	}

	private boolean isToDateValid(Long toDateLong) {


		return false;
	}




}
