package com.barclays.bmg.mvc.controller.cardpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.BCDPayeeInformationCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.MergeBarclayCardPayeeInfoOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeBarclayCardPayeeInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBarclayCardPayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class RetrieveBCDPayeeInformationController extends
		BMBAbstractCommandController {

	private MergeBarclayCardPayeeInfoOperation mergeBarclayCardPayeeInfoOperation;
	private RetrieveAccountListOperation retrieveAccountListOperation;
	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private static final String txnType = "BCD";

	@Override
	protected String getActivityId(Object command) {
		return ActivityIdConstantBean.BARCLAY_CARD_PAYMENT_ACTIVITY_ID;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {

		Context context = createContext(httpRequest);
		context.setActivityId(getActivityId(command));
		clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);
		BCDPayeeInformationCommand bcdPayeeInformationCommand = (BCDPayeeInformationCommand) command;

		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();

		retrievePayeeInfoOperationRequest.setPayId(bcdPayeeInformationCommand
				.getPayId());
		retrievePayeeInfoOperationRequest.setContext(context);
		retrievePayeeInfoOperationRequest.setPayGrp(txnType);
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation
				.retrievePayeeInfo(retrievePayeeInfoOperationRequest);
		MergeBarclayCardPayeeInfoOperationResponse mergeBarclayCardPayeeInfoOperationResponse = null;
		if (retrievePayeeInfoOperationResponse.isSuccess()) {
			MergeBarclayCardPayeeInfoOperationRequest mergeBarclayCardPayeeInfoOperationRequest = new MergeBarclayCardPayeeInfoOperationRequest();
			mergeBarclayCardPayeeInfoOperationRequest.setContext(context);
			mergeBarclayCardPayeeInfoOperationRequest
					.setBeneficiaryDTO(retrievePayeeInfoOperationResponse
							.getBeneficiaryDTO());
			mergeBarclayCardPayeeInfoOperationResponse = mergeBarclayCardPayeeInfoOperation
					.mergeBCDPayeeInfo(mergeBarclayCardPayeeInfoOperationRequest);
			retrievePayeeInfoOperationResponse.setBeneficiaryDTO(mergeBarclayCardPayeeInfoOperationResponse.getBeneficiaryDTO());
		}

		// Get the source accounts.
		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
		retrieveAcctListOperationRequest.setContext(context);
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
				.getCASASourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);

		// Get Transaction Limit.

		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);

		TransactionLimitOperationResponse transactionLimitOperationResponse = transactionLimitOperation
				.getAValidDailyLimit(transactionLimitOperationRequest);

		if (retrieveAcctListOperationResponse.isSuccess()) {
			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(),
					retrieveAcctListOperationRequest, httpRequest,
					retrieveAcctListOperationResponse, BMGProcessConstants.BILL_PAYMENT);
		}

		setTxnTypeInResponses(txnType, retrievePayeeInfoOperationResponse,
				mergeBarclayCardPayeeInfoOperationResponse,
				retrieveAcctListOperationResponse,
				transactionLimitOperationResponse);

		if (checkAllOperationResponses(retrievePayeeInfoOperationResponse,
				mergeBarclayCardPayeeInfoOperationResponse,
				retrieveAcctListOperationResponse,
				transactionLimitOperationResponse)) {
			setResponseInProcessMap(httpRequest, bcdPayeeInformationCommand,
					retrievePayeeInfoOperationResponse,
					mergeBarclayCardPayeeInfoOperationResponse,
					retrieveAcctListOperationResponse,
					transactionLimitOperationResponse);
		}

		return (BMBBaseResponseModel) bmbJSONBuilder
			.createMultipleJSONResponse(retrievePayeeInfoOperationResponse,mergeBarclayCardPayeeInfoOperationResponse,
				retrieveAcctListOperationResponse,
				transactionLimitOperationResponse);
	}


	/**
	 * @param txnType
	 * @param responses
	 */
	private void setTxnTypeInResponses(String txnType, ResponseContext... responses){
		if(responses!=null){
			for(ResponseContext response: responses){
				if(response!=null){
					response.setTxnTyp(txnType);
				}
			}
		}
	}

	/**
	 * @param httpRequest
	 * @param command
	 * @param responseContexts
	 */
	private void setResponseInProcessMap(HttpServletRequest httpRequest,Object command,ResponseContext... responseContexts){

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse)responseContexts[0];
		TransactionDTO transactionDTO = new TransactionDTO();

		transactionDTO.setBeneficiaryDTO(retrievePayeeInfoOperationResponse.getBeneficiaryDTO());
		transactionDTO.setTxnType(txnType);

		setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
	}

	public MergeBarclayCardPayeeInfoOperation getMergeBarclayCardPayeeInfoOperation() {
		return mergeBarclayCardPayeeInfoOperation;
	}

	public void setMergeBarclayCardPayeeInfoOperation(
			MergeBarclayCardPayeeInfoOperation mergeBarclayCardPayeeInfoOperation) {
		this.mergeBarclayCardPayeeInfoOperation = mergeBarclayCardPayeeInfoOperation;
	}

	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}

	public RetrievePayeeInfoOperation getRetrievePayeeInfoOperation() {
		return retrievePayeeInfoOperation;
	}

	public void setRetrievePayeeInfoOperation(
			RetrievePayeeInfoOperation retrievePayeeInfoOperation) {
		this.retrievePayeeInfoOperation = retrievePayeeInfoOperation;
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

	public static String getTxnType() {
		return txnType;
	}

}
