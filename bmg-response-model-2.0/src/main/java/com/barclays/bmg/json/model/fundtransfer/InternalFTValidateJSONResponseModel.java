package com.barclays.bmg.json.model.fundtransfer;

import com.barclays.bmg.json.model.AmountJSONModel;

public class InternalFTValidateJSONResponseModel extends OwnFundTransferValidateJSONResponseModel{

	/**
	 *
	 */
	private static final long serialVersionUID = -3919472945844163990L;
	private String payId;
	private String payName;
	private String beneName;
	private String beneBrnCde;

	// Fields for PayBills MakeBillPayment request CPB - 29/05/2017
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;
	private AmountJSONModel txnChargeAmt;

	// Xelerate offline error codes
	private String xelerateOfflineError;
	private String xelerateOfflineDesc;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getBeneBrnCde() {
		return beneBrnCde;
	}

	public void setBeneBrnCde(String beneBrnCde) {
		this.beneBrnCde = beneBrnCde;
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

	public AmountJSONModel getTxnChargeAmt() {
		return txnChargeAmt;
	}

	public void setTxnChargeAmt(AmountJSONModel txnChargeAmt) {
		this.txnChargeAmt = txnChargeAmt;
	}

	public String getXelerateOfflineError() {
		return xelerateOfflineError;
	}

	public void setXelerateOfflineError(String xelerateOfflineError) {
		this.xelerateOfflineError = xelerateOfflineError;
	}

	public String getXelerateOfflineDesc() {
		return xelerateOfflineDesc;
	}

	public void setXelerateOfflineDesc(String xelerateOfflineDesc) {
		this.xelerateOfflineDesc = xelerateOfflineDesc;
	}

}
