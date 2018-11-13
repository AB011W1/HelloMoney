package com.barclays.bmg.operation.response.fundtransfer;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class DomesticFundTransferExecuteOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -8604198669876049835L;

    private boolean scndLvlAuthReq;
    private Date trnDate;
    private String trnRef;
    private String txnMsg;

    public boolean isScndLvlAuthReq() {
	return scndLvlAuthReq;
    }

    public void setScndLvlAuthReq(boolean scndLvlAuthReq) {
	this.scndLvlAuthReq = scndLvlAuthReq;
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
