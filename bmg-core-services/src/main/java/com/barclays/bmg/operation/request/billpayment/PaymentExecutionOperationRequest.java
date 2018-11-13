package com.barclays.bmg.operation.request.billpayment;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class PaymentExecutionOperationRequest extends RequestContext {

    private CustomerAccountDTO fromAccount;
    private BeneficiaryDTO beneficiaryDTO;
    private BigDecimal txnAmt;
    private String txnDesc;
    private List<Charge> charges;
    private Amount tranFee;
    private BigDecimal txnLimit;
    private String billPayTxnTyp;
    private CustomerAccountDTO creditCardDestAcct;
    private BigDecimal outBalAmt;

    private boolean authRequired;
    private String authType;

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

    public String getTxnDesc() {
	return txnDesc;
    }

    public void setTxnDesc(String txnDesc) {
	this.txnDesc = txnDesc;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

    public Amount getTranFee() {
	return tranFee;
    }

    public void setTranFee(Amount tranFee) {
	this.tranFee = tranFee;
    }

    public BigDecimal getTxnLimit() {
	return txnLimit;
    }

    public void setTxnLimit(BigDecimal txnLimit) {
	this.txnLimit = txnLimit;
    }

    public String getBillPayTxnTyp() {
	return billPayTxnTyp;
    }

    public void setBillPayTxnTyp(String billPayTxnTyp) {
	this.billPayTxnTyp = billPayTxnTyp;
    }

    public CustomerAccountDTO getCreditCardDestAcct() {
	return creditCardDestAcct;
    }

    public void setCreditCardDestAcct(CustomerAccountDTO creditCardDestAcct) {
	this.creditCardDestAcct = creditCardDestAcct;
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

    public BigDecimal getOutBalAmt() {
	return outBalAmt;
    }

    public void setOutBalAmt(BigDecimal outBalAmt) {
	this.outBalAmt = outBalAmt;
    }

}
