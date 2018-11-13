package com.barclays.bmg.service.intrates.request;

import com.barclays.bmg.context.RequestContext;

public class InterestRatesServiceRequest extends RequestContext {

    private String categoryCode;

    // private String productCode;

    public String getCategoryCode() {
	return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
	this.categoryCode = categoryCode;
    }

    private boolean isFCRCountryFlag=false;

	public boolean isFCRCountryFlag() {
		return isFCRCountryFlag;
	}

	public void setFCRCountryFlag(boolean isFCRCountryFlag) {
		this.isFCRCountryFlag = isFCRCountryFlag;
	}
    
    
    
    /*
     * public String getProductCode() { return productCode; }
     * 
     * public void setProductCode(String productCode) { this.productCode = productCode; }
     */

}
