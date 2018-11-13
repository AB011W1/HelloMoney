package com.barclays.bmg.operation.request.fundtransfer.external;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;

public class InternationalFundTransferOperationRequest extends RequestContext {

    private boolean authRequired;
    private String authType;

    private BeneficiaryDTO beneficiaryDTO;
    private CustomerAccountDTO frmAcct;
    private Amount amt;
    private String chargeDesc;
    private String txnType;
    private String rem1;
    private String rem2;
    private String rem3;
    private BigDecimal txnAmtInLCY;
    private FxRateDTO fxRateDTO;

    private String payRsonKey;
    private String payRsonValue;
    private String payDtlsKey;
    private String payDtlsValue;
    private String txnNot;

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

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public CustomerAccountDTO getFrmAcct() {
	return frmAcct;
    }

    public void setFrmAcct(CustomerAccountDTO frmAcct) {
	this.frmAcct = frmAcct;
    }

    public Amount getAmt() {
	return amt;
    }

    public void setAmt(Amount amt) {
	this.amt = amt;
    }

    public String getChargeDesc() {
	return chargeDesc;
    }

    public void setChargeDesc(String chargeDesc) {
	this.chargeDesc = chargeDesc;
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
