package com.barclays.bmg.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransactionCutOffTimeDTO implements Serializable {

    private static final long serialVersionUID = -6601094519962811377L;

    private String businessId;

    private String systemId;

    private String activityId;

    private Timestamp cutOffTime;

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public Timestamp getCutOffTime() {
	return cutOffTime;
    }

    public void setCutOffTime(Timestamp cutOffTime) {
	this.cutOffTime = cutOffTime;
    }

}
