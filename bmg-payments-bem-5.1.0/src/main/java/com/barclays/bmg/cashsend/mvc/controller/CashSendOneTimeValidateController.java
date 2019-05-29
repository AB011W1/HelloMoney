package com.barclays.bmg.cashsend.mvc.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.cashsend.mvc.command.CashSendRequestValidationCommand;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.CashSendRequestDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class CashSendOneTimeValidateController extends BMBAbstractCommandController {

    private BMBJSONBuilder bmbJSONBuilder;
    private FormValidateOperation formValidateOperation;
    private GetSelectedAccountOperation getSelectedAccountOperation;
    private String activityId;
    private static final String ACCOUNT_PREFIX = "0";

    @Override
    protected String getActivityId(Object command) {
	return "PMT_FT_CS";
    }

    @Resource(name = "branchCodeCountryList")
    private List<String> branchCodeCountryList;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	// Build Context
	Context context = buildContext(request);

	CashSendRequestValidationCommand cashSendRequestValidationCommand = (CashSendRequestValidationCommand) command;

	GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
	getSelectedAccountOperationRequest.setContext(context);

	getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(cashSendRequestValidationCommand.getActNo(), request,
		BMGProcessConstants.CASH_SEND_PROCESS));
	GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = getSelectedAccountOperation
		.getSelectedSourceAccount(getSelectedAccountOperationRequest);

	FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();

	// Set OpCode for CashSend - CPB 16/05/2017
	//CBP Change
	//BMGGlobalContext logContext = BMGContextHolder.getLogContext();
	if(context.getActivityId().equals("PMT_FT_CS") &&
			(context!=null && context.getContextMap().get("isCBPFLAG").equals("Y") || (context.getBusinessId().equals("UGBRB")||context.getBusinessId().equals("GHBRB")
		 || context.getBusinessId().equals("KEBRB")||
			context.getBusinessId().equals("BWBRB")))){
		context.setOpCde(request.getParameter("opCde"));
	}

	formValidateOperationRequest.setContext(context);

	formValidateOperationRequest.setFrmAct(getSelectedAccountOperationResponse.getSelectedAcct());

	Amount txnAmount = new Amount();
	txnAmount.setAmount(new BigDecimal(cashSendRequestValidationCommand.getAmt()));
	txnAmount.setCurrency(getSelectedAccountOperationResponse.getSelectedAcct().getCurrency());
	formValidateOperationRequest.setTxnAmt(txnAmount);
	formValidateOperationRequest.setTxnType("CS");

	FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();

	if (getSelectedAccountOperationResponse.isSuccess()) {
//	    if (formValidateOperationResponse.isSuccess()) {
		formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
//	    }

	    formValidateOperationResponse.setMobileNo(cashSendRequestValidationCommand.getMobileNo());
	}

	CashSendRequestDTO cashSendRequestDTO = new CashSendRequestDTO();

	String brnAccNum = null;
	if (branchCodeCountryList.contains(context.getBusinessId())) {
	    String sourceAcct = getSelectedAccountOperationRequest.getAcctNumber();
	    String branchCode = getSelectedAccountOperationResponse.getSelectedAcct().getBranchCode();

	    if(branchCode.length()>1){
	    brnAccNum = branchCode + sourceAcct;
	    }else{
		branchCode = ACCOUNT_PREFIX + branchCode;
		brnAccNum = branchCode + sourceAcct;
	    }

	    cashSendRequestDTO.setActNo(brnAccNum);
	} else {
	    cashSendRequestDTO.setActNo(getSelectedAccountOperationResponse.getSelectedAcct().getAccountNumber());

	}
	// cashSendRequestDTO.setActNo(getSelectedAccountOperationResponse.getSelectedAcct().getAccountNumber());
	cashSendRequestDTO.setRecipientMobileNo(cashSendRequestValidationCommand.getMobileNo());
	cashSendRequestDTO.setSenderMobileNo(context.getMobilePhone());
	cashSendRequestDTO.setCurr(getSelectedAccountOperationResponse.getSelectedAcct().getCurrency());
	cashSendRequestDTO.setVPin(cashSendRequestValidationCommand.getVPin());
	cashSendRequestDTO.setTxnRefNo(formValidateOperationResponse.getContext().getOrgRefNo());
	cashSendRequestDTO.setTxnAmt(cashSendRequestValidationCommand.getAmt());
	cashSendRequestDTO.setTxnNot(cashSendRequestValidationCommand.getTxnNot());

	setSessionAttribute(request, "CASH_SEND_DTLS", cashSendRequestDTO);

	return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder).createMultipleJSONResponse(
		getSelectedAccountOperationResponse, formValidateOperationResponse);
    }

    public BMBJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    public GetSelectedAccountOperation getGetSelectedAccountOperation() {
	return getSelectedAccountOperation;
    }

    public void setGetSelectedAccountOperation(GetSelectedAccountOperation getSelectedAccountOperation) {
	this.getSelectedAccountOperation = getSelectedAccountOperation;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    protected Context buildContext(HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);
	context.setActivityId("PMT_FT_CS");
	return context;

    }

    public FormValidateOperation getFormValidateOperation() {
	return formValidateOperation;
    }

    public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
	this.formValidateOperation = formValidateOperation;
    }

    public List<String> getBranchCodeCountryList() {
	return branchCodeCountryList;
    }

    public void setBranchCodeCountryList(List<String> branchCodeCountryList) {
	this.branchCodeCountryList = branchCodeCountryList;
    }

}