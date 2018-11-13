package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class AddProspectServiceResponse extends ResponseContext {

    private String prospectId;
    private String assignedTo;
    private boolean duplicateFlag;

    public boolean isDuplicateFlag() {
	return duplicateFlag;
    }

    public void setDuplicateFlag(boolean duplicateFlag) {
	this.duplicateFlag = duplicateFlag;
    }

    public String getProspectId() {
	return prospectId;
    }

    public void setProspectId(String prospectId) {
	this.prospectId = prospectId;
    }

    public String getAssignedTo() {
	return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
	this.assignedTo = assignedTo;
    }

}
