package com.barclays.bmg.mvc.controller.billpayment;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.PayInfoCommandRel1;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.CheckInqueryBillOperation;
import com.barclays.bmg.operation.beneficiary.MergeBarclayCardPayeeInfoOperation;
import com.barclays.bmg.operation.beneficiary.MergeBillerInfoOperation;
import com.barclays.bmg.operation.beneficiary.MergeOwnCreditcardInfoOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.CheckInqueryBillOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeBarclayCardPayeeInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeBillerInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeOwnCreditcardInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.CheckInqueryBillOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBarclayCardPayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBillerInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeOwnCreditcardInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class RetreivePayeeInformationControllerRel1 extends
					BMBAbstractCommandController {

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private RetrieveAccountListOperation retrieveAccountListOperation;
	private MergeBillerInfoOperation mergeBillerInfoOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private CheckInqueryBillOperation checkInqueryBillOperation;
	private MergeBarclayCardPayeeInfoOperation mergeBarclayCardPayeeInfoOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;
	private MergeOwnCreditcardInfoOperation mergeOwnCreditcardInfoOperation;
	private String activityId;
	private String txnType;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

	@Override
	protected String getActivityId(Object command) {
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		Context context = createContext(httpRequest);
		PayInfoCommandRel1 payeeInformationCommand = (PayInfoCommandRel1) command;
		if(!StringUtils.isEmpty(payeeInformationCommand.getToActNo())){
			txnType = (String)getFromProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, payeeInformationCommand
					.getToActNo());
		}else{
			txnType = (String)getFromProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, payeeInformationCommand
					.getPayId());
		}

		setActivityIdTxnType(txnType);
		context.setActivityId(activityId);
		// Get Payee Info except for Own Credit card payment
		//Map<String,Object> processMap = getProcessMapFromSession(httpRequest);
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = new RetrievePayeeInfoOperationResponse();;
		if(!StringUtils.isEmpty(payeeInformationCommand.getPayId())){
			RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();

			retrievePayeeInfoOperationRequest.setPayId(payeeInformationCommand
					.getPayId());
			retrievePayeeInfoOperationRequest.setContext(context);
			retrievePayeeInfoOperationRequest.setPayGrp((String)getFromProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, payeeInformationCommand.getPayId()));
			retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation
					.retrievePayeeInfo(retrievePayeeInfoOperationRequest);
		}else{
			retrievePayeeInfoOperationResponse.setSuccess(false);
		}


		// Get the source accounts.
		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
		retrieveAcctListOperationRequest.setContext(context);
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
				.getSourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);


		if(!StringUtils.isEmpty(payeeInformationCommand.getToActNo())){
			GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
			getSelectedAccountOperationRequest.setContext(context);

			// Get Source Account
			getSelectedAccountOperationRequest.setAcctNumber(payeeInformationCommand.getToActNo());
			GetSelectedAccountOperationResponse selSourceAcctOpResp = getSelectedAccountOperation.getSelectedDestinationAccount(getSelectedAccountOperationRequest);
			MergeOwnCreditcardInfoOperationResponse mergeOwnCreditcardInfoOperationResponse  = null;
			if(selSourceAcctOpResp.isSuccess()){
				MergeOwnCreditcardInfoOperationRequest mergeOwnCreditcardInfoOperationRequest = new MergeOwnCreditcardInfoOperationRequest();
				mergeOwnCreditcardInfoOperationRequest.setContext(context);
				mergeOwnCreditcardInfoOperationRequest.setCustomerAccountDTO(selSourceAcctOpResp.getSelectedAcct());
				mergeOwnCreditcardInfoOperationResponse = mergeOwnCreditcardInfoOperation.mergeOwnCreditCardInfo(mergeOwnCreditcardInfoOperationRequest);
				if(mergeOwnCreditcardInfoOperationResponse.isSuccess()){
					retrievePayeeInfoOperationResponse.setBeneficiaryDTO(mergeOwnCreditcardInfoOperationResponse.getBeneficiaryDTO());
					retrievePayeeInfoOperationResponse.setSuccess(true);
				}
			}
		}



		MergeBarclayCardPayeeInfoOperationResponse mergeBarclayCardPayeeInfoOperationResponse = null;
		if( BillPaymentConstants.TXN_FACADE_RTN_TYPE_BARCLAY_CARD.equals(txnType)){

			if (retrievePayeeInfoOperationResponse.isSuccess()) {
				MergeBarclayCardPayeeInfoOperationRequest mergeBarclayCardPayeeInfoOperationRequest = new MergeBarclayCardPayeeInfoOperationRequest();
				mergeBarclayCardPayeeInfoOperationRequest.setContext(context);
				mergeBarclayCardPayeeInfoOperationRequest
						.setBeneficiaryDTO(retrievePayeeInfoOperationResponse
								.getBeneficiaryDTO());
				mergeBarclayCardPayeeInfoOperationResponse = mergeBarclayCardPayeeInfoOperation
						.mergeBCDPayeeInfo(mergeBarclayCardPayeeInfoOperationRequest);
				retrievePayeeInfoOperationResponse.setBeneficiaryDTO(mergeBarclayCardPayeeInfoOperationResponse.getBeneficiaryDTO());
			}

		}
		// Merge the biller information.
		MergeBillerInfoOperationResponse mergeBillerInfoOperationResponse = null;
		CheckInqueryBillOperationResponse checkInqueryBillOperationResponse = new CheckInqueryBillOperationResponse();
		if(BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT.equals(txnType) || BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP.equals(txnType)){
			if (retrievePayeeInfoOperationResponse.isSuccess()) {
				BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse.getBeneficiaryDTO();
				if(beneficiaryDTO!=null){
					MergeBillerInfoOperationRequest mergeBillerInfoOperationRequest = new MergeBillerInfoOperationRequest();
					mergeBillerInfoOperationRequest.setContext(context);
					mergeBillerInfoOperationRequest
							.setBeneficiaryDTO(retrievePayeeInfoOperationResponse
									.getBeneficiaryDTO());
					mergeBillerInfoOperationRequest.setPayeeType(txnType);

					mergeBillerInfoOperationResponse = mergeBillerInfoOperation
							.mergeBillerInformation(mergeBillerInfoOperationRequest);
					retrievePayeeInfoOperationResponse.setBeneficiaryDTO(mergeBillerInfoOperationResponse.getBeneficiaryDTO());

					if(BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(txnType)){
						beneficiaryDTO.setTopupService(payeeInformationCommand.getPaySer());
					}

					if(beneficiaryDTO.getPresentmentFlag()){
						// Check the inquery Bill.

						CheckInqueryBillOperationRequest checkInqueryBillOperationRequest = new CheckInqueryBillOperationRequest();
						checkInqueryBillOperationRequest.setContext(context);
						checkInqueryBillOperationRequest.setBeneficiaryDTO(retrievePayeeInfoOperationResponse
								.getBeneficiaryDTO());
						checkInqueryBillOperationResponse = checkInqueryBillOperation.checkAccountBill(checkInqueryBillOperationRequest);
					}
				}
			}
		}


		clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);
		//clearProcessMap(httpRequest);
		if(retrieveAcctListOperationResponse.isSuccess()){
			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest, retrieveAcctListOperationResponse, BMGProcessConstants.BILL_PAYMENT);
		}
		// Get Transaction Limit.

		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);

		TransactionLimitOperationResponse transactionLimitOperationResponse = transactionLimitOperation
				.getAValidDailyLimit(transactionLimitOperationRequest);

		setTxnTypeInResponses(txnType, retrievePayeeInfoOperationResponse,
						retrieveAcctListOperationResponse,
						mergeBillerInfoOperationResponse, checkInqueryBillOperationResponse,
						transactionLimitOperationResponse);

		if(checkAllOperationResponses(retrievePayeeInfoOperationResponse,
						retrieveAcctListOperationResponse,
						mergeBillerInfoOperationResponse, checkInqueryBillOperationResponse,
						transactionLimitOperationResponse)){
			setResponseInProcessMap(httpRequest,payeeInformationCommand,retrievePayeeInfoOperationResponse,mergeBillerInfoOperationResponse, checkInqueryBillOperationResponse,
					transactionLimitOperationResponse);
		}

		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(retrievePayeeInfoOperationResponse,
						retrieveAcctListOperationResponse,
						mergeBillerInfoOperationResponse, checkInqueryBillOperationResponse,
						transactionLimitOperationResponse);

	}


	private void setResponseInProcessMap(HttpServletRequest httpRequest,Object command,ResponseContext... responseContexts){
		PayInfoCommandRel1 payeeInformationCommand = (PayInfoCommandRel1) command;
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse)responseContexts[0];
		MergeBillerInfoOperationResponse mergeBillerInfoOperationResponse = (MergeBillerInfoOperationResponse)responseContexts[1];
		CheckInqueryBillOperationResponse checkInqueryBillOperationResponse = (CheckInqueryBillOperationResponse)responseContexts[2];
		TransactionDTO transactionDTO = new TransactionDTO();

		transactionDTO.setBeneficiaryDTO(retrievePayeeInfoOperationResponse.getBeneficiaryDTO());
		transactionDTO.setTxnType(txnType);
		transactionDTO.setMinAmt(checkInqueryBillOperationResponse.getMinBillAmt());
		transactionDTO.setMaxAmt(checkInqueryBillOperationResponse.getMaxBilAmt());
		if(mergeBillerInfoOperationResponse !=null && mergeBillerInfoOperationResponse.getIntervalAmt()!=null){
			transactionDTO.setIntAmt(new BigDecimal(mergeBillerInfoOperationResponse.getIntervalAmt()));
		}
		transactionDTO.setOutBalAmt(checkInqueryBillOperationResponse.getOutBalAmt());
		transactionDTO.setMtpService(payeeInformationCommand.getPaySer());
		setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
		//setProcessMapIntoSession(httpRequest, "transactionDTO", transactionDTO);
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

	private void setActivityIdTxnType(String txnTyp){

			if(BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT.equals(txnTyp)){
				activityId = ActivityConstant.BILL_PAYMENT_PAYEE_ACTIVITY_ID;
				txnType = BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT;
			}else if(BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP.equals(txnTyp)){
				activityId = ActivityConstant.MOBILE_TOPUP_PAYEE_ACTIVITY_ID;
				txnType = BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP;
			}else if(BillPaymentConstants.PAYEE_TYPE_BCD_FROM_BEM.equals(txnTyp)){
				activityId = ActivityConstant.BARCLAY_CARD_PAYMENT_PAYEE_ACTIVITY_ID;
				txnType = BillPaymentConstants.TXN_FACADE_RTN_TYPE_BARCLAY_CARD;
			}else if(BillPaymentConstants.TXN_FACADE_RTN_TYPE_CREDIT_CARD.equals(txnTyp)){
				activityId = ActivityConstant.FUND_TRANSFER_CARD_PAYMENT_PAYEE_ACTIVITY_ID;
				txnType = BillPaymentConstants.TXN_FACADE_RTN_TYPE_CREDIT_CARD;
			}

	}

	public RetrievePayeeInfoOperation getRetrievePayeeInfoOperation() {
		return retrievePayeeInfoOperation;
	}

	public void setRetrievePayeeInfoOperation(
			RetrievePayeeInfoOperation retrievePayeeInfoOperation) {
		this.retrievePayeeInfoOperation = retrievePayeeInfoOperation;
	}

	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}

	public MergeBillerInfoOperation getMergeBillerInfoOperation() {
		return mergeBillerInfoOperation;
	}

	public void setMergeBillerInfoOperation(
			MergeBillerInfoOperation mergeBillerInfoOperation) {
		this.mergeBillerInfoOperation = mergeBillerInfoOperation;
	}

	public TransactionLimitOperation getTransactionLimitOperation() {
		return transactionLimitOperation;
	}

	public void setTransactionLimitOperation(
			TransactionLimitOperation transactionLimitOperation) {
		this.transactionLimitOperation = transactionLimitOperation;
	}

	public CheckInqueryBillOperation getCheckInqueryBillOperation() {
		return checkInqueryBillOperation;
	}

	public void setCheckInqueryBillOperation(
			CheckInqueryBillOperation checkInqueryBillOperation) {
		this.checkInqueryBillOperation = checkInqueryBillOperation;
	}

	public MergeBarclayCardPayeeInfoOperation getMergeBarclayCardPayeeInfoOperation() {
		return mergeBarclayCardPayeeInfoOperation;
	}

	public void setMergeBarclayCardPayeeInfoOperation(
			MergeBarclayCardPayeeInfoOperation mergeBarclayCardPayeeInfoOperation) {
		this.mergeBarclayCardPayeeInfoOperation = mergeBarclayCardPayeeInfoOperation;
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

	public MergeOwnCreditcardInfoOperation getMergeOwnCreditcardInfoOperation() {
		return mergeOwnCreditcardInfoOperation;
	}

	public void setMergeOwnCreditcardInfoOperation(
			MergeOwnCreditcardInfoOperation mergeOwnCreditcardInfoOperation) {
		this.mergeOwnCreditcardInfoOperation = mergeOwnCreditcardInfoOperation;
	}

}
