package com.barclays.bmg.service.request;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class PayBillServiceRequest extends RequestContext {

    private BigDecimal billAmount;
    private String currency;
    private BeneficiaryDTO beneficiaryDTO;
    private Amount transactionFee;
    private List<Charge> charges;
    private CustomerAccountDTO fromAccount;
    private String txnNote;
    private String billPayTxnTyp;
    private BigDecimal outBalAmt;
    private BigDecimal amtInLCY;
    private String externalBillerId;
    private String ordCode;
    private String billRefNo2;
    private String action;

    //CPB MakeBillPayment fields DTO - 10/05
    private Charge chargeDTO;

    public String getBillRefNo2() {
	return billRefNo2;
    }

    public void setBillRefNo2(String billRefNo2) {
	this.billRefNo2 = billRefNo2;
    }

    public BigDecimal getBillAmount() {
	return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
	this.billAmount = billAmount;
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public Amount getTransactionFee() {
	return transactionFee;
    }

    public void setTransactionFee(Amount transactionFee) {
	this.transactionFee = transactionFee;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

    public CustomerAccountDTO getFromAccount() {
	return fromAccount;
    }

    public void setFromAccount(CustomerAccountDTO fromAccount) {
	this.fromAccount = fromAccount;
    }

    public String getTxnNote() {
	return txnNote;
    }

    public void setTxnNote(String txnNote) {
	this.txnNote = txnNote;
    }

    public String getBillPayTxnTyp() {
	return billPayTxnTyp;
    }

    public void setBillPayTxnTyp(String billPayTxnTyp) {
	this.billPayTxnTyp = billPayTxnTyp;
    }

    public BigDecimal getOutBalAmt() {
	return outBalAmt;
    }

    public void setOutBalAmt(BigDecimal outBalAmt) {
	this.outBalAmt = outBalAmt;
    }

    public BigDecimal getAmtInLCY() {
	return amtInLCY;
    }

    public void setAmtInLCY(BigDecimal amtInLCY) {
	this.amtInLCY = amtInLCY;
    }

    public String getExternalBillerId() {
	return externalBillerId;
    }

    public void setExternalBillerId(String externalBillerId) {
	this.externalBillerId = externalBillerId;
    }

    public String getOrdCode() {
	return ordCode;
    }

    public void setOrdCode(String ordCode) {
	this.ordCode = ordCode;
    }

	public Charge getChargeDTO() {
		return chargeDTO;
	}

	public void setChargeDTO(Charge chargeDTO) {
		this.chargeDTO = chargeDTO;
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


}
