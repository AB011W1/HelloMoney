package com.barclays.bmg.dto;

public class ProspectInfoEntity {

    private String createBy;
    private String createOn;
    private String modifiedOn;
    private String ownerName;

    public String getCreateBy() {
	return createBy;
    }

    public void setCreateBy(String createBy) {
	this.createBy = createBy;
    }

    public String getCreateOn() {
	return createOn;
    }

    public void setCreateOn(String createOn) {
	this.createOn = createOn;
    }

    public String getModifiedOn() {
	return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
	this.modifiedOn = modifiedOn;
    }

    public String getOwnerName() {
	return ownerName;
    }

    public void setOwnerName(String ownerName) {
	this.ownerName = ownerName;
    }

}
