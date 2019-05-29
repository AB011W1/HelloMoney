package com.barclays.bmg.mvc.controller.fundtransfer.nonregistered.internal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
//import com.barclays.bmg.constants.BeneficiaryConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.MessageCodeConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.fundtransfer.nonregistered.internal.InternalNonRegisteredFundTransferCommand;
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

public class FundTransferExecutionController extends
		BMBAbstractCommandController {

	private DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation;
	private OTPAuthenticationOperation otpAuthenticationOperation;
	private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
	private SQAAuthenticationOperation sqaAuthenticationOperation;
	private BMBJSONBuilder txnSQASecondAuthJSONBldr;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {

		setLastStep(httpRequest);
		InternalNonRegisteredFundTransferCommand fTExecuteCommand = (InternalNonRegisteredFundTransferCommand) command;

		String txnRefNo = (String) getFromProcessMap(httpRequest,
				BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER,
				FundTransferConstants.TXN_REF_NO);

		boolean verified = verifyTxnRefKey(fTExecuteCommand.getTxnRefNo(),txnRefNo);
		DomesticFundTransferExecuteOperationResponse fundTransferExecuteOperationResponse = null;
		if(verified){

			TransactionDTO fundTransferDTO = (TransactionDTO) getFromProcessMap(
					httpRequest, BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER,
					FundTransferConstants.TRANSACTION_DTO);

			if(fundTransferDTO != null){

			//fundTransferDTO.setScndLvlAuthTyp(BeneficiaryConstants.AUTH_TYPE_OTP);

			if(BillPaymentConstants.TRUE.equals(getFromProcessMap(httpRequest,
					BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
					BillPaymentConstants.SECOND_AUTH_DONE))){
				fundTransferDTO.setScndLvlauthReq(false);
			}/*else{
				fundTransferDTO.setScndLvlauthReq(true);
			}*/


			DomesticFundTransferExecuteOperationRequest  fundTransferExecuteOperationRequest =
						new DomesticFundTransferExecuteOperationRequest();
			//Activity Not set earlier for FT-Unregistered.
			Context context=createContext(httpRequest);
			context.setActivityId(getActivityId());

			fundTransferExecuteOperationRequest.setContext(context);

			// Set the fields for MakeDomesticFundTransferRequest - CPB 30/05
			//check for CBP
			context.setOpCde(httpRequest.getParameter("opCde"));
			Charge chargeDTO = null;
			if(httpRequest.getParameter("CpbOtherBarcDomesticFundTransfer") != null){
				chargeDTO = new Charge();
				if(httpRequest.getParameter("CpbOtherBarcDomesticFundTransfer").equals("setCpbDomesticInfoFields")){
					Double cpbChargeAmount = Double.parseDouble(httpRequest.getParameter("CpbChargeAmount"));
					chargeDTO.setCpbChargeAmount(cpbChargeAmount);
					chargeDTO.setFeeGLAccount((String)httpRequest.getParameter("CpbFeeGLAccount"));
					Double cpbTaxAmount = Double.parseDouble(httpRequest.getParameter("CpbTaxAmount"));
					chargeDTO.setTaxAmount(cpbTaxAmount);
					chargeDTO.setTaxGLAccount((String)httpRequest.getParameter("CpbTaxGLAccount"));
					chargeDTO.setChargeNarration((String)httpRequest.getParameter("CpbChargeNarration"));
					chargeDTO.setExciseDutyNarration((String)httpRequest.getParameter("CpbExciseDutyNarration"));
					chargeDTO.setTypeCode((String)httpRequest.getParameter("CpbtypeCode"));
					chargeDTO.setValue((String)httpRequest.getParameter("CpbValue"));
					chargeDTO.setCpbMakeBillPaymentFlag("setDomesticFundOthBarclaysFields");

				}else if(httpRequest.getParameter("CpbOtherBarcDomesticFundTransfer").equals("xelerateOffline")){
					Double cpbChargeAmount = Double.parseDouble(httpRequest.getParameter("CpbChargeAmount"));
					chargeDTO.setCpbChargeAmount(cpbChargeAmount);
					Double cpbTaxAmount = Double.parseDouble(httpRequest.getParameter("CpbTaxAmount"));
					chargeDTO.setTaxAmount(cpbTaxAmount);
					chargeDTO.setCpbMakeBillPaymentFlag("xelerateOffline");
				}
				//set charge DTO
				fundTransferDTO.setChargeDTO(chargeDTO);
			}

			fundTransferExecuteOperationRequest.setTransactionDTO(fundTransferDTO);
			 fundTransferExecuteOperationResponse =
						domesticFundTransferExecuteOperation.makeDomesticFundTransfer(fundTransferExecuteOperationRequest);


			 	if(fundTransferExecuteOperationResponse!=null){
					if(fundTransferExecuteOperationResponse.isScndLvlAuthReq()){
						String authType = fundTransferDTO.getScndLvlAuthTyp();

						setIntoProcessMap(httpRequest,BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER,
										FundTransferConstants.FUND_TRANSFER_DTO,fundTransferDTO);

						setIntoProcessMap(httpRequest,BMGProcessConstants.INTERNAL_NONREGISTERED_FUND_TRANSFER,
										FundTransferConstants.TXN_REF_NO, txnRefNo);

						setIntoProcessMap(
								httpRequest,
								BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
								BillPaymentConstants.SECOND_AUTH_FLOW_ID,
								FundTransferConstants.INTL_NON_REGISTERED_FT_FLOW_ID);

						if(BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)){
							return generateOTP(fundTransferExecuteOperationRequest , httpRequest, txnRefNo);
						}else if (BillPaymentConstants.AUTH_TYPE_SQA.equals(authType)){
							return generateSQA(fundTransferExecuteOperationRequest, httpRequest, txnRefNo);
						}
					}

					/* Added for sending SMS */
					if(fundTransferExecuteOperationResponse.isSuccess()){
						domesticFundTransferExecuteOperation.sendSMSSuccessAcknowledgment(fundTransferExecuteOperationRequest, fundTransferExecuteOperationResponse);
					} else if(!fundTransferExecuteOperationResponse.isSuccess()){
						domesticFundTransferExecuteOperation.sendSMSFailAcknowledgment(fundTransferExecuteOperationRequest, fundTransferExecuteOperationResponse);
					}
					/* Code ends for sending SMS */

			 	}
		}
		}else{
			fundTransferExecuteOperationResponse = new DomesticFundTransferExecuteOperationResponse();
			fundTransferExecuteOperationResponse.setResCde(FundTransferResponseCodeConstants.FT_NEVER_INITIATED);
			fundTransferExecuteOperationResponse.setResMsg(getErrorMessage(MessageCodeConstant.FT_NEVER_INITIATED));
			fundTransferExecuteOperationResponse.setSuccess(false);
		}
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(fundTransferExecuteOperationResponse);
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
		otpAuthenticationOperationRequest.setContext(context);
		otpAuthenticationOperationRequest
				.setCustomerId(context.getCustomerId());
		otpAuthenticationOperationRequest.setMobilePhone(context
				.getMobilePhone());
		String[] smsParams = (String[]) getSessionAttribute(httprequest,
				SessionConstant.SESSION_SMS_PARAMS);
		otpAuthenticationOperationRequest.setSmsParams(smsParams);
		OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse = otpAuthenticationOperation
				.generate(otpAuthenticationOperationRequest);
		setProcessMapIntoSession(httprequest,
				SessionConstant.SESSION_CHALLENGE_ID,
				otpAuthenticationOperationResponse.getChallengeId());
		TxnSecondAuthOTPOperationResponse response = new TxnSecondAuthOTPOperationResponse();
		response.setOtpResponse(otpAuthenticationOperationResponse);
		response.setTxnRefNo(txnRefNo);
		return (BMBPayload) txnOTPSecondAuthJSONBldr
				.createJSONResponse(response);
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
			sqaAuthenticationOperationRequest.setContext(request.getContext());
			SQAGenerateAuthenticationOperationResponse sqaAuthenticationOperationResponse = sqaAuthenticationOperation
					.generate(sqaAuthenticationOperationRequest);

			setProcessMapIntoSession(httprequest,
					SessionConstant.SESSION_QUESTION_ID,
					sqaAuthenticationOperationResponse.getQuestionId());
			TxnSecondAuthSQAOperationResponse response = new TxnSecondAuthSQAOperationResponse();
			response.setSqaResponse(sqaAuthenticationOperationResponse);
			response.setTxnRefNo(txnRefNo);
			return (BMBPayload) txnSQASecondAuthJSONBldr
					.createJSONResponse(response);
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
