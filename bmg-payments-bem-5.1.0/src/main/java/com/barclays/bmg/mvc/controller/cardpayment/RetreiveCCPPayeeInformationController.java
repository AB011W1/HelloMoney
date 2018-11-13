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
import com.barclays.bmg.mvc.command.billpayment.CCPPayeeInformationCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.MergeOwnCreditcardInfoOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeOwnCreditcardInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeOwnCreditcardInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class RetreiveCCPPayeeInformationController extends BMBAbstractCommandController {

	private RetrieveAccountListOperation retrieveAccountListOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private MergeOwnCreditcardInfoOperation mergeOwnCreditcardInfoOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private static final String txnType = "CCP";
	@Override
	protected String getActivityId(Object command) {
		return ActivityIdConstantBean.FUND_TRANSFER_CARD_PAYMENT_PAYEE_ACTIVITY_ID;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {

		Context context = createContext(httpRequest);
		context.setActivityId(getActivityId(command));
		clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);
		CCPPayeeInformationCommand ccpPayeeInformationCommand = (CCPPayeeInformationCommand)command;


		// Get the source accounts.
		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
		retrieveAcctListOperationRequest.setContext(context);
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
				.getCASASourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);



		// Get Transaction Limit.

		GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
		getSelectedAccountOperationRequest.setContext(context);

		// Get Source Account
		getSelectedAccountOperationRequest.setAcctNumber(ccpPayeeInformationCommand.getToActNo());
		GetSelectedAccountOperationResponse selSourceAcctOpResp = getSelectedAccountOperation.getSelectedDestinationAccount(getSelectedAccountOperationRequest);
		MergeOwnCreditcardInfoOperationResponse mergeOwnCreditcardInfoOperationResponse  = null;
		if(selSourceAcctOpResp.isSuccess()){
			MergeOwnCreditcardInfoOperationRequest mergeOwnCreditcardInfoOperationRequest = new MergeOwnCreditcardInfoOperationRequest();
			mergeOwnCreditcardInfoOperationRequest.setContext(context);
			mergeOwnCreditcardInfoOperationRequest.setCustomerAccountDTO(selSourceAcctOpResp.getSelectedAcct());
			mergeOwnCreditcardInfoOperationResponse = mergeOwnCreditcardInfoOperation.mergeOwnCreditCardInfo(mergeOwnCreditcardInfoOperationRequest);
		}

		// Merge the credit card info.
		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);

		TransactionLimitOperationResponse transactionLimitOperationResponse = transactionLimitOperation
				.getAValidDailyLimit(transactionLimitOperationRequest);


		setTxnTypeInResponses(txnType, retrieveAcctListOperationResponse,
				selSourceAcctOpResp,
				mergeOwnCreditcardInfoOperationResponse,
				transactionLimitOperationResponse);

		if (checkAllOperationResponses(retrieveAcctListOperationResponse,
				selSourceAcctOpResp,
				mergeOwnCreditcardInfoOperationResponse,
				transactionLimitOperationResponse)) {
			setResponseInProcessMap(httpRequest,ccpPayeeInformationCommand, mergeOwnCreditcardInfoOperationResponse,
					transactionLimitOperationResponse);
		}

		if(retrieveAcctListOperationResponse.isSuccess()){
			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest, retrieveAcctListOperationResponse, BMGProcessConstants.BILL_PAYMENT);
			retrieveAcctListOperationResponse.setContext(retrieveAcctListOperationRequest.getContext());
		}

		return (BMBBaseResponseModel) bmbJSONBuilder
			.createMultipleJSONResponse(retrieveAcctListOperationResponse,
					selSourceAcctOpResp,
					mergeOwnCreditcardInfoOperationResponse,
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

		MergeOwnCreditcardInfoOperationResponse mergeOwnCreditcardInfoOperationResponse = (MergeOwnCreditcardInfoOperationResponse)responseContexts[0];
		if(mergeOwnCreditcardInfoOperationResponse!=null){
			TransactionDTO transactionDTO = new TransactionDTO();

			transactionDTO.setBeneficiaryDTO(mergeOwnCreditcardInfoOperationResponse.getBeneficiaryDTO());
			transactionDTO.setTxnType(txnType);

			setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
		}

	}

	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}

	public GetSelectedAccountOperation getGetSelectedAccountOperation() {
		return getSelectedAccountOperation;
	}

	public void setGetSelectedAccountOperation(
			GetSelectedAccountOperation getSelectedAccountOperation) {
		this.getSelectedAccountOperation = getSelectedAccountOperation;
	}

	public TransactionLimitOperation getTransactionLimitOperation() {
		return transactionLimitOperation;
	}

	public void setTransactionLimitOperation(
			TransactionLimitOperation transactionLimitOperation) {
		this.transactionLimitOperation = transactionLimitOperation;
	}

	public MergeOwnCreditcardInfoOperation getMergeOwnCreditcardInfoOperation() {
		return mergeOwnCreditcardInfoOperation;
	}

	public void setMergeOwnCreditcardInfoOperation(
			MergeOwnCreditcardInfoOperation mergeOwnCreditcardInfoOperation) {
		this.mergeOwnCreditcardInfoOperation = mergeOwnCreditcardInfoOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}


}
