package com.barclays.bmg.mvc.controller.fundtransfer.internal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeListOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;

public class InternalFundtransferInitController extends BMBAbstractController {

    private RetrieveAccountListOperation retrieveAccountListOperation;
    private RetrievePayeeListOperation retrievePayeeListOperation;
    private TransactionLimitOperation transactionLimitOperation;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

    @Override
    protected String getActivityId() {

	return ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID;
    }

    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
	setFirstStep(httpRequest);
	cleanProcess(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
	clearCorrelationIds(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);

	Context context = createContext(httpRequest);
	context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID);
	// Get the payee List.
	RetrievePayeeListOperationRequest retrievePayeeListOperationRequest = new RetrievePayeeListOperationRequest();
	retrievePayeeListOperationRequest.setContext(context);
	retrievePayeeListOperationRequest.setPayGrp(FundTransferConstants.PAYEE_TYPE_INTERNAL);

	RetrievePayeeListOperationResponse retrievePayeeListOperationResponse = retrievePayeeListOperation
		.retrievePayeeList(retrievePayeeListOperationRequest);

	RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
	retrieveAcctListOperationRequest.setContext(context);
	// Source Account List.
	RetrieveAcctListOperationResponse srcActLstOperationResponse = retrieveAccountListOperation
		.getCASASourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);

	// Get Transaction Limit.
	TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
	transactionLimitOperationRequest.setContext(context);

	// TransactionLimitOperationResponse transactionLimitOperationResponse =
	// transactionLimitOperation.getAValidDailyLimit(transactionLimitOperationRequest);

	if (srcActLstOperationResponse.isSuccess()) {
	    mapCorrelationIds(srcActLstOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest, srcActLstOperationResponse,
		    BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
	}

	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrievePayeeListOperationResponse, srcActLstOperationResponse);
    }

    public RetrieveAccountListOperation getRetrieveAccountListOperation() {
	return retrieveAccountListOperation;
    }

    public void setRetrieveAccountListOperation(RetrieveAccountListOperation retrieveAccountListOperation) {
	this.retrieveAccountListOperation = retrieveAccountListOperation;
    }

    public RetrievePayeeListOperation getRetrievePayeeListOperation() {
	return retrievePayeeListOperation;
    }

    public void setRetrievePayeeListOperation(RetrievePayeeListOperation retrievePayeeListOperation) {
	this.retrievePayeeListOperation = retrievePayeeListOperation;
    }

    public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    public TransactionLimitOperation getTransactionLimitOperation() {
	return transactionLimitOperation;
    }

    public void setTransactionLimitOperation(TransactionLimitOperation transactionLimitOperation) {
	this.transactionLimitOperation = transactionLimitOperation;
    }

}
