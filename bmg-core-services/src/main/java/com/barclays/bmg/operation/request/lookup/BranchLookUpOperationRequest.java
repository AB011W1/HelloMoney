package com.barclays.bmg.operation.request.lookup;

import com.barclays.bmg.context.RequestContext;

public class BranchLookUpOperationRequest extends RequestContext {

    private String bankName;
    private String branchName;
    private String cityName;
    private String branchCode;

    public String getBankName() {
	return bankName;
    }

    public void setBankName(String bankName) {
	this.bankName = bankName;
    }

    public String getBranchName() {
	return branchName;
    }

    public void setBranchName(String branchName) {
	this.branchName = branchName;
    }

    public String getCityName() {
	return cityName;
    }

    public void setCityName(String cityName) {
	this.cityName = cityName;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

}
