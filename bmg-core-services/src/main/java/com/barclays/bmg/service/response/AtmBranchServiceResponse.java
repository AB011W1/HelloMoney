package com.barclays.bmg.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AtmDTO;
import com.barclays.bmg.dto.BranchDTO;

public class AtmBranchServiceResponse extends ResponseContext {
    private static final long serialVersionUID = -8668382225466365845L;

    private List<BranchDTO> branchList;
    private List<AtmDTO> atmList;

    public List<BranchDTO> getBranchList() {
	return branchList;
    }

    public void setBranchList(List<BranchDTO> branchList) {
	this.branchList = branchList;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public List<AtmDTO> getAtmList() {
	return atmList;
    }

    public void setAtmList(List<AtmDTO> atmList) {
	this.atmList = atmList;
    }

}
