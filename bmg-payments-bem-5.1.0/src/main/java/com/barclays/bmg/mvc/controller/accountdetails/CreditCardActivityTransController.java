package com.barclays.bmg.mvc.controller.accountdetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.StatementRequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardActivityTransCommand;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardStmtTransCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.CreditCardActivityTransOperation;
import com.barclays.bmg.operation.accountdetails.CreditCardStmtTransOperation;
import com.barclays.bmg.operation.accountdetails.request.CreditCardActivityTransOperationRequest;
import com.barclays.bmg.operation.accountdetails.request.CreditCardStmtTransOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CreditCardStmtTransOperationResponse;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardAccountActivityServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CreditCardTransActivityServiceResponse;
import com.barclays.bmg.utils.DateTimeUtil;

public class CreditCardActivityTransController extends BMBAbstractCommandController {

    private CreditCardActivityTransOperation creditCardActivityTransOperation;
    private BMBJSONBuilder bmbJsonBuilder;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {
		// TODO Auto-generated method stub
		CreditCardActivityTransCommand ccActivityTransCommand = (CreditCardActivityTransCommand) command;

		CreditCardActivityTransOperationRequest ccActivityOperationReq = makeRequest(httpRequest);

		ccActivityOperationReq.setAccountNumber(ccActivityTransCommand.getActNo());
		//Cards Migration
		ccActivityOperationReq.setStatementDate(DateTimeUtil.getDateFromStr(ccActivityTransCommand.getActivityDate(), StatementRequestConstants.SIMPLE_DATE_FORMAT));
		ccActivityOperationReq.setSequenceNumber(ccActivityTransCommand.getSequenceNumber());
		
		CreditCardTransActivityServiceResponse ccStmtTransOperationResp = creditCardActivityTransOperation
			.retrieveCreditCardActivityTrans(ccActivityOperationReq);
		ccStmtTransOperationResp.setCurrency(ccActivityTransCommand.getCurrency());

		return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(ccStmtTransOperationResp);

	}

	private CreditCardActivityTransOperationRequest makeRequest(HttpServletRequest request) {

		CreditCardActivityTransOperationRequest ccActivityOperationRequest = new CreditCardActivityTransOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.CREDIT_CARD_ACTIVITY_TRANS_ACTIVITY_ID);//check

	ccActivityOperationRequest.setContext(context);

	return ccActivityOperationRequest;

    }
	
	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return ActivityConstant.CREDIT_CARD_ACTIVITY_TRANS_ACTIVITY_ID;//check
	}
	
	public void setCreditCardActivityTransOperation(CreditCardActivityTransOperation creditCardActivityTransOperation) {
		this.creditCardActivityTransOperation = creditCardActivityTransOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;//development
	}


}