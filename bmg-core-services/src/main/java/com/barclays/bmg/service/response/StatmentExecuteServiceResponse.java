package com.barclays.bmg.service.response;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class StatmentExecuteServiceResponse extends ResponseContext {

    private static final long serialVersionUID = -5955669287603440504L;

    private String txnRefNo;
    private Date txnDt;
    private String txnMsg;

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
