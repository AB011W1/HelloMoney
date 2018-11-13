package com.barclays.bmg.dto;

public class CashSendRequestDTO extends BaseDomainDTO {

    private static final long serialVersionUID = -31563764274907555L;

    private String actNo;
    private String txnAmt;
    private String vPin;
    private String senderMobileNo;
    private String recipientMobileNo;
    private String curr;
    private String txnRefNo;
    private String txnNot;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
	this.txnAmt = txnAmt;
    }

    public String getVPin() {
	return vPin;
    }

    public void setVPin(String pin) {
	vPin = pin;
    }

    public String getSenderMobileNo() {
	return senderMobileNo;
    }

    public void setSenderMobileNo(String senderMobileNo) {
	this.senderMobileNo = senderMobileNo;
    }

    public String getRecipientMobileNo() {
	return recipientMobileNo;
    }

    public void setRecipientMobileNo(String recipientMobileNo) {
	this.recipientMobileNo = recipientMobileNo;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

}
