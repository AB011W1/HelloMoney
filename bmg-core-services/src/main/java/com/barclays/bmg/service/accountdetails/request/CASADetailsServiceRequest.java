package com.barclays.bmg.service.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class CASADetailsServiceRequest extends RequestContext {
    private String accountNo;
    private String branchCode;
    private int days;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public int getDays() {
	return days;
    }

    public void setDays(int days) {
	this.days = days;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

}
