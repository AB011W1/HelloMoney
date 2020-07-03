package com.barclays.bmg.mvc.controller.billpayment;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.PayeeInformationCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.CheckInqueryBillOperation;
import com.barclays.bmg.operation.beneficiary.MergeBillerInfoOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.CheckInqueryBillOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeBillerInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.CheckInqueryBillOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBillerInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class RetrieveBPPayeeInformationController extends
		BMBAbstractCommandController {

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private RetrieveAccountListOperation retrieveAccountListOperation;
	private MergeBillerInfoOperation mergeBillerInfoOperation;
	private TransactionLimitOperation transactionLimitOperation;
	private CheckInqueryBillOperation checkInqueryBillOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

	private String activityId;
	private String txnType;
	//CR-82 Account Mapping issue fix
	private String AIRTIME_TOPUP="AT";
	private String M_WALLETE="WT";
	
	//Data bundle change
	private String DATA_BUNDLE="DB";

	@Override
	protected String getActivityId(Object command) {

		return activityId; //ActivityIdConstantBean.BILL_PAYMENT_PAYEE_ACTIVITY_ID;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {
		Context context = createContext(httpRequest);
		context
				.setActivityId(activityId);
		//CR-82 Account mapping issue fix for Airtime and Mwallet
		String  airTimeMwallet= httpRequest.getParameter("BP_AT_WT");
		if(airTimeMwallet!= null && !(airTimeMwallet.equalsIgnoreCase(AIRTIME_TOPUP)|| airTimeMwallet.equalsIgnoreCase(M_WALLETE) || airTimeMwallet.equalsIgnoreCase(DATA_BUNDLE))){
			clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);
		}
		PayeeInformationCommand payeeInformationCommand = (PayeeInformationCommand) command;
		// Get Payee Info
		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();

		retrievePayeeInfoOperationRequest.setPayId(payeeInformationCommand
				.getPayId());
		retrievePayeeInfoOperationRequest.setContext(context);
		retrievePayeeInfoOperationRequest.setPayGrp(txnType);
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation
				.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

		// Get the source accounts.
		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
		retrieveAcctListOperationRequest.setContext(context);
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
				.getSourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);



		// Merge the biller information.
		MergeBillerInfoOperationResponse mergeBillerInfoOperationResponse = null;
		CheckInqueryBillOperationResponse checkInqueryBillOperationResponse = new CheckInqueryBillOperationResponse();
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

				if(beneficiaryDTO.getPresentmentFlag() && null != airTimeMwallet && !(airTimeMwallet.equals(DATA_BUNDLE))){
					// Check the inquery Bill.

					CheckInqueryBillOperationRequest checkInqueryBillOperationRequest = new CheckInqueryBillOperationRequest();
					checkInqueryBillOperationRequest.setContext(context);
					checkInqueryBillOperationRequest.setBeneficiaryDTO(retrievePayeeInfoOperationResponse
							.getBeneficiaryDTO());
					checkInqueryBillOperationResponse = checkInqueryBillOperation.checkAccountBill(checkInqueryBillOperationRequest);
				}
			}



		}


		if(retrieveAcctListOperationResponse.isSuccess()){
			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrieveAcctListOperationRequest, httpRequest, retrieveAcctListOperationResponse, BMGProcessConstants.BILL_PAYMENT);
		}
		// Get Transaction Limit.

		TransactionLimitOperationRequest transactionLimitOperationRequest = new TransactionLimitOperationRequest();
		transactionLimitOperationRequest.setContext(context);
		//Removed Daily limit as not required for USSD
		TransactionLimitOperationResponse transactionLimitOperationResponse = new TransactionLimitOperationResponse();
		/*transactionLimitOperation
				.getAValidDailyLimit(transactionLimitOperationRequest);*/
		//Removed Daily limit as not required for USSD

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
		PayeeInformationCommand payeeInformationCommand = (PayeeInformationCommand) command;
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse)responseContexts[0];
		MergeBillerInfoOperationResponse mergeBillerInfoOperationResponse = (MergeBillerInfoOperationResponse)responseContexts[1];
		CheckInqueryBillOperationResponse checkInqueryBillOperationResponse = (CheckInqueryBillOperationResponse)responseContexts[2];
		TransactionDTO transactionDTO = new TransactionDTO();

		transactionDTO.setBeneficiaryDTO(retrievePayeeInfoOperationResponse.getBeneficiaryDTO());
		transactionDTO.setTxnType(txnType);
		transactionDTO.setMinAmt(checkInqueryBillOperationResponse.getMinBillAmt());
		transactionDTO.setMaxAmt(checkInqueryBillOperationResponse.getMaxBilAmt());
		if(mergeBillerInfoOperationResponse.getIntervalAmt()!=null){
			transactionDTO.setIntAmt(new BigDecimal(mergeBillerInfoOperationResponse.getIntervalAmt()));
		}
		transactionDTO.setOutBalAmt(checkInqueryBillOperationResponse.getOutBalAmt());
		transactionDTO.setMtpService(payeeInformationCommand.getPaySer());
		setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
	//	setProcessMapIntoSession(httpRequest, "transactionDTO", transactionDTO);
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
