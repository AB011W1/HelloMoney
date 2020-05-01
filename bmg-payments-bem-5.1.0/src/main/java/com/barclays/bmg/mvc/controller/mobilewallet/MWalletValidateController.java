/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
/**
 * Package name is com.barclays.bmg.mvc.controller.mobilewallet
 * /
 */
package com.barclays.bmg.mvc.controller.mobilewallet;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.MWalletValidateDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.mobilewallet.MWalletValidateCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.mvc.operation.mobilewallet.MWalletValidate;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.MWalletValidateRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.service.request.MWalletValidateServiceRequest;
import com.barclays.bmg.service.response.MWalletValidateResponse;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-payments-bem-5.1.0</b> </br> The file name is
 * <b>MWalletValidateController.java</b> </br> Description is <b>V 1.1</b> </br> Updated Date is <b>May 28, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class MWalletValidateController created using JRE 1.6.0_10
 */
public class MWalletValidateController extends BMBAbstractCommandController {


    /**
     * The instance variable named "MOBILE_WALLET_MTP_RESP_CODE_ERROR" is created.
     */
    private String MOBILE_WALLET_MTP_RESP_CODE_ERROR = "RES0651";

    /**
     * The instance variable named "activityId" is created.
     */
    private String activityId;
    // private String payGrp;
    /**
     * The instance variable named "bmbJSONBuilder" is created.
     */
    private BMBJSONBuilder bmbJSONBuilder;

    /**
     * The instance variable named "getSelectedAccountOperation" is created.
     */
    private GetSelectedAccountOperation getSelectedAccountOperation;

    /**
     * The instance variable named "mWalletValidate" is created.
     */
    private MWalletValidate mWalletValidate;

    /**
     * The instance variable named "billerCatId" is created.
     */
    private String billerCatId;
    private FormValidateOperation formValidateOperation;

    /**
     * @return the formValidateOperation
     */
    public FormValidateOperation getFormValidateOperation() {
	return formValidateOperation;
    }

    /**
	 *
	 */
    public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
	this.formValidateOperation = formValidateOperation;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#getActivityId(java.lang.Object)
     */
    @Override
    protected String getActivityId(Object command) {

	return activityId;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	Context context = buildContext(request);
	MWalletValidateCommand mWalletValidateCommand = (MWalletValidateCommand) command;

	MWalletValidateRequest mWalletValidateRequest = new MWalletValidateRequest();
	mWalletValidateRequest.setActNo(mWalletValidateCommand.getActNo());
	mWalletValidateRequest.setAmt(mWalletValidateCommand.getTxnAmt());
	mWalletValidateRequest.setBillerCatId(getBillerCatId());
	mWalletValidateRequest.setMblNo(mWalletValidateCommand.getMblNo());
	mWalletValidateRequest.setContext(context);
	mWalletValidateRequest.setBillerId(mWalletValidateCommand.getBillerId());

	MWalletValidateResponse mWalletValidateResponse = null;
	mWalletValidateResponse = mWalletValidate.validateRequest(context, mWalletValidateRequest, mWalletValidateResponse);

	GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = null;
	GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
	getSelectedAccountOperationRequest.setContext(context);
	//AccountSummaryOperationRequest accountSummaryOperationRequest = new AccountSummaryOperationRequest();

	if ("CreditCardMW".equals(mWalletValidateCommand.getCrditCardFlag())) {
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(mWalletValidateCommand.getActNo(),
				request,BMGProcessConstants.CREDIT_PAYMENT));
        //Change to filter blocked card on selection
		String ccNumber = request.getParameterMap().get("ccNumber")!= null ? request.getParameterMap().get("ccNumber").toString() : "";
		getSelectedAccountOperationRequest.setCreditCardNumber(ccNumber);
		getSelectedAccountOperationResponse = getSelectedAccountOperation.getSelectedCreditCardAccount(getSelectedAccountOperationRequest);
		} else {
			getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(mWalletValidateCommand.getActNo(),
					request,BMGProcessConstants.BILL_PAYMENT));
			if(getSelectedAccountOperationRequest.getAcctNumber()==null)
				if(mWalletValidateCommand.getActNo()!=null)
					getSelectedAccountOperationRequest.setAcctNumber(mWalletValidateCommand.getActNo().trim());
			getSelectedAccountOperationResponse = getSelectedAccountOperation
					.getSelectedSourceAccount(getSelectedAccountOperationRequest);
		}

	Map<String, Object> sysMap = mWalletValidateRequest.getContext().getContextMap();
	String destBankCode = (String) sysMap.get(SystemParameterConstant.BUSINESS_BANK_CODE);

	MWalletValidateServiceResopnse res = null;
	FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();
	if (getSelectedAccountOperationResponse.isSuccess() && mWalletValidateResponse.isSuccess()) {
	    FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();

	    // Set OpCode for MWallet - CPB 16/05/2017
	    // Check for UGBRB
	    //Change CBP
		Map<String, Object> contextMap = context.getContextMap();
		if(context.getActivityId().equals("PMT_BP_MOBILE_WALLET_ONETIME") && (contextMap!=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y") || context.getBusinessId().equals("KEBRB"))){
			context.setOpCde(request.getParameter("opCde"));
		}

	    formValidateOperationRequest.setContext(context);
	    formValidateOperationRequest.setFrmAct(getSelectedAccountOperationResponse.getSelectedAcct());
	    Amount txnAmount = new Amount();
	    txnAmount.setAmount(new BigDecimal(mWalletValidateCommand.getTxnAmt()));
	    txnAmount.setCurrency(getSelectedAccountOperationResponse.getSelectedAcct().getCurrency());
	    formValidateOperationRequest.setTxnAmt(txnAmount);
	    formValidateOperationRequest.setTxnType(getBillerCatId());
	    formValidateOperationRequest.setCreditCardFlag(mWalletValidateCommand.getCrditCardFlag());
	    formValidateOperationRequest.setBillerId(mWalletValidateCommand.getBillerId());

	    formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);

	    MWalletValidateServiceRequest mWalletValidateServiceRequest = new MWalletValidateServiceRequest();

	    if("CreditCardMW".equals(mWalletValidateCommand.getCrditCardFlag())){
	    	mWalletValidateServiceRequest.setCreditCard(getSelectedAccountOperationResponse.getSelectedAcct());
	    } else {
	    	mWalletValidateServiceRequest.setAccount((CASAAccountDTO) getSelectedAccountOperationResponse.getSelectedAcct());
	    }

	    mWalletValidateServiceRequest.setBillerDTO(mWalletValidateResponse.getBillerDTO());
	    mWalletValidateServiceRequest.setContext(context);

	    res = setResponseMWallet(mWalletValidateServiceRequest, mWalletValidateRequest, mWalletValidateCommand.getCrditCardFlag());

	    // proccessMap(request, res);
	    TransactionDTO transactionDTO = new TransactionDTO();

	    if("CreditCardMW".equals(mWalletValidateCommand.getCrditCardFlag())){
	    	 transactionDTO.setSourceAcct(res.getCreditCard());
	    } else {
	    	  transactionDTO.setSourceAcct(res.getAccount());
	    }

	    Amount amount = new Amount();
	    amount.setAmount(BigDecimal.valueOf(Double.valueOf((res.getAmount()))));
	    amount.setCurrency(res.getBillerDTO().getCurrency());
	    transactionDTO.setTxnAmt(amount);
	    transactionDTO.setTxnType(getBillerCatId());
	    transactionDTO.setMtpService(getBillerCatId());
	    transactionDTO.setTxnAmtInLCY(BigDecimal.valueOf(Double.valueOf(res.getAmount())));
	    transactionDTO.setTxnNot(mWalletValidateCommand.getTxnNot());

	    BillerDTO billerDTO = res.getBillerDTO();
	    BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
	    beneficiaryDTO.setBillAggregatorId(billerDTO.getBillAggregatorId());
	    beneficiaryDTO.setBillerCategoryId(billerDTO.getBillerCategoryId());
	    beneficiaryDTO.setContactNumber(res.getMblNmbr());
	    beneficiaryDTO.setTransactionReferenceNumber(billerDTO.getReferenceNoKey1());
	    beneficiaryDTO.setBillRefNo1(res.getMblNmbr());
	    beneficiaryDTO.setBillRefNo(res.getMblNmbr());
	    beneficiaryDTO.setExternalBillerId(billerDTO.getExternalBillerId());
	    beneficiaryDTO.setBusinessId(billerDTO.getBusinessId());
	    beneficiaryDTO.setBillerName(billerDTO.getBillerName());
	    beneficiaryDTO.setDestinationAccountNumber(billerDTO.getBillerAccountNumber());
	    beneficiaryDTO.setDestinationBranchCode(billerDTO.getBranchCode());
	    beneficiaryDTO.setCurrency(billerDTO.getCurrency());
	    beneficiaryDTO.setBillerId(billerDTO.getBillerId());
	    beneficiaryDTO.setDestinationBankCode(destBankCode);
	    beneficiaryDTO.setActionCode(mWalletValidateCommand.getActionCode());
	    beneficiaryDTO.setStoreNumber(mWalletValidateCommand.getStoreNumber());
		// Cards Migration: Start
		CreditCardAccountDTO cardDTO = (CreditCardAccountDTO) getSelectedAccountOperationResponse.getSelectedAcct();
		if (cardDTO.getCardExpireDate() != null) {
			beneficiaryDTO.setCreditCardExpiryDate(cardDTO.getCardExpireDate());
		}
		// Cards Migration: Ends

	    transactionDTO.setBeneficiaryDTO(beneficiaryDTO);
	    setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);

	    MWalletValidateDTO mWalletValidateDTO = new MWalletValidateDTO();

	    if("CreditCardMW".equals(mWalletValidateCommand.getCrditCardFlag())){
	    	 mWalletValidateDTO.setAccount(res.getCreditCard());
	    } else {
	    	 mWalletValidateDTO.setAccount(res.getAccount());
	    }

	    mWalletValidateDTO.setAmount(res.getAmount());
	    mWalletValidateDTO.setBillerDTO(res.getBillerDTO());
	    mWalletValidateDTO.setMblNmbr(res.getMblNmbr());
	    setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TXN_REF_NO, res.getTxnRefNo());
	} else {
	    mWalletValidateResponse = new MWalletValidateResponse();
	    mWalletValidateResponse.setSuccess(false);
	    mWalletValidateResponse.setResCde(MOBILE_WALLET_MTP_RESP_CODE_ERROR);
	    mWalletValidateResponse.setContext(context);
	    getMessage(mWalletValidateResponse);

	}

	return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder).createMultipleJSONResponse(mWalletValidateResponse,
		getSelectedAccountOperationResponse, res, formValidateOperationResponse);
    }

    /**
     * The method is written for Sets the response m wallet.
     *
     * @param walletValidateServiceRequest
     *            the wallet validate service request
     * @param walletValidateRequest
     *            the wallet validate request
	 * @param creditCardFlag
     * @return the MWalletValidateServiceResopnse's object
     */
    private MWalletValidateServiceResopnse setResponseMWallet(MWalletValidateServiceRequest walletValidateServiceRequest,
	    MWalletValidateRequest walletValidateRequest, String creditCardFlag) {
	MWalletValidateServiceResopnse resopnse = new MWalletValidateServiceResopnse();

	if("CreditCardMW".equals(creditCardFlag)){
		resopnse.setCreditCard(walletValidateServiceRequest.getCreditCard());
    }else{
    	resopnse.setAccount(walletValidateServiceRequest.getAccount());
    }
	resopnse.setBillerDTO(walletValidateServiceRequest.getBillerDTO());
	resopnse.setContext(walletValidateServiceRequest.getContext());
	resopnse.setTxnRefNo(walletValidateServiceRequest.getContext().getActivityRefNo());
	resopnse.setAmount(walletValidateRequest.getAmt());
	resopnse.setMblNmbr(walletValidateRequest.getMblNo());

	return resopnse;
    }

    /**
     * The method is written for Proccess map.
     *
     * @param request
     *            the request
     * @param res
     *            the res
     */
    /*
     * private void proccessMap(HttpServletRequest request, MWalletValidateServiceResopnse res) { TransactionDTO transactionDTO = new
     * TransactionDTO(); transactionDTO.setSourceAcct(res.getAccount());
     *
     * Amount amount = new Amount(); amount.setAmount(BigDecimal.valueOf(Double.valueOf((res.getAmount()))));
     * amount.setCurrency(res.getBillerDTO().getCurrency()); transactionDTO.setTxnAmt(amount); transactionDTO.setTxnType(getBillerCatId());
     * transactionDTO.setMtpService(getBillerCatId()); transactionDTO.setTxnAmtInLCY(BigDecimal.valueOf(Double.valueOf(res.getAmount())));
     * transactionDTO.setBeneficiaryDTO(billerToBeneficiary(res)); setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT,
     * BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
     *
     * MWalletValidateDTO mWalletValidateDTO = new MWalletValidateDTO(); mWalletValidateDTO.setAccount(res.getAccount());
     * mWalletValidateDTO.setAmount(res.getAmount()); mWalletValidateDTO.setBillerDTO(res.getBillerDTO());
     * mWalletValidateDTO.setMblNmbr(res.getMblNmbr()); setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT,BillPaymentConstants.TXN_REF_NO,
     * res.getTxnRefNo()); // /setSessionAttribute(request, res.getTxnRefNo(), airTimeTopUpValidateDTO); }
     *//**
     * The method is written for Biller to beneficiary.
     *
     * @param res
     *            the res
     * @return the BeneficiaryDTO's object
     */
    /*
     * private BeneficiaryDTO billerToBeneficiary(MWalletValidateServiceResopnse res) { BillerDTO billerDTO = res.getBillerDTO(); BeneficiaryDTO
     * beneficiaryDTO = new BeneficiaryDTO(); beneficiaryDTO.setBillAggregatorId(billerDTO.getBillAggregatorId());
     * beneficiaryDTO.setBillerCategoryId(billerDTO.getBillerCategoryId()); beneficiaryDTO.setContactNumber(res.getMblNmbr());
     * beneficiaryDTO.setTransactionReferenceNumber(billerDTO.getReferenceNoKey1()); beneficiaryDTO.setBillRefNo1(res.getMblNmbr());
     * beneficiaryDTO.setBusinessId(billerDTO.getBusinessId()); beneficiaryDTO.setBillerName(billerDTO.getBillerName());
     * beneficiaryDTO.setDestinationAccountNumber(billerDTO.getBillerAccountNumber());
     * beneficiaryDTO.setDestinationBranchCode(billerDTO.getBranchCode()); beneficiaryDTO.setCurrency(billerDTO.getCurrency());
     * beneficiaryDTO.setBillerId(billerDTO.getBillerId()); beneficiaryDTO.setDestinationBankCode(destBankCode); return beneficiaryDTO; }
     */

    /**
     * The method is written for Builds the context.
     *
     * @param httpRequest
     *            the http request
     * @return the Context's object
     */
    @SuppressWarnings("unchecked")
    protected Context buildContext(HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);

	Map<String, String> ppMap = (Map<String, String>) getSessionAttribute(httpRequest, SessionConstant.SESSION_PP_MAP);

	context.setPpMap(ppMap);

	context.setActivityId(getActivityId());

	return context;

    }

    /**
     * Gets the activity id.
     *
     * @return the ActivityId
     */
    public String getActivityId() {
	return activityId;
    }

    /**
     * Sets values for ActivityId.
     *
     * @param activityId
     *            the activity id
     */
    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    /**
     * Gets the get selected account operation.
     *
     * @return the GetSelectedAccountOperation
     */
    public GetSelectedAccountOperation getGetSelectedAccountOperation() {
	return getSelectedAccountOperation;
    }

    /**
     * Sets values for GetSelectedAccountOperation.
     *
     * @param getSelectedAccountOperation
     *            the gets the selected account operation
     */
    public void setGetSelectedAccountOperation(GetSelectedAccountOperation getSelectedAccountOperation) {
	this.getSelectedAccountOperation = getSelectedAccountOperation;
    }

    /**
     * Gets the bmb json builder.
     *
     * @return the BmbJSONBuilder
     */
    public BMBJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    /**
     * Sets values for BmbJSONBuilder.
     *
     * @param bmbJSONBuilder
     *            the bmb json builder
     */
    public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    /**
     * Gets the biller cat id.
     *
     * @return the BillerCatId
     */
    public String getBillerCatId() {
	return billerCatId;
    }

    /**
     * Sets values for BillerCatId.
     *
     * @param billerCatId
     *            the biller cat id
     */
    public void setBillerCatId(String billerCatId) {
	this.billerCatId = billerCatId;
    }

    /**
     * Gets the m wallet validate.
     *
     * @return the MWalletValidate
     */
    public MWalletValidate getMWalletValidate() {
	return mWalletValidate;
    }

    /**
     * Sets values for MWalletValidate.
     *
     * @param walletValidate
     *            the m wallet validate
     */
    public void setMWalletValidate(MWalletValidate walletValidate) {
	mWalletValidate = walletValidate;
    }


}