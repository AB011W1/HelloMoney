package com.barclays.ussd.dto;

import com.barclays.bmg.dto.BaseDTO;

public class InterestedSubProductDTO extends BaseDTO {


	private static final long serialVersionUID = 5985826148596865141L;
	private String subProductName;
    private String businessId;
    private String systemId;
    private String productId;
    private String productName;

    public String getSubProductName() {
	return subProductName;
    }

    public void setSubProductName(String subProductName) {
	this.subProductName = subProductName;
    }

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

    public String getProductId() {
	return productId;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }

    public String getProductName() {
	return productName;
    }

    public void setProductName(String productName) {
	this.productName = productName;
    }

}
