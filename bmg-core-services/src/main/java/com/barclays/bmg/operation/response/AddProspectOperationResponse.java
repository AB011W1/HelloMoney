package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;

public class AddProspectOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -5650859992960757304L;

    private String prospectId;
    private String assignedTo;
    private boolean duplicateFlag;

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

    public boolean isDuplicateFlag() {
	return duplicateFlag;
    }

    public void setDuplicateFlag(boolean duplicateFlag) {
	this.duplicateFlag = duplicateFlag;
    }

}