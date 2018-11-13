package com.barclays.bmg.mvc.controller.fundtransfer.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ExternalFundTransferDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.ExternalFTPayInfoCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.fundtransfer.external.ExternalFundTransferDataOperation;
import com.barclays.bmg.operation.fundtransfer.external.GetPaymentReasonDetailsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.ExternalFundTransferDataOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetPaymentReasonDetailsOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFundTransferDataOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetPaymentReasonDetailsOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class RetrievePayeeInformationController extends BMBAbstractCommandController {

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private ExternalFundTransferDataOperation externalFundTransferDataOperation;
	private GetPaymentReasonDetailsOperation getPaymentReasonDetailsOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;
	private String payGrp;
	private String txnType;



	@Override
	protected String getActivityId(Object command) {
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		ExternalFTPayInfoCommand payeeInformationCommand = (ExternalFTPayInfoCommand) command;

		Context context = createContext(httpRequest);

		context.setActivityId(activityId);


		//set account map for ecrime purpose

		setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER);

		// Get the selected account.

		GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
		getSelectedAccountOperationRequest.setContext(context);
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(payeeInformationCommand.getFrActNo(),httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER));
		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = getSelectedAccountOperation.getSelectedSourceAccount(getSelectedAccountOperationRequest);

		// Retrieve Payee info.
		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();
		retrievePayeeInfoOperationRequest.setContext(context);

		retrievePayeeInfoOperationRequest.setPayId(payeeInformationCommand.getPayId());
		retrievePayeeInfoOperationRequest.setPayGrp(payGrp);
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

		// Get the required External FT data.
		ExternalFundTransferDataOperationRequest externalFundTransferDataOperationRequest = new ExternalFundTransferDataOperationRequest();
		externalFundTransferDataOperationRequest.setContext(context);

		ExternalFundTransferDataOperationResponse externalFundTransferDataOperationResponse =
						externalFundTransferDataOperation.getExternalFundTransferData(externalFundTransferDataOperationRequest);


		// Get the Payment Rson and Payment Dtls.
		GetPaymentReasonDetailsOperationRequest getPaymentReasonDetailsOperationRequest = new GetPaymentReasonDetailsOperationRequest();
		getPaymentReasonDetailsOperationRequest.setContext(context);

		GetPaymentReasonDetailsOperationResponse getPaymentReasonDetailsOperationResponse = getPaymentReasonDetailsOperation.getPaymentRsonDtls(getPaymentReasonDetailsOperationRequest);

		// Get the transaction Limit.
		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);
/*		TransactionLimitOperationResponse transactionLimitOperationResponse = transactionLimitOperation.getAValidDailyLimit(transactionLimitOperationRequest);

		if(getSelectedAccountOperationResponse.isSuccess() && retrievePayeeInfoOperationResponse.isSuccess() &&
				externalFundTransferDataOperationResponse.isSuccess() && transactionLimitOperationResponse.isSuccess()){
			setResponseInProcessMap(httpRequest, getSelectedAccountOperationResponse,retrievePayeeInfoOperationResponse,externalFundTransferDataOperationResponse);
		}

		setTxnTypeInResponses(txnType,getSelectedAccountOperationResponse,retrievePayeeInfoOperationResponse,externalFundTransferDataOperationResponse,transactionLimitOperationResponse,getPaymentReasonDetailsOperationResponse);
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(getSelectedAccountOperationResponse,retrievePayeeInfoOperationResponse,externalFundTransferDataOperationResponse,transactionLimitOperationResponse,getPaymentReasonDetailsOperationResponse);
*/

		if(getSelectedAccountOperationResponse.isSuccess() && retrievePayeeInfoOperationResponse.isSuccess() &&
				externalFundTransferDataOperationResponse.isSuccess()){
			setResponseInProcessMap(httpRequest, getSelectedAccountOperationResponse,retrievePayeeInfoOperationResponse,externalFundTransferDataOperationResponse);
		}

		setTxnTypeInResponses(txnType,getSelectedAccountOperationResponse,retrievePayeeInfoOperationResponse,externalFundTransferDataOperationResponse,getPaymentReasonDetailsOperationResponse);
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(getSelectedAccountOperationResponse,retrievePayeeInfoOperationResponse,externalFundTransferDataOperationResponse,getPaymentReasonDetailsOperationResponse);


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
	 * @param httprequest
	 * @param response
	 *
	 * Set  response in process map for next step
	 */
	private void setResponseInProcessMap(HttpServletRequest httprequest,ResponseContext... responses){

		// As there is no process map to carry object setting it in session.
		ExternalFundTransferDTO externalFundTransferDTO = new ExternalFundTransferDTO();

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse) responses[0];
		externalFundTransferDTO.setSourceAcct(getSelectedAccountOperationResponse.getSelectedAcct());

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responses[1];
		externalFundTransferDTO.setBeneficiaryDTO(retrievePayeeInfoOperationResponse.getBeneficiaryDTO());

		ExternalFundTransferDataOperationResponse externalFundTransferDataOperationResponse = (ExternalFundTransferDataOperationResponse)responses[2];
		externalFundTransferDTO.setTxnType(txnType);
		externalFundTransferDTO.setChargeDesc(externalFundTransferDataOperationResponse.getChargeDesc());
		setIntoProcessMap(httprequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, FundTransferConstants.FUND_TRANSFER_DTO,externalFundTransferDTO);
		//setSessionAttribute(httprequest, FundTransferConstants.FUND_TRANSFER_DTO,externalFundTransferDTO);
	}

	public RetrievePayeeInfoOperation getRetrievePayeeInfoOperation() {
		return retrievePayeeInfoOperation;
	}

	public void setRetrievePayeeInfoOperation(
			RetrievePayeeInfoOperation retrievePayeeInfoOperation) {
		this.retrievePayeeInfoOperation = retrievePayeeInfoOperation;
	}

	public ExternalFundTransferDataOperation getExternalFundTransferDataOperation() {
		return externalFundTransferDataOperation;
	}

	public void setExternalFundTransferDataOperation(
			ExternalFundTransferDataOperation externalFundTransferDataOperation) {
		this.externalFundTransferDataOperation = externalFundTransferDataOperation;
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

	public String getPayGrp() {
		return payGrp;
	}

	public void setPayGrp(String payGrp) {
		this.payGrp = payGrp;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public GetPaymentReasonDetailsOperation getGetPaymentReasonDetailsOperation() {
		return getPaymentReasonDetailsOperation;
	}

	public void setGetPaymentReasonDetailsOperation(
			GetPaymentReasonDetailsOperation getPaymentReasonDetailsOperation) {
		this.getPaymentReasonDetailsOperation = getPaymentReasonDetailsOperation;
	}

}
