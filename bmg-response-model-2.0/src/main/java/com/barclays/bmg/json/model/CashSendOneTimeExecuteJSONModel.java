package com.barclays.bmg.json.model;

import com.barclays.bmg.json.response.BMBPayloadData;

public class CashSendOneTimeExecuteJSONModel extends BMBPayloadData {


	private static final long serialVersionUID = -8763186664603817133L;
	private String txnRefNo;
    private String txnMsg;
    private String voucherId;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public String getTxnMsg() {
	return txnMsg;
    }

    public void setTxnMsg(String txnMsg) {
	this.txnMsg = txnMsg;
    }

    public String getVoucherId() {
	return voucherId;
    }

    public void setVoucherId(String voucherId) {
	this.voucherId = voucherId;
    }

}
