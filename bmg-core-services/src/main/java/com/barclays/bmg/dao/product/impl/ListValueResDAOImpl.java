package com.barclays.bmg.dao.product.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.product.ListValueResDAO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.dto.ListValueResDTO;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;

public class ListValueResDAOImpl extends BaseDAOImpl implements ListValueResDAO {

    public final String FIND_LISTVALUERES_BYGROUP_KEY = "findListValueResByGroupKey";
    public final String FIND_LISTVALUERES_BY_GROUP = "findListValueResByGroup";
    public final String FIND_LISTVALUERES_BY_GROUP_WITH_FILTER = "findListValueResByGroupWithFilter";
    public final String FIND_LISTVALUERES_BY_KEY = "findListValueResByKey";
    public final String DEFAULT_LANGUAGE = "EN";

    @Override
    public ListValueResServiceResponse findListValueLabel(ListValueResServiceRequest request) {
	// TODO Auto-generated method stub

	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", request.getContext().getSystemId());
	map.put("businessId", request.getContext().getBusinessId());
	map.put("langId", request.getContext().getLanguageId());
	map.put("group", request.getGroup());
	map.put("listValueKey", request.getListValueKey());

	ListValueResDTO listValueResDTO = (ListValueResDTO) super.queryForObject(this.FIND_LISTVALUERES_BYGROUP_KEY, map);

	ListValueResServiceResponse listValueResServiceResponse = new ListValueResServiceResponse();

	if (listValueResDTO == null) {
	    listValueResServiceResponse.setListValueLabel(null);
	} else {
	    listValueResServiceResponse.setListValueLabel(listValueResDTO.getListValue());
	}

	return listValueResServiceResponse;

    }

    @Override
    public ListValueResByGroupServiceResponse findListValueResByGroup(ListValueResServiceRequest request) {

	ListValueResByGroupServiceResponse response = new ListValueResByGroupServiceResponse();
	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", request.getContext().getSystemId());
	map.put("businessId", request.getContext().getBusinessId());
	map.put("langId", request.getContext().getLanguageId());
	map.put("group", request.getGroup());
	List<ListValueResDTO> listValueResDTO = super.queryForList(this.FIND_LISTVALUERES_BY_GROUP, map);

	// If for Lang no list is available. Get the list for default lang.

	if (listValueResDTO == null || listValueResDTO.size() == 0 && !DEFAULT_LANGUAGE.equals(request.getContext().getLanguageId())) {
	    map.put("langId", DEFAULT_LANGUAGE);
	    listValueResDTO = super.queryForList(this.FIND_LISTVALUERES_BY_GROUP, map);
	}

	if (listValueResDTO != null && listValueResDTO.size() > 0) {
	    List<ListValueCacheDTO> cacheList = new ArrayList<ListValueCacheDTO>();

	    for (ListValueResDTO valueresDTO : listValueResDTO) {
		ListValueCacheDTO dto = new ListValueCacheDTO();
		dto.setKey(valueresDTO.getListKey());
		dto.setLabel(valueresDTO.getListValue());
		cacheList.add(dto);
	    }
	    response.setListValueCahceDTO(cacheList);
	} else {
	    response.setSuccess(false);
	}
	return response;
    }

    @Override
    public ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request) {

	ListValueResByGroupServiceResponse response = new ListValueResByGroupServiceResponse();
	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", request.getContext().getSystemId());
	map.put("businessId", request.getContext().getBusinessId());
	map.put("langId", request.getContext().getLanguageId());
	map.put("group", request.getGroup());
	map.put("filterKey1", request.getFilterKey1());
	List<ListValueResDTO> listValueResDTO = super.queryForList(this.FIND_LISTVALUERES_BY_GROUP_WITH_FILTER, map);

	// If for Lang no list is available. Get the list for default lang.

	if (listValueResDTO == null || listValueResDTO.size() == 0 && !DEFAULT_LANGUAGE.equals(request.getContext().getLanguageId())) {
	    map.put("langId", DEFAULT_LANGUAGE);
	    listValueResDTO = super.queryForList(this.FIND_LISTVALUERES_BY_GROUP_WITH_FILTER, map);
	}

	if (listValueResDTO != null && listValueResDTO.size() > 0) {
	    List<ListValueCacheDTO> cacheList = new ArrayList<ListValueCacheDTO>();

	    for (ListValueResDTO valueresDTO : listValueResDTO) {
		ListValueCacheDTO dto = new ListValueCacheDTO();
		dto.setKey(valueresDTO.getListKey());
		dto.setLabel(valueresDTO.getListValue());
		dto.setFilterKey1(valueresDTO.getFilterKey1());
		cacheList.add(dto);
	    }
	    response.setListValueCahceDTO(cacheList);
	} else {
	    response.setSuccess(false);
	}
	return response;
    }

    @Override
    public ListValueResByGroupServiceResponse findListValueResByKey(ListValueResServiceRequest request) {

	ListValueResByGroupServiceResponse response = new ListValueResByGroupServiceResponse();
	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", request.getContext().getSystemId());
	map.put("businessId", request.getContext().getBusinessId());
	map.put("langId", request.getContext().getLanguageId());
	map.put("listValueKey", request.getListValueKey());
	List<ListValueResDTO> listValueResDTO = super.queryForList(this.FIND_LISTVALUERES_BY_KEY, map);

	// If for Lang no list is available. Get the list for default lang.

	if (listValueResDTO == null || listValueResDTO.size() == 0 && !DEFAULT_LANGUAGE.equals(request.getContext().getLanguageId())) {
	    map.put("langId", DEFAULT_LANGUAGE);
	    listValueResDTO = super.queryForList(this.FIND_LISTVALUERES_BY_GROUP, map);
	}

	if (listValueResDTO != null && listValueResDTO.size() > 0) {
	    List<ListValueCacheDTO> cacheList = new ArrayList<ListValueCacheDTO>();

	    for (ListValueResDTO valueresDTO : listValueResDTO) {
		ListValueCacheDTO dto = new ListValueCacheDTO();
		dto.setKey(valueresDTO.getListKey());
		dto.setLabel(valueresDTO.getListValue());
		cacheList.add(dto);
	    }
	    response.setListValueCahceDTO(cacheList);
	} else {
	    response.setSuccess(false);
	}
	return response;
    }

}
