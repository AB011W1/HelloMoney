package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dao.UrlBusinessMapDAO;
import com.barclays.bmg.dto.UrlBusinessMapDTO;
import com.barclays.bmg.service.request.UrlBusinessMapServiceRequest;
import com.barclays.bmg.service.response.UrlBusinessMapServiceResponse;

public class UrlBusinessMapDAOImpl extends BaseDAOImpl implements UrlBusinessMapDAO {

    private static final String GET_URL_BIZ_MAP_FRM_BIZ_ID = "getUrlBizMapFrmBizId";
    /**
     * Channel Id
     */
    private static final String CHANNEL_ID = "channelId";
    /**
     * Business Id
     */
    private static final String BUSINESS_ID = "businessId";

    /**
     * URL_PATTERN
     */
    private static final String URL_PATTERN = "urlPattern";

    public UrlBusinessMapServiceResponse getUrlBusinessMap(UrlBusinessMapServiceRequest request) {

	Map<String, String> parameterMap = new HashMap<String, String>(2);
	parameterMap.put(BUSINESS_ID, request.getBusinessId());
	String url = request.getUrl();
	parameterMap.put(URL_PATTERN, url);
	// UrlBusinessMapDTO urlBusinessMapDTO = (UrlBusinessMapDTO) this.queryForObject("getUrlBusinessMap", url);
	List<UrlBusinessMapDTO> urlBusinessMapDTO = this.queryForList("getUrlBusinessMap", parameterMap);
	UrlBusinessMapServiceResponse urlBusinessMapServiceResponse = new UrlBusinessMapServiceResponse();
	urlBusinessMapServiceResponse.setUrlBusinessMapDTO(urlBusinessMapDTO.get(0));
	// urlBusinessMapServiceResponse.setUrlBusinessMapDTO(urlBusinessMapDTO);

	return urlBusinessMapServiceResponse;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.dao.UrlBusinessMapDAO#getUrlBizMapFrmBizId(java.lang.String, java.lang.String)
     */
    public UrlBusinessMapServiceResponse getUrlBizMapFrmBizId(String businessId, String channelId) {
	Map<String, String> parameterMap = new HashMap<String, String>(2);
	parameterMap.put(BUSINESS_ID, businessId);
	parameterMap.put(CHANNEL_ID, channelId);
	List<UrlBusinessMapDTO> urlBusinessMapDTO = this.queryForList(GET_URL_BIZ_MAP_FRM_BIZ_ID, parameterMap);
	UrlBusinessMapServiceResponse urlBusinessMapServiceResponse = new UrlBusinessMapServiceResponse();
	urlBusinessMapServiceResponse.setUrlBusinessMapDTO(urlBusinessMapDTO.get(0));
	return urlBusinessMapServiceResponse;
    }

}
