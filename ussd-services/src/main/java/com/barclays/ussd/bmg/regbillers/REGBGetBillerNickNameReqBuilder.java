package com.barclays.ussd.bmg.regbillers;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class REGBGetBillerNickNameReqBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	USSDBaseRequest request = new USSDBaseRequest();

	return request;
    }
}
