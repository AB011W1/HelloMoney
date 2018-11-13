package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;

public class ExternalFTFormSubmissionOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -1602198487781126260L;

    private FxRateDTO fxRateDTO;
    private boolean authReq;
    private String authType;

    private BeneficiaryDTO beneficiaryDTO;

    private CustomerAccountDTO sourceAcct;
    private String txnNot;
    private String rem1;
    private String rem2;
    private String rem3;
    private String chargeKey;
    private String charDtls;
    private String payRson;
    private String payDtls;
    private Amount txnAmt;
    private String txnRefNo;

    private String payRsonValue;
    private String payDtlsValue;

    public FxRateDTO getFxRateDTO() {
	return fxRateDTO;
    }

    public void setFxRateDTO(FxRateDTO fxRateDTO) {
	this.fxRateDTO = fxRateDTO;
    }

    public boolean isAuthReq() {
	return authReq;
    }

    public void setAuthReq(boolean authReq) {
	this.authReq = authReq;
    }

    public String getAuthType() {
	return authType;
    }

    public void setAuthType(String authType) {
	this.authType = authType;
    }

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
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

    public String getPayRson() {
	return payRson;
    }

    public void setPayRson(String payRson) {
	this.payRson = payRson;
    }

    public String getPayDtls() {
	return payDtls;
    }

    public void setPayDtls(String payDtls) {
	this.payDtls = payDtls;
    }

    public String getCharDtls() {
	return charDtls;
    }

    public void setCharDtls(String charDtls) {
	this.charDtls = charDtls;
    }

    public Amount getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(Amount txnAmt) {
	this.txnAmt = txnAmt;
    }

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

    public String getChargeKey() {
	return chargeKey;
    }

    public void setChargeKey(String chargeKey) {
	this.chargeKey = chargeKey;
    }

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public String getPayRsonValue() {
	return payRsonValue;
    }

    public void setPayRsonValue(String payRsonValue) {
	this.payRsonValue = payRsonValue;
    }

    public String getPayDtlsValue() {
	return payDtlsValue;
    }

    public void setPayDtlsValue(String payDtlsValue) {
	this.payDtlsValue = payDtlsValue;
    }

}
