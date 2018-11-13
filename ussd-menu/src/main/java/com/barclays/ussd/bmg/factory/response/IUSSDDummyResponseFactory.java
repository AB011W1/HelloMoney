package com.barclays.ussd.bmg.factory.response;

import com.barclays.ussd.utils.IDummyResponse;

public interface IUSSDDummyResponseFactory {

    public abstract IDummyResponse getDummyResponseBuilderObject(String businessId, String countryCode);

}