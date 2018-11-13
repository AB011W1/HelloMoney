package com.barclays.bmg.operation.payments;

import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.fundtransfer.RetrieveCurrencyListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.RetrieveCurrencyListOperationResponse;

public class RetrieveCurrencyListOperation extends BMBCommonOperation{

	public RetrieveCurrencyListOperationResponse retrieveCurrencyList(RetrieveCurrencyListOperationRequest request){
		RetrieveCurrencyListOperationResponse response = new RetrieveCurrencyListOperationResponse();

		response.setCurrencyList(getSysParamListById(request.getContext()
										, request.getParamKey(), request.getActivityId()));

		return response;

	}

}
