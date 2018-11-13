package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;

public class InternationalFundTransferServiceRequest extends RequestContext {

    private CustomerAccountDTO sourceAcct;
    private BeneficiaryDTO beneficiaryDTO;
    private Amount txnAmt;
    private String remPart1;
    private String remPart2;
    private String remPart3;
    private String remPart4;
    private String chargeDescCode;
    private String txnType;
    private FxRateDTO fxRateDTO;
    private String payRsonKey;
    private String payRsonValue;
    private String payDtlsKey;
    private String payDtlsValue;
    private String txnNot;

    public CustomerAccountDTO getSourceAcct() {
	return sourceAcct;
    }

    public void setSourceAcct(CustomerAccountDTO sourceAcct) {
	this.sourceAcct = sourceAcct;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public Amount getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(Amount txnAmt) {
	this.txnAmt = txnAmt;
    }

    public String getRemPart1() {
	return remPart1;
    }

    public void setRemPart1(String remPart1) {
	this.remPart1 = remPart1;
    }

    public String getRemPart2() {
	return remPart2;
    }

    public void setRemPart2(String remPart2) {
	this.remPart2 = remPart2;
    }

    public String getRemPart3() {
	return remPart3;
    }

    public void setRemPart3(String remPart3) {
	this.remPart3 = remPart3;
    }

    public String getRemPart4() {
	return remPart4;
    }

    public void setRemPart4(String remPart4) {
	this.remPart4 = remPart4;
    }

    public String getChargeDescCode() {
	return chargeDescCode;
    }

    public void setChargeDescCode(String chargeDescCode) {
	this.chargeDescCode = chargeDescCode;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
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

    public FxRateDTO getFxRateDTO() {
	return fxRateDTO;
    }

    public void setFxRateDTO(FxRateDTO fxRateDTO) {
	this.fxRateDTO = fxRateDTO;
    }

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

}
