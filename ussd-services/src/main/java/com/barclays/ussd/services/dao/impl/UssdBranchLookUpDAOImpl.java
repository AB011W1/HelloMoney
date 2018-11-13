package com.barclays.ussd.services.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.ussd.dto.UssdBranchLookUpDTO;

public class UssdBranchLookUpDAOImpl extends SqlMapClientDaoSupport implements UssdBranchLookUpDAO {

    private static String BANK_CODE_LIST_LOOKUP_QUERY = "ussdRetrieveBankList";

    private static String BRANCH_LIST_LOOKUP_QUERY = "ussdRetrieveBranchList";

    private static String BRANCH_CODE_LIST_LOOKUP_QUERY = "ussdRetrieveBranchCodeList";

    private static String BANK_LIST_LOOKUP_QUERY_GHIPS = "ussdRetrieveBankListGHIPS";

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.services.dao.impl.BranchLookUpDAO#getBranchList(java.lang.String, java.lang.String)
     */
    @Override
    public List<UssdBranchLookUpDTO> getBankCodeList(String businessId, String bankName) {

	UssdBranchLookUpDTO branchDTO = new UssdBranchLookUpDTO();
	branchDTO.setBusinessId(businessId);
	branchDTO.setBankName(formatInput(bankName));
	List<UssdBranchLookUpDTO> branchList = this.getSqlMapClientTemplate().queryForList(BANK_CODE_LIST_LOOKUP_QUERY, branchDTO);
	return branchList;
    }

    public List<UssdBranchLookUpDTO> getBranchList(String businessId, String bankCode, String bankName, String branchName) {
	UssdBranchLookUpDTO branchDTO = new UssdBranchLookUpDTO();
	branchDTO.setBusinessId(businessId);
	branchDTO.setBankName(bankName);
	branchDTO.setBankCode(bankCode);
	if(branchName.matches("\\d+")){
		branchDTO.setBranchName(formatInputBranchCode(branchName));
	}else{
		branchDTO.setBranchName(formatInput(branchName));
	}
	List<UssdBranchLookUpDTO> branchList = this.getSqlMapClientTemplate().queryForList(BRANCH_LIST_LOOKUP_QUERY, branchDTO);
	return branchList;
    }

    @Override
    public List<UssdBranchLookUpDTO> getBranchCodeList(String businessId, String branchName) {
	String bank = "BARCLAYS";
	UssdBranchLookUpDTO branchDTO = new UssdBranchLookUpDTO();
	branchDTO.setBusinessId(businessId);
	if(branchName.matches("\\d+")){
		branchDTO.setBranchName(formatInputBranchCode(branchName));
	}else{
		branchDTO.setBranchName(formatInput(branchName));
	}
	branchDTO.setBankName(formatInput(bank));

	List<UssdBranchLookUpDTO> branchList = this.getSqlMapClientTemplate().queryForList(BRANCH_CODE_LIST_LOOKUP_QUERY, branchDTO);
	return branchList;
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

    @Override
    public List<UssdBranchLookUpDTO> getBankListGHIPS(String bankName) {
	UssdBranchLookUpDTO branchDTO = new UssdBranchLookUpDTO();
	branchDTO.setBankName(formatInput(bankName));
	List<UssdBranchLookUpDTO> branchList = this.getSqlMapClientTemplate().queryForList(BANK_LIST_LOOKUP_QUERY_GHIPS, branchDTO);
	return branchList;
    }

}
