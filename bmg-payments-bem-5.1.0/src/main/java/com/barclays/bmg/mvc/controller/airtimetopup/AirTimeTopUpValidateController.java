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
 * Package name is com.barclays.bmg.mvc.controller.airtimetopup
 * /
 */
package com.barclays.bmg.mvc.controller.airtimetopup;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.AirTimeTopUpValidateDTO;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.airtimetopup.AirTimeTopUpValidateCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.mvc.operation.airtimetopup.AirTimeTopUpValidate;
import com.barclays.bmg.operation.accounts.AccountSummaryOperation;
import com.barclays.bmg.operation.accounts.request.AccountSummaryOperationRequest;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.AirTimeTopUpValidateRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.AirTimeTopUpValidateServiceResopnse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.service.request.AirTimeTopUpValidateServiceRequest;
import com.barclays.bmg.service.response.AirTimeTopUpValidateResponse;
import com.barclays.bmg.utils.BMBCommonUtility;
import com.ibm.ws.activity.ActivityConstants;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-payments-bem-5.1.0</b> </br> The file name is
 * <b>AirTimeTopUpValidateController.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 13, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class AirTimeTopUpValidateController created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidateController extends BMBAbstractCommandController {

    /**
     * The instance variable named "AIR_MTP_RESP_CODE_ERROR" is created.
     */
    // private String AIR_MTP_RESP_CODE_ERROR = "RES0651";
    /**
     * The instance variable named "activityId" is created.
     */
    private String activityId;


    private AccountSummaryOperation accountSummaryOperation;
    private AirTimeTopUpValidateServiceResopnse res;

    /**
     * The instance variable named "payGrp" is created.
     */
    private String payGrp;

    /**
     * The instance variable named "bmbJSONBuilder" is created.
     */
    private BMBJSONBuilder bmbJSONBuilder;

    /**
     * The instance variable named "getSelectedAccountOperation" is created.
     */
    private GetSelectedAccountOperation getSelectedAccountOperation;
    FormValidateOperationResponse formValidateOperationResponse ;

    /**
     * The instance variable named "airTimeTopUpValidate" is created.
     */
    private AirTimeTopUpValidate airTimeTopUpValidate;

    /**
     * The instance variable named "billerCatId" is created.
     */
    private FormValidateOperation formValidateOperation;
    private String billerCatId;
    private final static String RECH = "RECHARGE";
 	private final static String IDENTIFIRE = "CreditCardAT";

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

	AirTimeTopUpValidateCommand airTimeTopUpValidateCommand = (AirTimeTopUpValidateCommand) command;
	String extra=airTimeTopUpValidateCommand.getExtra();
	if((airTimeTopUpValidateCommand.getBillerId().equals("AIRTELCR-5") || airTimeTopUpValidateCommand.getBillerId().equals("AIRTELZMBANKTOWALLET-2")) && extra!=null && (extra.equals("FREEDIALAIRTEL") || extra.equals("FREEDIALAIRTELZM"))){
		billerCatId="MobileMoney";
		context.setActivityId("PMT_BP_MOBILE_WALLET_ONETIME");
	}else
		billerCatId="Telephone";
// Get Credit card Flag from USSD Request
		String creditCardFlag = null;
		if (!StringUtils.isEmpty(airTimeTopUpValidateCommand.getCreditCardFlag())) {
			creditCardFlag = airTimeTopUpValidateCommand.getCreditCardFlag();
		}

		// Get Account Type from USSD Request
		String accountType = null;
		if (!StringUtils.isEmpty(airTimeTopUpValidateCommand.getAccountType())) {
			accountType = airTimeTopUpValidateCommand.getAccountType();
		}

		// Get AccountID Param from USSD Request
		String activityIDParam = null;
		if (!StringUtils.isEmpty(airTimeTopUpValidateCommand.getActivityId())) {
			activityIDParam = airTimeTopUpValidateCommand.getActivityId();
		}


	AirTimeTopUpValidateRequest airTimeTopUpValidateRequest = new AirTimeTopUpValidateRequest();
	if(IDENTIFIRE.equalsIgnoreCase(creditCardFlag))
		{
			airTimeTopUpValidateRequest.setActNo(airTimeTopUpValidateCommand.getCreditcardNo());
		}else
        {
			airTimeTopUpValidateRequest.setActNo(airTimeTopUpValidateCommand.getActNo());
		}

//airTimeTopUpValidateRequest.setActNo(airTimeTopUpValidateCommand.getActNo());
	airTimeTopUpValidateRequest.setAmt(airTimeTopUpValidateCommand.getTxnAmt());
	airTimeTopUpValidateRequest.setBillerCatId(getBillerCatId());
	airTimeTopUpValidateRequest.setMblNo(airTimeTopUpValidateCommand.getMblNo());
	airTimeTopUpValidateRequest.setContext(context);
	airTimeTopUpValidateRequest.setBillerId(airTimeTopUpValidateCommand.getBillerId());
	airTimeTopUpValidateRequest.setBillHolderNam(airTimeTopUpValidateCommand.getBillHolderNam());

	AirTimeTopUpValidateResponse airTimeTopUpValidateResponse = null;

   airTimeTopUpValidateResponse = airTimeTopUpValidate.validateRequest(context, airTimeTopUpValidateRequest, airTimeTopUpValidateResponse);

	GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = null;
	GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();

	getSelectedAccountOperationRequest.setContext(context);
if (IDENTIFIRE.equalsIgnoreCase(creditCardFlag)) {

        	getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(airTimeTopUpValidateCommand.getCreditcardNo(),
    				request,BMGProcessConstants.CREDIT_PAYMENT));
  			//Change to filter blocked card on selection
    		String ccNumber = request.getParameterMap().get("ccNumber")!= null ? request.getParameterMap().get("ccNumber").toString() : "";
    		getSelectedAccountOperationRequest.setCreditCardNumber(ccNumber);
        	getSelectedAccountOperationResponse = getSelectedAccountOperation.getSelectedCreditCardAccount(getSelectedAccountOperationRequest);
        }else{
        	if(extra!=null && (extra.equals("FREEDIALAIRTEL") || extra.equals("FREEDIALAIRTELZM")))
        		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(
    					airTimeTopUpValidateCommand.getActNo(), request,
    					BMGProcessConstants.ACCOUNTS_PROCESS));
        	else
        		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(
					airTimeTopUpValidateCommand.getActNo(), request,
					BMGProcessConstants.BILL_PAYMENT));
			// BMGProcessConstants.AIR_TIME_TOP_UP));
        	getSelectedAccountOperationResponse = getSelectedAccountOperation
					.getSelectedSourceAccount(getSelectedAccountOperationRequest);

        }
CreditCardAccountDTO selectedCreditCard = null;
airTimeTopUpValidateResponse = airTimeTopUpValidate.validateRequest(context, airTimeTopUpValidateRequest, airTimeTopUpValidateResponse);

	Map<String, Object> sysMap = airTimeTopUpValidateRequest.getContext().getContextMap();
	String destBankCode = (String) sysMap.get(SystemParameterConstant.BUSINESS_BANK_CODE);

	AirTimeTopUpValidateServiceResopnse res = null;
	FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();

	if (getSelectedAccountOperationResponse.isSuccess() && airTimeTopUpValidateResponse.isSuccess()) {
	    FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
	    formValidateOperationRequest.setContext(context);
	    formValidateOperationRequest.setFrmAct(getSelectedAccountOperationResponse.getSelectedAcct());
	    Amount txnAmount = new Amount();
	    txnAmount.setAmount(new BigDecimal(airTimeTopUpValidateCommand.getTxnAmt()));
	    txnAmount.setCurrency(getSelectedAccountOperationResponse.getSelectedAcct().getCurrency());
	    formValidateOperationRequest.setTxnAmt(txnAmount);
	    formValidateOperationRequest.setTxnType(getPayGrp());
        formValidateOperationRequest.setCreditCardFlag(creditCardFlag);

        if(extra!=null && (extra.equals("FREEDIALAIRTEL") || extra.equals("FREEDIALAIRTELZM")))
        	formValidateOperationRequest.getContext().setActivityId("PMT_BP_MOBILE_WALLET_ONETIME");

	    formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
	    if(extra!=null && (extra.equals("FREEDIALAIRTEL") || extra.equals("FREEDIALAIRTELZM")) && formValidateOperationResponse.getResCde()!=null && formValidateOperationResponse.getResCde().equals("BMB90011"))
	    	formValidateOperationResponse.setResCde("BMB90012");
	    AirTimeTopUpValidateServiceRequest airTimeTopUpValidateServiceRequest = new AirTimeTopUpValidateServiceRequest();
	    if(IDENTIFIRE.equalsIgnoreCase(creditCardFlag))
	    {
           //airTimeTopUpValidateServiceRequest.setCustomerAccountDTO((CustomerAccountDTO) getSelectedAccountOperationResponse.getSelectedAcct());
	    	airTimeTopUpValidateServiceRequest.setCreditcardAccountDTO((CreditCardAccountDTO)getSelectedAccountOperationResponse.getSelectedAcct());
		}else
	    {
			airTimeTopUpValidateServiceRequest.setAccount((CASAAccountDTO) getSelectedAccountOperationResponse.getSelectedAcct());
	    }
airTimeTopUpValidateServiceRequest.setBillerDTO(airTimeTopUpValidateResponse.getBillerDTO());
airTimeTopUpValidateServiceRequest.setContext(context);

	    res = setResponseAirTimeTopUp(airTimeTopUpValidateServiceRequest, airTimeTopUpValidateRequest,creditCardFlag);

	    // proccessMap(request, res);



		TransactionDTO transactionDTO = new TransactionDTO();

		if(IDENTIFIRE.equalsIgnoreCase(creditCardFlag))
	    {
	    	 transactionDTO.setSourceAcct(res.getCreditCardAccountDTO());
	    }
 		else
	    {
			transactionDTO.setSourceAcct(res.getAccount());
 		}

		Amount amount = new Amount();
		amount.setAmount(BigDecimal.valueOf(Double.valueOf((res.getAmount()))));
	    amount.setCurrency(res.getBillerDTO().getCurrency());
	    transactionDTO.setTxnAmt(amount);
	    transactionDTO.setTxnType(getPayGrp());
	    transactionDTO.setMtpService(RECH);
	    transactionDTO.setTxnAmtInLCY(BigDecimal.valueOf(Double.valueOf(res.getAmount())));
	    transactionDTO.setTxnNot(airTimeTopUpValidateCommand.getTxnNot());

	    BillerDTO billerDTO = res.getBillerDTO();
	    BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
	    beneficiaryDTO.setBillAggregatorId(billerDTO.getBillAggregatorId());
	    beneficiaryDTO.setBillerCategoryId(billerDTO.getBillerCategoryId());
	    beneficiaryDTO.setContactNumber(res.getMblNmbr());
	    beneficiaryDTO.setTransactionReferenceNumber(billerDTO.getReferenceNoKey1());
	    beneficiaryDTO.setBillRefNo1(res.getMblNmbr());
	    beneficiaryDTO.setBillRefNo(res.getMblNmbr());
	    beneficiaryDTO.setExternalBillerId(billerDTO.getExternalBillerId());

	    beneficiaryDTO.setBillerName(billerDTO.getBillerName());
	    beneficiaryDTO.setDestinationAccountNumber(billerDTO.getBillerAccountNumber());
	    beneficiaryDTO.setDestinationBranchCode(billerDTO.getBranchCode());
	    beneficiaryDTO.setCurrency(billerDTO.getCurrency());
	    beneficiaryDTO.setBillerId(billerDTO.getBillerId());
	    beneficiaryDTO.setBeneficiaryName(res.getBillHolderNam());
	    beneficiaryDTO.setDestinationBankCode(destBankCode);
	    beneficiaryDTO.setActionCode(airTimeTopUpValidateCommand.getActionCode());
	    beneficiaryDTO.setStoreNumber(airTimeTopUpValidateCommand.getStoreNumber());

	    if(extra!=null && (extra.equals("FREEDIALAIRTEL") || extra.equals("FREEDIALAIRTELZM")))
	    	transactionDTO.setTxnType("MobileWallet");
	    transactionDTO.setBeneficiaryDTO(beneficiaryDTO);
	    setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);

	    AirTimeTopUpValidateDTO airTimeTopUpValidateDTO = new AirTimeTopUpValidateDTO();
	    airTimeTopUpValidateDTO.setAccount(res.getAccount());
	    airTimeTopUpValidateDTO.setAmount(res.getAmount());
	    airTimeTopUpValidateDTO.setBillerDTO(res.getBillerDTO());
	    airTimeTopUpValidateDTO.setMblNmbr(res.getMblNmbr());
	    setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TXN_REF_NO, res.getTxnRefNo());
	} else {
	    airTimeTopUpValidateResponse = new AirTimeTopUpValidateResponse();
	    airTimeTopUpValidateResponse.setSuccess(false);
	    if (!getSelectedAccountOperationResponse.isSuccess()) {
		airTimeTopUpValidateResponse.setResCde(getSelectedAccountOperationResponse.getResCde());

	    } else {
		airTimeTopUpValidateResponse.setResCde(airTimeTopUpValidateResponse.getResCde());
	    }
	    airTimeTopUpValidateResponse.setContext(context);
	    getMessage(airTimeTopUpValidateResponse);
	}
	return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder).createMultipleJSONResponse(airTimeTopUpValidateResponse,
		getSelectedAccountOperationResponse, res, formValidateOperationResponse);
	}

 public void getSelectedAccount(AccountSummaryOperationResponse accountSummaryOperationResponse,
    		AirTimeTopUpValidateCommand	airTimeTopUpValidateCommand,CreditCardAccountDTO selectedCreditCard, GetSelectedAccountOperationResponse selSourceAcctOpResp)
    {


		List<? extends CustomerAccountDTO> acctList = accountSummaryOperationResponse
				.getAccountList();
		for (CustomerAccountDTO acct : acctList) {
			if (acct instanceof CreditCardAccountDTO) {
				selectedCreditCard = (CreditCardAccountDTO) acct;
				if (airTimeTopUpValidateCommand.getActNo()
						.equals(
								selectedCreditCard.getPrimary()
										.getCardNumber())) {
					selSourceAcctOpResp
							.setSelectedAcct(selectedCreditCard);

				}
			}

		}
    }


    public void setAccountSummaryOperation(AccountSummaryOperation accountSummaryOperation) {
    	this.accountSummaryOperation = accountSummaryOperation;
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
     * private void proccessMap(HttpServletRequest request, AirTimeTopUpValidateServiceResopnse res) {
     *
     * TransactionDTO transactionDTO = new TransactionDTO(); transactionDTO.setSourceAcct(res.getAccount());
     *
     * Amount amount = new Amount(); amount.setAmount(BigDecimal.valueOf(Double.valueOf((res.getAmount()))));
     * amount.setCurrency(res.getBillerDTO().getCurrency()); transactionDTO.setTxnAmt(amount); transactionDTO.setTxnType(payGrp);
     * transactionDTO.setMtpService("RECHARGE"); transactionDTO.setTxnAmtInLCY(BigDecimal.valueOf(Double.valueOf(res.getAmount())));
     * transactionDTO.setBeneficiaryDTO(billerToBeneficiary(res)); setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT,
     * BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
     *
     * AirTimeTopUpValidateDTO airTimeTopUpValidateDTO = new AirTimeTopUpValidateDTO(); airTimeTopUpValidateDTO.setAccount(res.getAccount());
     * airTimeTopUpValidateDTO.setAmount(res.getAmount()); airTimeTopUpValidateDTO.setBillerDTO(res.getBillerDTO());
     * airTimeTopUpValidateDTO.setMblNmbr(res.getMblNmbr()); setIntoProcessMap(request, BMGProcessConstants.BILL_PAYMENT,
     * BillPaymentConstants.TXN_REF_NO, res.getTxnRefNo()); // /setSessionAttribute(request, res.getTxnRefNo(), airTimeTopUpValidateDTO);
     *
     * }
     *//**
     * The method is written for Biller to beneficiary.
     *
     * @param res
     *            the res
     * @return the BeneficiaryDTO's object
     */
    /*
     * private BeneficiaryDTO billerToBeneficiary(AirTimeTopUpValidateServiceResopnse res) { BillerDTO billerDTO = res.getBillerDTO(); BeneficiaryDTO
     * beneficiaryDTO = new BeneficiaryDTO(); beneficiaryDTO.setBillAggregatorId(billerDTO.getBillAggregatorId());
     * beneficiaryDTO.setBillerCategoryId(billerDTO.getBillerCategoryId()); beneficiaryDTO.setContactNumber(res.getMblNmbr());
     * beneficiaryDTO.setTransactionReferenceNumber(billerDTO.getReferenceNoKey1()); beneficiaryDTO.setBillRefNo1(res.getMblNmbr());
     * beneficiaryDTO.setBillerName(billerDTO.getBillerName()); beneficiaryDTO.setDestinationAccountNumber(billerDTO.getBillerAccountNumber());
     * beneficiaryDTO.setDestinationBranchCode(billerDTO.getBranchCode()); beneficiaryDTO.setCurrency(billerDTO.getCurrency());
     * beneficiaryDTO.setBillerId(billerDTO.getBillerId()); beneficiaryDTO.setBeneficiaryName(res.getBillHolderNam());
     * beneficiaryDTO.setDestinationBankCode(destBankCode); return beneficiaryDTO; }
     */

    /**
     * The method is written for Sets the response air time top up.
     *
     * @param airTimeTopUpValidateServiceRequest
     *            the air time top up validate service request
     * @param airTimeTopUpValidateRequest
     *            the air time top up validate request
     * @return the AirTimeTopUpValidateServiceResopnse's object
     */
    private AirTimeTopUpValidateServiceResopnse setResponseAirTimeTopUp(AirTimeTopUpValidateServiceRequest airTimeTopUpValidateServiceRequest,
	    AirTimeTopUpValidateRequest airTimeTopUpValidateRequest, String creditCardFlag) {

		AirTimeTopUpValidateServiceResopnse airTimeTopUpValidateServiceResopnse = new AirTimeTopUpValidateServiceResopnse();

		if(null!=creditCardFlag && ("CreditCardAT".equals(creditCardFlag)))
	    {
			airTimeTopUpValidateServiceResopnse.setCreditCardAccountDTO(airTimeTopUpValidateServiceRequest.getCreditcardAccountDTO());
        }else
	    {
			airTimeTopUpValidateServiceResopnse.setAccount(airTimeTopUpValidateServiceRequest.getAccount());
		}
	airTimeTopUpValidateServiceResopnse.setBillerDTO(airTimeTopUpValidateServiceRequest.getBillerDTO());
	airTimeTopUpValidateServiceResopnse.setContext(airTimeTopUpValidateServiceRequest.getContext());
	airTimeTopUpValidateServiceResopnse.setTxnRefNo(airTimeTopUpValidateServiceRequest.getContext().getActivityRefNo());
	airTimeTopUpValidateServiceResopnse.setAmount(airTimeTopUpValidateRequest.getAmt());
	airTimeTopUpValidateServiceResopnse.setMblNmbr(airTimeTopUpValidateRequest.getMblNo());
	airTimeTopUpValidateServiceResopnse.setBillHolderNam(airTimeTopUpValidateRequest.getBillHolderNam());
	return airTimeTopUpValidateServiceResopnse;
    }

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
     * Gets the pay grp.
     *
     * @return the PayGrp
     */
    public String getPayGrp() {
	return payGrp;
    }

    /**
     * Sets values for PayGrp.
     *
     * @param payGrp
     *            the pay grp
     */
    public void setPayGrp(String payGrp) {
	this.payGrp = payGrp;
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
     * Gets the air time top up validate.
     *
     * @return the AirTimeTopUpValidate
     */
    public AirTimeTopUpValidate getAirTimeTopUpValidate() {
	return airTimeTopUpValidate;
    }

    /**
     * Sets values for AirTimeTopUpValidate.
     *
     * @param airTimeTopUpValidate
     *            the air time top up validate
     */
    public void setAirTimeTopUpValidate(AirTimeTopUpValidate airTimeTopUpValidate) {
	this.airTimeTopUpValidate = airTimeTopUpValidate;
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
}