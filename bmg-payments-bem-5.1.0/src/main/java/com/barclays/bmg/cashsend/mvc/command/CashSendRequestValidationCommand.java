package com.barclays.bmg.cashsend.mvc.command;

public class CashSendRequestValidationCommand {

    private String actNo;
    private String amt;
    private String vPin;
    private String mobileNo;
    private String currency;
    private String txnNot;

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getAmt() {
	return amt;
    }

    public void setAmt(String amt) {
	this.amt = amt;
    }

    public String getVPin() {
	return vPin;
    }

    public void setVPin(String pin) {
	vPin = pin;
    }

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

}
