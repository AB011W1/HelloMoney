package com.barclays.bmg.json.model.addprospect;

import java.io.Serializable;

public class AddProspectJSONBean implements Serializable {

    private static final long serialVersionUID = 7412582930544502134L;

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

    public AddProspectJSONBean(String prospectId, String assignedTo, boolean duplicateFlag) {
	super();
	this.prospectId = prospectId;
	this.assignedTo = assignedTo;
	this.duplicateFlag = duplicateFlag;
    }

}
