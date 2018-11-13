package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;

import com.barclays.bem.RetrieveFXRate.FxRateInfo;
import com.barclays.bem.RetrieveFXRate.RetrieveFXRateResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;


public class FxRateRespAdptOperation extends AbstractResAdptOperation {

	public FxRateServiceResponse adaptResponse(WorkContext workContext, Object obj){

		FxRateServiceResponse response = new FxRateServiceResponse();

		RetrieveFXRateResponse bemResponse = (RetrieveFXRateResponse)obj;
		checkResponseHeader(bemResponse.getResponseHeader());
		FxRateInfo fxRateInfo = bemResponse.getFxRateInfo();

		if(fxRateInfo!=null){
		response.setEffFxRate(BigDecimal.valueOf(fxRateInfo.getEffectiveFXRate()));


	        if(fxRateInfo.getTargetCurrencyRateInfo()!=null&& fxRateInfo.getTargetCurrencyRateInfo().getTargetAmount()!=null){
	        	response.setEqAmt(BigDecimal.valueOf(fxRateInfo.getTargetCurrencyRateInfo().getTargetAmount()));
	        }
		}
	    return response;
	}
}
