package com.barclays.bmg.mvc.controller.accountdetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.CasaTransactionActivityCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.CASADetailsOperation;
import com.barclays.bmg.operation.accountdetails.CasaTransactionActivityOperation;
import com.barclays.bmg.operation.accountdetails.request.CasaTransactionActivityOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionActivityOperationResponse;

public class CasaTransactionActivityController extends BMBAbstractCommandController {

    private CasaTransactionActivityOperation casaTransactionActivityOperation;
    private BMBJSONBuilder bmbJsonBuilder;
    private CASADetailsOperation casaDetailsOperation;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	setLastStep(httpRequest);
	Context context = createContext(httpRequest);

	CasaTransactionActivityCommand casaTranxActvCommand = (CasaTransactionActivityCommand) command;

	CasaTransactionActivityOperationRequest casaTranxActvOperationReq = makeRequest(httpRequest, context);
	casaTranxActvOperationReq.setAccountNo(getAccountNumber(casaTranxActvCommand.getActNo(), httpRequest, BMGProcessConstants.ACCOUNTS_PROCESS));
	if(casaTranxActvOperationReq.getAccountNo()==null)
		casaTranxActvOperationReq.setAccountNo(casaTranxActvCommand.getActNo());
	casaTranxActvOperationReq.setDays(casaTranxActvCommand.getDays());
	casaTranxActvOperationReq.setBrnCde(casaTranxActvCommand.getBrnCde());

	CasaTransactionActivityOperationResponse casaTranxActvOperationResp = casaTransactionActivityOperation
		.retrieveCasaTransactionActivity(casaTranxActvOperationReq);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(casaTranxActvOperationResp);

    }

    /**
     * makes CasaTransactionActivityOperationRequest object
     *
     * @param request
     * @param context
     * @return
     */
    private CasaTransactionActivityOperationRequest makeRequest(HttpServletRequest request, Context context) {

	CasaTransactionActivityOperationRequest casaTransactionActivityOperationRequest = new CasaTransactionActivityOperationRequest();

	context.setActivityId(ActivityConstant.MINI_STMT_ACTIVITY_ID);

	casaTransactionActivityOperationRequest.setContext(context);

	return casaTransactionActivityOperationRequest;

    }

    public void setCasaTransactionActivityOperation(CasaTransactionActivityOperation casaTransactionActivityOperation) {
	this.casaTransactionActivityOperation = casaTransactionActivityOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    @Override
    protected String getActivityId(Object command) {
	return ActivityConstant.MINI_STMT_ACTIVITY_ID;
    }

    public CASADetailsOperation getCasaDetailsOperation() {
	return casaDetailsOperation;
    }

    public void setCasaDetailsOperation(CASADetailsOperation casaDetailsOperation) {
	this.casaDetailsOperation = casaDetailsOperation;
    }

}
