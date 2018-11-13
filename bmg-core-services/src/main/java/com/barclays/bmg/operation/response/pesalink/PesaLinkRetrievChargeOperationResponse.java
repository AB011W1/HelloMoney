package com.barclays.bmg.operation.response.pesalink;

import java.math.BigDecimal;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;

public class PesaLinkRetrievChargeOperationResponse extends ResponseContext {

	private static final long serialVersionUID = 6017509621703548165L;
	private Amount transactionFee;
	private Amount txnAmt;
	private String mobileNo = "";

	private Double chargeAmount;
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;

	private boolean scndLvlauthReq;
    private String scndLvlAuthTyp;
    private BigDecimal txnAmtInLCY;

	public Amount getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(Amount txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Amount getTransactionFee() {
		return transactionFee;
	}
	public void setTransactionFee(Amount transactionFee) {
		this.transactionFee = transactionFee;
	}
	public Double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getFeeGLAccount() {
		return feeGLAccount;
	}
	public void setFeeGLAccount(String feeGLAccount) {
		this.feeGLAccount = feeGLAccount;
	}
	public String getChargeNarration() {
		return chargeNarration;
	}
	public void setChargeNarration(String chargeNarration) {
		this.chargeNarration = chargeNarration;
	}
	public Double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxGLAccount() {
		return taxGLAccount;
	}
	public void setTaxGLAccount(String taxGLAccount) {
		this.taxGLAccount = taxGLAccount;
	}
	public String getExciseDutyNarration() {
		return ExciseDutyNarration;
	}
	public void setExciseDutyNarration(String exciseDutyNarration) {
		ExciseDutyNarration = exciseDutyNarration;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isScndLvlauthReq() {
		return scndLvlauthReq;
	}
	public void setScndLvlauthReq(boolean scndLvlauthReq) {
		this.scndLvlauthReq = scndLvlauthReq;
	}
	public String getScndLvlAuthTyp() {
		return scndLvlAuthTyp;
	}
	public void setScndLvlAuthTyp(String scndLvlAuthTyp) {
		this.scndLvlAuthTyp = scndLvlAuthTyp;
	}
	public BigDecimal getTxnAmtInLCY() {
		return txnAmtInLCY;
	}
	public void setTxnAmtInLCY(BigDecimal txnAmtInLCY) {
		this.txnAmtInLCY = txnAmtInLCY;
	}




}
