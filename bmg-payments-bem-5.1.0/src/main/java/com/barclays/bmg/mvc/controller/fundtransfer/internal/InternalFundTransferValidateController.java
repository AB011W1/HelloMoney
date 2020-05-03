package com.barclays.bmg.mvc.controller.fundtransfer.internal;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.fundtransfer.internal.InternalFundTransferCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class InternalFundTransferValidateController extends BMBAbstractCommandController {

    private GetSelectedAccountOperation getSelectedAccountOperation;
    private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
    private FormValidateOperation formValidateOperation;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

    @Override
    protected String getActivityId(Object command) {// TODO Auto-generated
	// method stub
	return ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	InternalFundTransferCommand interFundTransferCommand = (InternalFundTransferCommand) command;
	Context context = createContext(httpRequest);
	context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID);
	// Get the selected account.

	GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
	getSelectedAccountOperationRequest.setContext(context);
	GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = null;

	if(httpRequest.getParameterMap().containsKey("CreditCard"))
	{
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(interFundTransferCommand.getFrActNo(),
				httpRequest,BMGProcessConstants.CREDIT_PAYMENT));
      	//Change to filter blocked card on selection
		String ccNumber = httpRequest.getParameterMap().get("ccNumber")!= null ? httpRequest.getParameterMap().get("ccNumber").toString() : "";
		getSelectedAccountOperationRequest.setCreditCardNumber(ccNumber);
		getSelectedAccountOperationResponse = getSelectedAccountOperation
		.getSelectedCreditCardAccount(getSelectedAccountOperationRequest);
	}else{
	getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(interFundTransferCommand.getFrActNo(), httpRequest,
		BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER));
    getSelectedAccountOperationResponse = getSelectedAccountOperation
		.getSelectedSourceAccount(getSelectedAccountOperationRequest);
	}
	// set account map for ecrime purpose

	setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);

	// Retrieve Payee info.
	RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();
	retrievePayeeInfoOperationRequest.setContext(context);
	retrievePayeeInfoOperationRequest.setPayId(interFundTransferCommand.getPayId());
	retrievePayeeInfoOperationRequest.setPayGrp(FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL);
	RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation
		.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

	FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();
	// Form validation for Own fund transfer.
	if (getSelectedAccountOperationResponse.isSuccess() && retrievePayeeInfoOperationResponse.isSuccess()) {

		// Set OpCode for OtherBarclays Registered FundTransfer - CPB 31/08/2017
		// Uganda CBP Check

		//check for CBP
		 Map<String, Object> contextMap = context.getContextMap();
		if(context.getActivityId().equals("PMT_FT_INTERNAL_PAYEE") && (contextMap!=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y")|| context.getBusinessId().equalsIgnoreCase("KEBRB"))){
			context.setOpCde(httpRequest.getParameter("opCde"));
		}
	    FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
	    formValidateOperationRequest.setContext(context);
	    formValidateOperationRequest.setFrmAct(getSelectedAccountOperationResponse.getSelectedAcct());
	    Amount txnAmount = new Amount();
	    txnAmount.setAmount(new BigDecimal(interFundTransferCommand.getTxnAmt()));

	    if (interFundTransferCommand.getCurr() != null) {
		txnAmount.setCurrency(interFundTransferCommand.getCurr());
	    } else {
		txnAmount.setCurrency(retrievePayeeInfoOperationResponse.getCasaAccountDTO().getCurrency());
	    }
	    formValidateOperationRequest.setTxnAmt(txnAmount);
	    formValidateOperationRequest.setTxnType(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL);
	    String localCurr = retrievePayeeInfoOperationResponse.getCasaAccountDTO().getCurrency();
	    if(httpRequest.getParameterMap().containsKey("CreditCard")){
	    formValidateOperationRequest.setCreditCardFlag("CreditCard");
		}
	    formValidateOperation.currencyValidation(context, localCurr, FundTransferConstants.LIST_VAL_CURR_SUPPORT_INT_ACC,
		    getSelectedAccountOperationResponse.getSelectedAcct().getCurrency(), retrievePayeeInfoOperationResponse.getCasaAccountDTO()
			    .getCurrencyCode().getCurrency(), formValidateOperationResponse);

	    if (formValidateOperationResponse.isSuccess()) {
		formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
	    }

	    if (formValidateOperationResponse.isSuccess()) {
		setResponseInProcessMap(httpRequest, interFundTransferCommand, getSelectedAccountOperationResponse,
			retrievePayeeInfoOperationResponse, formValidateOperationResponse);
	    }
	}

	formValidateOperationResponse.setTxnNot(interFundTransferCommand.getTxnNot());
	formValidateOperationResponse.setTxnDt(interFundTransferCommand.getTxnDt());

	BMBPayload bmbPayload = (BMBPayload) bmbJSONBuilder.createMultipleJSONResponse(getSelectedAccountOperationResponse,
		retrievePayeeInfoOperationResponse, formValidateOperationResponse);
	/*
	 * InternalFTValidateJSONResponseModel responseModel = (InternalFTValidateJSONResponseModel) bmbPayload .getPayData(); if (responseModel !=
	 * null) { responseModel.setTxnDt(interFundTransferCommand.getTxnDt()); //Added convertStringToUnicode() to convert Character from Unicode
	 * responseModel .setTxnNot(convertStringToUnicode(interFundTransferCommand .getTxnNot())); responseModel.setTxnRefNo(context.getOrgRefNo());
	 * }
	 */

	return bmbPayload;
    }

    private void setResponseInProcessMap(HttpServletRequest httpRequest, Object command, ResponseContext... responseContexts) {

	GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
	RetrievePayeeInfoOperationResponse beneResponse = (RetrievePayeeInfoOperationResponse) responseContexts[1];
	FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[2];
	InternalFundTransferCommand interFundTransferCommand = (InternalFundTransferCommand) command;
	TransactionDTO transactionDTO = new TransactionDTO();
	transactionDTO.setSourceAcct(selSourceAcctOpResp.getSelectedAcct());
	transactionDTO.setTxnType(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL);
	BeneficiaryDTO beneficiaryDTO = beneResponse.getBeneficiaryDTO();
	beneficiaryDTO.setDestinationAccount(beneResponse.getCasaAccountDTO());
	// Cards Migration: Start
	if (selSourceAcctOpResp.getSelectedAcct() instanceof CreditCardAccountDTO) {
		CreditCardAccountDTO cardDTO = (CreditCardAccountDTO) selSourceAcctOpResp.getSelectedAcct();
		if (cardDTO.getCardExpireDate() != null) {
			beneficiaryDTO.setCreditCardExpiryDate(cardDTO.getCardExpireDate());
		}
	}
	// Cards Migration: Ends
	transactionDTO.setBeneficiaryDTO(beneficiaryDTO);
	transactionDTO.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
	transactionDTO.setTxnAmt(formValidateOperationResponse.getTxnAmt());
	transactionDTO.setTxnNot(interFundTransferCommand.getTxnNot());
	transactionDTO.setScndLvlauthReq(formValidateOperationResponse.isScndLvlauthReq());
	transactionDTO.setScndLvlAuthTyp(formValidateOperationResponse.getScndLvlAuthTyp());
	transactionDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
	setIntoProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER, FundTransferConstants.TXN_REF_NO,
		formValidateOperationResponse.getContext().getOrgRefNo());
	setIntoProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER, FundTransferConstants.TRANSACTION_DTO, transactionDTO);
    }

    public GetSelectedAccountOperation getGetSelectedAccountOperation() {
	return getSelectedAccountOperation;
    }

    public void setGetSelectedAccountOperation(GetSelectedAccountOperation getSelectedAccountOperation) {
	this.getSelectedAccountOperation = getSelectedAccountOperation;
    }

    public RetrievePayeeInfoOperation getRetrievePayeeInfoOperation() {
	return retrievePayeeInfoOperation;
    }

    public void setRetrievePayeeInfoOperation(RetrievePayeeInfoOperation retrievePayeeInfoOperation) {
	this.retrievePayeeInfoOperation = retrievePayeeInfoOperation;
    }

    public FormValidateOperation getFormValidateOperation() {
	return formValidateOperation;
    }

    public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
	this.formValidateOperation = formValidateOperation;
    }

    public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

}