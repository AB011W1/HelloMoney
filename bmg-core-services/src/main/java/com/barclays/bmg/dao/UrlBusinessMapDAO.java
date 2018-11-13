package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.UrlBusinessMapServiceRequest;
import com.barclays.bmg.service.response.UrlBusinessMapServiceResponse;

public interface UrlBusinessMapDAO {

    public UrlBusinessMapServiceResponse getUrlBusinessMap(UrlBusinessMapServiceRequest request);

    /**
     * @param businessId
     * @param channelId
     * @return UrlBusinessMapServiceResponse
     */
    public UrlBusinessMapServiceResponse getUrlBizMapFrmBizId(String businessId, String channelId);

}
