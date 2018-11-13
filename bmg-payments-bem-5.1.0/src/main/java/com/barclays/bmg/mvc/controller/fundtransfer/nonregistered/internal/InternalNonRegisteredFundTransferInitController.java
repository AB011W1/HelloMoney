package com.barclays.bmg.mvc.controller.fundtransfer.nonregistered.internal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class InternalNonRegisteredFundTransferInitController extends BMBAbstractController {

	private RetrieveAccountListOperation retrieveAccountListOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	@Override
	protected String getActivityId() {

		//return ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID;
		return ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID;
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws Exception {
		setFirstStep(httpRequest);
		cleanProcess(httpRequest, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER);
		clearCorrelationIds(httpRequest, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER);

		Context context = createContext(httpRequest);
		//Working
		//context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID);
		//TODO: Need to change to this
		//context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_NONREGISTERED_PAYEE_ACTIVITY_ID);
		context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID);

		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
		retrieveAcctListOperationRequest.setContext(context);
		// Source Account List.
		RetrieveAcctListOperationResponse srcActLstOperationResponse = retrieveAccountListOperation.getCASASourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);

		// Get Transaction Limit.
		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);

		TransactionLimitOperationResponse transactionLimitOperationResponse = new TransactionLimitOperationResponse();//transactionLimitOperation.getAValidDailyLimit(transactionLimitOperationRequest);

		if(srcActLstOperationResponse.isSuccess() ){
			mapCorrelationIds(srcActLstOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest,srcActLstOperationResponse, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER);
		}

		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(srcActLstOperationResponse,transactionLimitOperationResponse);
	}

	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
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

	public void setTransactionLimitOperation(
			TransactionLimitOperation transactionLimitOperation) {
		this.transactionLimitOperation = transactionLimitOperation;
	}

}
