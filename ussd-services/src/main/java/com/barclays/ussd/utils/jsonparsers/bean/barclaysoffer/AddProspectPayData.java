package com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddProspectPayData {

    @JsonProperty
    private String prospectId;
    @JsonProperty
    private String assignedTo;
    @JsonProperty
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
