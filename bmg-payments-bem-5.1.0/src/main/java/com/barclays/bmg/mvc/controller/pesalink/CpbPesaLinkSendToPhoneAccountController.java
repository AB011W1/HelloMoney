package com.barclays.bmg.mvc.controller.pesalink;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.pesalink.CpbPesaLinkCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.pesalink.GetPesaLinkToPhoneAccountRetrievChargeOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.pesalink.PesaLinkRetrievChargeOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.pesalink.PesaLinkRetrievChargeOperationResponse;

public class CpbPesaLinkSendToPhoneAccountController extends
		BMBAbstractCommandController {

	private static final Logger LOGGER = Logger.getLogger(CpbPesaLinkSendToPhoneAccountController.class);

	private GetSelectedAccountOperation getSelectedAccountOperation;
	private GetPesaLinkToPhoneAccountRetrievChargeOperation getPesaLinkToPhoneAccountRetrievChargeOperation;
	private String activityId;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

	 @Override
	protected String getActivityId(Object command) {
		return activityId;
	}

	public void setGetPesaLinkToPhoneAccountRetrievChargeOperation(
			GetPesaLinkToPhoneAccountRetrievChargeOperation getPesaLinkToPhoneAccountRetrievChargeOperation) {
		this.getPesaLinkToPhoneAccountRetrievChargeOperation = getPesaLinkToPhoneAccountRetrievChargeOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public GetSelectedAccountOperation getGetSelectedAccountOperation() {
		return getSelectedAccountOperation;
	}

	public void setGetSelectedAccountOperation(
			GetSelectedAccountOperation getSelectedAccountOperation) {
		this.getSelectedAccountOperation = getSelectedAccountOperation;
	}


	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {

		CpbPesaLinkCommand cpbCommand =  (CpbPesaLinkCommand) command;
		// Build Context
		Context context = buildContext(httpRequest);
		context.setOpCde(httpRequest.getParameter("opCde"));
//		setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.CPB_PESALINK_PROCESS);

		/*PesaLinkRetrievChargeOperationRequest pesaLinkRetrievChargeOperationRequest = new PesaLinkRetrievChargeOperationRequest();
		pesaLinkRetrievChargeOperationRequest.setContext(context);
		PesaLinkRetrievChargeOperationResponse pesaLinkRetrievChargeOperationResponse = getPesaLinkToPhoneAccountRetrievChargeOperation
				.validateForm(pesaLinkRetrievChargeOperationRequest);*/

//		TransactionDTO transactionDTO = (TransactionDTO)getFromProcessMap(httpRequest, BMGProcessConstants.CPB_PESALINK_PROCESS,BillPaymentConstants.TRANSACTION_DTO);
//		String txnTyp = transactionDTO.getTxnType();
		//context.setActivityId(getActivityId(txnTyp));
		GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
		getSelectedAccountOperationRequest.setContext(context);

		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(cpbCommand.getAccountNo(), httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER));
		GetSelectedAccountOperationResponse selSourceAcctOpResp = getSelectedAccountOperation.getSelectedSourceAccount(getSelectedAccountOperationRequest);

		Amount txnAmount = new Amount();
		txnAmount.setAmount(new BigDecimal(cpbCommand.getAmount()));
		if(!StringUtils.isEmpty(cpbCommand.getCurr())){
			txnAmount.setCurrency(cpbCommand.getCurr());
		}else{
			txnAmount.setCurrency(context.getLocalCurrency());
		}
		PesaLinkRetrievChargeOperationResponse pesaLinkRetrievChargeOperationResponse = null;
		if(selSourceAcctOpResp.isSuccess()){
			PesaLinkRetrievChargeOperationRequest pesaLinkRetrievChargeOperationRequest = new PesaLinkRetrievChargeOperationRequest();
			pesaLinkRetrievChargeOperationRequest.setContext(context);
			pesaLinkRetrievChargeOperationRequest.setFrmAct(selSourceAcctOpResp.getSelectedAcct());
		//	pesaLinkRetrievChargeOperationRequest.setTxnType(txnTyp);
			pesaLinkRetrievChargeOperationRequest.setTxnAmt(txnAmount);
			pesaLinkRetrievChargeOperationResponse = getPesaLinkToPhoneAccountRetrievChargeOperation
					.validateForm(pesaLinkRetrievChargeOperationRequest);
		}

		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
				.createMultipleJSONResponse(selSourceAcctOpResp, pesaLinkRetrievChargeOperationResponse);
	}


	 protected Context buildContext(HttpServletRequest httpRequest) {
			Context context = createContext(httpRequest);
			context.setActivityId("PMT_FT_PESALINK");
			return context;
	}
/*
	private void setTxnTypeInResponses(String txnType, ResponseContext... responses){
		if(responses!=null){
			for(ResponseContext response: responses){
				if(response!=null){
					response.setTxnTyp(txnType);
				}
			}
		}
	}*/



}
