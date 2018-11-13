package com.barclays.bmg.mvc.controller.bankdraft;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeListOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;

public class BankDraftStepOneController extends BMBAbstractController {

	private BMBJSONBuilder bmbJSONBuilder;
	private RetrievePayeeListOperation retrievePayeeListOperation;
	private RetrieveAccountListOperation retrieveSourceAccountListOperation;
	private String payGroup;
	private String activityId;

	public String getPayGroup() {
		return payGroup;
	}

	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}

	/**
	 *
	 */
	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Clear the co-relation ids.
		clearCorrelationIds(request, BMGProcessConstants.BANK_DRAFT_PROCESS_KEY);
		setFirstStep(request);
		// Build Context
		Context context = createContext(request);
		context.setActivityId(activityId);
		// Retrieve PayeeList
		// a) Create The Request
		RetrievePayeeListOperationRequest retrievePayeeOperationRequest = new RetrievePayeeListOperationRequest();
		// b) Set The Context
		retrievePayeeOperationRequest.setContext(context);
		retrievePayeeOperationRequest.setPayGrp(getPayGroup());
		// c) Invoke the Service
		RetrievePayeeListOperationResponse retrievePayeeListOperationResponse = retrievePayeeListOperation
				.retrievePayeeList(retrievePayeeOperationRequest);

		// Retrieve the SourceAccount List
		// a) Create The Request
		RetrieveAcctListOperationRequest retrieveAccountListOperationReq = new RetrieveAcctListOperationRequest();
		// b) Set The Context
		retrieveAccountListOperationReq.setContext(context);
		// c) Invoke the Service
		RetrieveAcctListOperationResponse retrieveSourceAccountOperationResponse = retrieveSourceAccountListOperation
				.getCASASourceAccounts(retrieveAccountListOperationReq);

		if (retrieveSourceAccountOperationResponse.isSuccess()) {
			mapCorrelationIds(retrieveSourceAccountOperationResponse
					.getAcctList(), retrievePayeeOperationRequest, request,retrieveSourceAccountOperationResponse, BMGProcessConstants.BANK_DRAFT_PROCESS_KEY);
		}

		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
				.createMultipleJSONResponse(retrievePayeeListOperationResponse,
						retrieveSourceAccountOperationResponse);

	}

	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public RetrievePayeeListOperation getRetrievePayeeListOperation() {
		return retrievePayeeListOperation;
	}

	public void setRetrievePayeeListOperation(
			RetrievePayeeListOperation retrievePayeeListOperation) {
		this.retrievePayeeListOperation = retrievePayeeListOperation;
	}

	public RetrieveAccountListOperation getRetrieveSourceAccountListOperation() {
		return retrieveSourceAccountListOperation;
	}

	public void setRetrieveSourceAccountListOperation(
			RetrieveAccountListOperation retrieveSourceAccountListOperation) {
		this.retrieveSourceAccountListOperation = retrieveSourceAccountListOperation;
	}

	@Override
	protected String getActivityId() {

		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
