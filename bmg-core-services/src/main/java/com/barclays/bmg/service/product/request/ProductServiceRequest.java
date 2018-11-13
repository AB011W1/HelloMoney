package com.barclays.bmg.service.product.request;

import java.util.List;

import com.barclays.bmg.context.RequestContext;

public class ProductServiceRequest extends RequestContext {
    private String productGroup;
    private String productCode;
    private String currencyCode;

    private List<String> prodCodeList;

    public String getCurrencyCode() {
	return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
    }

    public String getProductGroup() {
	return productGroup;
    }

    public void setProductGroup(String productGroup) {
	this.productGroup = productGroup;
    }

    public String getProductCode() {
	return productCode;
    }

    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    public List<String> getProdCodeList() {
	return prodCodeList;
    }

    public void setProdCodeList(List<String> prodCodeList) {
	this.prodCodeList = prodCodeList;
    }

}
