package com.barclays.bmg.operation.response.lookup;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BranchLookUpDTO;

public class BranchLookUpOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1223185346330703044L;

    private List<BranchLookUpDTO> branchList;

    public List<BranchLookUpDTO> getBranchList() {
	return branchList;
    }

    public void setBranchList(List<BranchLookUpDTO> branchList) {
	this.branchList = branchList;
    }
}
