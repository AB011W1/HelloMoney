package com.barclays.bmg.cashsend.mvc.controller;

/**
 * @author E20044137
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

public class CashSendOneTimeInitController extends BMBAbstractController {

    private BMBJSONBuilder cashSendOneTimeJsonBldr;
    private RetrieveAccountListOperation retrieveAccountListOperation;
    private String activityId;

    @Override
    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */

    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse response) throws Exception {
	setFirstStep(request);

	clearCorrelationIds(request, BMGProcessConstants.CASH_SEND_PROCESS);

	Context context = buildContext(request);

	RetrieveAcctListOperationRequest retrieveAccountListOperationReq = new RetrieveAcctListOperationRequest();
	retrieveAccountListOperationReq.setContext(context);

	RetrieveAcctListOperationResponse retrieveSourceAccountOperationResponse = retrieveAccountListOperation
		.getCASASourceAccounts(retrieveAccountListOperationReq);

	if (retrieveSourceAccountOperationResponse.isSuccess()) {
	    mapCorrelationIds(retrieveSourceAccountOperationResponse.getAcctList(), retrieveAccountListOperationReq, request,
		    retrieveSourceAccountOperationResponse, BMGProcessConstants.CASH_SEND_PROCESS);
	}

	return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) cashSendOneTimeJsonBldr)
		.createMultipleJSONResponse(retrieveSourceAccountOperationResponse);

    }

    /**
     * This method takes in httpserquest object as the input parameter and builds the context.
     *
     * @param httpRequest
     * @return
     */
    protected Context buildContext(HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);
	context.setActivityId(this.getActivityId());
	return context;

    }

    public RetrieveAccountListOperation getRetrieveAccountListOperation() {
	return retrieveAccountListOperation;
    }

    public void setRetrieveAccountListOperation(RetrieveAccountListOperation retrieveAccountListOperation) {
	this.retrieveAccountListOperation = retrieveAccountListOperation;
    }

    public BMBJSONBuilder getCashSendOneTimeJsonBldr() {
	return cashSendOneTimeJsonBldr;
    }

    public void setCashSendOneTimeJsonBldr(BMBJSONBuilder cashSendOneTimeJsonBldr) {
	this.cashSendOneTimeJsonBldr = cashSendOneTimeJsonBldr;
    }

}
