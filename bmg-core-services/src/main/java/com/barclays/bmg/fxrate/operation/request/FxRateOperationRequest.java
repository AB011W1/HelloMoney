package com.barclays.bmg.fxrate.operation.request;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;

public class FxRateOperationRequest extends RequestContext {
    private BigDecimal txnAmt;
    private String srcCurrency;
    private String destCurrency;
    private String txnType;
    private String actNo;
    private String branchCode;

    public String getSrcCurrency() {
	return srcCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
	this.srcCurrency = srcCurrency;
    }

    public String getDestCurrency() {
	return destCurrency;
    }

    public void setDestCurrency(String destCurrency) {
	this.destCurrency = destCurrency;
    }

    public BigDecimal getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
	this.txnAmt = txnAmt;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

}
