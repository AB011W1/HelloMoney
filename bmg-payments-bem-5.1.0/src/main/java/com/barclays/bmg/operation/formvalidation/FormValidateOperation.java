package com.barclays.bmg.operation.formvalidation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.fxrate.service.request.FxRateServiceRequest;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.service.FxRateService;
import com.barclays.bmg.service.RetreiveChargeDetailsService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardAccountDetailsServiceResponse;
import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

/**
 * @author e20027734
 *
 *         It will Check balance, Check transaction limit with Proper FxRate and Transaction Fee. It will return Fx Rate Corresponding to Source acct
 *         and txn curr. It will return transaction fee and charges. It will return whether second level authentication is required.
 *
 */
public class FormValidateOperation extends BMBPaymentsOperation {

    private FxRateService fxRateService;
    private RetreiveChargeDetailsService retreiveChargeDetailsService;
    private TransactionLimitService transactionLimitService;
    private CreditCardDetailsService creditCardDetailsService;

    // @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_UB, serviceDescription =
    // "SD_RETRIEVE_INTERNAL_FUND_TRANSFER", stepId = "2", activityType="auditInternalFundTransfer")
    public FormValidateOperationResponse validateForm(FormValidateOperationRequest request) {

	FormValidateOperationResponse response = new FormValidateOperationResponse();
	Context context = request.getContext();
	response.setContext(context);
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	response.setFxRateDTO(retrieveFxRate(request));

	// CPB changes 18/04/2017
	// Check for UGBRB
	//change CBP
	Map<String, Object> contextMap = context.getContextMap();
	if(contextMap !=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y") || request.getContext().getBusinessId().equals("KEBRB")){
		if(request.getCreditCardFlag() == null){		//!(request.getCreditCardFlag().equals("CreditCardMW")
			getTransactionFeeAndCharges(request, response);
		}
	}

	if (!checkBalance(request, response)) {
	    response.setSuccess(false);
	}
	if (response.isSuccess()) {
		boolean checkLimit = true;
	    if(null != context.getValue("billerCatId") && (context.getValue("billerCatId").toString().equalsIgnoreCase("DataBundle"))) {
	    	checkLimit = false;
	    }
		if (checkLimit && !checkTransactionLimit(request, response)) {
			response.setSuccess(false);
		}
	}

	if (!response.isSuccess() && StringUtils.isEmpty(response.getResMsg())) {
	    getMessage(response);
	} else {
	    response.setTxnAmt(request.getTxnAmt());
	}
	return response;
    }

    protected FxRateDTO retrieveFxRate(FormValidateOperationRequest request) {
	if (request.getFrmAct() != null && request.getTxnAmt() != null) {
	    return retrieveFxRate(request, request.getFrmAct().getCurrency());
	}
	return null;
    }

    /**
     * @param request
     * @param sourceAcct
     * @param response
     */
    protected FxRateDTO retrieveFxRate(FormValidateOperationRequest request, String frmCurr) {
	FxRateDTO fxRateDTO = null;
	CustomerAccountDTO toAcct = new CustomerAccountDTO();
	toAcct.setCurrency(request.getTxnAmt().getCurrency());
	CustomerAccountDTO sourceAcct = request.getFrmAct();
	CustomerAccountDTO fromAcct = new CustomerAccountDTO();
	fromAcct.setAccountNumber(sourceAcct.getAccountNumber());
	fromAcct.setCurrency(frmCurr);
	fromAcct.setBranchCode(sourceAcct.getBranchCode());
	fromAcct.setProductCode(sourceAcct.getProductCode());

	if (fromAcct != null && fromAcct.getCurrency() != null && toAcct != null) {

	    if (!fromAcct.getCurrency().equals(toAcct.getCurrency())) {

		FxRateServiceRequest fxRequest = new FxRateServiceRequest();

		fxRequest.setContext(request.getContext());

		fxRequest.setFrmCustActDTO(fromAcct);
		fxRequest.setToCustActDTO(toAcct);

		fxRequest.setTxnAmt(request.getTxnAmt().getAmount());
		fxRequest.setTxnType(request.getTxnType());
		FxRateServiceResponse fxRateResponse = fxRateService.retreiveFxRate(fxRequest);
		if (fxRateResponse != null) {
		    fxRateDTO = new FxRateDTO();
		    fxRateDTO.setEffectiveFXRate(fxRateResponse.getEffFxRate());
		    fxRateDTO.setEquivalentAmount(fxRateResponse.getEqAmt());
		}
	    }
	}
	return fxRateDTO;
    }

    protected void getTransactionFeeAndCharges(FormValidateOperationRequest request, FormValidateOperationResponse response) {

	Map<String, Object> sysMap = request.getContext().getContextMap();
	//String taskCode = (String) sysMap.get("chargeDetailTaskCode");
	Amount transacFee = null;
	List<Charge> charges = null;
	//if (taskCode != null && taskCode.length() > 0) {
	    RetreiveChargeDetailsServiceRequest retreiveChargeDetailsServiceRequest = new RetreiveChargeDetailsServiceRequest();
	    //   retreiveChargeDetailsServiceRequest.setChargeDetailTaskCode(taskCode);
	    // Check for UGBRB
	    //Change CBP
	    Map<String, Object> contextMap = request.getContext().getContextMap();
	    if((request.getContext().getActivityId().equals("PMT_BP_MOBILE_WALLET_ONETIME") || request.getContext().getActivityId().equals("PMT_BP_BILLPAY_PAYEE")
	    		|| request.getContext().getActivityId().equals("PMT_BP_BILLPAY_ONETIME")) &&
	    		((contextMap !=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y")) || request.getContext().getBusinessId().equals("KEBRB"))){
	    	retreiveChargeDetailsServiceRequest.setChargeDetailTaskCode("BP");
	    } else if(request.getContext().getActivityId().equals("PMT_FT_CS") && ((contextMap !=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y"))|| request.getContext().getBusinessId().equals("KEBRB")) ){
	    	retreiveChargeDetailsServiceRequest.setChargeDetailTaskCode("CS");
	    } else if(request.getContext().getActivityId().equals("PMT_FT_INTERNAL_PAYEE") && ((contextMap !=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y"))|| request.getContext().getBusinessId().equals("KEBRB"))){
	    	retreiveChargeDetailsServiceRequest.setChargeDetailTaskCode("IT");
	    } else if(request.getContext().getActivityId().equals("PMT_FT_OWN") && (contextMap !=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y")) && (request.getContext().getBusinessId().equals("UGBRB")||
	    		request.getContext().getBusinessId().equals("ZMBRB") || request.getContext().getBusinessId().equals("GHBRB") || request.getContext().getBusinessId().equals("BWBRB"))){

	    	retreiveChargeDetailsServiceRequest.setChargeDetailTaskCode("IT");
	    }

		retreiveChargeDetailsServiceRequest.setBranchCode(sysMap.get(SystemParameterConstant.SERVICE_HEADER_BRANCH_CODE).toString());
	    retreiveChargeDetailsServiceRequest.setContext(request.getContext());
	    retreiveChargeDetailsServiceRequest.setFrmAcct(request.getFrmAct().getAccountNumber());
	    retreiveChargeDetailsServiceRequest.setTxnAmt(request.getTxnAmt().getAmount());
	    retreiveChargeDetailsServiceRequest.setCurrency(request.getTxnAmt().getCurrency());
	    // Biller ID change - CBP 29/09/2017
	    retreiveChargeDetailsServiceRequest.setBillerID(request.getBillerId());

	    RetreiveChargeDetailsServiceResponse retreiveChargeDetailsServiceResponse = retreiveChargeDetailsService
		    .retreiveChargeDetails(retreiveChargeDetailsServiceRequest);
	    transacFee = retreiveChargeDetailsServiceResponse.getTotalFeeAmount();
	    charges = retreiveChargeDetailsServiceResponse.getCharges();
	//}
	response.setCharges(charges);
	response.setTranFee(transacFee);


	//CPB change 12/05/2017
    //response.setChargeAmount(retreiveChargeDetailsServiceResponse.getTotalFeeAmount();//.getChargeAmount());
    response.setFeeGLAccount(retreiveChargeDetailsServiceResponse.getFeeGLAccount());
    response.setChargeNarration(retreiveChargeDetailsServiceResponse.getChargeNarration());
    response.setTaxAmount(retreiveChargeDetailsServiceResponse.getTaxAmount());
    response.setTaxGLAccount(retreiveChargeDetailsServiceResponse.getTaxGLAccount());
    response.setExciseDutyNarration(retreiveChargeDetailsServiceResponse.getExciseDutyNarration());
    response.setTypeCode(retreiveChargeDetailsServiceResponse.getTypeCode());
    response.setValue(retreiveChargeDetailsServiceResponse.getValue());
    // Xelerate offline data
    response.setXelerateOfflineError(retreiveChargeDetailsServiceResponse.getXelerateOfflineError());
    response.setXelerateOfflineDesc(retreiveChargeDetailsServiceResponse.getXelerateOfflineDesc());

    }

    /**
     * @param request
     * @param response
     * @return
     */
    protected boolean checkTransactionLimit(FormValidateOperationRequest request, FormValidateOperationResponse response) {
	BigDecimal txnAmount = request.getTxnAmt().getAmount();

	String txnCurr = request.getTxnAmt().getCurrency();
	String localCurr = request.getContext().getLocalCurrency();
	FxRateDTO fxRateDTO = null;
	if (txnCurr != null && localCurr != null) {
	    if (!txnCurr.equals(localCurr)) {
		fxRateDTO = retrieveFxRate(request, localCurr);
	    }
	}
	if (fxRateDTO != null) {
	    txnAmount = fxRateDTO.getEquivalentAmount();
	}
	response.setTxnAmtInLCY(txnAmount);
	if (response.getTranFee() != null) {
	    txnAmount = txnAmount.add(response.getTranFee().getAmount());
	}

	Context context = request.getContext();
	TransactionLimitServiceRequest txnLimitServiceReq = new TransactionLimitServiceRequest();
	txnLimitServiceReq.setAmountInLCY(txnAmount);
	txnLimitServiceReq.setContext(context);
	txnLimitServiceReq.setTxnType(request.getTxnType());
	TransactionLimitServiceResponse txnLimitResponse = transactionLimitService.checkTransactionLimit(txnLimitServiceReq);

	if (!txnLimitResponse.isSuccess()) {
	    response.setResCde(txnLimitResponse.getResCde());
	    response.setResMsg(txnLimitResponse.getResMsg());
	    response.setErrTyp(txnLimitResponse.getErrTyp());
	} else {
	    if (txnLimitResponse.isAuthRequired()) {
		String authType = getAuthType(request, context.getActivityId());
		response.setScndLvlauthReq(true);
		response.setScndLvlAuthTyp(authType);
	    }
	}
	return txnLimitResponse.isSuccess();
    }

    protected boolean checkBalance(FormValidateOperationRequest request, FormValidateOperationResponse response) {
	BigDecimal txnAmt = request.getTxnAmt().getAmount();
	CustomerAccountDTO fromAccount = request.getFrmAct();
	boolean valid = false;

	if (txnAmt.doubleValue() <= 0) {

	    response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_GREATER_THEN_ZERO);
	    return valid;
	    // Invalid transaction amount.
	}

	if (response != null && response.getFxRateDTO() != null) {
	    FxRateDTO fxRate = response.getFxRateDTO();
	    if (fxRate.getEquivalentAmount() != null) {
		txnAmt = fxRate.getEquivalentAmount();
	    }
	}

	if (response != null && response.getTranFee() != null) {
	    txnAmt = txnAmt.add(response.getTranFee().getAmount());
	}

	// Check for sufficient balance - CBP 22/09/2017
	if(response !=null && response.getTaxAmount() !=null && (response.getContext().getBusinessId().equals("KEBRB") || response.getContext().getBusinessId().equals("UGBRB") || response.getContext().getBusinessId().equals("GHBRB")
			|| response.getContext().getBusinessId().equals("ZMBRB") || response.getContext().getBusinessId().equals("BWBRB"))){
		txnAmt = txnAmt.add(BigDecimal.valueOf(response.getTaxAmount()));
	}

	if (fromAccount != null) {

	    if (fromAccount instanceof CreditCardAccountDTO) {

	    if("CreditCard".equals(request.getCreditCardFlag())){
	    	CreditCardAccountDTO creditcard = (CreditCardAccountDTO)fromAccount;
	    	if (creditcard.getAvailableCashLimit() != null) {
			    if (creditcard.getAvailableCashLimit().doubleValue() >= txnAmt.doubleValue()) {
				valid = true;
			    }
			}

	    } else {
	    	if("CreditCardMW".equals(request.getCreditCardFlag())
	    		|| "CreditCardBP".equals(request.getCreditCardFlag())
	    		|| "CreditCardOT".equals(request.getCreditCardFlag())
	    		|| "CreditCardAT".equals(request.getCreditCardFlag())
	    		) {
	    	CreditCardAccountDTO creditcard = (CreditCardAccountDTO)fromAccount;
	    	if (creditcard.getKadiKopeOutstandingBalanceAmount() != null) {
			    if (creditcard.getKadiKopeOutstandingBalanceAmount().doubleValue() >= txnAmt.doubleValue()) {
				valid = true;
			    }
			}
	    } else {

		CreditCardAccountDetailsServiceRequest ccAccDetlServiceRequest = new CreditCardAccountDetailsServiceRequest();
		ccAccDetlServiceRequest.setContext(request.getContext());

		ccAccDetlServiceRequest.setAccountNo(fromAccount.getAccountNumber());

		CreditCardAccountDetailsServiceResponse ccAccDetlServiceResponse = creditCardDetailsService
			.retrieveCreditCardAccountDetails(ccAccDetlServiceRequest);

		if (ccAccDetlServiceResponse != null) {

		    fromAccount.setAvailableBalance(ccAccDetlServiceResponse.getCreditCardAccountDTO().getAvailableBalance());

		}

		if (fromAccount.getAvailableBalance() != null) {

		    if (fromAccount.getAvailableBalance().doubleValue() >= txnAmt.doubleValue()) {

			valid = true;

		    			}
					}
	    		}
	    	}
	    } else {

		if (fromAccount.getAvailableBalance() != null) {

		    if (fromAccount.getAvailableBalance().doubleValue() >= txnAmt.doubleValue()) {

			valid = true;

		    }
		}
	    }
	}
	//CR82 Mocking the changes
	//valid=true;
	if (!valid) {
	    response.setResCde(BillPaymentResponseCodeConstants.BP_INSUFFICIENT_BALANCE);
	}

	return valid;
    }

    public void currencyValidation(Context context, String amountCurrency, String groupId, String sourceAcctCurrency, String destinationAcctCurrency,
	    ResponseContext... responseContexts) {

	// Changed for Currency validation in Kenya

	// GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse) responseContexts[0];

	// RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responseContexts[1];

	FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[0];

	if (formValidateOperationResponse.getContext() == null) {
	    formValidateOperationResponse.setContext(context);
	}
	// String sourceAcctCurrency = getSelectedAccountOperationResponse
	// .getSelectedAcct().getCurrency();

	// String destinationAcctCurrency = retrievePayeeInfoOperationResponse
	// .getCasaAccountDTO().getCurrencyCode().getCurrency();

	// String localCurrency = context.getLocalCurrency();

	// Cross currency Check
	if (!validateEligible(sourceAcctCurrency, destinationAcctCurrency, groupId, context)) {
	    formValidateOperationResponse.setResCde(FundTransferResponseCodeConstants.PMT_FT_INVALID_CURRENCY_TRANSACTION);
	    formValidateOperationResponse.setSuccess(false);
	    getMessage(formValidateOperationResponse);
	}

	// to check the transfer amount currency should be the same as the
	// destination account currency
	if (destinationAcctCurrency != null && formValidateOperationResponse.isSuccess()) {
	    if (!destinationAcctCurrency.equals(amountCurrency)) {
		formValidateOperationResponse.setResCde(FundTransferResponseCodeConstants.PMT_FT_INVALID_TRANSFER_CURRENCY);
		formValidateOperationResponse.setSuccess(false);
		getMessage(formValidateOperationResponse, new Object[] { destinationAcctCurrency });
	    }

	}

    }

    public FxRateService getFxRateService() {
	return fxRateService;
    }

    public void setFxRateService(FxRateService fxRateService) {
	this.fxRateService = fxRateService;
    }

    public RetreiveChargeDetailsService getRetreiveChargeDetailsService() {
	return retreiveChargeDetailsService;
    }

    public void setRetreiveChargeDetailsService(RetreiveChargeDetailsService retreiveChargeDetailsService) {
	this.retreiveChargeDetailsService = retreiveChargeDetailsService;
    }

    @Override
    public TransactionLimitService getTransactionLimitService() {
	return transactionLimitService;
    }

    @Override
    public void setTransactionLimitService(TransactionLimitService transactionLimitService) {
	this.transactionLimitService = transactionLimitService;
    }

    public CreditCardDetailsService getCreditCardDetailsService() {
	return creditCardDetailsService;
    }

    public void setCreditCardDetailsService(CreditCardDetailsService creditCardDetailsService) {
	this.creditCardDetailsService = creditCardDetailsService;
    }

    public boolean validateEligible(String sourceCurrency, String destinationCurrency, String groupID, Context sscContent) {
	// TODO Auto-generated method stub
	if (sourceCurrency == null || destinationCurrency == null) {
	    return false;
	} else {
	    String localCurrency = sscContent.getLocalCurrency();
	    List<ListValueCacheDTO> valueList = getListValueByGroup(sscContent, groupID);
	    HashMap<String, String> eligibleTypeMap = new HashMap<String, String>();
	    // Put all eligible type into a Map
	    if (valueList != null && valueList.size() > 0) {
		for (ListValueCacheDTO valueDTO : valueList) {
		    if (valueDTO != null && "Y".equalsIgnoreCase(valueDTO.getLabel())) {
			eligibleTypeMap.put(valueDTO.getKey(), valueDTO.getLabel());
		    }
		}
	    }
	    if (sourceCurrency.equals(localCurrency) && destinationCurrency.equals(localCurrency)
		    && eligibleTypeMap.containsKey(FundTransferConstants.LCYLCY)) {
		return true;
	    } else if (!sourceCurrency.equals(localCurrency) && sourceCurrency.equals(destinationCurrency)
		    && eligibleTypeMap.containsKey(FundTransferConstants.FCYFCY)) {
		return true;
	    } else if (sourceCurrency.equals(localCurrency) && !destinationCurrency.equals(localCurrency)
		    && eligibleTypeMap.containsKey(FundTransferConstants.LCYFCY)) {
		return true;
	    } else if (!sourceCurrency.equals(localCurrency) && destinationCurrency.equals(localCurrency)
		    && eligibleTypeMap.containsKey(FundTransferConstants.FCYLCY)) {
		return true;
	    } else if (!sourceCurrency.equals(localCurrency) && !destinationCurrency.equals(localCurrency)
		    && !sourceCurrency.equals(destinationCurrency) && eligibleTypeMap.containsKey(FundTransferConstants.FCY1FCY2)) {
		return true;
	    } else {
		return false;
	    }

	}
    }
}
