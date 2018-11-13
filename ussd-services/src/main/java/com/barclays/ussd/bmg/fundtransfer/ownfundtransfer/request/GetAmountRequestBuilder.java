package com.barclays.ussd.bmg.fundtransfer.ownfundtransfer.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class GetAmountRequestBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	// if(StringUtils.isNotEmpty(requestBuilderParamsDTO.getUserInput())){
	// requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName(),
	// requestBuilderParamsDTO.getUserInput());
	// }
	return null;
    }
}
