package com.barclays.bmg.cashsend.operation.response;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class CashSendOneTimeExecuteOperationResponse extends ResponseContext {
    private static final long serialVersionUID = 2174553166755248L;

    private String txnRefNo;
    private Date txnDt;
    private String txnMsg;
    private String voucherId;
    private String pin;

    public String getPin() {
	return pin;
    }

    public void setPin(String pin) {
	this.pin = pin;
    }

    public String getVoucherId() {
	return voucherId;
    }

    public void setVoucherId(String voucherId) {
	this.voucherId = voucherId;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public Date getTxnDt() {
	return new Date(txnDt.getTime());
    }

    public void setTxnDt(Date txnDt) {
	if (txnDt == null) {
	    this.txnDt = null;
	} else {
	    this.txnDt = new Date(txnDt.getTime());
	}
    }

    public String getTxnMsg() {
	return txnMsg;
    }

    public void setTxnMsg(String txnMsg) {
	this.txnMsg = txnMsg;
    }
}