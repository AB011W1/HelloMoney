package com.barclays.bmg.dto;

import java.util.List;

public class BranchLookUpDTO extends BaseDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = 3256312993522118925L;

    private String businessId;
    private String bankName;
    private String branchName;
    private String cityName;
    private String bankCode;
    private String branchCode;
    private String clearingCode;
    private List<String> branchCodeList;

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

    public String getBankCode() {
	return bankCode;
    }

    public void setBankCode(String bankCode) {
	this.bankCode = bankCode;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getClearingCode() {
	return clearingCode;
    }

    public void setClearingCode(String clearingCode) {
	this.clearingCode = clearingCode;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public List<String> getBranchCodeList() {
	return branchCodeList;
    }

    public void setBranchCodeList(List<String> branchCodeList) {
	this.branchCodeList = branchCodeList;
    }

}
