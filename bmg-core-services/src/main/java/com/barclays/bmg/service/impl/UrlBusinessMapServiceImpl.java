package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.UrlBusinessMapDAO;
import com.barclays.bmg.service.UrlBusinessMapService;
import com.barclays.bmg.service.request.UrlBusinessMapServiceRequest;
import com.barclays.bmg.service.response.UrlBusinessMapServiceResponse;

public class UrlBusinessMapServiceImpl implements UrlBusinessMapService {

    UrlBusinessMapDAO urlBusinessMapDAO;

    public UrlBusinessMapServiceResponse getUrlBusinessMap(UrlBusinessMapServiceRequest request) {

	UrlBusinessMapServiceResponse urlBusinessMapServiceResponse = urlBusinessMapDAO.getUrlBusinessMap(request);

	return urlBusinessMapServiceResponse;
    }

    public UrlBusinessMapDAO getUrlBusinessMapDAO() {
	return urlBusinessMapDAO;
    }

    public void setUrlBusinessMapDAO(UrlBusinessMapDAO urlBusinessMapDAO) {
	this.urlBusinessMapDAO = urlBusinessMapDAO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.service.UrlBusinessMapService#getUrlBizMapFrmBizId(java.lang.String, java.lang.String)
     */
    public UrlBusinessMapServiceResponse getUrlBizMapFrmBizId(String businessId, String channelId) {
	UrlBusinessMapServiceResponse urlBusinessMapServiceResponse = urlBusinessMapDAO.getUrlBizMapFrmBizId(businessId, channelId);
	return urlBusinessMapServiceResponse;
    }

}
