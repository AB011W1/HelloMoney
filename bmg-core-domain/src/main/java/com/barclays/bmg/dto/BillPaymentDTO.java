package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BillPaymentDTO implements Serializable {

    private static final long serialVersionUID = -106339568000883528L;
    private BeneficiaryDTO beneficiaryDTO;
    private CustomerAccountDTO sourceAccount;
    private List<Charge> charges;
    private Amount tranFee;
    private BigDecimal txnAmt;
    private String txnNote;
    private BigDecimal txnLimit;
    private BigDecimal minBillAmt;
    private BigDecimal maxBillAmt;
    private BigDecimal outBalAmt;
    private String billPayTxnType;
    private String paySer;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

    public CustomerAccountDTO getSourceAccount() {
	return sourceAccount;
    }

    public void setSourceAccount(CustomerAccountDTO sourceAccount) {
	this.sourceAccount = sourceAccount;
    }

    public Amount getTranFee() {
	return tranFee;
    }

    public void setTranFee(Amount tranFee) {
	this.tranFee = tranFee;
    }

    public BigDecimal getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
	this.txnAmt = txnAmt;
    }

    public String getTxnNote() {
	return txnNote;
    }

    public void setTxnNote(String txnNote) {
	this.txnNote = txnNote;
    }

    public BigDecimal getTxnLimit() {
	return txnLimit;
    }

    public void setTxnLimit(BigDecimal txnLimit) {
	this.txnLimit = txnLimit;
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

    public String getBillPayTxnType() {
	return billPayTxnType;
    }

    public void setBillPayTxnType(String billPayTxnType) {
	this.billPayTxnType = billPayTxnType;
    }

    public String getPaySer() {
	return paySer;
    }

    public void setPaySer(String paySer) {
	this.paySer = paySer;
    }

    public BigDecimal getOutBalAmt() {
	return outBalAmt;
    }

    public void setOutBalAmt(BigDecimal outBalAmt) {
	this.outBalAmt = outBalAmt;
    }

}
