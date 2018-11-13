package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class AtmBranchServiceRequest extends RequestContext {

    private String businessId;
    private String area;
    private String city;
    private String activityId;

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

}
