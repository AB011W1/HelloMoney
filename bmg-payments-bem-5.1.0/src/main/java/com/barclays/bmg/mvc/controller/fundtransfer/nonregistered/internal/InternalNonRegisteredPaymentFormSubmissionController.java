package com.barclays.bmg.mvc.controller.fundtransfer.nonregistered.internal;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.fundtransfer.nonregistered.internal.InternalNonRegisteredFTDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.formvalidation.InternalNonRegisteredFTFormValidateOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.fundtransfer.nonregistered.internal.InternalNonRegisteredlFTFormSubmissionOperation;
import com.barclays.bmg.operation.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationResponse;

public class InternalNonRegisteredPaymentFormSubmissionController extends BMBAbstractCommandController {

    private RetrieveAccountListOperation retrieveAccountListOperation;
    private TransactionLimitOperation transactionLimitOperation;
    private InternalNonRegisteredlFTFormSubmissionOperation internalNonRegisteredFTFormSubmissionOperation;
    private RetrieveInternalNonRegisteredPayeeInfoOperation retrieveInternalNonRegisteredPayeeInfoOperation;
    private GetSelectedAccountOperation getSelectedAccountOperation;
    private InternalNonRegisteredFTFormValidateOperation formValidateOperation;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private String activityId;

    @Override
    protected String getActivityId(Object command) {
	return ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_NONREGISTERED_PAYEE_ACTIVITY_ID;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	Context context = createContext(httpRequest);
	// Correct
	// context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_NONREGISTERED_PAYEE_ACTIVITY_ID);
	// Test
	context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID);

	InternalNonRegisteredFTDetailsCommand internalFTCommand = (InternalNonRegisteredFTDetailsCommand) command;

	GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
	getSelectedAccountOperationRequest.setContext(context);
	GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = null;

	String CreditCardFlag = (String)httpRequest.getParameterMap().get("CREDIT_CARD_TRAN");

	if (httpRequest.getParameterMap().containsKey("CREDIT_CARD_TRAN")) {
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(internalFTCommand.getFrActNo(),
				httpRequest,BMGProcessConstants.CREDIT_PAYMENT));
		getSelectedAccountOperationResponse = getSelectedAccountOperation
				.getSelectedCreditCardAccount(getSelectedAccountOperationRequest);
	} else {

	getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(internalFTCommand.getFrActNo(), httpRequest,
		BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER));
	// getSelectedAccountOperationRequest.setAcctNumber(internalFTCommand.getFrActNo());
	 getSelectedAccountOperationResponse = getSelectedAccountOperation.getSelectedSourceAccount(getSelectedAccountOperationRequest);

	}

	// set account map for ecrime purpose
	setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER);

	// TODO: complete the code below to validate the account details-
	RetrieveInternalNonRegisteredPayeeInfoOperationRequest retrieveInternalNonRegisteredPayeeInfoOperationRequest = new RetrieveInternalNonRegisteredPayeeInfoOperationRequest();
	retrieveInternalNonRegisteredPayeeInfoOperationRequest.setContext(context);

	buildAddBeneficiaryOperationRequest(internalFTCommand, retrieveInternalNonRegisteredPayeeInfoOperationRequest);

	RetrieveInternalNonRegisteredPayeeInfoOperationResponse retrieveInternalNonRegisteredPayeeInfoOperationResponse = retrieveInternalNonRegisteredPayeeInfoOperation
		.retrievePayeeInfo(retrieveInternalNonRegisteredPayeeInfoOperationRequest);

	FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();
	FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();

	if (getSelectedAccountOperationResponse.isSuccess() && retrieveInternalNonRegisteredPayeeInfoOperationResponse.isSuccess()) {

		// Set OpCode for OtherBarclays FundTransfer - CPB 31/05/2017
		if(context.getActivityId().equals("PMT_FT_INTERNAL_ONETIME") && context.getBusinessId().equals("KEBRB")){
			context.setOpCde(httpRequest.getParameter("opCde"));
		}

	    formValidateOperationRequest.setContext(context);
	    formValidateOperationRequest.setFrmAct(getSelectedAccountOperationResponse.getSelectedAcct());
	    Amount txnAmount = new Amount();
	    txnAmount.setAmount(new BigDecimal(internalFTCommand.getTxnAmt()));

	    if (internalFTCommand.getCurr() != null) {
		txnAmount.setCurrency(internalFTCommand.getCurr());
	    } else {
		txnAmount.setCurrency(retrieveInternalNonRegisteredPayeeInfoOperationResponse.getCasaAccountDTO().getCurrency());
	    }
	    formValidateOperationRequest.setTxnAmt(txnAmount);
	    formValidateOperationRequest.setTxnType(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL);


	    // Changed for Currency validation in Kenya
	    /*if (CommonConstants.TZBRB_BUSINESS_ID.equals(context.getBusinessId())
		    || CommonConstants.KEBRB_BUSINESS_ID.equals(context.getBusinessId())
		    || CommonConstants.AEBRB_BUSINESS_ID.equals(context.getBusinessId())
		    || CommonConstants.UGBRB_BUSINESS_ID.equals(context.getBusinessId())
	    	|| CommonConstants.ZMBRB_BUSINESS_ID.equals(context.getBusinessId())
	    	|| CommonConstants.GHBRB_BUSINESS_ID.equals(context.getBusinessId())){*/
        //Defect #2156 Removing country validation since it is applicable for all the countries, as discussed with mandeep Bhatia
		formValidateOperation.currencyValidation(context, internalFTCommand.getCurr(), FundTransferConstants.LIST_VAL_CURR_SUPPORT_INT_ACC,
			getSelectedAccountOperationResponse.getSelectedAcct().getCurrency(), retrieveInternalNonRegisteredPayeeInfoOperationResponse
				.getCasaAccountDTO().getCurrencyCode().getCurrency(), formValidateOperationResponse);
	   // }

		if(httpRequest.getParameterMap().containsKey("CREDIT_CARD_TRAN")){
			formValidateOperationRequest.setCreditCardFlag("CREDIT_CARD_TRAN");
		}

	    if (formValidateOperationResponse.isSuccess()) {
		formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
	    }

	    if (formValidateOperationResponse.isSuccess()) {

		String txnRefNo = formValidateOperationRequest.getContext().getOrgRefNo();
		formValidateOperationResponse.setTxnRefNo(txnRefNo);

		setResponseInProcessMap(httpRequest, internalFTCommand, getSelectedAccountOperationResponse,
			retrieveInternalNonRegisteredPayeeInfoOperationResponse, formValidateOperationResponse);
	    }
	}

	formValidateOperationResponse.setTxnNot(internalFTCommand.getPayDesc());
	formValidateOperationResponse.setTxnDt(internalFTCommand.getTxnDt());

	BMBPayload bmbPayload = (BMBPayload) bmbJSONBuilder.createMultipleJSONResponse(getSelectedAccountOperationResponse,
		retrieveInternalNonRegisteredPayeeInfoOperationResponse, formValidateOperationResponse);

	return bmbPayload;
    }

    public RetrieveInternalNonRegisteredPayeeInfoOperation getRetrieveInternalNonRegisteredPayeeInfoOperation() {
	return retrieveInternalNonRegisteredPayeeInfoOperation;
    }

    public void setRetrieveInternalNonRegisteredPayeeInfoOperation(
	    RetrieveInternalNonRegisteredPayeeInfoOperation retrieveInternalNonRegisteredPayeeInfoOperation) {
	this.retrieveInternalNonRegisteredPayeeInfoOperation = retrieveInternalNonRegisteredPayeeInfoOperation;
    }

    private RetrieveInternalNonRegisteredPayeeInfoOperationRequest buildAddBeneficiaryOperationRequest(
	    InternalNonRegisteredFTDetailsCommand internalFTCommand, RetrieveInternalNonRegisteredPayeeInfoOperationRequest request) {
	request.setBranchCode(internalFTCommand.getBeneficiaryBranchCode());
	request.setAccountNumber(internalFTCommand.getBeneficiaryAccountNumber());
	request.setBeneficiaryName(internalFTCommand.getBeneficiaryName());
	return request;

    }

    /**
     * @param httprequest
     * @param response
     *
     *            Set payment response in Process Map for next Execution Operation.
     */
    private void setResponseInProcessMap(HttpServletRequest httpRequest, Object command, ResponseContext... responseContexts) {

	GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
	RetrieveInternalNonRegisteredPayeeInfoOperationResponse retrieveInternalNonRegisteredPayeeInfoOperationResponse = (RetrieveInternalNonRegisteredPayeeInfoOperationResponse) responseContexts[1];
	FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[2];
	InternalNonRegisteredFTDetailsCommand interFundTransferCommand = (InternalNonRegisteredFTDetailsCommand) command;
	TransactionDTO transactionDTO = new TransactionDTO();
	transactionDTO.setSourceAcct(selSourceAcctOpResp.getSelectedAcct());
	transactionDTO.setTxnType(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL);
	BeneficiaryDTO beneficiaryDTO = retrieveInternalNonRegisteredPayeeInfoOperationResponse.getBeneficiaryDTO();
	beneficiaryDTO.setDestinationAccount(retrieveInternalNonRegisteredPayeeInfoOperationResponse.getCasaAccountDTO());
	transactionDTO.setBeneficiaryDTO(beneficiaryDTO);
	transactionDTO.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
	transactionDTO.setTxnAmt(formValidateOperationResponse.getTxnAmt());
	transactionDTO.setTxnNot(formValidateOperationResponse.getTxnNot());
	transactionDTO.setTxnNot(interFundTransferCommand.getPayDesc());
	transactionDTO.setScndLvlauthReq(formValidateOperationResponse.isScndLvlauthReq());
	transactionDTO.setScndLvlAuthTyp(formValidateOperationResponse.getScndLvlAuthTyp());
	transactionDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
	setIntoProcessMap(httpRequest, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER, FundTransferConstants.TXN_REF_NO,
		formValidateOperationResponse.getContext().getOrgRefNo());
	setIntoProcessMap(httpRequest, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER, FundTransferConstants.TRANSACTION_DTO,
		transactionDTO);
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

    public InternalNonRegisteredFTFormValidateOperation getFormValidateOperation() {
	return formValidateOperation;
    }

    public void setFormValidateOperation(InternalNonRegisteredFTFormValidateOperation formValidateOperation) {
	this.formValidateOperation = formValidateOperation;
    }

    public InternalNonRegisteredlFTFormSubmissionOperation getInternalNonRegisteredFTFormSubmissionOperation() {
	return internalNonRegisteredFTFormSubmissionOperation;
    }

    public void setInternalNonRegisteredFTFormSubmissionOperation(
	    InternalNonRegisteredlFTFormSubmissionOperation internalNonRegisteredFTFormSubmissionOperation) {
	this.internalNonRegisteredFTFormSubmissionOperation = internalNonRegisteredFTFormSubmissionOperation;
    }

    public GetSelectedAccountOperation getGetSelectedAccountOperation() {
	return getSelectedAccountOperation;
    }

    public void setGetSelectedAccountOperation(GetSelectedAccountOperation getSelectedAccountOperation) {
	this.getSelectedAccountOperation = getSelectedAccountOperation;
    }

    public RetrieveAccountListOperation getRetrieveAccountListOperation() {
	return retrieveAccountListOperation;
    }

    public void setRetrieveAccountListOperation(RetrieveAccountListOperation retrieveAccountListOperation) {
	this.retrieveAccountListOperation = retrieveAccountListOperation;
    }

    public TransactionLimitOperation getTransactionLimitOperation() {
	return transactionLimitOperation;
    }

    public void setTransactionLimitOperation(TransactionLimitOperation transactionLimitOperation) {
	this.transactionLimitOperation = transactionLimitOperation;
    }

}
