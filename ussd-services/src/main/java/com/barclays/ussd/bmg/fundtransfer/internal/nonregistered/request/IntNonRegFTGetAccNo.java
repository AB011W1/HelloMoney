package com.barclays.ussd.bmg.fundtransfer.internal.nonregistered.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class IntNonRegFTGetAccNo implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	return new USSDBaseRequest();
    }
}
