package com.barclays.bmg.service.lookup.impl;

import java.util.List;

import com.barclays.bmg.dao.lookup.BranchLookUpDAO;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.service.lookup.BranchLookUpService;
import com.barclays.bmg.service.request.lookup.BranchLookUpServiceRequest;
import com.barclays.bmg.service.response.lookup.BranchLookUpServiceResponse;

public class BranchLookUpServiceImpl implements BranchLookUpService {

    private BranchLookUpDAO branchLookUpDAO;

    @Override
    public BranchLookUpServiceResponse getBranchList(BranchLookUpServiceRequest branchLookUpServiceRequest) {
	return branchLookUpDAO.getBranchList(branchLookUpServiceRequest);
    }

    @Override
    public BranchLookUpServiceResponse getBranchName(BranchLookUpServiceRequest branchLookUpServiceRequest) {
	return branchLookUpDAO.getBranchName(branchLookUpServiceRequest);
    }

    public BranchLookUpDAO getBranchLookUpDAO() {
	return branchLookUpDAO;
    }

    public void setBranchLookUpDAO(BranchLookUpDAO branchLookUpDAO) {
	this.branchLookUpDAO = branchLookUpDAO;
    }

    @Override
    public BranchLookUpServiceResponse getAllBranches(BranchLookUpServiceRequest branchLookUpServiceRequest) {
	return branchLookUpDAO.getAllBranches(branchLookUpServiceRequest);
    }

    public List<BranchLookUpDTO> getBankSwiftCode(String bankCode, String branchCode){
    	return branchLookUpDAO.getBankSwiftCode(bankCode, branchCode);
    }
}
