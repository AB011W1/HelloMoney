package com.barclays.bmg.service.response.lookup;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BranchLookUpDTO;

public class BranchLookUpServiceResponse extends ResponseContext {

    private static final long serialVersionUID = -8668382225466365845L;

    private List<BranchLookUpDTO> branchList;

    public List<BranchLookUpDTO> getBranchList() {
	return branchList;
    }

    public void setBranchList(List<BranchLookUpDTO> branchList) {
	this.branchList = branchList;
    }

}
