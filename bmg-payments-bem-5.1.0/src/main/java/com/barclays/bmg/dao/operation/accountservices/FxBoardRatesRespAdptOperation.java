package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;

import com.barclays.bem.RetrieveFXBoardRates.FxBoardRateList;
import com.barclays.bem.RetrieveFXBoardRates.FxProductRateInfo;
import com.barclays.bem.RetrieveFXBoardRates.FxRateCurrency;
import com.barclays.bem.RetrieveFXBoardRates.RetrieveFXBoardRatesResponse;
import com.barclays.bem.RetrieveFXRate.FxRateInfo;
import com.barclays.bem.RetrieveFXRate.RetrieveFXRateResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.fxrate.service.request.FXBoardRatesServiceRequest;
import com.barclays.bmg.fxrate.service.response.FXBoardRatesServiceResponse;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountActivityServiceRequest;


public class FxBoardRatesRespAdptOperation extends AbstractResAdptOperationAcct {

	private String productTypeCodeCash="CASH";
	private static final String invalid_CurrencyNotAvailable = "FXR00171";


	public FXBoardRatesServiceResponse adaptResponse(WorkContext workContext, Object obj){

		FXBoardRatesServiceResponse response=new FXBoardRatesServiceResponse();
		boolean isCurrencyFound=false;
		RetrieveFXBoardRatesResponse bemResponse = (RetrieveFXBoardRatesResponse)obj;
		if (bemResponse != null) {


		if(checkResponseHeader(bemResponse.getResponseHeader())){
		response.setSuccess(true);
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();

		FXBoardRatesServiceRequest fXBoardRatesServiceRequest = (FXBoardRatesServiceRequest) args[0];
		CustomerAccountDTO toAcct = fXBoardRatesServiceRequest.getToCustActDTO();
		String taregtCurrency="";
		if(toAcct!=null && toAcct.getCurrency()!=null){
			taregtCurrency=toAcct.getCurrency();
		}

		//FxRateInfo fxRateInfo = bemResponse.getFxRateInfo();
		FxBoardRateList fxBoardRateList=bemResponse.getFxBoardRateList();
		if(fxBoardRateList!=null){
			FxRateCurrency[] fxRateCurrencyArray =fxBoardRateList.getFxRateCurrencyInfo();
			if(fxRateCurrencyArray!=null){
				for(FxRateCurrency fxRateCurrency:fxRateCurrencyArray){
					if(fxRateCurrency.getCurrencyCode()!=null && fxRateCurrency.getCurrencyCode().equalsIgnoreCase(taregtCurrency)){
						isCurrencyFound=true;
						if(fxRateCurrency.getFxProductInfo()!=null && fxRateCurrency.getFxProductInfo().length!=0){
							for(FxProductRateInfo productRateInfo:fxRateCurrency.getFxProductInfo()){
								if(productRateInfo.getFxProductTypeCode()!=null && productRateInfo.getFxProductTypeCode().equalsIgnoreCase(productTypeCodeCash) ){

									if(productRateInfo.getFxRate()!=null){
										if(productRateInfo.getFxRate().getFXBuyRate()!=null){
											response.setBuyRate(BigDecimal.valueOf(productRateInfo.getFxRate().getFXBuyRate()));
										}else{
											System.out.println("Buy Rate Not available");
										}if(productRateInfo.getFxRate().getFXSellRate()!=null){
											response.setSellRate(BigDecimal.valueOf(productRateInfo.getFxRate().getFXSellRate()));
										}else{

											System.out.println("Sell Rate Not available");
										}
									}
								}
							}
						}
					}
					}
				if(!isCurrencyFound){
					 response.setSuccess(false);
					response.setResCde(invalid_CurrencyNotAvailable);
					response.setResMsg("Currency Not Available");
					System.out.println("Currency Not Available");
				}
			}
		}
		}
		}else{
			 response.setSuccess(false);
		}
		/*if(fxRateInfo!=null){
		response.setEffFxRate(BigDecimal.valueOf(fxRateInfo.getEffectiveFXRate()));


	        if(fxRateInfo.getTargetCurrencyRateInfo()!=null&& fxRateInfo.getTargetCurrencyRateInfo().getTargetAmount()!=null){
	        	response.setEqAmt(BigDecimal.valueOf(fxRateInfo.getTargetCurrencyRateInfo().getTargetAmount()));
	        }
		}
*/	    return response;
	}
}
