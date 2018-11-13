package com.barclays.bmg.operation.request.fundtransfer;

import com.barclays.bmg.context.RequestContext;

public class InternalFundTransferValidateOperationRequest extends RequestContext {

    private String sourceAccount;
    private String payId;
    private String txnAmount;
    private String txNote;
    private String txDate;

    public String getSourceAccount() {
	return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
	this.sourceAccount = sourceAccount;
    }

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
    }

    public String getTxnAmount() {
	return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
	this.txnAmount = txnAmount;
    }

    public String getTxNote() {
	return txNote;
    }

    public void setTxNote(String txNote) {
	this.txNote = txNote;
    }

    public String getTxDate() {
	return txDate;
    }

    public void setTxDate(String txDate) {
	this.txDate = txDate;
    }
}
