package com.barclays.bmg.dto;

import java.math.BigDecimal;

public class FundTransferDTO extends BaseDomainDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = -4001538090210730710L;
    private String txnAmount;
    private String txNote;
    private CustomerAccountDTO frmAcct;
    private CustomerAccountDTO toAcct;
    private BigDecimal fxRate;
    private BigDecimal eqAmt;
    private String txnType;
    private String activityId;
    private BigDecimal txnLimit;
    private String beneName;

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

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
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
