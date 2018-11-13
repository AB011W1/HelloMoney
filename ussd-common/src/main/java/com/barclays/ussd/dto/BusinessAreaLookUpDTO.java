package com.barclays.ussd.dto;

public class BusinessAreaLookUpDTO {

    private String businessId;
    private String businessAreaName;
    private String serviceCategoryId;
    private String serviceCategoryName;

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getBusinessAreaName() {
	return businessAreaName;
    }

    public void setBusinessAreaName(String businessAreaName) {
	this.businessAreaName = businessAreaName;
    }

    public String getServiceCategoryId() {
	return serviceCategoryId;
    }

    public void setServiceCategoryId(String serviceCategoryId) {
	this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceCategoryName() {
	return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
	this.serviceCategoryName = serviceCategoryName;
    }

}
