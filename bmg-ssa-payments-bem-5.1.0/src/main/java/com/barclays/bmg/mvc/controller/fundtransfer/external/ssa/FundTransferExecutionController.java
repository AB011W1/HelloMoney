package com.barclays.bmg.mvc.controller.fundtransfer.external.ssa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.ExternalFundTransferDTO;
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

public class FundTransferExecutionController extends BMBAbstractCommandController {

	private DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation;
	private OTPAuthenticationOperation otpAuthenticationOperation;
	private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
	private SQAAuthenticationOperation sqaAuthenticationOperation;
	private BMBJSONBuilder txnSQASecondAuthJSONBldr;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;

	@Override
	protected String getActivityId(Object command) {

		return null;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		DomesticFundTransferExecuteOperationRequest  fundTransferExecuteOperationRequest = new DomesticFundTransferExecuteOperationRequest();
		DomesticFundTransferExecuteOperationResponse fudnTransferExecuteOperationResponse = null;

		FundTransferExecuteCommand  fTExecuteCommand = (FundTransferExecuteCommand) command;
		setLastStep(httpRequest);
		Context context = createContext(httpRequest);
		if(activityId.equalsIgnoreCase(ActivityIdConstantBean.GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS)){
			context.setOpCde(String.valueOf(httpRequest.getParameter("opCde")));
		}
		context.setActivityId(activityId);
		//Map<String,Object> processMap = getProcessMapFromSession(httpRequest);
		String txnRefNo = (String)getFromProcessMap(httpRequest,BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER , FundTransferConstants.TXN_REF_NO);
		ExternalFundTransferDTO fundTransferDTO = (ExternalFundTransferDTO) getFromProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, FundTransferConstants.FUND_TRANSFER_DTO);

		boolean verified = verifyTxnRefKey(fTExecuteCommand.getTxnRefNo(),txnRefNo);

		if(verified && fundTransferDTO !=null){

			fundTransferExecuteOperationRequest.setContext(context);
			makeRequest(fundTransferExecuteOperationRequest, fundTransferDTO);
			TransactionDTO transactionDTO = fundTransferExecuteOperationRequest.getTransactionDTO();
			transactionDTO.setScndLvlauthReq(getBooleanValue((String)getFromProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, SessionConstant.SESSION_AUTH_REQUIRED)));
			transactionDTO.setScndLvlAuthTyp((String)getFromProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, SessionConstant.SESSION_AUTH_TYPE));
			if(BillPaymentConstants.TRUE.equals(getFromProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_DONE))){
				transactionDTO.setScndLvlauthReq(false);
			}
			try {
				fudnTransferExecuteOperationResponse =
					domesticFundTransferExecuteOperation.makeDomesticFundTransfer(fundTransferExecuteOperationRequest);

			 	if(fudnTransferExecuteOperationResponse!=null){
					if(fudnTransferExecuteOperationResponse.isScndLvlAuthReq()){
						String authType = (String)getFromProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, SessionConstant.SESSION_AUTH_TYPE);
						setIntoProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, FundTransferConstants.FUND_TRANSFER_DTO, fundTransferDTO);
						setIntoProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, FundTransferConstants.TXN_REF_NO, txnRefNo);
						setIntoProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_FLOW_ID, getFlowId(activityId));

					/*	setSessionAttribute(httpRequest, txnRefNo, fundTransferDTO);
						// Flow Id needs to be changed and configured.....
						setProcessMapIntoSession(httpRequest, BillPaymentConstants.SECOND_AUTH_FLOW_ID, FundTransferConstants.INTL_FLOW_ID);
						setProcessMapIntoSession(httpRequest, BillPaymentConstants.TXN_REF_NO, txnRefNo);*/
						if(BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)){

							return generateOTP(fundTransferExecuteOperationRequest , httpRequest, txnRefNo);
						}else if (BillPaymentConstants.AUTH_TYPE_SQA.equals(authType)){
							return generateSQA(fundTransferExecuteOperationRequest, httpRequest, txnRefNo);
						}
						//get Auth Type and Return the response model.
					}
			 	}

				/* Added for sending SMS */
			 	if(fudnTransferExecuteOperationResponse!=null)
				if(fudnTransferExecuteOperationResponse.isSuccess()){
					domesticFundTransferExecuteOperation.sendSMSSuccessAcknowledgment(fundTransferExecuteOperationRequest, fudnTransferExecuteOperationResponse);
				} else if(!fudnTransferExecuteOperationResponse.isSuccess()){
					domesticFundTransferExecuteOperation.sendSMSFailAcknowledgment(fundTransferExecuteOperationRequest, fudnTransferExecuteOperationResponse);
				}
				/* Code ends for sending SMS */
			} catch(Exception ex) {
				domesticFundTransferExecuteOperation.sendSMSFailAcknowledgment(fundTransferExecuteOperationRequest, fudnTransferExecuteOperationResponse);
				throw ex;
		 	}
		}else{
			fudnTransferExecuteOperationResponse = new DomesticFundTransferExecuteOperationResponse();
			fudnTransferExecuteOperationResponse.setResCde(FundTransferResponseCodeConstants.FT_NEVER_INITIATED);
			fudnTransferExecuteOperationResponse.setContext(context);
			getMessage(fudnTransferExecuteOperationResponse);
			fudnTransferExecuteOperationResponse.setSuccess(false);
		}
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(fudnTransferExecuteOperationResponse);

	}

	private String getFlowId(String activityId){
		if(ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID.equals(activityId)){
			return FundTransferConstants.EXTERNAL_FLOW_ID;
		}else if(ActivityIdConstantBean.FUND_TRANSFER_INTERNATIONAL_PAYEE_ACTIVITY_ID.equals(activityId)){
			return FundTransferConstants.INTL_FLOW_ID;
		}
		return null;
	}

	private void makeRequest(DomesticFundTransferExecuteOperationRequest fundTransferOperationRequest ,
					ExternalFundTransferDTO fundTransferDTO) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setBeneficiaryDTO(fundTransferDTO.getBeneficiaryDTO());
		transactionDTO.setFxRateDTO(fundTransferDTO.getFxRateDTO());
		//transactionDTO.setScndLvlauthReq(fundTransferDTO.get)
		transactionDTO.setSourceAcct(fundTransferDTO.getSourceAcct());
		transactionDTO.setTxnAmt(fundTransferDTO.getTxAmount());
		transactionDTO.setTxnAmtInLCY(fundTransferDTO.getTxnAmtInLCY());
		transactionDTO.setTxnNot(fundTransferDTO.getTxnNot());
		transactionDTO.setTxnType(fundTransferDTO.getTxnType());

		fundTransferOperationRequest.setTransactionDTO(transactionDTO);
		/*
		fundTransferOperationRequest.setAmt(fundTransferDTO.getTxAmount().getAmount());
		//fundTransferOperationRequest.setAuthRequired(fundTransferDTO.);
		fundTransferOperationRequest.setBeneficiaryDTO(fundTransferDTO.getBeneficiaryDTO());
		Map<String,String> chDescMap = fundTransferDTO.getChargeDesc();
		String selectedChDesc = null;
		if(!chDescMap.keySet().isEmpty()){
			Set<String> chDescs = chDescMap.keySet();
			for(String chDesc : chDescs){
				selectedChDesc = chDesc;
				break;
			}
		}
		fundTransferOperationRequest.setChargeDesc(selectedChDesc);
		fundTransferOperationRequest.setCurr(fundTransferDTO.getTxAmount().getCurrency());
		fundTransferOperationRequest.setFrmAcct(fundTransferDTO.getSourceAcct());
		fundTransferOperationRequest.setRem1(fundTransferDTO.getRem1());
		fundTransferOperationRequest.setRem2(fundTransferDTO.getRem2());
		fundTransferOperationRequest.setRem3(fundTransferDTO.getRem3());
		fundTransferOperationRequest.setTxnType(fundTransferDTO.getTxnType());
		fundTransferOperationRequest.setPayRsonKey(fundTransferDTO.getPayRsonKey());
		fundTransferOperationRequest.setPayRsonValue(fundTransferDTO.getPayRsonValue());
		fundTransferOperationRequest.setPayDtlsKey(fundTransferDTO.getPayDtlsKey());
		fundTransferOperationRequest.setPayDtlsValue(fundTransferDTO.getPayDtlsValue());*/

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

	private boolean getBooleanValue(String val){
		if(BillPaymentConstants.TRUE.equals(val)){
			return true;
		}else
			return false;
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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


}
