package com.barclays.bmg.mvc.controller.billpayment;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.PaymentFormSubmissionCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.BillPayAmountValidationOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.BillPayAmountValidationOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.BillPayAmountValidationOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class BillPayFormSubmissionValidationController  extends BMBAbstractCommandController{

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
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		PaymentFormSubmissionCommand paymentCommand = (PaymentFormSubmissionCommand) command;
		Context context = createContext(httpRequest);
		context.setOpCde(httpRequest.getParameter("opCde"));
		//set account map for ecrime purpose

		setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.BILL_PAYMENT);

		//Map<String,Object> processMap = getProcessMapFromSession(httpRequest);
		TransactionDTO transactionDTO = (TransactionDTO)getFromProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT,BillPaymentConstants.TRANSACTION_DTO);

		//if(transactionDTO!=null){
			String txnTyp = transactionDTO.getTxnType();
			context.setActivityId(getActivityId(txnTyp));
			GetSelectedAccountOperationResponse selSourceAcctOpResp = new GetSelectedAccountOperationResponse();
			GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
			getSelectedAccountOperationRequest.setContext(context);

			// Get Source Account CASA / Credit.
			if ("CreditCardBP".equals(paymentCommand.getCrditCardFlag())) {
			getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(paymentCommand.getFrActNo(),
					httpRequest,BMGProcessConstants.CREDIT_PAYMENT));
            //Change to filter blocked card on selection
			String ccNumber = httpRequest.getParameterMap().get("ccNumber")!= null ? httpRequest.getParameterMap().get("ccNumber").toString() : "";
			getSelectedAccountOperationRequest.setCreditCardNumber(ccNumber);
			selSourceAcctOpResp = getSelectedAccountOperation.getSelectedCreditCardAccount(getSelectedAccountOperationRequest);
			//adding actionCode and storeNumber
			BeneficiaryDTO  beneficiary = transactionDTO.getBeneficiaryDTO();
			beneficiary.setActionCode(paymentCommand.getActionCode());
			beneficiary.setStoreNumber(paymentCommand.getStoreNumber());
			transactionDTO.setBeneficiaryDTO(beneficiary);
			}else {
			getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(paymentCommand.getFrActNo(), httpRequest, BMGProcessConstants.BILL_PAYMENT));
			selSourceAcctOpResp = getSelectedAccountOperation.getSelectedSourceAccount(getSelectedAccountOperationRequest);
			}

			Amount txnAmount = new Amount();
			txnAmount.setAmount(new BigDecimal(paymentCommand.getAmt()));
			if(!StringUtils.isEmpty(paymentCommand.getCurr())){
				txnAmount.setCurrency(paymentCommand.getCurr());
			}else{
				txnAmount.setCurrency(context.getLocalCurrency());
			}

			// Bill Payment validation.
			BillPayAmountValidationOperationRequest billPayAmountValidationOperationRequest = new BillPayAmountValidationOperationRequest();
			billPayAmountValidationOperationRequest.setContext(context);
			billPayAmountValidationOperationRequest.setBeneficiaryDTO(transactionDTO.getBeneficiaryDTO());
			billPayAmountValidationOperationRequest.setMaxAmt(transactionDTO.getMaxAmt());
			billPayAmountValidationOperationRequest.setMinAmt(transactionDTO.getMinAmt());
			billPayAmountValidationOperationRequest.setTxnAmount(txnAmount);
			billPayAmountValidationOperationRequest.setTxnType(txnTyp);
			BillPayAmountValidationOperationResponse billAmountValidationOperationResponse =
						billPayAmountValidationOperation.validateTxnAmount(billPayAmountValidationOperationRequest);
			FormValidateOperationResponse formValidateOperationResponse = null;
			// Validate the form.
			if(selSourceAcctOpResp.isSuccess()){
				FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
				formValidateOperationRequest.setContext(context);
				formValidateOperationRequest.setFrmAct(selSourceAcctOpResp.getSelectedAcct());
				formValidateOperationRequest.setCreditCardFlag(paymentCommand.getCrditCardFlag());
				formValidateOperationRequest.setTxnType(txnTyp);

				formValidateOperationRequest.setTxnAmt(txnAmount);
				//BillerId change - CBP
			    if(transactionDTO.getBeneficiaryDTO() !=null){
			    	formValidateOperationRequest.setBillerId(transactionDTO.getBeneficiaryDTO().getBillerId());
			    }
				 formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
			}


			if(checkAllOperationResponses(selSourceAcctOpResp,billAmountValidationOperationResponse,formValidateOperationResponse)){
				setResponseInProcessMap(httpRequest, paymentCommand, transactionDTO, selSourceAcctOpResp,formValidateOperationResponse);
			}

			setTxnTypeInResponses(txnTyp,selSourceAcctOpResp,billAmountValidationOperationResponse,formValidateOperationResponse);
		//}
			Map<String, Object> contextMap = context.getContextMap();
			contextMap.put("txnNot", paymentCommand.getPmtRem());
			contextMap.put("paySer", transactionDTO.getMtpService());

		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(selSourceAcctOpResp,billAmountValidationOperationResponse,formValidateOperationResponse);
	}


	private void setResponseInProcessMap(HttpServletRequest httpRequest,Object command,TransactionDTO transactionDTO,ResponseContext... responseContexts){

		GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse)responseContexts[0];
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
		setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
		setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TXN_REF_NO,  txnRefNo);
	}

	private String getActivityId(String txnType){
		String actId = "";
		if(StringUtils.isEmpty(txnType)){
			actId = ActivityConstant.BILL_PAYMENT_ACTIVITY_ID;
		}
		if(BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(txnType)){
			actId = ActivityConstant.BILL_PAYMENT_PAYEE_ACTIVITY_ID;
		}else if(BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(txnType)){
			actId = ActivityConstant.MOBILE_TOPUP_PAYEE_ACTIVITY_ID;
		}else if(BillPaymentConstants.PAYEE_TYPE_BARCLAY_CARD.equals(txnType)){
			actId = ActivityConstant.BARCLAY_CARD_PAYMENT_PAYEE_ACTIVITY_ID;
		}else if(BillPaymentConstants.PAYEE_TYPE_CREDIT_CARD_PAYMENT.equals(txnType)){
			actId = ActivityConstant.FUND_TRANSFER_CARD_PAYMENT_PAYEE_ACTIVITY_ID;
		}
		return actId;
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

	public GetSelectedAccountOperation getGetSelectedAccountOperation() {
		return getSelectedAccountOperation;
	}

	public void setGetSelectedAccountOperation(
			GetSelectedAccountOperation getSelectedAccountOperation) {
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

	public void setBillPayAmountValidationOperation(
			BillPayAmountValidationOperation billPayAmountValidationOperation) {
		this.billPayAmountValidationOperation = billPayAmountValidationOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

}