package com.barclays.bmg.mvc.controller.fundtransfer.own;

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
import com.barclays.bmg.operation.request.fundtransfer.own.ValidateAccountListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.own.ValidateAccountListOperationResponse;
import com.barclays.bmg.operation.validation.fundtransfer.own.ValidateAccountListOperation;

public class OwnFundTransferInitController extends BMBAbstractController{

	private RetrieveAccountListOperation retrieveAccountListOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private ValidateAccountListOperation validateAccountListOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

	@Override
	protected String getActivityId() {
		return ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID;
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws Exception {

		setFirstStep(httpRequest);
		cleanProcess(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
		clearCorrelationIds(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
		Context context = createContext(httpRequest);
		//Kadikope
		String creditcardToCASAFlag = (String) httpRequest.getParameterMap().get("CreditCardFTCASA");

		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();

		TransactionLimitOperationResponse transactionLimitOperationResponse = null;

		retrieveAcctListOperationRequest.setContext(context);
		retrieveAcctListOperationRequest.getContext().setActivityId(ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID);
		RetrieveAcctListOperationResponse srcActLstOperationResponse =null;

		if(creditcardToCASAFlag == null){
		// Source Account List.
		 srcActLstOperationResponse = retrieveAccountListOperation.getSourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);
		}

		// Destination Account List.
		RetrieveAcctListOperationResponse destActLstOperationResponse = retrieveAccountListOperation.getDestAccountsForLocalCurrency(retrieveAcctListOperationRequest);
		// Validate Account List.
		ValidateAccountListOperationResponse validateAccountListOperationResponse = null;

		if(creditcardToCASAFlag == null){
			if(srcActLstOperationResponse.isSuccess() && destActLstOperationResponse.isSuccess()){
				ValidateAccountListOperationRequest validateAccountListOperationRequest = new ValidateAccountListOperationRequest();
				validateAccountListOperationRequest.setContext(context);
				validateAccountListOperationRequest.getContext().setActivityId(ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID);
				validateAccountListOperationRequest.setSrcAcctLst(srcActLstOperationResponse.getAcctList());
				validateAccountListOperationRequest.setDestAcctLst(destActLstOperationResponse.getAcctList());
				validateAccountListOperationResponse =
											validateAccountListOperation.validate(validateAccountListOperationRequest);

				// Get Transaction Limit...
				TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
				transactionLimitOperationRequest.setContext(context);
				transactionLimitOperationRequest.getContext().setActivityId(ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID);
				//USSD Not required
				//transactionLimitOperationResponse = transactionLimitOperation.getAValidDailyLimit(transactionLimitOperationRequest);
				transactionLimitOperationResponse = new TransactionLimitOperationResponse();
				//USSD Not required
				mapCorrelationIds(srcActLstOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest,srcActLstOperationResponse, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
				mapCorrelationIds(destActLstOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest,destActLstOperationResponse, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
			}
		} else {
			mapCorrelationIds(destActLstOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest,destActLstOperationResponse, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
		}

		if(creditcardToCASAFlag != null){
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(destActLstOperationResponse,validateAccountListOperationResponse,transactionLimitOperationResponse);
		}else {
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(srcActLstOperationResponse,destActLstOperationResponse,validateAccountListOperationResponse,transactionLimitOperationResponse);
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

	public ValidateAccountListOperation getValidateAccountListOperation() {
		return validateAccountListOperation;
	}

	public void setValidateAccountListOperation(
			ValidateAccountListOperation validateAccountListOperation) {
		this.validateAccountListOperation = validateAccountListOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

}
