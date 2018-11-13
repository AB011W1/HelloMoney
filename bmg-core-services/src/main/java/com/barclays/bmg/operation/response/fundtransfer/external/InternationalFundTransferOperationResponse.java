package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class InternationalFundTransferOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 4444584099260598499L;

    private boolean authRequired;
    private Date trnDate;
    private String trnRef;
    private String txnMsg;

    public boolean isAuthRequired() {
	return authRequired;
    }

    public void setAuthRequired(boolean authRequired) {
	this.authRequired = authRequired;
    }

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
