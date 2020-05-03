package com.barclays.bmg.mvc.controller.billpayment;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.PaymentFormSubmissionCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.BillPayAmountValidationOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class CreditCardPayFormSubmissionValidationController extends BMBAbstractCommandController {

    private GetSelectedAccountOperation getSelectedAccountOperation;
    private FormValidateOperation formValidateOperation;
    private BillPayAmountValidationOperation billPayAmountValidationOperation;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	PaymentFormSubmissionCommand paymentCommand = (PaymentFormSubmissionCommand) command;
	Context context = createContext(httpRequest);

	setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT);
	TransactionDTO transactionDTO = new TransactionDTO();
	context.setActivityId(BillPaymentConstants.PMT_FT_CARD_PAYMENT_OWN);
	GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
	getSelectedAccountOperationRequest.setContext(context);

	// Get Source Account
	getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(paymentCommand.getFrActNo(), httpRequest,
		BMGProcessConstants.CREDIT_CARD_PAYMENT));

	GetSelectedAccountOperationResponse selSourceAcctOpResp = getSelectedAccountOperation
		.getSelectedSourceAccount(getSelectedAccountOperationRequest);

	Amount txnAmount = new Amount();
	txnAmount.setAmount(new BigDecimal(paymentCommand.getAmt()));
	if (!StringUtils.isEmpty(paymentCommand.getCurr())) {
	    txnAmount.setCurrency(paymentCommand.getCurr());
	} else {
	    txnAmount.setCurrency(context.getLocalCurrency());
	}

	FormValidateOperationResponse formValidateOperationResponse = null;
	// Validate the form.
	if (selSourceAcctOpResp.isSuccess()) {
	    FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
	    formValidateOperationRequest.setContext(context);
	    formValidateOperationRequest.setFrmAct(selSourceAcctOpResp.getSelectedAcct());
	    formValidateOperationRequest.setTxnType(BillPaymentConstants.TXN_TYP);

	    formValidateOperationRequest.setTxnAmt(txnAmount);
	    formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
	}


	// Get Destination Account
	Map<String, String> destAcctMap = (Map) getFromProcessMap(httpRequest, BMGProcessConstants.CREDIT_PAYMENT, AccountConstants.ACCOUNT_MAP);
	if(null != destAcctMap && null != destAcctMap.get(paymentCommand.getCrdList())) {
		getSelectedAccountOperationRequest.setAcctNumber(destAcctMap.get(paymentCommand.getCrdList()));
		getSelectedAccountOperationRequest.setCreditCardNumber(paymentCommand.getCrdNo());
		GetSelectedAccountOperationResponse selDestAcctOpResp = getSelectedAccountOperation
				.getSelectedCreditCardAccount(getSelectedAccountOperationRequest);
	

	if (checkAllOperationResponses(selSourceAcctOpResp, null, formValidateOperationResponse)) {
		BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
		  //Cards Migration: Start
		if (selDestAcctOpResp.getSelectedAcct() instanceof CreditCardAccountDTO) {
			CreditCardAccountDTO cardDTO= (CreditCardAccountDTO) selDestAcctOpResp.getSelectedAcct();
			if(cardDTO.getCardExpireDate() != null) {
				beneficiaryDTO.setCreditCardExpiryDate(cardDTO.getCardExpireDate());
			}
		}
			//Cards Migration: Ends
		transactionDTO.setBeneficiaryDTO(beneficiaryDTO);
	    setResponseInProcessMap(httpRequest, paymentCommand, transactionDTO, selSourceAcctOpResp, formValidateOperationResponse);
	}
	}

	setTxnTypeInResponses(BillPaymentConstants.TXN_TYP, selSourceAcctOpResp, null, formValidateOperationResponse);
	Map<String, Object> contextMap = context.getContextMap();
	contextMap.put("txnNot", BillPaymentConstants.PMT_DONE);
	contextMap.put("paySer", transactionDTO.getMtpService());

	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(selSourceAcctOpResp, null, formValidateOperationResponse);

    }

    private void setResponseInProcessMap(HttpServletRequest httpRequest, Object command, TransactionDTO transactionDTO,
	    ResponseContext... responseContexts) {

	GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
	FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[1];
	String txnRefNo = formValidateOperationResponse.getContext().getOrgRefNo();
	PaymentFormSubmissionCommand paymentCommand = (PaymentFormSubmissionCommand) command;
	transactionDTO.setSourceAcct(selSourceAcctOpResp.getSelectedAcct());
	transactionDTO.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
	transactionDTO.setTxnAmt(formValidateOperationResponse.getTxnAmt());
	transactionDTO.setTxnNot(paymentCommand.getPmtRem());
	transactionDTO.setScndLvlauthReq(formValidateOperationResponse.isScndLvlauthReq());
	transactionDTO.setScndLvlAuthTyp(formValidateOperationResponse.getScndLvlAuthTyp());
	transactionDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
	setIntoProcessMap(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
	setIntoProcessMap(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT, BillPaymentConstants.TXN_REF_NO, txnRefNo);
    }

    private void setTxnTypeInResponses(String txnType, ResponseContext... responses) {
	if (responses != null) {
	    for (ResponseContext response : responses) {
		if (response != null) {
		    response.setTxnTyp(txnType);
		}
	    }
	}
    }

    public GetSelectedAccountOperation getGetSelectedAccountOperation() {
	return getSelectedAccountOperation;
    }

    public void setGetSelectedAccountOperation(GetSelectedAccountOperation getSelectedAccountOperation) {
	this.getSelectedAccountOperation = getSelectedAccountOperation;
    }

    public FormValidateOperation getFormValidateOperation() {
	return formValidateOperation;
    }

    public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
	this.formValidateOperation = formValidateOperation;
    }

    public BillPayAmountValidationOperation getBillPayAmountValidationOperation() {
	return billPayAmountValidationOperation;
    }

    public void setBillPayAmountValidationOperation(BillPayAmountValidationOperation billPayAmountValidationOperation) {
	this.billPayAmountValidationOperation = billPayAmountValidationOperation;
    }

    public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

}
