package com.barclays.bmg.mvc.controller.bankdraft;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BankDraftTransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.ExternalFTPayInfoCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class BankDraftRetrievePayeeInformationController extends
		BMBAbstractCommandController {

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String transactionTypeKey;
	private String transactionType;
	private String activityId;

	@Override
	protected String getActivityId(Object command) {
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {
		ExternalFTPayInfoCommand payeeInformationCommand = (ExternalFTPayInfoCommand) command;
		Context context = createContext(httpRequest);

		context.setActivityId(activityId);

		//set account map for ecrime purpose

		setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.BANK_DRAFT_PROCESS_KEY);

		// Get the selected account.

		GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
		getSelectedAccountOperationRequest.setContext(context);
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(
				payeeInformationCommand.getFrActNo(), httpRequest, BMGProcessConstants.BANK_DRAFT_PROCESS_KEY));
		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = getSelectedAccountOperation
				.getSelectedSourceAccount(getSelectedAccountOperationRequest);

		// Retrieve Payee info.
		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();
		retrievePayeeInfoOperationRequest.setContext(context);

		retrievePayeeInfoOperationRequest.setPayId(payeeInformationCommand
				.getPayId());
		retrievePayeeInfoOperationRequest
				.setPayGrp(getTransactionTypeKey());
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation
				.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

		// Get the transaction Limit.
		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);
		TransactionLimitOperationResponse transactionLimitOperationResponse = transactionLimitOperation
				.getAValidDailyLimit(transactionLimitOperationRequest);

		if (getSelectedAccountOperationResponse.isSuccess()
				&& retrievePayeeInfoOperationResponse.isSuccess()
				&& transactionLimitOperationResponse.isSuccess()) {
			setResponseInProcessMap(httpRequest,
					getSelectedAccountOperationResponse,
					retrievePayeeInfoOperationResponse);
		}

		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(
						getSelectedAccountOperationResponse,
						retrievePayeeInfoOperationResponse,
						transactionLimitOperationResponse);
	}


	/**
	 * @param httprequest
	 * @param response
	 *
	 *            Set response in process map for next step
	 */
	private void setResponseInProcessMap(HttpServletRequest httprequest,
			ResponseContext... responses) {

		// As there is no process map to carry object setting it in session.
		BankDraftTransactionDTO transactionDTO = new BankDraftTransactionDTO();

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse) responses[0];
		transactionDTO.setSourceAcct(getSelectedAccountOperationResponse
				.getSelectedAcct());

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responses[1];
		transactionDTO.setBeneficiaryDTO(retrievePayeeInfoOperationResponse
				.getBeneficiaryDTO());

		transactionDTO.setTxnType(getTransactionType());
		setIntoProcessMap(httprequest,
				BMGProcessConstants.BANK_DRAFT_PROCESS_KEY,
				FundTransferConstants.BANK_DRAFT_DTO, transactionDTO);
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

	public GetSelectedAccountOperation getGetSelectedAccountOperation() {
		return getSelectedAccountOperation;
	}

	public void setGetSelectedAccountOperation(
			GetSelectedAccountOperation getSelectedAccountOperation) {
		this.getSelectedAccountOperation = getSelectedAccountOperation;
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

}
