package com.barclays.bmg.service.product.request;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class ProductEligibilityServiceRequest extends RequestContext {
    private String activityId;
    private String productIndicator;
    private String incOrExc;
    private String productCode;
    private String drOrCr;
    private String productCategory;
    private String roleCategoryCode;
    private List<? extends CustomerAccountDTO> accountList;

    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }

    public String getProductIndicator() {
	return productIndicator;
    }

    public void setProductIndicator(String productIndicator) {
	this.productIndicator = productIndicator;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public String getIncOrExc() {
	return incOrExc;
    }

    public void setIncOrExc(String incOrExc) {
	this.incOrExc = incOrExc;
    }

    public String getProductCode() {
	return productCode;
    }

    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    public String getDrOrCr() {
	return drOrCr;
    }

    public void setDrOrCr(String drOrCr) {
	this.drOrCr = drOrCr;
    }

    public String getProductCategory() {
	return productCategory;
    }

    public void setProductCategory(String productCategory) {
	this.productCategory = productCategory;
    }

    public String getRoleCategoryCode() {
	return roleCategoryCode;
    }

    public void setRoleCategoryCode(String roleCategoryCode) {
	this.roleCategoryCode = roleCategoryCode;
    }

}
