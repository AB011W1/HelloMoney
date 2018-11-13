package com.barclays.bmg.mvc.controller.fundtransfer.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.dto.ExternalFundTransferDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.fundtransfer.own.FundTransferExecuteCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.fundtransfer.external.InternationalFundTransferOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.InternationalFundTransferOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.InternationalFundTransferOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthOTPOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthSQAOperationResponse;
import com.barclays.bmg.service.lookup.BranchLookUpService;
import com.barclays.bmg.service.lookup.impl.BranchLookUpServiceImpl;

public class FundTransferExecutionController extends
		BMBAbstractCommandController {

	private InternationalFundTransferOperation internationalFundTransferOperation;
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
			HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {
		setLastStep(httpRequest);
		Context context = createContext(httpRequest);

		context.setActivityId(activityId);
		FundTransferExecuteCommand fTExecuteCommand = (FundTransferExecuteCommand) command;

		String txnRefNo = (String) getFromProcessMap(httpRequest,
				BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER,
				FundTransferConstants.TXN_REF_NO);
		ExternalFundTransferDTO fundTransferDTO = (ExternalFundTransferDTO) getFromProcessMap(
				httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER,
				FundTransferConstants.FUND_TRANSFER_DTO);

		InternationalFundTransferOperationResponse fundTransferOperationResponse = null;
		boolean verified = verifyTxnRefKey(fTExecuteCommand.getTxnRefNo(),
				txnRefNo);

		if (verified && fundTransferDTO != null) {
			InternationalFundTransferOperationRequest fundTransferOperationRequest = new InternationalFundTransferOperationRequest();
			fundTransferOperationRequest.setContext(context);
			makeRequest(fundTransferOperationRequest, fundTransferDTO);
			fundTransferOperationRequest
					.setAuthRequired(getBooleanValue((String) getFromProcessMap(
							httpRequest,
							BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER,
							SessionConstant.SESSION_AUTH_REQUIRED)));
			fundTransferOperationRequest
					.setAuthType((String) getFromProcessMap(httpRequest,
							BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER,
							SessionConstant.SESSION_AUTH_TYPE));
			if (BillPaymentConstants.TRUE.equals(getFromProcessMap(httpRequest,
					BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
					BillPaymentConstants.SECOND_AUTH_DONE))) {
				fundTransferOperationRequest.setAuthRequired(false);
			}
			fundTransferOperationResponse = internationalFundTransferOperation
					.makeInternationalFundTransfer(fundTransferOperationRequest);

			if (fundTransferOperationResponse != null) {
				if (fundTransferOperationResponse.isAuthRequired()) {
					String authType = (String) getFromProcessMap(httpRequest,
							BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER,
							SessionConstant.SESSION_AUTH_TYPE);
					setIntoProcessMap(httpRequest,
							BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER,
							FundTransferConstants.FUND_TRANSFER_DTO,
							fundTransferDTO);
					setIntoProcessMap(httpRequest,
							BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER,
							FundTransferConstants.TXN_REF_NO, txnRefNo);
					setIntoProcessMap(
							httpRequest,
							BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
							BillPaymentConstants.SECOND_AUTH_FLOW_ID,
							getFlowId(activityId));

					if (BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)) {

						return generateOTP(fundTransferOperationRequest,
								httpRequest, txnRefNo);
					} else if (BillPaymentConstants.AUTH_TYPE_SQA
							.equals(authType)) {
						return generateSQA(fundTransferOperationRequest,
								httpRequest, txnRefNo);
					}
					// get Auth Type and Return the response model.
				}

			}

		} else {
			fundTransferOperationResponse = new InternationalFundTransferOperationResponse();
			fundTransferOperationResponse
					.setResCde(FundTransferResponseCodeConstants.FT_NEVER_INITIATED);
			fundTransferOperationResponse.setContext(context);
			getMessage(fundTransferOperationResponse);
			fundTransferOperationResponse.setSuccess(false);
		}
		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(fundTransferOperationResponse);
	}

	private void makeRequest(
			InternationalFundTransferOperationRequest fundTransferOperationRequest,
			ExternalFundTransferDTO fundTransferDTO) {

		fundTransferOperationRequest.setAmt(fundTransferDTO.getTxAmount());
		// fundTransferOperationRequest.setAuthRequired(fundTransferDTO.);
		fundTransferOperationRequest.setBeneficiaryDTO(fundTransferDTO
				.getBeneficiaryDTO());
		fundTransferOperationRequest.setChargeDesc(fundTransferDTO
				.getSelChDesc());
		fundTransferOperationRequest.setFxRateDTO(fundTransferDTO
				.getFxRateDTO());
		fundTransferOperationRequest
				.setFrmAcct(fundTransferDTO.getSourceAcct());
		fundTransferOperationRequest.setRem1(fundTransferDTO.getRem1());
		fundTransferOperationRequest.setRem2(fundTransferDTO.getRem2());
		fundTransferOperationRequest.setRem3(fundTransferDTO.getRem3());
		fundTransferOperationRequest.setTxnType(fundTransferDTO.getTxnType());
		fundTransferOperationRequest.setPayRsonKey(fundTransferDTO
				.getPayRsonKey());
		fundTransferOperationRequest.setPayRsonValue(fundTransferDTO
				.getPayRsonValue());
		fundTransferOperationRequest.setPayDtlsKey(fundTransferDTO
				.getPayDtlsKey());
		fundTransferOperationRequest.setPayDtlsValue(fundTransferDTO
				.getPayDtlsValue());
		fundTransferOperationRequest.setTxnAmtInLCY(fundTransferDTO
				.getTxnAmtInLCY());

		fundTransferOperationRequest.setTxnNot(fundTransferDTO.getTxnNot());

	}

	private String getFlowId(String activityId) {
		if (ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID
				.equals(activityId)) {
			return FundTransferConstants.EXTERNAL_FLOW_ID;
		} else if (ActivityIdConstantBean.FUND_TRANSFER_INTERNATIONAL_PAYEE_ACTIVITY_ID
				.equals(activityId)) {
			return FundTransferConstants.INTL_FLOW_ID;
		}
		return null;
	}

	/**
	 * @param key
	 * @param request
	 * @return Verify whether txn key is valid.
	 */
	private boolean verifyTxnRefKey(String userEnteredTxnRefNo, String txnRefNo) {
		if (userEnteredTxnRefNo.equals(txnRefNo)) {
			return true;
		}
		return false;
	}

	/**
	 * @param request
	 * @param httprequest
	 * @param txnRefNo
	 * @return It will generate OTP and return the response model.
	 */
	private BMBPayload generateOTP(RequestContext request,
			HttpServletRequest httprequest, String txnRefNo) {
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
	 * @return It will generate SQA and return the response model
	 */
	private BMBPayload generateSQA(RequestContext request,
			HttpServletRequest httprequest, String txnRefNo) {
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

	private boolean getBooleanValue(String val) {
		if (BillPaymentConstants.TRUE.equals(val)) {
			return true;
		} else
			return false;
	}

	public InternationalFundTransferOperation getInternationalFundTransferOperation() {
		return internationalFundTransferOperation;
	}

	public void setInternationalFundTransferOperation(
			InternationalFundTransferOperation internationalFundTransferOperation) {
		this.internationalFundTransferOperation = internationalFundTransferOperation;
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

	public void setTxnOTPSecondAuthJSONBldr(
			BMBJSONBuilder txnOTPSecondAuthJSONBldr) {
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

	public void setTxnSQASecondAuthJSONBldr(
			BMBJSONBuilder txnSQASecondAuthJSONBldr) {
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
