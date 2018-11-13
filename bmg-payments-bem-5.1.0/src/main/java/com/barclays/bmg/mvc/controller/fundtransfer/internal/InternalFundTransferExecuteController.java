package com.barclays.bmg.mvc.controller.fundtransfer.internal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.MessageCodeConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.fundtransfer.own.FundTransferExecuteCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.payments.DomesticFundTransferExecuteOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthOTPOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthSQAOperationResponse;

public class InternalFundTransferExecuteController extends BMBAbstractCommandController {

	private DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation;
	private OTPAuthenticationOperation otpAuthenticationOperation;
	private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
	private SQAAuthenticationOperation sqaAuthenticationOperation;
	private BMBJSONBuilder txnSQASecondAuthJSONBldr;
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

		FundTransferExecuteCommand  fTExecuteCommand = (FundTransferExecuteCommand) command;
		Map<String,Object> processMap = getProcessMapFromSession(httpRequest);
		String txnRefNo = (String) processMap.get("txnRefNo");
		boolean verified = verifyTxnRefKey(fTExecuteCommand.getTxnRefNo(),txnRefNo);
		DomesticFundTransferExecuteOperationResponse fudnTransferExecuteOperationResponse = null;
		if(verified){
			TransactionDTO transactionDTO = (TransactionDTO)processMap.get("transactionDTO");

			if(BillPaymentConstants.TRUE.equals(processMap.get(BillPaymentConstants.SECOND_AUTH_DONE))){
				transactionDTO.setScndLvlauthReq(false);
			}


			DomesticFundTransferExecuteOperationRequest  fundTransferExecuteOperationRequest =
						new DomesticFundTransferExecuteOperationRequest();
			fundTransferExecuteOperationRequest.setContext(createContext(httpRequest));
			fundTransferExecuteOperationRequest.setTransactionDTO(transactionDTO);
			 fudnTransferExecuteOperationResponse =
						domesticFundTransferExecuteOperation.makeDomesticFundTransfer(fundTransferExecuteOperationRequest);


			 	if(fudnTransferExecuteOperationResponse!=null){
					if(fudnTransferExecuteOperationResponse.isScndLvlAuthReq()){
						String authType = transactionDTO.getScndLvlAuthTyp();
						setProcessMapIntoSession(httpRequest, txnRefNo, transactionDTO);
						setProcessMapIntoSession(httpRequest, BillPaymentConstants.SECOND_AUTH_FLOW_ID, FundTransferConstants.FT_FLOW_ID);
						setProcessMapIntoSession(httpRequest, "txnRefNo", txnRefNo);
						if(BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)){

							return generateOTP(fundTransferExecuteOperationRequest , httpRequest, txnRefNo);
						}else if (BillPaymentConstants.AUTH_TYPE_SQA.equals(authType)){
							return generateSQA(fundTransferExecuteOperationRequest, httpRequest, txnRefNo);
						}
					}

			 	}
		}else{
			fudnTransferExecuteOperationResponse = new DomesticFundTransferExecuteOperationResponse();
			fudnTransferExecuteOperationResponse.setResCde(FundTransferResponseCodeConstants.FT_NEVER_INITIATED);
			fudnTransferExecuteOperationResponse.setResMsg(getErrorMessage(MessageCodeConstant.FT_NEVER_INITIATED));
			fudnTransferExecuteOperationResponse.setSuccess(false);
		}
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(fudnTransferExecuteOperationResponse);
	}

	/**
	 * @param key
	 * @param request
	 * @return
	 * Verify whether txn key is valid.
	 */
	private boolean verifyTxnRefKey(String userEnteredTxnRefNo,String txnRefNo){
		if(userEnteredTxnRefNo.equals(txnRefNo)){
			return true;
		}
		return false;
	}

	/**
	 * @param request
	 * @param httprequest
	 * @param txnRefNo
	 * @return
	 *
	 * It will generate OTP and return the response model.
	 */
	private BMBPayload generateOTP(RequestContext request , HttpServletRequest httprequest, String txnRefNo){
		OTPGenerateAuthenticationOperationRequest otpAuthenticationOperationRequest = new OTPGenerateAuthenticationOperationRequest();
		Context context = request.getContext();
		otpAuthenticationOperationRequest
				.setContext(context);
		otpAuthenticationOperationRequest.setCustomerId(context.getCustomerId());
		otpAuthenticationOperationRequest.setMobilePhone(context.getMobilePhone());
		String [] smsParams = (String[])getSessionAttribute(httprequest, SessionConstant.SESSION_SMS_PARAMS);
		otpAuthenticationOperationRequest.setSmsParams(smsParams);
		OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse = otpAuthenticationOperation
										.generate(otpAuthenticationOperationRequest);
		setProcessMapIntoSession(httprequest, SessionConstant.SESSION_CHALLENGE_ID, otpAuthenticationOperationResponse.getChallengeId());
		TxnSecondAuthOTPOperationResponse response = new TxnSecondAuthOTPOperationResponse();
		response.setOtpResponse(otpAuthenticationOperationResponse);
		response.setTxnRefNo(txnRefNo);
		return (BMBPayload) txnOTPSecondAuthJSONBldr.createJSONResponse(response);
	}

	/**
	 * @param request
	 * @param httprequest
	 * @param txnRefNo
	 * @return
	 * It will generate SQA and return the response model
	 */
	private BMBPayload generateSQA(RequestContext request, HttpServletRequest httprequest, String txnRefNo){
		SQAGenerateAuthenticationOperationRequest sqaAuthenticationOperationRequest = new SQAGenerateAuthenticationOperationRequest();
		sqaAuthenticationOperationRequest
				.setContext(request.getContext());
		SQAGenerateAuthenticationOperationResponse sqaAuthenticationOperationResponse = sqaAuthenticationOperation
													.generate(sqaAuthenticationOperationRequest);

		setProcessMapIntoSession(httprequest, SessionConstant.SESSION_QUESTION_ID, sqaAuthenticationOperationResponse.getQuestionId());
		TxnSecondAuthSQAOperationResponse response = new TxnSecondAuthSQAOperationResponse();
		response.setSqaResponse(sqaAuthenticationOperationResponse);
		response.setTxnRefNo(txnRefNo);
		return (BMBPayload) txnSQASecondAuthJSONBldr.createJSONResponse(response);
	}

	public DomesticFundTransferExecuteOperation getDomesticFundTransferExecuteOperation() {
		return domesticFundTransferExecuteOperation;
	}

	public void setDomesticFundTransferExecuteOperation(
			DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation) {
		this.domesticFundTransferExecuteOperation = domesticFundTransferExecuteOperation;
	}

	public OTPAuthenticationOperation getOtpAuthenticationOperation() {
		return otpAuthenticationOperation;
	}

	public void setOtpAuthenticationOperation(
			OTPAuthenticationOperation otpAuthenticationOperation) {
		this.otpAuthenticationOperation = otpAuthenticationOperation;
	}

	public BMBJSONBuilder getTxnOTPSecondAuthJSONBldr() {
		return txnOTPSecondAuthJSONBldr;
	}

	public void setTxnOTPSecondAuthJSONBldr(BMBJSONBuilder txnOTPSecondAuthJSONBldr) {
		this.txnOTPSecondAuthJSONBldr = txnOTPSecondAuthJSONBldr;
	}

	public SQAAuthenticationOperation getSqaAuthenticationOperation() {
		return sqaAuthenticationOperation;
	}

	public void setSqaAuthenticationOperation(
			SQAAuthenticationOperation sqaAuthenticationOperation) {
		this.sqaAuthenticationOperation = sqaAuthenticationOperation;
	}

	public BMBJSONBuilder getTxnSQASecondAuthJSONBldr() {
		return txnSQASecondAuthJSONBldr;
	}

	public void setTxnSQASecondAuthJSONBldr(BMBJSONBuilder txnSQASecondAuthJSONBldr) {
		this.txnSQASecondAuthJSONBldr = txnSQASecondAuthJSONBldr;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}


}
