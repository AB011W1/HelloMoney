package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveFXBoardRates.FxRateRequestInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.fxrate.service.request.FXBoardRatesServiceRequest;

public class FxBoardRatesPayloadAdapter {


    public FxRateRequestInfo adaptPayload(WorkContext workContext) {

	FxRateRequestInfo requestBody = new FxRateRequestInfo();
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	FXBoardRatesServiceRequest fXBoardRatesServiceRequest = (FXBoardRatesServiceRequest) args[0];
	CustomerAccountDTO frmAcct = fXBoardRatesServiceRequest.getFrmCustActDTO();
	requestBody.setBaseCurrencyCode(frmAcct.getCurrency());
	//As per confirmation from Sujeet we can send "Y" always
	requestBody.setReciprocalIndicator("Y");




	return requestBody;
    }
}
