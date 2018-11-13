package com.barclays.bmg.operation.response.fundtransfer;

import java.math.BigDecimal;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;

public class InternalFundTransferValidateOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 2120907540486396233L;
    private String txnAmount;
    private String txnFee;
    private String curreny;
    private String txnNote;
    private String txnDate;
    private String txnRef;
    private FxRateDTO fxRateDTO;
    private BigDecimal aValidDailyLimit;
    private CustomerAccountDTO frmAcct;
    private CustomerAccountDTO toAcct;
    private String payId;
    private String payNickName;
    private String beneName;

    private boolean authRequired;
    private String authType;

    public String getTxnAmount() {
	return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
	this.txnAmount = txnAmount;
    }

    public String getTxnFee() {
	return txnFee;
    }

    public void setTxnFee(String txnFee) {
	this.txnFee = txnFee;
    }

    public String getCurreny() {
	return curreny;
    }

    public void setCurreny(String curreny) {
	this.curreny = curreny;
    }

    public String getTxnNote() {
	return txnNote;
    }

    public void setTxnNote(String txnNote) {
	this.txnNote = txnNote;
    }

    public String getTxnDate() {
	return txnDate;
    }

    public void setTxnDate(String txnDate) {
	this.txnDate = txnDate;
    }

    public String getTxnRef() {
	return txnRef;
    }

    public void setTxnRef(String txnRef) {
	this.txnRef = txnRef;
    }

    public FxRateDTO getFxRateDTO() {
	return fxRateDTO;
    }

    public void setFxRateDTO(FxRateDTO fxRateDTO) {
	this.fxRateDTO = fxRateDTO;
    }

    public BigDecimal getAValidDailyLimit() {
	return aValidDailyLimit;
    }

    public void setAValidDailyLimit(BigDecimal validDailyLimit) {
	aValidDailyLimit = validDailyLimit;
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

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
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

    public String getPayNickName() {
	return payNickName;
    }

    public void setPayNickName(String payNickName) {
	this.payNickName = payNickName;
    }

    public String getBeneName() {
	return beneName;
    }

    public void setBeneName(String beneName) {
	this.beneName = beneName;
    }
}