package com.barclays.ussd.bmg.factory.response;

import java.util.Map;

import javax.annotation.Resource;

import com.barclays.ussd.utils.IDummyResponse;
import com.barclays.ussd.utils.USSDConstants;

public class USSDDummyResponseFactory implements IUSSDDummyResponseFactory {

    @Resource(name = "dummyResponseMap")
    private Map<String, IDummyResponse> dummyResponseMap;

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.response.IUSSDDummyResponseFactory#getDummyResponseBuilderObject(java.lang.String, java.lang.String)
     */
    public IDummyResponse getDummyResponseBuilderObject(String businessId, String countryCode) {
	String mapKey = createDummyMapKey(businessId, countryCode);
	return dummyResponseMap.get(mapKey);
    }

    private String createDummyMapKey(String businessId, String countryCode) {
	StringBuffer key = new StringBuffer();
	key.append(businessId).append(USSDConstants.UNDERSCORE_SEPERATOR).append(countryCode);
	return key.toString();
    }
}
