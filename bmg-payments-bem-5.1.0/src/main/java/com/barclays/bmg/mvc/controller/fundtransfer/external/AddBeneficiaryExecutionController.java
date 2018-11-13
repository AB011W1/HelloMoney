package com.barclays.bmg.mvc.controller.fundtransfer.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BeneficiaryConstants;
import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.AddBeneficiaryCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.beneficiary.AddBeneficiaryOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.AddBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.AddBeneficiaryOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthOTPOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthSQAOperationResponse;

/**
 * @author BTCI
 *
 */
public class AddBeneficiaryExecutionController extends
		BMBAbstractCommandController {
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private AddBeneficiaryOperation addBeneficiaryOperation;
	private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
	private OTPAuthenticationOperation otpAuthenticationOperation;
	private SQAAuthenticationOperation sqaAuthenticationOperation;
	private BMBJSONBuilder txnSQASecondAuthJSONBldr;

	/**
	 * @param txnOTPSecondAuthJSONBldr
	 */
	public void setTxnOTPSecondAuthJSONBldr(
			BMBJSONBuilder txnOTPSecondAuthJSONBldr) {
		this.txnOTPSecondAuthJSONBldr = txnOTPSecondAuthJSONBldr;
	}

	/**
	 * @param sqaAuthenticationOperation
	 */
	public void setSqaAuthenticationOperation(
			SQAAuthenticationOperation sqaAuthenticationOperation) {
		this.sqaAuthenticationOperation = sqaAuthenticationOperation;
	}

	/**
	 * @param txnSQASecondAuthJSONBldr
	 */
	public void setTxnSQASecondAuthJSONBldr(
			BMBJSONBuilder txnSQASecondAuthJSONBldr) {
		this.txnSQASecondAuthJSONBldr = txnSQASecondAuthJSONBldr;
	}

	/**
	 * @param otpAuthenticationOperation
	 */
	public void setOtpAuthenticationOperation(
			OTPAuthenticationOperation otpAuthenticationOperation) {
		this.otpAuthenticationOperation = otpAuthenticationOperation;
	}

	/**
	 * @param bmbJSONBuilder
	 */
	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	/**
	 * @param addBeneficiaryOperation
	 */
	public void setAddBeneficiaryOperation(
			AddBeneficiaryOperation addBeneficiaryOperation) {
		this.addBeneficiaryOperation = addBeneficiaryOperation;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#
	 * getActivityId(java.lang.Object)
	 */
	@Override
	protected String getActivityId(Object command) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse response, Object command, BindException errors) throws Exception {
		setLastStep(httpRequest);
		AddBeneficiaryCommand addBeneficiaryCommand = (AddBeneficiaryCommand) command;
		Context context = createContext(httpRequest);

		AddBeneficiaryOperationResponse addBeneficiaryOperationResponse = null;
		AddBeneficiaryOperationRequest addBeneficiaryOperationRequest = new AddBeneficiaryOperationRequest();
		String txnRefNo = (String) getFromProcessMap(httpRequest, BMGProcessConstants.ADD_BENEFICIARY, BeneficiaryConstants.TXN_REF_NO);
		boolean verified = true;
		if (!StringUtils.isEmpty(addBeneficiaryCommand.getTxnRefNo())) {
			verified = verifyTxnRefKey(addBeneficiaryCommand.getTxnRefNo(), txnRefNo);
		}
		BeneficiaryDTO beneficiaryDTO = (BeneficiaryDTO) getFromProcessMap(httpRequest, BMGProcessConstants.ADD_BENEFICIARY, BeneficiaryConstants.BENEFICIARY_DTO);
		if (beneficiaryDTO != null && FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(beneficiaryDTO.getPayeeTypeCode())) {
			context.setActivityId(ActivityIdConstantBean.ADD_INTERNAL_PAYEE_ACTIVITY_ID);
		} else {
			context.setActivityId(ActivityIdConstantBean.ADD_EXTERNAL_PAYEE_ACTIVITY_ID);
		}
		addBeneficiaryOperationRequest.setContext(context);

		cleanProcess(httpRequest, BMGProcessConstants.ADD_BENEFICIARY);


		if (verified && beneficiaryDTO != null) {
			//set whether we need sec level auth
			if (BeneficiaryConstants.TRUE.equals(getFromProcessMap(httpRequest,BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,BillPaymentConstants.SECOND_AUTH_DONE))) {
				addBeneficiaryOperationRequest.setScndLvlauthReq(false);
			} else {
				addBeneficiaryOperationRequest.setScndLvlauthReq(true);
			}

			addBeneficiaryOperationRequest = buildAddBeneficiaryOperationRequest(beneficiaryDTO, addBeneficiaryOperationRequest);
			try {
				addBeneficiaryOperationResponse = addBeneficiaryOperation.addBeneficiary(addBeneficiaryOperationRequest);

				//call sec level auth based on requirement
				if (addBeneficiaryOperationResponse != null) {
					if (addBeneficiaryOperationResponse.isScndLvlAuthReq()) {
						String authType = addBeneficiaryOperationRequest.getScndLvlAuthTyp();
						setIntoProcessMap(httpRequest,BMGProcessConstants.ADD_BENEFICIARY,BeneficiaryConstants.BENEFICIARY_DTO,beneficiaryDTO);
						setIntoProcessMap(httpRequest,BMGProcessConstants.ADD_BENEFICIARY,BeneficiaryConstants.TXN_REF_NO, txnRefNo);
						setIntoProcessMap(httpRequest,BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,BillPaymentConstants.SECOND_AUTH_FLOW_ID,BeneficiaryConstants.AB_FLOW_ID);

						if (BeneficiaryConstants.AUTH_TYPE_OTP.equals(authType)) {
							addBeneficiaryOperationResponse.setBeneficiaryDTO(beneficiaryDTO);
							return generateOTP(addBeneficiaryOperationRequest, httpRequest, txnRefNo);
						} else if (BeneficiaryConstants.AUTH_TYPE_SQA.equals(authType)) {
							return generateSQA(addBeneficiaryOperationRequest,
									httpRequest, txnRefNo);
						}
					}
				}
				/* Code starts for SMS */
				if(addBeneficiaryOperationResponse != null)
				if(addBeneficiaryOperationResponse.isSuccess()){
					addBeneficiaryOperation.sendSMSSuccessAcknowledgment(addBeneficiaryOperationRequest, addBeneficiaryOperationResponse);
				} else if(!addBeneficiaryOperationResponse.isSuccess()){
					addBeneficiaryOperation.sendSMSFailAcknowledgment(addBeneficiaryOperationRequest, addBeneficiaryOperationResponse);
				}
				/* Code ends for SMS */
			} catch(Exception ex) {
				addBeneficiaryOperation.sendSMSFailAcknowledgment(addBeneficiaryOperationRequest, addBeneficiaryOperationResponse);
				throw ex;
			}

		} else {
			addBeneficiaryOperationResponse = new AddBeneficiaryOperationResponse();
			addBeneficiaryOperationResponse.setResCde(BeneficiaryResponseCodeConstants.ADD_BENEFICIARY_NEVER_INITIATED);
			addBeneficiaryOperationResponse.setContext(context);
			getMessage(addBeneficiaryOperationResponse);
			addBeneficiaryOperationResponse.setSuccess(false);
		}

		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(addBeneficiaryOperationResponse);
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
	 * @param beneficiaryDTO
	 * @param AddBeneficiaryOperationRequest
	 * @return AddBeneficiaryOperationRequest
	 */
	private AddBeneficiaryOperationRequest buildAddBeneficiaryOperationRequest(
			BeneficiaryDTO beneficiaryDTO,
			AddBeneficiaryOperationRequest request) {
		request.setBeneficiaryDTO(beneficiaryDTO);
		return request;

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

}
