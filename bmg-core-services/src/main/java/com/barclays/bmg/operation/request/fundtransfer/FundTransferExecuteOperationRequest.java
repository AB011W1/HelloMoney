package com.barclays.bmg.operation.request.fundtransfer;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class FundTransferExecuteOperationRequest extends RequestContext {

    private String txnAmount;
    private String txnNote;
    private CustomerAccountDTO frmAcct;
    private CustomerAccountDTO toAcct;
    private BigDecimal fxRate;
    private BigDecimal eqAmt;
    private String txnType;
    private BigDecimal txnLimit;
    private String beneName;

    private boolean authRequired;
    private String authType;

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

    public CustomerAccountDTO getFrmAcct() {
	return frmAcct;
    }

    public void setFrmAcct(CustomerAccountDTO frmAcct) {
	this.frmAcct = frmAcct;
    }

    public CustomerAccountDTO getToAcct() {
	return toAcct;
    }

    public void setToAcct(CustomerAccountDTO toAcct) {
	this.toAcct = toAcct;
    }

    public BigDecimal getFxRate() {
	return fxRate;
    }

    public void setFxRate(BigDecimal fxRate) {
	this.fxRate = fxRate;
    }

    public BigDecimal getEqAmt() {
	return eqAmt;
    }

    public void setEqAmt(BigDecimal eqAmt) {
	this.eqAmt = eqAmt;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

    public boolean isAuthRequired() {
	return authRequired;
    }

    public void setAuthRequired(boolean authRequired) {
	this.authRequired = authRequired;
    }

    public String getAuthType() {
	return authType;
    }

    public void setAuthType(String authType) {
	this.authType = authType;
    }

    public BigDecimal getTxnLimit() {
	return txnLimit;
    }

    public void setTxnLimit(BigDecimal txnLimit) {
	this.txnLimit = txnLimit;
    }

    public String getBeneName() {
	return beneName;
    }

    public void setBeneName(String beneName) {
	this.beneName = beneName;
    }

}
