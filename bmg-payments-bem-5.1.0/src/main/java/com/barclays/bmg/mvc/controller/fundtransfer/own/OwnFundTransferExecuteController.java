package com.barclays.bmg.mvc.controller.fundtransfer.own;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Charge;
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

public class OwnFundTransferExecuteController extends BMBAbstractCommandController {

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
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	setLastStep(httpRequest);
	FundTransferExecuteCommand fTExecuteCommand = (FundTransferExecuteCommand) command;
	/*
	 * Map<String,Object> processMap = getProcessMapFromSession(httpRequest); String txnRefNo = (String) processMap.get("txnRefNo");
	 */
	String txnRefNo = (String) getFromProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER,
		FundTransferConstants.TXN_REF_NO);
	boolean verified = verifyTxnRefKey(fTExecuteCommand.getTxnRefNo(), txnRefNo);
	Context context = createContext(httpRequest);
	DomesticFundTransferExecuteOperationResponse fudnTransferExecuteOperationResponse = null;

	if (verified) {
	    TransactionDTO transactionDTO = (TransactionDTO) getFromProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER,
		    FundTransferConstants.TRANSACTION_DTO);

	    if (BillPaymentConstants.TRUE.equals(getFromProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
		    BillPaymentConstants.SECOND_AUTH_DONE))) {
		transactionDTO.setScndLvlauthReq(false);
	    }
	    // clearProcessMap(httpRequest);

	    context.setActivityId(getActivityId(transactionDTO.getTxnType()));
	    // context.setActivityRefNo(txnRefNo);
	    DomesticFundTransferExecuteOperationRequest fundTransferExecuteOperationRequest = new DomesticFundTransferExecuteOperationRequest();
	    fundTransferExecuteOperationRequest.setContext(context);

	    // Set the fields for MakeDomesticFundTransferRequest - CPB 30/05
	    context.setOpCde(httpRequest.getParameter("opCde"));
		Charge chargeDTO = null;
		// need to add opcode for the respective CPB DomesticFundTransferRequest requests
		if(httpRequest.getParameter("CpbMakeDomesticFundTransferFields") != null){
			chargeDTO = new Charge();
			if(httpRequest.getParameter("CpbMakeDomesticFundTransferFields").equals("setCpbDomesticInfoFields") && context.getBusinessId().equals("KEBRB")){
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

			}else if(httpRequest.getParameter("CpbMakeDomesticFundTransferFields").equals("xelerateOffline")){
				Double cpbChargeAmount = Double.parseDouble(httpRequest.getParameter("CpbChargeAmount"));
				chargeDTO.setCpbChargeAmount(cpbChargeAmount);
				Double cpbTaxAmount = Double.parseDouble(httpRequest.getParameter("CpbTaxAmount"));
				chargeDTO.setTaxAmount(cpbTaxAmount);
				chargeDTO.setCpbMakeBillPaymentFlag("xelerateOffline");
			}
			//set charge DTO
			transactionDTO.setChargeDTO(chargeDTO);
		}
		fundTransferExecuteOperationRequest.setTransactionDTO(transactionDTO);

	    try {
		fudnTransferExecuteOperationResponse = domesticFundTransferExecuteOperation
			.makeDomesticFundTransfer(fundTransferExecuteOperationRequest);

		if (fudnTransferExecuteOperationResponse != null) {
		    if (fudnTransferExecuteOperationResponse.isScndLvlAuthReq()) {
			String authType = transactionDTO.getScndLvlAuthTyp();
			setIntoProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER, FundTransferConstants.TRANSACTION_DTO,
				transactionDTO);
			setIntoProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER, FundTransferConstants.TXN_REF_NO, txnRefNo);
			setIntoProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_FLOW_ID,
				FundTransferConstants.FT_FLOW_ID);
			/*
			 * setProcessMapIntoSession(httpRequest, "transactionDTO", transactionDTO); setProcessMapIntoSession(httpRequest,
			 * BillPaymentConstants.SECOND_AUTH_FLOW_ID, FundTransferConstants.FT_FLOW_ID); setProcessMapIntoSession(httpRequest,
			 * "txnRefNo", txnRefNo);
			 */if (BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)) {

			    return generateOTP(fundTransferExecuteOperationRequest, httpRequest, txnRefNo);
			} else if (BillPaymentConstants.AUTH_TYPE_SQA.equals(authType)) {
			    return generateSQA(fundTransferExecuteOperationRequest, httpRequest, txnRefNo);
			}
		    }
		}

		if (fudnTransferExecuteOperationResponse != null)
		    if (fudnTransferExecuteOperationResponse.isSuccess()) {
			cleanProcess(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
			domesticFundTransferExecuteOperation.sendSMSSuccessAcknowledgment(fundTransferExecuteOperationRequest,
				fudnTransferExecuteOperationResponse);
		    } else if (!fudnTransferExecuteOperationResponse.isSuccess()) {
			domesticFundTransferExecuteOperation.sendSMSFailAcknowledgment(fundTransferExecuteOperationRequest,
				fudnTransferExecuteOperationResponse);
		    }
	    } catch (Exception ex) {
		domesticFundTransferExecuteOperation.sendSMSFailAcknowledgment(fundTransferExecuteOperationRequest,
			fudnTransferExecuteOperationResponse);
		throw ex;
	    }
	} else {
	    fudnTransferExecuteOperationResponse = new DomesticFundTransferExecuteOperationResponse();
	    fudnTransferExecuteOperationResponse.setResCde(FundTransferResponseCodeConstants.FT_NEVER_INITIATED);
	    fudnTransferExecuteOperationResponse.setContext(context);
	    getMessage(fudnTransferExecuteOperationResponse);
	    fudnTransferExecuteOperationResponse.setSuccess(false);
	}
	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(fudnTransferExecuteOperationResponse);
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

    private String getActivityId(String txnType) {
	String actId = null;
	if (FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER.equals(txnType)) {
	    actId = ActivityConstant.PMT_FT_OWN;
	} else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL.equals(txnType)) {
	    actId = ActivityConstant.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID;
	}
	return actId;
    }

    /**
     * @param request
     * @param httprequest
     * @param txnRefNo
     * @return It will generate OTP and return the response model.
     */
    private BMBPayload generateOTP(RequestContext request, HttpServletRequest httprequest, String txnRefNo) {
	OTPGenerateAuthenticationOperationRequest otpAuthenticationOperationRequest = new OTPGenerateAuthenticationOperationRequest();
	Context context = request.getContext();
	otpAuthenticationOperationRequest.setContext(context);
	otpAuthenticationOperationRequest.setCustomerId(context.getCustomerId());
	otpAuthenticationOperationRequest.setMobilePhone(context.getMobilePhone());
	String[] smsParams = (String[]) getSessionAttribute(httprequest, SessionConstant.SESSION_SMS_PARAMS);
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
     * @return It will generate SQA and return the response model
     */
    private BMBPayload generateSQA(RequestContext request, HttpServletRequest httprequest, String txnRefNo) {
	SQAGenerateAuthenticationOperationRequest sqaAuthenticationOperationRequest = new SQAGenerateAuthenticationOperationRequest();
	sqaAuthenticationOperationRequest.setContext(request.getContext());
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

    public void setDomesticFundTransferExecuteOperation(DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation) {
	this.domesticFundTransferExecuteOperation = domesticFundTransferExecuteOperation;
    }

    public OTPAuthenticationOperation getOtpAuthenticationOperation() {
	return otpAuthenticationOperation;
    }

    public void setOtpAuthenticationOperation(OTPAuthenticationOperation otpAuthenticationOperation) {
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

    public void setSqaAuthenticationOperation(SQAAuthenticationOperation sqaAuthenticationOperation) {
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
