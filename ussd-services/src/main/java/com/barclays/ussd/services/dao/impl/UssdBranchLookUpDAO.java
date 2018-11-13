package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.dto.UssdBranchLookUpDTO;

public interface UssdBranchLookUpDAO {

	/**
	 * It return the List of Branch code on passing the BusinessId and branch code, these method
	 * search the Branch code on passing starting letter of the branch code.
	 * @param businessId
	 * @param branchCode
	 * @return List
	 */
	public List<UssdBranchLookUpDTO> getBankCodeList(String businessId, String bankName);

	public List<UssdBranchLookUpDTO>  getBranchList(String businessId, String bankCode,String bankName,String branchName);

	/**
	 *  Get the branch name list from the given branch letter.
	 * @param businessId
	 * @param branchLetter
	 * @return
	 */
	public List<UssdBranchLookUpDTO> getBranchCodeList(String businessId, String branchLetter);

	public List<UssdBranchLookUpDTO> getBankListGHIPS(String bankName);
}
