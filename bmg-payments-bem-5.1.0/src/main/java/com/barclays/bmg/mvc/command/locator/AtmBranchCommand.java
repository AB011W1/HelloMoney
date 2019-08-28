package com.barclays.bmg.mvc.command.locator;

public class AtmBranchCommand {

    private String businessId;
    private String area;
    private String city;
    private String activityId;
    private String auditRequired;

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getArea() {
	return area;
    }

    public void setArea(String area) {
	this.area = area;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public String getAuditRequired() {
	return auditRequired;
    }

    public void setAuditRequired(String auditRequired) {
	this.auditRequired = auditRequired;
    }

}
