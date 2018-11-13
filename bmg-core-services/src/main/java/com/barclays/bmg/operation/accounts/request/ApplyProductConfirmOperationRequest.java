package com.barclays.bmg.operation.accounts.request;

import com.barclays.bmg.context.RequestContext;

public class ApplyProductConfirmOperationRequest extends RequestContext {
	private String primaryAccountNumber;
	private String activityId;
	private String mobileNumber;
	private String cifNumber;
	private String productName;
	private String subProductName;

	public String getPrimaryAccountNumber() {
		return primaryAccountNumber;
	}
	public void setPrimaryAccountNumber(String primaryAccountNumber) {
		this.primaryAccountNumber = primaryAccountNumber;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public void setCifNumber(String cifNumber) {
		this.cifNumber = cifNumber;
	}
	public String getCifNumber() {
		return cifNumber;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductName() {
		return productName;
	}
	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
	public String getSubProductName() {
		return subProductName;
	}


}
