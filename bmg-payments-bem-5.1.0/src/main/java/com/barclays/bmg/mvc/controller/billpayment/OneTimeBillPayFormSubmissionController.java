package com.barclays.bmg.mvc.controller.billpayment;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.OneTimeBillPayFormSubmitCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.payments.OneTimeBillPayOperation;
import com.barclays.bmg.operation.request.billpayment.OneTimeBillPayOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.billpayment.OneTimeBillPayOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class OneTimeBillPayFormSubmissionController extends BMBAbstractCommandController {

    private GetSelectedAccountOperation getSelectedAccountOperation;
    private FormValidateOperation formValidateOperation;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private OneTimeBillPayOperation oneTimeBillPayOperation;
    private String activityId;
    private String txnType;
	private final static String IDENTIFIRE = "CreditCardOT";

    public void setOneTimeBillPayOperation(OneTimeBillPayOperation oneTimeBillPayOperation) {
	this.oneTimeBillPayOperation = oneTimeBillPayOperation;
    }

    public void setGetSelectedAccountOperation(GetSelectedAccountOperation getSelectedAccountOperation) {
	this.getSelectedAccountOperation = getSelectedAccountOperation;
    }

    public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
	this.formValidateOperation = formValidateOperation;
    }

    @Override
    protected String getActivityId(Object command) {

	return activityId; // PMT_BP_BILLPAY_ONETIME;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	/*
	 * what is SALIK interval Amt validation validate Biller based on ref no. ? set into Txn DTO ..
	 */

	OneTimeBillPayFormSubmitCommand oneTimeBillPayFormSubmitCommand = (OneTimeBillPayFormSubmitCommand) command;
	Context context = createContext(httpRequest);

	// set account map for ecrime purpose

	setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.BILL_PAYMENT);

	// Get Credit card Flag from USSD Request
	String creditCardFlag = null;
	if (!StringUtils.isEmpty(oneTimeBillPayFormSubmitCommand.getCreditCardFlag())) {
		creditCardFlag = oneTimeBillPayFormSubmitCommand.getCreditCardFlag();
	}

	// Get Account Type from USSD Request
	String accountType = null;
	if (!StringUtils.isEmpty(oneTimeBillPayFormSubmitCommand.getAccountType())) {
		accountType = oneTimeBillPayFormSubmitCommand.getAccountType();
	}

	// Get AccountID Param from USSD Request
	String activityIDParam = null;
	if (!StringUtils.isEmpty(oneTimeBillPayFormSubmitCommand.getActivityId())) {
		activityIDParam = oneTimeBillPayFormSubmitCommand.getActivityId();
	}
	// Map<String,Object> processMap = getProcessMapFromSession(httpRequest);
	GetSelectedAccountOperationResponse selSourceAcctOpResp = null ;
	GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
	TransactionDTO transactionDTO = (TransactionDTO) getFromProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT,
		BillPaymentConstants.TRANSACTION_DTO);
	String txnTyp = null;
	if (transactionDTO != null)
	    txnTyp = transactionDTO.getTxnType();
	if(IDENTIFIRE.equalsIgnoreCase(creditCardFlag))
	{
	context.setActivityId(activityIDParam);
	getSelectedAccountOperationRequest.setContext(context);
    	getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(oneTimeBillPayFormSubmitCommand.getCreditcardNo(),
    			httpRequest,BMGProcessConstants.CREDIT_PAYMENT));
    	selSourceAcctOpResp = getSelectedAccountOperation.getSelectedCreditCardAccount(getSelectedAccountOperationRequest);
    	transactionDTO.getBeneficiaryDTO().setActionCode(oneTimeBillPayFormSubmitCommand.getActionCode());
    	transactionDTO.getBeneficiaryDTO().setStoreNumber(oneTimeBillPayFormSubmitCommand.getStoreNumber());
}else
	{
	context.setActivityId(getActivityId(txnTyp));
	getSelectedAccountOperationRequest.setContext(context);
// Get Source Account
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(oneTimeBillPayFormSubmitCommand.getFromActNumber(), httpRequest,
			BMGProcessConstants.BILL_PAYMENT));
	        selSourceAcctOpResp = getSelectedAccountOperation
			.getSelectedSourceAccount(getSelectedAccountOperationRequest);
	}

	Amount txnAmount = new Amount();
	txnAmount.setAmount(new BigDecimal(oneTimeBillPayFormSubmitCommand.getAmt()));
	if (StringUtils.isEmpty(oneTimeBillPayFormSubmitCommand.getCurrency())) {
	    txnAmount.setCurrency(context.getLocalCurrency());
	} else {
	    txnAmount.setCurrency(oneTimeBillPayFormSubmitCommand.getCurrency());
	}

	if(context.getActivityId().equals("PMT_BP_BILLPAY_ONETIME") && context.getBusinessId().equals("KEBRB")){
		context.setOpCde(httpRequest.getParameter("opCde"));
	}
	FormValidateOperationResponse formValidateOperationResponse = null;
	// Validate the form.
	if (selSourceAcctOpResp.isSuccess()) {
	    FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
	    formValidateOperationRequest.setContext(context);
	    formValidateOperationRequest.setFrmAct(selSourceAcctOpResp.getSelectedAcct());
	    formValidateOperationRequest.setTxnType(txnTyp);

		if(IDENTIFIRE.equalsIgnoreCase(creditCardFlag))
	    {
	        formValidateOperationRequest.setCreditCardFlag(creditCardFlag);
	    }
	    formValidateOperationRequest.setTxnAmt(txnAmount);
	    //BillerId change - CBP
	    if(transactionDTO.getBeneficiaryDTO() !=null){
	    	formValidateOperationRequest.setBillerId(transactionDTO.getBeneficiaryDTO().getBillerId());
	    }
	    formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);

	}

	if (checkAllOperationResponses(selSourceAcctOpResp, formValidateOperationResponse)) {
	    setResponseInProcessMap(httpRequest, oneTimeBillPayFormSubmitCommand, transactionDTO, selSourceAcctOpResp, formValidateOperationResponse);
	}

	setTxnTypeInResponses(txnTyp, selSourceAcctOpResp, formValidateOperationResponse);
	// }
	/*
	 * Map<String, Object> contextMap = context.getContextMap(); contextMap.put("txnNot", oneTimeBillPayFormSubmitCommand.getRemarks());
	 */
	OneTimeBillPayOperationResponse oneTimeBillPayOperationResponse = new OneTimeBillPayOperationResponse();
	if (transactionDTO != null) {
	    oneTimeBillPayOperationResponse.setBeneficiaryDTO(transactionDTO.getBeneficiaryDTO());
	}
	oneTimeBillPayOperationResponse.setContext(context);
	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(selSourceAcctOpResp, formValidateOperationResponse,
		oneTimeBillPayOperationResponse);

    }

    private void setResponseInProcessMap(HttpServletRequest httpRequest, Object command, TransactionDTO transactionDTO,
	    ResponseContext... responseContexts) {

	GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
	FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[1];
	String txnRefNo = formValidateOperationResponse.getContext().getOrgRefNo();
	OneTimeBillPayFormSubmitCommand oneTimeBillPayFormSubmitCommand = (OneTimeBillPayFormSubmitCommand) command;
	transactionDTO.setSourceAcct(selSourceAcctOpResp.getSelectedAcct());
	transactionDTO.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
	transactionDTO.setTxnAmt(formValidateOperationResponse.getTxnAmt());
	transactionDTO.setTxnNot(oneTimeBillPayFormSubmitCommand.getRemarks());
	transactionDTO.getBeneficiaryDTO().setBillRefNo(oneTimeBillPayFormSubmitCommand.getBillerRefNumber());
	transactionDTO.getBeneficiaryDTO().setBillRefNo2(oneTimeBillPayFormSubmitCommand.getAreaCode());
	transactionDTO.setScndLvlauthReq(true);

	OneTimeBillPayOperationRequest request = new OneTimeBillPayOperationRequest();
	request.setContext(formValidateOperationResponse.getContext());
	transactionDTO.setScndLvlAuthTyp(oneTimeBillPayOperation.getAuthType(request, request.getContext().getActivityId()));
	transactionDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
	setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
	setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TXN_REF_NO, txnRefNo);
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
