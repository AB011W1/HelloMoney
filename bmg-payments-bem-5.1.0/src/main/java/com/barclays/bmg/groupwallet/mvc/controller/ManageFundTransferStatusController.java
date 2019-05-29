package com.barclays.bmg.groupwallet.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.builder.fundtransfer.own.DomesticFundTransferJSONBldr;
import com.barclays.bmg.json.model.builder.pesalink.ManageFundtransferStatusJsonBldr;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.pesalink.ManageFundtransferStatusCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.payments.DomesticFundTransferExecuteOperation;
import com.barclays.bmg.operation.payments.MakeBillPaymentOperation;
import com.barclays.bmg.operation.payments.ManageFundtransferStatusOperation;
import com.barclays.bmg.operation.request.billpayment.MakeBillPaymentOperationRequest;
import com.barclays.bmg.operation.request.billpayment.ManageFundtransferStatusOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.operation.response.billpayment.MakeBillPaymentOperationResponse;
import com.barclays.bmg.operation.response.billpayment.ManageFundtransferStatusOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class ManageFundTransferStatusController extends BMBAbstractCommandController {
	ManageFundtransferStatusOperation manageFundtransferStatusOperation;
	ManageFundtransferStatusJsonBldr manageFundtransferStatusJsonBldr;
	private MakeBillPaymentOperation makeBillPaymentOperation;
	String activityId;
	DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation;
	DomesticFundTransferJSONBldr domesticbmbJSONBuilder;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

	public DomesticFundTransferExecuteOperation getDomesticFundTransferExecuteOperation() {
		return domesticFundTransferExecuteOperation;
	}

	public void setDomesticFundTransferExecuteOperation(
			DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation) {
		this.domesticFundTransferExecuteOperation = domesticFundTransferExecuteOperation;
	}

	public DomesticFundTransferJSONBldr getDomesticbmbJSONBuilder() {
		return domesticbmbJSONBuilder;
	}

	public void setDomesticbmbJSONBuilder(
			DomesticFundTransferJSONBldr domesticbmbJSONBuilder) {
		this.domesticbmbJSONBuilder = domesticbmbJSONBuilder;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public MakeBillPaymentOperation getMakeBillPaymentOperation() {
		return makeBillPaymentOperation;
	}

	public void setMakeBillPaymentOperation(
			MakeBillPaymentOperation makeBillPaymentOperation) {
		this.makeBillPaymentOperation = makeBillPaymentOperation;
	}

	public ManageFundtransferStatusOperation getManageFundtransferStatusOperation() {
		return manageFundtransferStatusOperation;
	}

	public void setManageFundtransferStatusOperation(
			ManageFundtransferStatusOperation manageFundtransferStatusOperation) {
		this.manageFundtransferStatusOperation = manageFundtransferStatusOperation;
	}

	public ManageFundtransferStatusJsonBldr getManageFundtransferStatusJsonBldr() {
		return manageFundtransferStatusJsonBldr;
	}

	public void setManageFundtransferStatusJsonBldr(
			ManageFundtransferStatusJsonBldr manageFundtransferStatusJsonBldr) {
		this.manageFundtransferStatusJsonBldr = manageFundtransferStatusJsonBldr;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityId() {
		return activityId;
	}

	private ManageFundtransferStatusOperationRequest makeRequest(
			HttpServletRequest request,ManageFundtransferStatusCommand manageFundtransferStatusCommand) {
		// TODO Auto-generated method stub
		Context context = createContext(request);
		context.getContextMap().put("inputMobileNumber",
				(String) request.getParameter("mobileNo"));
		context.setActivityId(request.getParameter("activityId"));
		context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter("opCde"));

		ManageFundtransferStatusOperationRequest manageFundtransferStatusOperationRequest=new ManageFundtransferStatusOperationRequest();
		manageFundtransferStatusOperationRequest.setBankCIF(manageFundtransferStatusCommand.getBankCIF());
		manageFundtransferStatusOperationRequest.setAccno(manageFundtransferStatusCommand.getAccono());
		manageFundtransferStatusOperationRequest.setDebitAmount(manageFundtransferStatusCommand.getDebitAmount());
		manageFundtransferStatusOperationRequest.setCreditAmount(manageFundtransferStatusCommand.getDebitAmount());
		manageFundtransferStatusOperationRequest.setInitiatingCustomerFullName(context.getFullName());
		manageFundtransferStatusOperationRequest.setRemarks(manageFundtransferStatusCommand.getRemarks());
		manageFundtransferStatusOperationRequest.setUserId(context.getCustomerId());
		if(manageFundtransferStatusCommand.getActionCode().equals("UPDATE")){
			manageFundtransferStatusOperationRequest.setTransactionNumber(manageFundtransferStatusCommand.getTransactionNumber());
			context.setActivityRefNo(manageFundtransferStatusCommand.getTransactionNumber());
		}
		else if(manageFundtransferStatusCommand.getActionCode().equals("SAVE"))
			manageFundtransferStatusOperationRequest.setTransactionNumber(context.getActivityRefNo());
		manageFundtransferStatusOperationRequest.setTransactionStatus(manageFundtransferStatusCommand.getTransactionStatus());
		manageFundtransferStatusOperationRequest.setActionCode(manageFundtransferStatusCommand.getActionCode());
		manageFundtransferStatusOperationRequest.setContext(context);
		manageFundtransferStatusOperationRequest.setBillerId(manageFundtransferStatusCommand.getBillerId());
		manageFundtransferStatusOperationRequest.setBillerName(manageFundtransferStatusCommand.getBillerName());
		manageFundtransferStatusOperationRequest.setAcconoToCredit(manageFundtransferStatusCommand.getAcconoToCredit());
		manageFundtransferStatusOperationRequest.setFundTransferType(manageFundtransferStatusCommand.getFundTransferType());
		manageFundtransferStatusOperationRequest.setTransactionStatusMessage(manageFundtransferStatusCommand.getTransactionStatusMessage());
		return manageFundtransferStatusOperationRequest;
	}

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		BMBBaseResponseModel responseModel=null;
		TransactionDTO transactionDTO=null ,fundTransferDTO=null ;
		ManageFundtransferStatusCommand manageFundtransferStatusCommand=(ManageFundtransferStatusCommand)command;

		if(manageFundtransferStatusCommand.getFundTransferType()!=null && manageFundtransferStatusCommand.getFundTransferType().equals("LOCALWALLETTUP"))
			transactionDTO = (TransactionDTO) getFromProcessMap(request, BMGProcessConstants.BILL_PAYMENT,
					BillPaymentConstants.TRANSACTION_DTO);
		else
			fundTransferDTO = (TransactionDTO) getFromProcessMap(
				request, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER,
				FundTransferConstants.TRANSACTION_DTO);

		Context context=createContext(request);
		String sendSMS="N";
		if(manageFundtransferStatusCommand.getSendSMS()!=null)
			sendSMS="Y";

		if(manageFundtransferStatusCommand.getTransactionStatus().equals("PendingAuthorization") || manageFundtransferStatusCommand.getTransactionStatus().equals("Rejected")){
		ManageFundtransferStatusOperationRequest manageFundtransferStatusOperationRequest  = makeRequest(request,manageFundtransferStatusCommand);
		ManageFundtransferStatusOperationResponse allGroupWalletAccountOperationResponse = manageFundtransferStatusOperation.manageFundtransferStatus(manageFundtransferStatusOperationRequest);
		String[] mobileNos=allGroupWalletAccountOperationResponse.getMobileNos();
		if(manageFundtransferStatusCommand.getTransactionStatus().equals("PendingAuthorization")&& sendSMS.equals("Y"))
			manageFundtransferStatusOperation.transactionInitiatedSMSAcknowledgment(manageFundtransferStatusOperationRequest,mobileNos);
		else if(manageFundtransferStatusCommand.getTransactionStatus().equals("Rejected")&& sendSMS.equals("Y"))
			manageFundtransferStatusOperation.transactionRejectedSMSAcknowledgment(manageFundtransferStatusOperationRequest,mobileNos);

		responseModel=(BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) manageFundtransferStatusJsonBldr)
		.createMultipleJSONResponse(allGroupWalletAccountOperationResponse);
		}else {
			ManageFundtransferStatusOperationRequest manageFundtransferStatusOperationRequest  = makeRequest(request,manageFundtransferStatusCommand);
			ManageFundtransferStatusOperationResponse allGroupWalletAccountOperationResponse =manageFundtransferStatusOperation.manageFundtransferStatus(manageFundtransferStatusOperationRequest);
			responseModel=(BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) manageFundtransferStatusJsonBldr)
			.createMultipleJSONResponse(allGroupWalletAccountOperationResponse);
			String[] mobileNos=allGroupWalletAccountOperationResponse.getMobileNos();

			if(manageFundtransferStatusCommand.getFundTransferType().equals("LOCALWALLETTUP")){
			MakeBillPaymentOperationRequest makeBillPaymentOperationRequest = new MakeBillPaymentOperationRequest();
			context.setActivityRefNo(manageFundtransferStatusCommand.getTransactionNumber());
			transactionDTO.setAction(null);
			String txnType = transactionDTO.getTxnType();
		    context.setActivityId(getActivityId(txnType));
			makeBillPaymentOperationRequest.setContext(context);
			makeBillPaymentOperationRequest.setTransactionDTO(transactionDTO);
			MakeBillPaymentOperationResponse makeBillPaymentOperationResponse = makeBillPaymentOperation.makeBillPayment(makeBillPaymentOperationRequest);
			if (makeBillPaymentOperationResponse != null) {
				if (makeBillPaymentOperationResponse.isSuccess()) {
				    cleanProcess(request, BMGProcessConstants.BILL_PAYMENT);
				}
			}else {
			    makeBillPaymentOperationResponse = new MakeBillPaymentOperationResponse();
			    makeBillPaymentOperationResponse.setResCde(BillPaymentResponseCodeConstants.BILL_PAY_NEVER_INITIATED);
			    makeBillPaymentOperationResponse.setContext(context);
			    getMessage(makeBillPaymentOperationResponse);
			    makeBillPaymentOperationResponse.setSuccess(false);

			}

			setTxnTypeInResponses(txnType, makeBillPaymentOperationResponse);
			if(makeBillPaymentOperationResponse.isSuccess())
				manageFundtransferStatusOperation.transactionApprovedSMSAcknowledgment(manageFundtransferStatusOperationRequest,mobileNos);
			responseModel=(BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(makeBillPaymentOperationResponse);

		}else{
			DomesticFundTransferExecuteOperationRequest domesticRequest=new DomesticFundTransferExecuteOperationRequest();
			context.setActivityRefNo(manageFundtransferStatusCommand.getTransactionNumber());
			fundTransferDTO.setAction(null);
			String txnType = fundTransferDTO.getTxnType();
		    context.setActivityId(getActivityId(txnType));
		    domesticRequest.setContext(context);
			domesticRequest.setTransactionDTO(fundTransferDTO);
			DomesticFundTransferExecuteOperationResponse domesticResponse= domesticFundTransferExecuteOperation.makeDomesticFundTransfer(domesticRequest);
			responseModel=(BMBBaseResponseModel) domesticbmbJSONBuilder.createMultipleJSONResponse(domesticResponse);
			if(domesticResponse.isSuccess())
				manageFundtransferStatusOperation.transactionApprovedSMSAcknowledgment(manageFundtransferStatusOperationRequest,mobileNos);
		}
		}
		return responseModel;
	}

	private String getActivityId(String txnType) {
		String actId = ActivityConstant.BILL_PAYMENT_ACTIVITY_ID;

		if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(txnType)) {
		    actId = ActivityConstant.BILL_PAYMENT_PAYEE_ACTIVITY_ID;
		} else if (BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(txnType)) {
		    actId = ActivityConstant.MOBILE_TOPUP_PAYEE_ACTIVITY_ID;
		} else if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_BARCLAY_CARD.equals(txnType)) {
		    actId = ActivityIdConstantBean.BARCLAY_CARD_PAYMENT_ACTIVITY_ID;
		} else if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_CREDIT_CARD.equals(txnType)) {
		    actId = ActivityConstant.FUND_TRANSFER_CARD_PAYMENT_PAYEE_ACTIVITY_ID;
		} else if (BillPaymentConstants.PAYEE_TYPE_MOBILE_WALLET.equals(txnType)) {
		    actId = ActivityConstant.MOBILE_WALLET_PAYEE_ACTIVITY_ID;
		} else if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT_ONE_TIME.equals(txnType)) {
		    actId = ActivityIdConstantBean.BILL_PAYMENT_ONETIME_ACTIVITY_ID;
		}
		return actId;
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

}