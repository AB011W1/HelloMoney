package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.CreditCardPaymentExecuteCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.payments.OtherBarclayBillPaymentOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.billpayment.MakeBillPaymentOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.billpayment.MakeBillPaymentOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthOTPOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthSQAOperationResponse;

public class OtherBarclayCreditCardExecutionController extends BMBAbstractCommandController {

    private OtherBarclayBillPaymentOperation otherBarclayBillPaymentOperation;
    private OTPAuthenticationOperation otpAuthenticationOperation;
    private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
    private SQAAuthenticationOperation sqaAuthenticationOperation;
    private BMBJSONBuilder txnSQASecondAuthJSONBldr;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

    @Override
    protected String getActivityId(Object command) {
	return null;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	setLastStep(httpRequest);
	CreditCardPaymentExecuteCommand billPaymentExecuteCommand = (CreditCardPaymentExecuteCommand) command;
	String txnRefNo = (String) getFromProcessMap(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT, BillPaymentConstants.TXN_REF_NO);

	boolean verified = true;
	if (!StringUtils.isEmpty(billPaymentExecuteCommand.getTxnRefNo())) {
	    verified = verifyTxnRefKey(billPaymentExecuteCommand.getTxnRefNo(), txnRefNo);
	}

	Context context = createContext(httpRequest);
	TransactionDTO transactionDTO = (TransactionDTO) getFromProcessMap(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT,
		BillPaymentConstants.TRANSACTION_DTO);
	if(null != transactionDTO)
		transactionDTO.setBeneficiaryDTO(new BeneficiaryDTO());
	MakeBillPaymentOperationResponse makeBillPaymentOperationResponse = null;

	if (verified && transactionDTO != null) {

	    context.setActivityId(BillPaymentConstants.PMT_FT_CARD_PAYMENT_OWN);
	    if (BillPaymentConstants.TRUE.equals(getFromProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
		    BillPaymentConstants.SECOND_AUTH_DONE))) {
		transactionDTO.setScndLvlauthReq(false);
	    }

	    transactionDTO.getBeneficiaryDTO().setDestinationAccountNumber(billPaymentExecuteCommand.getCrCardActNo());
	    transactionDTO.getBeneficiaryDTO().setCardNumber(billPaymentExecuteCommand.getCrdNo());
	    transactionDTO.getBeneficiaryDTO().setBeneficiaryName(billPaymentExecuteCommand.getBeneficiaryName());
	    transactionDTO.setOrgCode(billPaymentExecuteCommand.getOrgCode());
	    transactionDTO.getBeneficiaryDTO().setCurrency(transactionDTO.getSourceAcct().getCurrency());

	    MakeBillPaymentOperationRequest makeBillPaymentOperationRequest = new MakeBillPaymentOperationRequest();
	    makeBillPaymentOperationRequest.setContext(context);
	    makeBillPaymentOperationRequest.setTransactionDTO(transactionDTO);

	    makeBillPaymentOperationResponse = otherBarclayBillPaymentOperation.makeBillPayment(makeBillPaymentOperationRequest);

	    if (makeBillPaymentOperationResponse != null) {
		if (makeBillPaymentOperationResponse.isScndLvlAuthReq()) {
		    String authType = transactionDTO.getScndLvlAuthTyp();
		    setIntoProcessMap(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
		    setIntoProcessMap(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT, BillPaymentConstants.TXN_REF_NO, txnRefNo);
		    setIntoProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_FLOW_ID,
			    BillPaymentConstants.BP_FLOW_ID);
		    if (BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)) {

			return generateOTP(makeBillPaymentOperationRequest, httpRequest, txnRefNo);
		    } else if (BillPaymentConstants.AUTH_TYPE_SQA.equals(authType)) {
			return generateSQA(makeBillPaymentOperationRequest, httpRequest, txnRefNo);
		    }
		}
		if (makeBillPaymentOperationResponse.isSuccess()) {
		    cleanProcess(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT);
		}
	    }

	} else {
	    makeBillPaymentOperationResponse = new MakeBillPaymentOperationResponse();
	    makeBillPaymentOperationResponse.setResCde(BillPaymentResponseCodeConstants.BILL_PAY_NEVER_INITIATED);
	    makeBillPaymentOperationResponse.setContext(context);
	    getMessage(makeBillPaymentOperationResponse);
	    makeBillPaymentOperationResponse.setSuccess(false);
	}

	setTxnTypeInResponses(BillPaymentConstants.TXN_TYP, makeBillPaymentOperationResponse);

	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(makeBillPaymentOperationResponse);

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

    public OtherBarclayBillPaymentOperation getOtherBarclayBillPaymentOperation() {
	return otherBarclayBillPaymentOperation;
    }

    public void setOtherBarclayBillPaymentOperation(OtherBarclayBillPaymentOperation otherBarclayBillPaymentOperation) {
	this.otherBarclayBillPaymentOperation = otherBarclayBillPaymentOperation;
    }

}
