package com.barclays.bmg.service.accounts.request;

import com.barclays.bmg.context.RequestContext;

public class AllAccountServiceRequest extends RequestContext {

    private String scvid;

    private String bankCIF;

    private String branchCode;

    // ORCHARD CHANGES START
    private String accountNo;

    private String accountType;

    public String getScvid() {
	return scvid;
    }

    public void setScvid(String scvid) {
	this.scvid = scvid;
    }

    public String getBankCIF() {
	return bankCIF;
    }

    public void setBankCIF(String bankCIF) {
	this.bankCIF = bankCIF;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public String getAccountType() {
	return accountType;
    }

    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }

}
