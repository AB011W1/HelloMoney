package com.barclays.ussd.services.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.ussd.dto.BusinessAreaLookUpDTO;

public class CallMeBackLookUpDAOImpl extends SqlMapClientDaoSupport implements CallMeBackLookUpDAO {

    private static String SERVICE_CATEGORY_LIST_LOOKUP_QUERY = "serviceCategoryList";
    private static String BUSINESS_AREA_LIST_LOOKUP_QUERY = "businessAreaList";

    @Override
    public List<BusinessAreaLookUpDTO> getBusinessAreaList(String businessId) {
	BusinessAreaLookUpDTO businessAreaLookUpDTO = new BusinessAreaLookUpDTO();
	businessAreaLookUpDTO.setBusinessId(businessId);
	List<BusinessAreaLookUpDTO> businessAreaList = this.getSqlMapClientTemplate().queryForList(BUSINESS_AREA_LIST_LOOKUP_QUERY,
		businessAreaLookUpDTO);
	return businessAreaList;
    }

    @Override
    public List<BusinessAreaLookUpDTO> getServiceCategoryList(String businessId, String businessArea) {
	BusinessAreaLookUpDTO businessAreaLookUpDTO = new BusinessAreaLookUpDTO();
	businessAreaLookUpDTO.setBusinessId(businessId);
	businessAreaLookUpDTO.setBusinessAreaName(businessArea);
	List<BusinessAreaLookUpDTO> serviceCategoryList = this.getSqlMapClientTemplate().queryForList(SERVICE_CATEGORY_LIST_LOOKUP_QUERY,
		businessAreaLookUpDTO);
	return serviceCategoryList;
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
