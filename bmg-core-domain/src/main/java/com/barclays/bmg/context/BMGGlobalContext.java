package com.barclays.bmg.context;

import java.util.Map;

public class BMGGlobalContext {

    private String businessId;
    private String activityId;
    private String systemId;
    private String userId;

    private Map<String, Object> contextMap;

    public Map<String, Object> getContextMap() {
	return contextMap;
    }

    public void setContextMap(Map<String, Object> contextMap) {
	this.contextMap = contextMap;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

}
