package com.barclays.bmg.fxrate.mvc.command;

public class FxRateCommand {
    private String amt;
    private String srcCurrency;
    private String destCurrency;
    private String actNo;
    private String branchCode;
    private String tranType;

    public String getAmt() {
	return amt;
    }

    public void setAmt(String amt) {
	this.amt = amt;
    }

    public String getSrcCurrency() {
	return srcCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
	this.srcCurrency = srcCurrency;
    }

    public String getDestCurrency() {
	return destCurrency;
    }

    public void setDestCurrency(String destCurrency) {
	this.destCurrency = destCurrency;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public String getTranType() {
	return tranType;
    }

    public void setTranType(String tranType) {
	this.tranType = tranType;
    }

}
