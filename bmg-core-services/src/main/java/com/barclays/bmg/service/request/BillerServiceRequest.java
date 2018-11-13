package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class BillerServiceRequest extends RequestContext {
    private String businessId;
    private String billerId;
    private String billerName;

    private String billerCategoryId;

    private String billerCategoryName;

    private boolean showAllBillers = false;

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    public String getBillerName() {
	return billerName;
    }

    public void setBillerName(String billerName) {
	this.billerName = billerName;
    }

    public String getBillerCategoryId() {
	return billerCategoryId;
    }

    public void setBillerCategoryId(String billerCategoryId) {
	this.billerCategoryId = billerCategoryId;
    }

    public String getBillerCategoryName() {
	return billerCategoryName;
    }

    public void setBillerCategoryName(String billerCategoryName) {
	this.billerCategoryName = billerCategoryName;
    }

    public String getReferenceNoText1() {
	return referenceNoText1;
    }

    public void setReferenceNoText1(String referenceNoText1) {
	this.referenceNoText1 = referenceNoText1;
    }

    public String getReferenceNoText2() {
	return referenceNoText2;
    }

    public void setReferenceNoText2(String referenceNoText2) {
	this.referenceNoText2 = referenceNoText2;
    }

    public boolean isShowAllBillers() {
	return showAllBillers;
    }

    public void setShowAllBillers(boolean showAllBillers) {
	this.showAllBillers = showAllBillers;
    }

    private String referenceNoText1;

    private String referenceNoText2;

}
