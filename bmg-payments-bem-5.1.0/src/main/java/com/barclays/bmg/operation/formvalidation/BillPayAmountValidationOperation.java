package com.barclays.bmg.operation.formvalidation;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.BillPayAmountValidationOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.BillPayAmountValidationOperationResponse;

public class BillPayAmountValidationOperation extends BMBPaymentsOperation{

	private static final String BILLER_ID_SALIK = "Salik";
	private static final String PARAM_PMT_SALIK_AMOUNT_INTERVAL = "PMT_SALIK_AMOUNT_INTERVAL";
	private static final String DEFAULT_PMT_SALIK_AMOUNT_INTERVAL = "50";
	public BillPayAmountValidationOperationResponse validateTxnAmount(BillPayAmountValidationOperationRequest request){
		Context context = request.getContext();
		BillPayAmountValidationOperationResponse response = new BillPayAmountValidationOperationResponse();
		response.setContext(context);

		loadParameters(context,ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		String txnTyp = request.getTxnType();
		BeneficiaryDTO beneficiaryDTO = request.getBeneficiaryDTO();
		if(BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP.equals(txnTyp)){
			if(!validateTopupAmount(request,response)){
				if(response.isSuccess()){
					response.setResCde(BillPaymentResponseCodeConstants.INVALID_BILL_PAY_AMT);
					response.setSuccess(false);
				}

			}
		}

		if(BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT.equals(txnTyp)){
			if(beneficiaryDTO!=null){

				if(checkForMinimalAmount(beneficiaryDTO, request, response)){
					if (BILLER_ID_SALIK.equals(beneficiaryDTO.getBillerId()) && !validateInputAmount(request)) {
	    				// amount is not valid
						response.setResCde(BillPaymentResponseCodeConstants.INVALID_SALIK_AMOUNT);
						response.setSuccess(false);
	    			}
				}
			}
		}
		if(!response.isSuccess()){
			if(StringUtils.isEmpty(response.getResMsg())){
				getMessage(response);
			}

		}else{
			response.setBeneficiaryDTO(request.getBeneficiaryDTO());
		}
		return response;
	}

	public boolean checkForMinimalAmount(BeneficiaryDTO beneficiaryDTO,BillPayAmountValidationOperationRequest request ,BillPayAmountValidationOperationResponse response ){

        BigDecimal minPaymentAmount = beneficiaryDTO.getBillersMinPaymentAmount();
        BigDecimal txnAmt = null;
        boolean valid = true;
        if(request.getTxnAmount()!=null){
        	 txnAmt = request.getTxnAmount().getAmount();
        }
        if (minPaymentAmount != null && minPaymentAmount.doubleValue() > 0 && txnAmt!=null) {
            if (txnAmt.doubleValue() < minPaymentAmount.doubleValue()) {
            	response.setSuccess(false);
            	response.setResCde(BillPaymentResponseCodeConstants.BILLPAYMENT_MINIMAL_TXN_AMT);
            	getMessage(response);
            	response.setResMsg(MessageFormat.format(response.getResMsg(),request.getContext().getLocalCurrency(),minPaymentAmount.doubleValue()));
                valid= false;
            }
        }

        return valid;
	}


/**
 * Check amount for Mobile top up.
 * @param request
 * @return
 */
private boolean validateTopupAmount(BillPayAmountValidationOperationRequest request,BillPayAmountValidationOperationResponse response){


	Map<String,Object> sysMap = request.getContext().getContextMap();

	String topupAmtInterval = (String) sysMap.get(BillPaymentConstants.PARAM_PMT_MOBILE_TOPUP_AMOUNT_INTERVAL);

	if (topupAmtInterval == null || "".equals(topupAmtInterval)) {
		topupAmtInterval = BillPaymentConstants.DEFAULT_PMT_MOBILE_TOPUP_AMOUNT_INTERVAL;
	}
	double interval = Double.parseDouble(topupAmtInterval);
	Double txnAmt =request.getTxnAmount().getAmount().doubleValue();

	if(txnAmt.doubleValue() <= 0){
		response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_AMOUNT_GREATER_THEN_ZERO);
		response.setSuccess(false);
		return false;
		// Invalid transaction amount.
	}
	if(request.getMinAmt()!= null && request.getMaxAmt()!=null){
		Double minAmt = request.getMinAmt().doubleValue();
		Double maxAmt = request.getMaxAmt().doubleValue();
		if ((txnAmt < minAmt) || (txnAmt > maxAmt)) {
					return false;
		} else {
			boolean valid = ((txnAmt - minAmt) % interval) == 0;
			if(!valid){
				response.setResCde(BillPaymentResponseCodeConstants.MTP_NOT_ACCORDING_INTERVAL);
				getMessage(response);
				response.setResMsg(MessageFormat.format(response.getResMsg(),interval));
				response.setSuccess(false);
			}
			return valid;
		}
	}
	return false;
}

private boolean validateInputAmount(BillPayAmountValidationOperationRequest request) {

	Map<String,Object> sysMap = request.getContext().getContextMap();
	String  amtInterval = (String)sysMap.get(PARAM_PMT_SALIK_AMOUNT_INTERVAL);
	Double txnAmt =request.getTxnAmount().getAmount().doubleValue();
	if (amtInterval == null || "".equals(amtInterval)) {
		amtInterval = DEFAULT_PMT_SALIK_AMOUNT_INTERVAL;
	}
	double interval = Double.parseDouble(amtInterval);
	if (txnAmt != null) {
		double inputAmount = txnAmt.doubleValue();
		return (inputAmount % interval) == 0;
	} else {
		return false;
	}
}


}
