package com.barclays.bmg.mvc.controller.billpayment;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.InvoiceDetails;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.BillPaymentExecuteCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.payments.MakeBillPaymentOperation;
import com.barclays.bmg.operation.payments.ManageFundtransferStatusOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.billpayment.MakeBillPaymentOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.billpayment.MakeBillPaymentOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthOTPOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthSQAOperationResponse;

public class BillPaymentExecutionController extends BMBAbstractCommandController {

    private MakeBillPaymentOperation makeBillPaymentOperation;
    private OTPAuthenticationOperation otpAuthenticationOperation;
    private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
    private SQAAuthenticationOperation sqaAuthenticationOperation;
    private BMBJSONBuilder txnSQASecondAuthJSONBldr;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private ManageFundtransferStatusOperation manageFundtransferStatusOperation;

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	setLastStep(httpRequest);
	BillPaymentExecuteCommand billPaymentExecuteCommand = (BillPaymentExecuteCommand) command;
	// Map<String, Object> processMap = getProcessMapFromSession(httpRequest);
	String txnRefNo = (String) getFromProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TXN_REF_NO);
	boolean verified = true;
	if (!StringUtils.isEmpty(billPaymentExecuteCommand.getTxnRefNo())) {
	    verified = verifyTxnRefKey(billPaymentExecuteCommand.getTxnRefNo(), txnRefNo);
	}

	Context context = createContext(httpRequest);


	TransactionDTO transactionDTO = (TransactionDTO) getFromProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT,
		BillPaymentConstants.TRANSACTION_DTO);


	String isGHMWFreeDialUssdFlow= String.valueOf(httpRequest.getParameter("isGHMWFreeDialUssdFlow"));
	if(null != context && context.getBusinessId().equals("GHBRB") && isGHMWFreeDialUssdFlow!=null && isGHMWFreeDialUssdFlow.equals("TRUE")){
		if(transactionDTO!=null){
			transactionDTO.setAction(isGHMWFreeDialUssdFlow);
		}
	}

	MakeBillPaymentOperationResponse makeBillPaymentOperationResponse = null;

	String txnType = null;

	// context.setActivityRefNo(txnRefNo);
	if (verified && transactionDTO != null) {

	    txnType = transactionDTO.getTxnType();
	    if(null != txnType)
	    	context.setActivityId(getActivityId(txnType));
	    if (BillPaymentConstants.TRUE.equals(getFromProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
		    BillPaymentConstants.SECOND_AUTH_DONE))) {
		transactionDTO.setScndLvlauthReq(false);
	    }

	    //Set the fields for MakeBillPaymentRequest - CPB 15/05
	    // Check for UGBRB
	    //CBP changes
	    if((null != context && context.getBusinessId().equals("GHBRB")|| context.getBusinessId().equals("KEBRB")|| context.getBusinessId().equals("UGBRB")
	    		|| context.getBusinessId().equals("ZMBRB") || context.getBusinessId().equals("BWBRB"))){
		    context.setOpCde(httpRequest.getParameter("opCde"));
			Charge chargeDTO = null;
			// need to add opcode for the respective CPB make bill payment requests 26/05/2017
			if(httpRequest.getParameter("CpbMakeBillPaymentFields") != null){
				chargeDTO = new Charge();
				if(httpRequest.getParameter("CpbMakeBillPaymentFields").equals("setCpbFields")){
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
					chargeDTO.setCpbMakeBillPaymentFlag("setBillPayFields");
				}else if(httpRequest.getParameter("CpbMakeBillPaymentFields").equals("xelerateOffline")){
					Double cpbChargeAmount = Double.parseDouble(httpRequest.getParameter("CpbChargeAmount"));
					chargeDTO.setCpbChargeAmount(cpbChargeAmount);
					Double cpbTaxAmount = Double.parseDouble(httpRequest.getParameter("CpbTaxAmount"));
					chargeDTO.setTaxAmount(cpbTaxAmount);
					chargeDTO.setCpbMakeBillPaymentFlag("xelerateOffline");
				}
			}
			//set charge DTO
			transactionDTO.setChargeDTO(chargeDTO);
	    }

	    //For GePG
	    if(null != context && ("PMT_BP_BILLPAY_ONETIME".equalsIgnoreCase(context.getActivityId()) || "PMT_BP_BILLPAY_PAYEE".equalsIgnoreCase(context.getActivityId()))){
	    	context.setOpCde(httpRequest.getParameter("opCde"));
	    }

	  //GHIPS2- to exclude freeDial Flow
	    String isFreeDialUssdFlow=String.valueOf(httpRequest.getParameter("isFreeDialUssdFlow"));
	    if(null!= context && "GHBRB".equalsIgnoreCase(context.getBusinessId()) && ((ActivityConstant.MOBILE_WALLET_PAYEE_ACTIVITY_ID).equalsIgnoreCase(context.getActivityId()))) {
	    	context.setOpCde(httpRequest.getParameter("opCde"));
    		context.setIsFreeDialUssdFlow(isFreeDialUssdFlow);
	    }
	    //GHIPS2- to set the beneficiary name
	    if(null!= context && "GHBRB".equalsIgnoreCase(context.getBusinessId()) && ((ActivityConstant.MOBILE_WALLET_PAYEE_ACTIVITY_ID).equalsIgnoreCase(context.getActivityId()))
	    		&& null!=httpRequest.getParameter("customerName") && null!=isFreeDialUssdFlow && !isFreeDialUssdFlow.equalsIgnoreCase("TRUE"))  {
	    	transactionDTO.getBeneficiaryDTO().setBeneficiaryNickName(httpRequest.getParameter("customerName"));
	    }

	  //Probase
	    InvoiceDetails invoiceDetails= new InvoiceDetails();
    	LinkedHashMap<String, String> probaseDetails = new LinkedHashMap<String, String>();
	    if(null!= context && "ZMBRB".equalsIgnoreCase(context.getBusinessId()) && "PMT_BP_BILLPAY_ONETIME".equalsIgnoreCase(context.getActivityId()) )
	    {
	    	Iterator it = httpRequest.getParameterMap().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				if(pair.getKey().toString().contains("Probase"))
				{
					probaseDetails.put(pair.getKey().toString().replace("Probase", "") , pair.getValue().toString());
				}
		}
			if(probaseDetails.size()>0){
	    	invoiceDetails.setProbaseDetails(probaseDetails);
	    	transactionDTO.getBeneficiaryDTO().setInvoiceDetails(invoiceDetails);
			}
	    }


	    MakeBillPaymentOperationRequest makeBillPaymentOperationRequest = new MakeBillPaymentOperationRequest();
	    makeBillPaymentOperationRequest.setContext(context);

	    // WUC change - Botswana 20/06/2017
	    if(context.getBusinessId() !=null && context.getBusinessId().equals("BWBRB") && httpRequest.getParameter("wucBillerRefCategory")!=null &&
	    		httpRequest.getParameter("wucBillerRefCategory").equals("WUC-2")){
	    	transactionDTO.getBeneficiaryDTO().setBusinessId(context.getBusinessId());
	    	transactionDTO.getBeneficiaryDTO().setBillerId(httpRequest.getParameter("wucBillerRefCategory"));
	    	transactionDTO.getBeneficiaryDTO().setBillRefNo1(httpRequest.getParameter("customerBillerRef1"));
	    	transactionDTO.getBeneficiaryDTO().setBillRefNo2(httpRequest.getParameter("contractBillerRef2"));
	    }

	    makeBillPaymentOperationRequest.setTransactionDTO(transactionDTO);
	    makeBillPaymentOperationResponse = makeBillPaymentOperation.makeBillPayment(makeBillPaymentOperationRequest);

	    if (makeBillPaymentOperationResponse != null) {
		if (makeBillPaymentOperationResponse.isScndLvlAuthReq()) {
		    String authType = transactionDTO.getScndLvlAuthTyp();
		    setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
		    setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TXN_REF_NO, txnRefNo);
		    setIntoProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_FLOW_ID,
			    BillPaymentConstants.BP_FLOW_ID);

		    // setProcessMapIntoSession(httpRequest, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
		    /*
		     * setProcessMapIntoSession(httpRequest, BillPaymentConstants.SECOND_AUTH_FLOW_ID, FundTransferConstants.FT_FLOW_ID);
		     * setProcessMapIntoSession(httpRequest, BillPaymentConstants.TXN_REF_NO, txnRefNo);
		     */
		    if (BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)) {

			return generateOTP(makeBillPaymentOperationRequest, httpRequest, txnRefNo);
		    } else if (BillPaymentConstants.AUTH_TYPE_SQA.equals(authType)) {
			return generateSQA(makeBillPaymentOperationRequest, httpRequest, txnRefNo);
		    }
		}
		if (makeBillPaymentOperationResponse.isSuccess()) {
		    cleanProcess(httpRequest, BMGProcessConstants.BILL_PAYMENT);
		}
	    }

	    /* Code starts for SMS */
	    /*
	     * if(makeBillPaymentOperationResponse!=null && makeBillPaymentOperationResponse.isSuccess()){
	     * makeBillPaymentOperation.sendSMSSuccessAcknowledgment(makeBillPaymentOperationRequest, makeBillPaymentOperationResponse); } else
	     * if(!makeBillPaymentOperationResponse.isSuccess()){ makeBillPaymentOperation.sendSMSFailAcknowledgment(makeBillPaymentOperationRequest,
	     * makeBillPaymentOperationResponse); }
	     */
	    /* Code ends for SMS */

	} else {
	    makeBillPaymentOperationResponse = new MakeBillPaymentOperationResponse();
	    makeBillPaymentOperationResponse.setResCde(BillPaymentResponseCodeConstants.BILL_PAY_NEVER_INITIATED);
	    makeBillPaymentOperationResponse.setContext(context);
	    getMessage(makeBillPaymentOperationResponse);
	    makeBillPaymentOperationResponse.setSuccess(false);

	}

	setTxnTypeInResponses(txnType, makeBillPaymentOperationResponse);

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

    public MakeBillPaymentOperation getMakeBillPaymentOperation() {
	return makeBillPaymentOperation;
    }

    public void setMakeBillPaymentOperation(MakeBillPaymentOperation makeBillPaymentOperation) {
	this.makeBillPaymentOperation = makeBillPaymentOperation;
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
    public ManageFundtransferStatusOperation getManageFundtransferStatusOperation() {
		return manageFundtransferStatusOperation;
	}

	public void setManageFundtransferStatusOperation(
			ManageFundtransferStatusOperation manageFundtransferStatusOperation) {
		this.manageFundtransferStatusOperation = manageFundtransferStatusOperation;
	}
}