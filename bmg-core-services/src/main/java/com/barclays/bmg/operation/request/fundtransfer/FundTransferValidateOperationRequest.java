package com.barclays.bmg.operation.request.fundtransfer;

import com.barclays.bmg.context.RequestContext;

public class FundTransferValidateOperationRequest extends RequestContext {
    private String sourceAccount;
    private String destAccount;
    private String txnAmount;
    private String txNote;
    private String txDate;

    public String getSourceAccount() {
	return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
	this.sourceAccount = sourceAccount;
    }

    public String getDestAccount() {
	return destAccount;
    }

    public void setDestAccount(String destAccount) {
	this.destAccount = destAccount;
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
