package com.barclays.bmg.service.request;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class FundTransferExecuteServiceRequest extends RequestContext {

    private String txnAmount;
    private String txnNote;
    private CustomerAccountDTO frmAcct;
    private CustomerAccountDTO toAcct;
    private BigDecimal fxRate;
    private BigDecimal eqAmt;
    private String txnType;
    private String destBankCode;
    private String beneName;

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

    public String getDestBankCode() {
	return destBankCode;
    }

    public void setDestBankCode(String destBankCode) {
	this.destBankCode = destBankCode;
    }

    public String getBeneName() {
	return beneName;
    }

    public void setBeneName(String beneName) {
	this.beneName = beneName;
    }

}
