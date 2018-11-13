package com.barclays.bmg.dao.lookup.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.lookup.BranchLookUpDAO;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.service.request.lookup.BranchLookUpServiceRequest;
import com.barclays.bmg.service.response.lookup.BranchLookUpServiceResponse;

public class BranchLookUpDAOImpl extends BaseDAOImpl implements BranchLookUpDAO {

    private static String BRANCH_LIST_LOOKUP_QUERY = "retrieveBranchList";
    private static String BRANCH_NAME_LOOKUP_QUERY = "retrieveBranchName";
    private static final String BUSINESS_ID = "businessId";
    private static final String BRANCH_CODE_LIST = "branchCodeList";
    private static String ALL_BRANCH_LOOKUP_QUERY = "retrieveAllBranches";
    private static String RETRIEVE_BANK_SWIFT_CODE="retrieveBankSwiftCode";

    @Override
    public BranchLookUpServiceResponse getBranchList(BranchLookUpServiceRequest branchLookUpServiceRequest) {

	BranchLookUpDTO branchDTO = new BranchLookUpDTO();
	branchDTO.setBusinessId(branchLookUpServiceRequest.getContext().getBusinessId());
	branchDTO.setCityName(formatInput(branchLookUpServiceRequest.getCityName()));
	branchDTO.setBranchName(formatInput(branchLookUpServiceRequest.getBranchName()));

	List<BranchLookUpDTO> branchList = this.queryForList(BRANCH_LIST_LOOKUP_QUERY, branchDTO);

	BranchLookUpServiceResponse branchLookUpServiceResponse = new BranchLookUpServiceResponse();
	branchLookUpServiceResponse.setBranchList(branchList);

	return branchLookUpServiceResponse;
    }

    @Override
    public BranchLookUpServiceResponse getBranchName(BranchLookUpServiceRequest branchLookUpServiceRequest) {

	Map parameterMap = new HashMap();

	parameterMap.put(BUSINESS_ID, branchLookUpServiceRequest.getContext().getBusinessId());
	parameterMap.put(BRANCH_CODE_LIST, branchLookUpServiceRequest.getBranchCodeList());

	/*
	 * BranchLookUpDTO branchDTO = new BranchLookUpDTO(); branchDTO.setBusinessId(branchLookUpServiceRequest.getContext().getBusinessId());
	 *
	 * //branchDTO.setBranchCode(branchLookUpServiceRequest.getBranchCode());
	 * branchDTO.setBranchCodeList(branchLookUpServiceRequest.getBranchCodeLst());
	 */

	List<BranchLookUpDTO> branchList = this.queryForList(BRANCH_NAME_LOOKUP_QUERY, parameterMap);

	BranchLookUpServiceResponse branchLookUpServiceResponse = new BranchLookUpServiceResponse();
	branchLookUpServiceResponse.setBranchList(branchList);

	return branchLookUpServiceResponse;
    }

    /*
     * public BranchLookUpServiceResponse getBranchNames( BranchLookUpDTO branchDTO) {
     *
     *
     *
     *
     * List<BranchLookUpDTO> branchList = this.queryForList(BRANCH_NAME_LOOKUP_QUERY, branchDTO);
     *
     * BranchLookUpServiceResponse branchLookUpServiceResponse = new BranchLookUpServiceResponse();
     * branchLookUpServiceResponse.setBranchList(branchList);
     *
     * return branchLookUpServiceResponse; }
     */

    /**
     * @param value
     *            value
     * @return String
     */
    private String formatInput(String value) {
	String updatedvalue = value;
	if (null == updatedvalue || updatedvalue.trim().length() == 0) {
	    return null;
	} else {
	    // return "%" + value + "%";
	    updatedvalue = updatedvalue.toLowerCase();
	    return new StringBuilder().append("%").append(updatedvalue).append("%").toString();
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.dao.lookup.BranchLookUpDAO#getAllBranches(com.barclays.bmg.service.request.lookup.BranchLookUpServiceRequest)
     *
     * get list all branches irrespective of branch code
     */
    @Override
    public BranchLookUpServiceResponse getAllBranches(BranchLookUpServiceRequest branchLookUpServiceRequest) {
	BranchLookUpDTO branchDTO = new BranchLookUpDTO();
	branchDTO.setBusinessId(branchLookUpServiceRequest.getContext().getBusinessId());
	List<BranchLookUpDTO> branchList = this.queryForList(ALL_BRANCH_LOOKUP_QUERY, branchDTO);

	BranchLookUpServiceResponse branchLookUpServiceResponse = new BranchLookUpServiceResponse();
	branchLookUpServiceResponse.setBranchList(branchList);

	return branchLookUpServiceResponse;
    }

    public List<BranchLookUpDTO> getBankSwiftCode(String bankCode, String branchCode){

    	Map<String,String> parameterMap = new HashMap<String,String>();
    	parameterMap.put("bankCode", bankCode);
    	parameterMap.put("branchCode", branchCode);
    	List<BranchLookUpDTO> branchList= this.queryForList(RETRIEVE_BANK_SWIFT_CODE, parameterMap);
    	return branchList;
    }
}
