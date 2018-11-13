package com.barclays.bmg.dao.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.product.ComponentResDAO;
import com.barclays.bmg.dto.ComponentResCacheDTO;
import com.barclays.bmg.dto.LabelValueDetailsDTO;
import com.barclays.bmg.dto.UBPBusinessIdsDTO;
import com.barclays.bmg.service.product.request.ComponentResServiceRequest;
import com.barclays.bmg.service.product.response.ComponentResServiceResponse;

public class ComponentResDAOImpl extends BaseDAOImpl implements ComponentResDAO {

    public static final String FIND_COMPONENTRES_BYKEY = "findComponentResByKey";
    public static final String GET_LABEL_BY_KEY = "getLabelByKey";
    private static final String UBP_BUSINESS_IDS_LIST = "getUBPBusinessIds";
    public static final String GET_CONFIRM_LABEL_BY_KEY = "getConfirmLabelByKey";

    @Override
    public ComponentResServiceResponse getComponentResCache(ComponentResServiceRequest request) {

	Context context = request.getContext();
	ComponentResServiceResponse response = new ComponentResServiceResponse();
	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", context.getSystemId());
	map.put("businessId", context.getBusinessId());
	map.put("langId", context.getLanguageId());
	map.put("componentKey", request.getComponentKey());
	map.put("screenId", request.getScreenId());
	ComponentResCacheDTO componentResCacheDTO = (ComponentResCacheDTO) super.queryForObject(FIND_COMPONENTRES_BYKEY, map);
	response.setComponentResCacheDTO(componentResCacheDTO);
	return response;
    }

    @Override
	public String getBillerLabelByKey(String billerId, String businesId, String language) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("billerid",billerId);
		map.put("businessid", businesId);
		map.put("languageId", language.toUpperCase());
		LabelValueDetailsDTO labelValueDetailsDTO = (LabelValueDetailsDTO) super.queryForObject(GET_LABEL_BY_KEY, map);

		return labelValueDetailsDTO.getLabelValue();
	}

    public String getUBPBusinessId(){
    	List<UBPBusinessIdsDTO> ubpBusinessIdsDTO=this.queryForList(UBP_BUSINESS_IDS_LIST);
    	String businessId=ubpBusinessIdsDTO.get(0).getParamValue();

    	return businessId;
    }

    /*@Override
	public String getBillerConfirmLabelByKey(String billerId, String businesId, String language) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("billerid",billerId);
		map.put("businessid", businesId);
		map.put("languageId", language.toUpperCase());
		LabelValueDetailsDTO labelValueDetailsDTO = (LabelValueDetailsDTO) super.queryForObject(GET_CONFIRM_LABEL_BY_KEY, map);

		return labelValueDetailsDTO.getLabelValue();
	}*/
}
