package com.barclays.ussd.sysprefs.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

public class ListValueResDAOImpl extends BaseDAOImpl implements ListValueResDAO {

    public final static String FIND_LISTVALUERES_BYGROUP_KEY = "ussdFindListValueResByGroupKey";
    public final static String FIND_LISTVALUERES_BY_GROUP = "ussdFindListValueResByGroup";
    public final static String FIND_LISTVALUERES_BY_GROUP_KITS = "ussdFindListValueResByGroupKits";
    public final static String FIND_LISTVALUERES_BY_GROUP_WITH_FILTER = "ussdFindListValueResByGroupWithFilter";
    public final static String DEFAULT_LANGUAGE = "EN";

    /*
     * String FIND_LISTVALUE_BYGRP = "findListValueResByGroup"; String FIND_LISTVALUE_BYGRPKEY = "findListValueResByGroupKey"; String
     * FIND_LISTVALUE_BYGRP_WITHFILTER = "findListValueResByGroupWithFilter"; String FIND_LISTVALUE_SUPPORTEDLANG ="findSupportedLang";
     */
    @Override
    public ListValueResByGroupServiceResponse findListValueResByGroupKey(ListValueResServiceRequest request) {

	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", request.getSystemId());
	map.put("businessId", request.getBusinessId());
	map.put("langId", request.getLangId());
	map.put("group", request.getGroup());
	map.put("listValueKey", request.getListValueKey());

	ListValueResDTO listValueResDTO = (ListValueResDTO) super.queryForObject(FIND_LISTVALUERES_BYGROUP_KEY, map);

	// If for Lang no list is available. Get the list for default lang.

	if (listValueResDTO == null && !DEFAULT_LANGUAGE.equals(request.getLangId())) {
	    map.put("langId", DEFAULT_LANGUAGE);
	    listValueResDTO = (ListValueResDTO) super.queryForObject(FIND_LISTVALUERES_BYGROUP_KEY, map);
	}

	ListValueResByGroupServiceResponse response = new ListValueResByGroupServiceResponse();
	if (listValueResDTO != null) {
	    ListValueCacheDTO dto = new ListValueCacheDTO();
	    dto.setKey(listValueResDTO.getListKey());
	    dto.setLabel(listValueResDTO.getListValue());
	    response.setListValueCacheDTOOneRow(dto);
	    // cacheList.add(dto);
	    // response.setListValueCahceDTO(cacheList);
	} else {
	    response.setListValueCahceDTO(null);
	}
	return response;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListValueResByGroupServiceResponse findListValueResByGroup(ListValueResServiceRequest request) {

	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", request.getSystemId());
	map.put("businessId", request.getBusinessId());
	map.put("langId", request.getLangId());
	map.put("group", request.getGroup());
	List<ListValueResDTO> listValueResDTO = super.queryForList(FIND_LISTVALUERES_BY_GROUP, map);

	// If for Lang no list is available. Get the list for default lang.

	if (CollectionUtils.isEmpty(listValueResDTO) && !DEFAULT_LANGUAGE.equals(request.getLangId())) {
	    map.put("langId", DEFAULT_LANGUAGE);
	    listValueResDTO = super.queryForList(FIND_LISTVALUERES_BY_GROUP, map);
	}

	ListValueResByGroupServiceResponse response = new ListValueResByGroupServiceResponse();
	if (CollectionUtils.isNotEmpty(listValueResDTO)) {
	    List<ListValueCacheDTO> cacheList = new ArrayList<ListValueCacheDTO>();

	    for (ListValueResDTO valueresDTO : listValueResDTO) {
		ListValueCacheDTO dto = new ListValueCacheDTO();
		dto.setKey(valueresDTO.getListKey());
		dto.setLabel(valueresDTO.getListValue());
		cacheList.add(dto);
	    }
	    response.setListValueCahceDTO(cacheList);
	} else {
	    response.setListValueCahceDTO(null);
	}
	return response;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request) {

	ListValueResByGroupServiceResponse response = new ListValueResByGroupServiceResponse();
	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", request.getSystemId());
	map.put("businessId", request.getBusinessId());
	map.put("langId", request.getLangId());
	map.put("group", request.getGroup());
	map.put("filterKey1", request.getFilterKey1());
	List<ListValueResDTO> listValueResDTO = super.queryForList(FIND_LISTVALUERES_BY_GROUP_WITH_FILTER, map);

	// If for Lang no list is available. Get the list for default lang.

	if (CollectionUtils.isEmpty(listValueResDTO) && !DEFAULT_LANGUAGE.equals(request.getLangId())) {
	    map.put("langId", DEFAULT_LANGUAGE);
	    listValueResDTO = super.queryForList(FIND_LISTVALUERES_BY_GROUP_WITH_FILTER, map);
	}

	if (CollectionUtils.isNotEmpty(listValueResDTO)) {
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
	    response.setListValueCahceDTO(null);
	}
	return response;
    }

    @Override
    public ListValueResServiceResponse findListValueLabel(ListValueResServiceRequest request) {
	// TODO Auto-generated method stub
	return null;
    }

	@Override
	public ListValueResByGroupServiceResponse findListValueResByGroupKits(
			ListValueResServiceRequest request, String bankCodeLetter) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("systemId", request.getSystemId());
		map.put("businessId", request.getBusinessId());
		map.put("langId", request.getLangId());
		map.put("group", request.getGroup());
		map.put("bankCodeLetter", (formatInput(bankCodeLetter)).toUpperCase());
		List<ListValueResDTO> listValueResDTO = super.queryForList(FIND_LISTVALUERES_BY_GROUP_KITS, map);

		// If for Lang no list is available. Get the list for default lang.

		if (CollectionUtils.isEmpty(listValueResDTO) && !DEFAULT_LANGUAGE.equals(request.getLangId())) {
		    map.put("langId", DEFAULT_LANGUAGE);
		    listValueResDTO = super.queryForList(FIND_LISTVALUERES_BY_GROUP_KITS, map);
		}

		ListValueResByGroupServiceResponse response = new ListValueResByGroupServiceResponse();
		if (CollectionUtils.isNotEmpty(listValueResDTO)) {
		    List<ListValueCacheDTO> cacheList = new ArrayList<ListValueCacheDTO>();

		    for (ListValueResDTO valueresDTO : listValueResDTO) {
			ListValueCacheDTO dto = new ListValueCacheDTO();
			dto.setKey(valueresDTO.getListKey());
			dto.setLabel(valueresDTO.getListValue());
			cacheList.add(dto);
		    }
		    response.setListValueCahceDTO(cacheList);
		} else {
		    response.setListValueCahceDTO(null);
		}
		return response;
	}
	private String formatInput(String value) {
    	String updatedvalue = value;
    	if (null == updatedvalue || updatedvalue.trim().length() == 0) {
    	    return null;
    	} else {
    	    updatedvalue = updatedvalue.toLowerCase();
    	    return new StringBuilder().append(updatedvalue).append("%").toString();
    	}
     }
}
