package com.barclays.bmg.mvc.controller.bankdraft;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.ExternalPayeeDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class BankDraftPayeeDetailsController  extends
BMBAbstractCommandController{

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String transactionTypeKey;
	private String transactionType;
	private String activityId;

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		ExternalPayeeDetailsCommand payeeInformationCommand = (ExternalPayeeDetailsCommand) command;
		Context context = createContext(httpRequest);

		context.setActivityId(activityId);
		// Retrieve Payee info.
		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();
		retrievePayeeInfoOperationRequest.setContext(context);

		retrievePayeeInfoOperationRequest.setPayId(payeeInformationCommand
				.getPayId());
		retrievePayeeInfoOperationRequest
				.setPayGrp(getTransactionTypeKey());
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation
				.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

		return (BMBBaseResponseModel) bmbJSONBuilder
									.createMultipleJSONResponse(retrievePayeeInfoOperationResponse);
	}

	public RetrievePayeeInfoOperation getRetrievePayeeInfoOperation() {
		return retrievePayeeInfoOperation;
	}

	public void setRetrievePayeeInfoOperation(
			RetrievePayeeInfoOperation retrievePayeeInfoOperation) {
		this.retrievePayeeInfoOperation = retrievePayeeInfoOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public String getTransactionTypeKey() {
		return transactionTypeKey;
	}

	public void setTransactionTypeKey(String transactionTypeKey) {
		this.transactionTypeKey = transactionTypeKey;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
