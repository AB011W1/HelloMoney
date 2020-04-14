package com.barclays.ussd.services.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.ussd.dto.LeadGenProductDTO;
import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;

public class UssdBranchLocatorLookUpDAOImpl extends SqlMapClientDaoSupport implements UssdBranchLocatorLookUpDAO {

    private static String BRANCH_CITY_LIST_LOOKUP_QUERY = "ussdRetrieveBranchCityList";
    private static String BRANCH_AREA_LIST_LOOKUP_QUERY = "ussdRetrieveBranchAreaList";

    private static String CHEQUE_BOOK_BRANCH_LIST_LOOKUP_QUERY = "ussdRetrieveChequeBookBranchList";
    private static String CHEQUE_BOOK_BRANCH_LIST_LOOKUP_QUERY_TZNBC = "ussdRetrieveChequeBookBranchListTZNBC";
    private static String LEAD_PRODUCT_LIST_LOOKUP_QUERY = "leadProductList";
    private static String LEAD_PRODUCT_SUB_LIST_LOOKUP_QUERY = "leadProductSubList";


    @Override
    public List<UssdBranchLocatorLookUpDTO> getAreaList(String businessId, String cityName, String areaLetter) {
	UssdBranchLocatorLookUpDTO branchDTO = new UssdBranchLocatorLookUpDTO();
	branchDTO.setBusinessId(businessId);
	branchDTO.setCityName(formatInput(cityName));
	branchDTO.setBranchName(formatInput(areaLetter));
	List<UssdBranchLocatorLookUpDTO> areaList = this.getSqlMapClientTemplate().queryForList(BRANCH_AREA_LIST_LOOKUP_QUERY, branchDTO);
	return areaList;
    }

    @Override
    public List<UssdBranchLocatorLookUpDTO> getCityList(String businessId, String cityLetter) {
	UssdBranchLocatorLookUpDTO branchDTO = new UssdBranchLocatorLookUpDTO();
	branchDTO.setBusinessId(businessId);
	branchDTO.setCityName(formatInput(cityLetter));
	List<UssdBranchLocatorLookUpDTO> cityList = this.getSqlMapClientTemplate().queryForList(BRANCH_CITY_LIST_LOOKUP_QUERY, branchDTO);
	return cityList;
    }

    @Override
    public List<UssdBranchLocatorLookUpDTO> getBranchList(String businessId, String branchLetter) {
	List<UssdBranchLocatorLookUpDTO> branchList=null;
	UssdBranchLocatorLookUpDTO branchDTO = new UssdBranchLocatorLookUpDTO();
	branchDTO.setBusinessId(businessId);
	if(branchLetter.matches("\\d+")){
		branchDTO.setBranchName(formatInputBranchCode(branchLetter));
	}else{
		branchDTO.setBranchName(formatInput(branchLetter));
	}
	if(businessId.equalsIgnoreCase("TZNBC")){
	    branchDTO.setBankName(formatInput("National"));
	    branchList = this.getSqlMapClientTemplate().queryForList(CHEQUE_BOOK_BRANCH_LIST_LOOKUP_QUERY_TZNBC, branchDTO);
	}else{
	    branchDTO.setBankName(formatInput("Barclays"));
	    branchList = this.getSqlMapClientTemplate().queryForList(CHEQUE_BOOK_BRANCH_LIST_LOOKUP_QUERY, branchDTO);
	}
	if(branchList.size()>0){
		return branchList;
	}
	else {
		String absaBank="Absa";
		branchDTO.setBankName(formatInput(absaBank));
	    branchList = this.getSqlMapClientTemplate().queryForList(CHEQUE_BOOK_BRANCH_LIST_LOOKUP_QUERY, branchDTO);
		return branchList;
    	}
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

    private String formatInputBranchCode(String value) {
    	String updatedvalue = value;
    	if (null == updatedvalue || updatedvalue.trim().length() == 0) {
    	    return null;
    	} else {
    	    return new StringBuilder().append("%").append(updatedvalue).append("%").toString();
    	}
      }
    //Lead Generation
    @Override
    public List<LeadGenProductDTO> getLeadProductList(String businessId,String langId) {
    	LeadGenProductDTO leadGenProdDTO = new LeadGenProductDTO();
    	leadGenProdDTO.setBusinessId(businessId);
    	leadGenProdDTO.setLangId(langId.toUpperCase());
	List<LeadGenProductDTO> leadProductList = this.getSqlMapClientTemplate().queryForList(LEAD_PRODUCT_LIST_LOOKUP_QUERY, leadGenProdDTO);
	return leadProductList;
    }

    @Override
    public List<LeadGenProductDTO> getLeadProductSubList(String businessId,String prodName,String langId) {
    	LeadGenProductDTO leadGenProdDTO = new LeadGenProductDTO();
    	leadGenProdDTO.setBusinessId(businessId);
    	leadGenProdDTO.setProductName(prodName);
    	leadGenProdDTO.setLangId(langId.toUpperCase());
	List<LeadGenProductDTO> leadProductList = this.getSqlMapClientTemplate().queryForList(LEAD_PRODUCT_SUB_LIST_LOOKUP_QUERY, leadGenProdDTO);
	return leadProductList;
    }
}
