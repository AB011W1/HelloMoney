package com.barclays.bmg.operation.request.billpayment;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;

public class PaymentFormSubmissionOperationRequest extends RequestContext {

    private String fromAccount;
    private String txnAmount;
    private String txnNote;
    private String billPayTxnType;
    private BigDecimal minBillAmt;
    private BigDecimal maxBillAmt;
    private String toAcctCurr;
    private String billerId;

    public String getFromAccount() {
	return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
	this.fromAccount = fromAccount;
    }

    public String getTxnAmount() {
	return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
	this.txnAmount = txnAmount;
    }

    public String getTxnNote() {
	return txnNote;
    }

    public void setTxnNote(String txnNote) {
	this.txnNote = txnNote;
    }

    public String getBillPayTxnType() {
	return billPayTxnType;
    }

    public void setBillPayTxnType(String billPayTxnType) {
	this.billPayTxnType = billPayTxnType;
    }

    public BigDecimal getMinBillAmt() {
	return minBillAmt;
    }

    public void setMinBillAmt(BigDecimal minBillAmt) {
	this.minBillAmt = minBillAmt;
    }

    public BigDecimal getMaxBillAmt() {
	return maxBillAmt;
    }

    public void setMaxBillAmt(BigDecimal maxBillAmt) {
	this.maxBillAmt = maxBillAmt;
    }

    public String getToAcctCurr() {
	return toAcctCurr;
    }

    public void setToAcctCurr(String toAcctCurr) {
	this.toAcctCurr = toAcctCurr;
    }

    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

}
