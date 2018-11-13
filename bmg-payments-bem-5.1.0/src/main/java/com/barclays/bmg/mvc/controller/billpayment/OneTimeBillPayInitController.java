package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.OneTimeBillPayInitCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.payments.OneTimeBillPayOperation;
import com.barclays.bmg.operation.request.billpayment.OneTimeBillPayOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.billpayment.OneTimeBillPayOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class OneTimeBillPayInitController extends
		BMBAbstractCommandController {

	private RetrieveAccountListOperation retrieveAccountListOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private OneTimeBillPayOperation oneTimeBillPayOperation;

	private String activityId;
	private String txnType;


	public void setOneTimeBillPayOperation(
			OneTimeBillPayOperation oneTimeBillPayOperation) {
		this.oneTimeBillPayOperation = oneTimeBillPayOperation;
	}

	@Override
	protected String getActivityId(Object command) {

		return activityId; //PMT_BP_BILLPAY_ONETIME;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {

		Context context = createContext(httpRequest);
		context.setActivityId(activityId);
		clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);
		OneTimeBillPayInitCommand oneTimeBillPayInitCommand = (OneTimeBillPayInitCommand) command;

		OneTimeBillPayOperationRequest oneTimeBillPayOperationRequest=new OneTimeBillPayOperationRequest();
		oneTimeBillPayOperationRequest.setBillerId(oneTimeBillPayInitCommand.getBillerId());
		oneTimeBillPayOperationRequest.setContext(context);
		oneTimeBillPayOperationRequest.setBillerType(oneTimeBillPayInitCommand.getBillerType());
		OneTimeBillPayOperationResponse oneTimeBillPayOperationResponse=oneTimeBillPayOperation.getBillerDetails(oneTimeBillPayOperationRequest);


		// Get the source accounts.
		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
		retrieveAcctListOperationRequest.setContext(context);
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
				.getSourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);

		if(retrieveAcctListOperationResponse.isSuccess()){
			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest, retrieveAcctListOperationResponse, BMGProcessConstants.BILL_PAYMENT);
		}
		// Get Transaction Limit.

		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);

		//USSD
		TransactionLimitOperationResponse transactionLimitOperationResponse = new TransactionLimitOperationResponse();
		//transactionLimitOperation.getAValidDailyLimit(transactionLimitOperationRequest);

		setTxnTypeInResponses(txnType,retrieveAcctListOperationResponse,transactionLimitOperationResponse,oneTimeBillPayOperationResponse);

		if(checkAllOperationResponses(retrieveAcctListOperationResponse,transactionLimitOperationResponse,oneTimeBillPayOperationResponse)){

			TransactionDTO transactionDTO=oneTimeBillPayOperation.mergeBillerForOneTimePay(oneTimeBillPayOperationResponse.getBillerDTO(),oneTimeBillPayOperationRequest);
			transactionDTO.setTxnType(txnType);
			setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);


		}


		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(retrieveAcctListOperationResponse,transactionLimitOperationResponse,oneTimeBillPayOperationResponse);
	}


	private void setResponseInProcessMap(HttpServletRequest httpRequest,Object command,ResponseContext... responseContexts){
	}

	private void setTxnTypeInResponses(String txnType, ResponseContext... responses){
		if(responses!=null){
			for(ResponseContext response: responses){
				if(response!=null){
					response.setTxnTyp(txnType);
				}
			}
		}
	}

	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}


	public TransactionLimitOperation getTransactionLimitOperation() {
		return transactionLimitOperation;
	}

	public void setTransactionLimitOperation(
			TransactionLimitOperation transactionLimitOperation) {
		this.transactionLimitOperation = transactionLimitOperation;
	}


	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}
