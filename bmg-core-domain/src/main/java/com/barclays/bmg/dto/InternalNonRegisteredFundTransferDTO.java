package com.barclays.bmg.dto;

import java.math.BigDecimal;
import java.util.Map;

public class InternalNonRegisteredFundTransferDTO extends BaseDomainDTO {

    private static final long serialVersionUID = 788298914908539853L;

    private BeneficiaryDTO beneficiaryDTO;
    private CustomerAccountDTO sourceAcct;
    private Amount txAmount;
    private String txnNot;
    private Map<String, String> chargeDesc;
    private String rem1;
    private String rem2;
    private String rem3;
    private FxRateDTO fxRateDTO;
    private String txnType;
    private BigDecimal txnAmtInLCY;
    private String selChDesc;

    private String payRsonKey;
    private String payRsonValue;
    private String payDtlsKey;
    private String payDtlsValue;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public CustomerAccountDTO getSourceAcct() {
	return sourceAcct;
    }

    public void setSourceAcct(CustomerAccountDTO sourceAcct) {
	this.sourceAcct = sourceAcct;
    }

    public Amount getTxAmount() {
	return txAmount;
    }

    public void setTxAmount(Amount txAmount) {
	this.txAmount = txAmount;
    }

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

    public FxRateDTO getFxRateDTO() {
	return fxRateDTO;
    }

    public void setFxRateDTO(FxRateDTO fxRateDTO) {
	this.fxRateDTO = fxRateDTO;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

    public String getRem1() {
	return rem1;
    }

    public void setRem1(String rem1) {
	this.rem1 = rem1;
    }

    public String getRem2() {
	return rem2;
    }

    public void setRem2(String rem2) {
	this.rem2 = rem2;
    }

    public String getRem3() {
	return rem3;
    }

    public void setRem3(String rem3) {
	this.rem3 = rem3;
    }

    public static long getSerialVersionUID() {
	return serialVersionUID;
    }

    public Map<String, String> getChargeDesc() {
	return chargeDesc;
    }

    public void setChargeDesc(Map<String, String> chargeDesc) {
	this.chargeDesc = chargeDesc;
    }

    public String getPayRsonKey() {
	return payRsonKey;
    }

    public void setPayRsonKey(String payRsonKey) {
	this.payRsonKey = payRsonKey;
    }

    public String getPayRsonValue() {
	return payRsonValue;
    }

    public void setPayRsonValue(String payRsonValue) {
	this.payRsonValue = payRsonValue;
    }

    public String getPayDtlsKey() {
	return payDtlsKey;
    }

    public void setPayDtlsKey(String payDtlsKey) {
	this.payDtlsKey = payDtlsKey;
    }

    public String getPayDtlsValue() {
	return payDtlsValue;
    }

    public void setPayDtlsValue(String payDtlsValue) {
	this.payDtlsValue = payDtlsValue;
    }

    public BigDecimal getTxnAmtInLCY() {
	return txnAmtInLCY;
    }

    public void setTxnAmtInLCY(BigDecimal txnAmtInLCY) {
	this.txnAmtInLCY = txnAmtInLCY;
    }

    public String getSelChDesc() {
	return selChDesc;
    }

    public void setSelChDesc(String selChDesc) {
	this.selChDesc = selChDesc;
    }

}
