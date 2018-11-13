package com.barclays.ussd.bmg.factory.request;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class USSDRequestFactory implements IUSSDRequestFactory {

    @Resource(name = "requestMap")
    private Map<String, BmgBaseRequestBuilder> requestMap;

    public BmgBaseRequestBuilder getBmgRequestBuilderObject(String tranDataId) {
	return requestMap.get(tranDataId);
    }
}
