package com.barclays.bmg.operation.response.billpayment;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class PaymentExecutionOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -1876825670548709475L;

    private String txnRefNo;
    private Date txnDate;
    private String billPayTxnType;
    private boolean authRequired;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public Date getTxnDate() {
	return new Date(txnDate.getTime());
    }

    public void setTxnDate(Date txnDate) {
	if (txnDate == null) {
	    this.txnDate = null;
	} else {
	    this.txnDate = new Date(txnDate.getTime());
	}
    }

    public String getBillPayTxnType() {
	return billPayTxnType;
    }

    public void setBillPayTxnType(String billPayTxnType) {
	this.billPayTxnType = billPayTxnType;
    }

    public boolean isAuthRequired() {
	return authRequired;
    }

    public void setAuthRequired(boolean authRequired) {
	this.authRequired = authRequired;
    }
}