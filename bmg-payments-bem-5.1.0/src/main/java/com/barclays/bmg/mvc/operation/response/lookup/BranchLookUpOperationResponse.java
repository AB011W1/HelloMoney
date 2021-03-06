package com.barclays.bmg.mvc.operation.response.lookup;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BranchLookUpDTO;

public class BranchLookUpOperationResponse extends ResponseContext {

	private List<BranchLookUpDTO> branchList;

	public List<BranchLookUpDTO> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchLookUpDTO> branchList) {
		this.branchList = branchList;
	}
}
