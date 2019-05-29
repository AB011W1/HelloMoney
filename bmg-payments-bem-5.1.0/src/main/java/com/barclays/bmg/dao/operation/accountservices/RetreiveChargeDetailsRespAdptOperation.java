package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.RetrieveChargeDetails.RetrieveChargeDetailsResponse;
import com.barclays.bem.RetrieveChargeDetails.RetrieveChargeInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;

public class RetreiveChargeDetailsRespAdptOperation {

    public RetreiveChargeDetailsServiceResponse adaptResponse(WorkContext workContext, Object obj){

    	DAOContext daoContext = (DAOContext) workContext;
    	Object[] args = daoContext.getArguments();
    	RetreiveChargeDetailsServiceRequest retrieveChargeReq = (RetreiveChargeDetailsServiceRequest) args[0];
        RetreiveChargeDetailsServiceResponse response = new RetreiveChargeDetailsServiceResponse();
        Double taxAmount = 0.0;

        RetrieveChargeDetailsResponse bemResponse = (RetrieveChargeDetailsResponse) obj;
        RetrieveChargeInfo chargeInfo = bemResponse.getRetrieveCharge();
        List<Charge> charges = new ArrayList<Charge>();
        if(chargeInfo!=null){
	        if(chargeInfo.getChargeDetails()!=null){
	            for(ChargeDetails charge : chargeInfo.getChargeDetails()){
	//                Charge c = new Charge(charge.getChargeTypeCode(),new Amount(charge.getChargeAmountCurrencyCode(),BigDecimal.valueOf(charge.getChargeAmount())));
	                response.setFeeGLAccount(charge.getFeeGLAccount());
	                response.setChargeNarration(charge.getChargeNarration());
	                response.setTaxAmount(charge.getTaxAmount());
	                response.setTaxGLAccount(charge.getTaxGLAccount());
	                response.setExciseDutyNarration(charge.getExciseDutyNarration());
	                response.setTypeCode(charge.getChargeTypeCode());
	                response.setValue(chargeInfo.getExternalTransactionReferenceNo());
	                taxAmount = charge.getTaxAmount();
	// Commented on 31/08/2017 for testing of CBP
	//                c.setWaiverFlag(charge.getChargeWaiverFlag());
	//                c.setEffectiveFXRate(BigDecimal.valueOf(charge.getTransactionChargeFxRate().getEffectiveFXRate()));
	//                c.setLCYToTargetFXRate(BigDecimal.valueOf(charge.getTransactionChargeFxRate().getLCYToTargetFXRate()));
	//                c.setSourceToLCYFXRate(BigDecimal.valueOf(charge.getTransactionChargeFxRate().getSourceToLCYFXRate()));
	//                c.setDebitAccountNumber(c.getDebitAccountNumber());
	//                c.setDebitAccountCurrency(c.getDebitAccountCurrency());
	//                c.setUpdated(charge.getIsChargeUpdated());
	//                c.setChargeAmountInLCY(new Amount(null,BigDecimal.valueOf(charge.getLCYChargeAmount())));
	                Charge c = new Charge();
	                charges.add(c);
	            }
	        }
        if(chargeInfo.getTotalFeeAmountCurrencyCode()!=null){
    		response.setTotalFeeAmount(new Amount(chargeInfo.getTotalFeeAmountCurrencyCode(),BigDecimal.valueOf(chargeInfo.getTotalFeeAmount())));
    	}else{
    		response.setTotalFeeAmount(new Amount(retrieveChargeReq.getCurrency(),BigDecimal.valueOf(chargeInfo.getTotalFeeAmount())));
    	}
       }else{
    	   // Note need to fetch the ERROR code if Xelerate is offline  -- and then transaction fee will be updated as '0'
    	   response.setXelerateOfflineError(bemResponse.getResponseHeader().getErrorList()[0].getErrorCode());
    	   response.setXelerateOfflineDesc(bemResponse.getResponseHeader().getErrorList()[0].getErrorDesc());
       }
        response.setCharges(charges);
        return response;
    }
}