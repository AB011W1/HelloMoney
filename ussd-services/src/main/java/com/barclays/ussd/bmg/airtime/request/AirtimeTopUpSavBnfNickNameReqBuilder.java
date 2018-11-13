package com.barclays.ussd.bmg.airtime.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class AirtimeTopUpSavBnfNickNameReqBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	USSDBaseRequest request = new USSDBaseRequest();

	return request;
    }
}
