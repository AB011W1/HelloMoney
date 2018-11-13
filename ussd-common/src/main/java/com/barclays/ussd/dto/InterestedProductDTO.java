package com.barclays.ussd.dto;

import com.barclays.bmg.dto.BaseDTO;

public class InterestedProductDTO extends BaseDTO {


	private static final long serialVersionUID = -1512402414452891867L;
	private String productId;
    private String productName;
    private String systemId;
    private String businessId;

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

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

}
