package com.barclays.bmg.dao.lookup.impl;

import java.util.List;

import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.lookup.AtmBranchDAO;
import com.barclays.bmg.dto.AtmDTO;
import com.barclays.bmg.dto.BranchDTO;
import com.barclays.bmg.service.request.AtmBranchServiceRequest;
import com.barclays.bmg.service.response.AtmBranchServiceResponse;

public class AtmBranchDAOImpl extends BaseDAOImpl implements AtmBranchDAO {

    private static String BRANCH_LIST_LOOKUP_QUERY = "branchLocatorList";
    private static String ATM_LIST_LOOKUP_QUERY = "atmLocatorList";

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

    @Override
    public AtmBranchServiceResponse retrieveBranchList(AtmBranchServiceRequest atmBranchServiceRequest) {

	BranchDTO branchDTO = new BranchDTO();
	branchDTO.setBusinessId(atmBranchServiceRequest.getBusinessId());
	branchDTO.setCity((atmBranchServiceRequest.getCity()));
	branchDTO.setName(atmBranchServiceRequest.getArea());

	List<BranchDTO> branchList = this.queryForList(BRANCH_LIST_LOOKUP_QUERY, branchDTO);

	AtmBranchServiceResponse atmBranchServiceResponse = new AtmBranchServiceResponse();
	atmBranchServiceResponse.setBranchList(branchList);

	if (branchList.size() > 0) {
	    atmBranchServiceResponse.setSuccess(true);
	    atmBranchServiceResponse.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);

	}

	return atmBranchServiceResponse;

    }

    @Override
    public AtmBranchServiceResponse retrieveATMList(AtmBranchServiceRequest atmBranchServiceRequest) {
	AtmDTO atmDTO = new AtmDTO();

	atmDTO.setBusinessId(atmBranchServiceRequest.getBusinessId());
	atmDTO.setCity(atmBranchServiceRequest.getCity());
	atmDTO.setName(atmBranchServiceRequest.getArea());

	List<AtmDTO> atmList = this.queryForList(ATM_LIST_LOOKUP_QUERY, atmDTO);

	AtmBranchServiceResponse atmBranchServiceResponse = new AtmBranchServiceResponse();
	atmBranchServiceResponse.setAtmList(atmList);

	if (atmList.size() > 0) {
	    atmBranchServiceResponse.setSuccess(true);
	    atmBranchServiceResponse.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);
	}

	return atmBranchServiceResponse;
    }
}
