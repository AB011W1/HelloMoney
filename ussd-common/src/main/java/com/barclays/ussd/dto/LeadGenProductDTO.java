package com.barclays.ussd.dto;


public class LeadGenProductDTO {


	private static final long serialVersionUID = 5985826148596865141L;
	private String productName;
	private String subProductName;
	private String serviceCode;
	private String businessId;
    private String productStatus;
    private String sequenceNo;
    private String langId;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubProductName() {
		return subProductName;
	}
	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getLangId() {
		return langId;
	}
	public void setLangId(String langId) {
		this.langId = langId;
	}

}