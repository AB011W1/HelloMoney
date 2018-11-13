package com.barclays.ussd.services.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;

public class UssdAtmLocatorLookUpDAOImpl extends SqlMapClientDaoSupport implements UssdAtmLocatorLookUpDAO {

    private static String ATM_CITY_LIST_LOOKUP_QUERY = "ussdRetrieveAtmCityList";
    private static String ATM_AREA_LIST_LOOKUP_QUERY = "ussdRetrieveAtmAreaList";

    @Override
    public List<UssdBranchLocatorLookUpDTO> getAreaList(String businessId, String cityName, String areaLetter) {
	UssdBranchLocatorLookUpDTO branchDTO = new UssdBranchLocatorLookUpDTO();
	branchDTO.setBusinessId(businessId);
	branchDTO.setCityName(formatInput(cityName));
	branchDTO.setBranchName(formatInput(areaLetter));
	List<UssdBranchLocatorLookUpDTO> areaList = this.getSqlMapClientTemplate().queryForList(ATM_AREA_LIST_LOOKUP_QUERY, branchDTO);
	return areaList;
    }

    @Override
    public List<UssdBranchLocatorLookUpDTO> getCityList(String businessId, String cityLetter) {
	UssdBranchLocatorLookUpDTO branchDTO = new UssdBranchLocatorLookUpDTO();
	branchDTO.setBusinessId(businessId);
	branchDTO.setCityName(formatInput(cityLetter));
	List<UssdBranchLocatorLookUpDTO> cityList = this.getSqlMapClientTemplate().queryForList(ATM_CITY_LIST_LOOKUP_QUERY, branchDTO);
	return cityList;
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
