package com.barclays.bmg.mvc.operation.locator.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AtmDTO;
import com.barclays.bmg.dto.BranchDTO;

public class AtmBranchLocatorOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1223185346330703044L;

    private List<BranchDTO> branchList;
    private List<AtmDTO> atmList;

    public List<BranchDTO> getBranchList() {
	return branchList;
    }

    public void setBranchList(List<BranchDTO> branchList) {
	this.branchList = branchList;
    }

    public List<AtmDTO> getAtmList() {
	return atmList;
    }

    public void setAtmList(List<AtmDTO> atmList) {
	this.atmList = atmList;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

}
