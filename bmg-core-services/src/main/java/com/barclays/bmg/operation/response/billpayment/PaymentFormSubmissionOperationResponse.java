package com.barclays.bmg.operation.response.billpayment;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;

public class PaymentFormSubmissionOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 4253956797506790260L;

    private CustomerAccountDTO fromAccount;
    private Amount tranFee;
    private BigDecimal txnAmt;
    private List<Charge> charges;
    private String txnNote;
    private BigDecimal txnLimit;
    private String txnRefNo;
    private BeneficiaryDTO beneficiaryDTO;
    private CustomerAccountDTO destCreditCardAcct;
    private FxRateDTO fxRate;
    private String billPayTxnType;
    private String paySer;

    private boolean authRequired = false;
    private String authType;

    public Amount getTranFee() {
	return tranFee;
    }

    public void setTranFee(Amount tranFee) {
	this.tranFee = tranFee;
    }

    public CustomerAccountDTO getFromAccount() {
	return fromAccount;
    }

    public void setFromAccount(CustomerAccountDTO fromAccount) {
	this.fromAccount = fromAccount;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public BigDecimal getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
	this.txnAmt = txnAmt;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
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

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public CustomerAccountDTO getDestCreditCardAcct() {
	return destCreditCardAcct;
    }

    public void setDestCreditCardAcct(CustomerAccountDTO destCreditCardAcct) {
	this.destCreditCardAcct = destCreditCardAcct;
    }

    public FxRateDTO getFxRate() {
	return fxRate;
    }

    public void setFxRate(FxRateDTO fxRate) {
	this.fxRate = fxRate;
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

    public String getAuthType() {
	return authType;
    }

    public void setAuthType(String authType) {
	this.authType = authType;
    }

    public String getPaySer() {
	return paySer;
    }

    public void setPaySer(String paySer) {
	this.paySer = paySer;
    }

}
