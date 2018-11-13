package com.barclays.bmg.service.response;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class InternationalFundTransferServiceResponse extends ResponseContext {

    private static final long serialVersionUID = 3642204021482850677L;

    private Date trnDate;
    private String trnRef;
    private String txnMsg;

    public Date getTrnDate() {
	return new Date(trnDate.getTime());
    }

    public void setTrnDate(Date trnDate) {
	if (trnDate == null) {
	    this.trnDate = null;
	} else {
	    this.trnDate = new Date(trnDate.getTime());
	}
    }

    public String getTrnRef() {
	return trnRef;
    }

    public void setTrnRef(String trnRef) {
	this.trnRef = trnRef;
    }

    public String getTxnMsg() {
	return txnMsg;
    }

    public void setTxnMsg(String txnMsg) {
	this.txnMsg = txnMsg;
    }

}
