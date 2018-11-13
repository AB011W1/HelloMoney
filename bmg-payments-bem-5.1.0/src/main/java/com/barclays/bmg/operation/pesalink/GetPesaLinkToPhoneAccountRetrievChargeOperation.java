package com.barclays.bmg.operation.pesalink;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.fxrate.service.request.FxRateServiceRequest;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.pesalink.PesaLinkRetrievChargeOperationRequest;
import com.barclays.bmg.operation.response.pesalink.PesaLinkRetrievChargeOperationResponse;
import com.barclays.bmg.service.FxRateService;
import com.barclays.bmg.service.RetreiveChargeDetailsService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

public class GetPesaLinkToPhoneAccountRetrievChargeOperation extends
		BMBCommonOperation {

	private RetreiveChargeDetailsService retreiveChargeDetailsService;
	private TransactionLimitService transactionLimitService;
	private FxRateService fxRateService;

	public PesaLinkRetrievChargeOperationResponse validateForm(
			PesaLinkRetrievChargeOperationRequest request) {

		PesaLinkRetrievChargeOperationResponse response = new PesaLinkRetrievChargeOperationResponse();
		Context context = request.getContext();
		response.setContext(context);
		if (request.getContext().getBusinessId().equals("KEBRB")) {
			getTransactionFeeAndCharges(request, response);
		}

		if (!checkBalance(request, response)) {
		    response.setSuccess(false);
		}

	/*	if (response.isSuccess()) {
		    if (!checkTransactionLimit(request, response)) {
			response.setSuccess(false);
		    }
		}
*/
		if (!response.isSuccess() && StringUtils.isEmpty(response.getResMsg())) {
		    getMessage(response);
		} else {
		    response.setTxnAmt(request.getTxnAmt());
		}

		return response;
	}

	public RetreiveChargeDetailsService getRetreiveChargeDetailsService() {
		return retreiveChargeDetailsService;
	}

	public void setRetreiveChargeDetailsService(
			RetreiveChargeDetailsService retreiveChargeDetailsService) {
		this.retreiveChargeDetailsService = retreiveChargeDetailsService;
	}


    public TransactionLimitService getTransactionLimitService() {
		return transactionLimitService;
	}

	public void setTransactionLimitService(
			TransactionLimitService transactionLimitService) {
		this.transactionLimitService = transactionLimitService;
	}

	public FxRateService getFxRateService() {
		return fxRateService;
	}

    public void setFxRateService(FxRateService fxRateService) {
	this.fxRateService = fxRateService;
    }

	protected void getTransactionFeeAndCharges(
			PesaLinkRetrievChargeOperationRequest request,
			PesaLinkRetrievChargeOperationResponse response) {

		Amount transacFee = null;
		Map<String, Object> contextMap = request.getContext().getContextMap();
		RetreiveChargeDetailsServiceRequest retreiveChargeDetailsServiceRequest = new RetreiveChargeDetailsServiceRequest();

		if(request.getContext().getActivityId().equals("PMT_FT_PESALINK") && request.getContext().getBusinessId().equals("KEBRB")){
			retreiveChargeDetailsServiceRequest.setChargeDetailTaskCode("BP");
			retreiveChargeDetailsServiceRequest.setBillerID("PesaLink");
		}
		retreiveChargeDetailsServiceRequest.setBranchCode(contextMap.get(SystemParameterConstant.SERVICE_HEADER_BRANCH_CODE).toString());
		retreiveChargeDetailsServiceRequest.setContext(request.getContext());
	    retreiveChargeDetailsServiceRequest.setFrmAcct(request.getFrmAct().getAccountNumber());
	    retreiveChargeDetailsServiceRequest.setTxnAmt(request.getTxnAmt().getAmount());
	    retreiveChargeDetailsServiceRequest.setCurrency(request.getTxnAmt().getCurrency());
		RetreiveChargeDetailsServiceResponse retreiveChargeDetailsServiceResponse = retreiveChargeDetailsService
				.retreiveChargeDetails(retreiveChargeDetailsServiceRequest);

		transacFee = retreiveChargeDetailsServiceResponse.getTotalFeeAmount();

		//CPB change 09/05/2017
        //response.setChargeAmount(retreiveChargeDetailsServiceResponse.getTotalFeeAmount();//.getChargeAmount());
        response.setFeeGLAccount(retreiveChargeDetailsServiceResponse.getFeeGLAccount());
        response.setChargeNarration(retreiveChargeDetailsServiceResponse.getChargeNarration());
        response.setTaxAmount(retreiveChargeDetailsServiceResponse.getTaxAmount());
        response.setTaxGLAccount(retreiveChargeDetailsServiceResponse.getTaxGLAccount());
        response.setExciseDutyNarration(retreiveChargeDetailsServiceResponse.getExciseDutyNarration());
        response.setTypeCode(retreiveChargeDetailsServiceResponse.getTypeCode());
        response.setValue(retreiveChargeDetailsServiceResponse.getValue());

		response.setTransactionFee(transacFee);

	}


	 protected boolean checkBalance(PesaLinkRetrievChargeOperationRequest request, PesaLinkRetrievChargeOperationResponse response) {
			BigDecimal txnAmt = request.getTxnAmt().getAmount();
			CustomerAccountDTO fromAccount = request.getFrmAct();
			boolean valid = false;

			if (txnAmt.doubleValue() <= 0) {

			    response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_GREATER_THEN_ZERO);
			    return valid;
			    // Invalid transaction amount.
			}


			if (response != null && response.getTransactionFee() != null) {
			    txnAmt = txnAmt.add(response.getTransactionFee().getAmount());
			}

			// Check for sufficient balance - CBP 22/09/2017
			if(response !=null && response.getTaxAmount() !=null && response.getContext().getBusinessId().equals("KEBRB")){
				txnAmt = txnAmt.add(BigDecimal.valueOf(response.getTaxAmount()));
			}

			if (fromAccount != null && fromAccount instanceof CASAAccountDTO) {


				if (fromAccount.getAvailableBalance() != null) {

				    if (fromAccount.getAvailableBalance().doubleValue() >= txnAmt.doubleValue()) {

					valid = true;

				    }
			    }
			}
			if (!valid) {
			    response.setResCde(BillPaymentResponseCodeConstants.BP_INSUFFICIENT_BALANCE);
			}

			return valid;
	 }

	 /*protected boolean checkTransactionLimit(
			PesaLinkRetrievChargeOperationRequest request,
			PesaLinkRetrievChargeOperationResponse response) {
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
		if (response.getTransactionFee() != null) {
			txnAmount = txnAmount.add(response.getTransactionFee().getAmount());
		}

		Context context = request.getContext();
		TransactionLimitServiceRequest txnLimitServiceReq = new TransactionLimitServiceRequest();
		txnLimitServiceReq.setAmountInLCY(txnAmount);
		txnLimitServiceReq.setContext(context);
		txnLimitServiceReq.setTxnType(request.getTxnType());
		TransactionLimitServiceResponse txnLimitResponse = transactionLimitService
				.checkTransactionLimit(txnLimitServiceReq);

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

	protected FxRateDTO retrieveFxRate(PesaLinkRetrievChargeOperationRequest request,
			String frmCurr) {
		FxRateDTO fxRateDTO = null;
		CustomerAccountDTO toAcct = new CustomerAccountDTO();
		toAcct.setCurrency(request.getTxnAmt().getCurrency());
		CustomerAccountDTO sourceAcct = request.getFrmAct();
		CustomerAccountDTO fromAcct = new CustomerAccountDTO();
		fromAcct.setAccountNumber(sourceAcct.getAccountNumber());
		fromAcct.setCurrency(frmCurr);
		fromAcct.setBranchCode(sourceAcct.getBranchCode());
		fromAcct.setProductCode(sourceAcct.getProductCode());

		if (fromAcct != null && fromAcct.getCurrency() != null
				&& toAcct != null) {

			if (!fromAcct.getCurrency().equals(toAcct.getCurrency())) {

				FxRateServiceRequest fxRequest = new FxRateServiceRequest();

				fxRequest.setContext(request.getContext());

				fxRequest.setFrmCustActDTO(fromAcct);
				fxRequest.setToCustActDTO(toAcct);

				fxRequest.setTxnAmt(request.getTxnAmt().getAmount());
				fxRequest.setTxnType(request.getTxnType());
				FxRateServiceResponse fxRateResponse = fxRateService
						.retreiveFxRate(fxRequest);
				if (fxRateResponse != null) {
					fxRateDTO = new FxRateDTO();
					fxRateDTO.setEffectiveFXRate(fxRateResponse.getEffFxRate());
					fxRateDTO.setEquivalentAmount(fxRateResponse.getEqAmt());
				}
			}
		}
		return fxRateDTO;
	}*/

}
