package com.barclays.bmg.json.model.fundtransfer;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class OwnFundTransferValidateJSONResponseModel extends BMBPayloadData {

	/**
	 *
	 */
	private static final long serialVersionUID = 1595418061290819157L;
	private String frActNo = "";
	private String toActNo = "";
	private AmountJSONModel txnAmt;
	private String txnDt = "";
	private String txnNot = "";
	private String txnRefNo = "";

	private String fxRt = "";
	private AmountJSONModel eqAmt;
	private String totFee = "";
	private String frMskActNo = "";
	private String toMskActNo = "";
	private String curr = "";
	private String toActCurr = "";
	private String frActCurr = "";
	private AmountJSONModel txnLmt;
	private String toActDesc;
	private String frmActDesc;
	private String mkdCrdNo;

	// FundTransfer ToOwn - Uganda
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


	public String getFrActNo() {
		return frActNo;
	}

	public void setFrActNo(String frActNo) {
		this.frActNo = frActNo;
	}

	public String getToActNo() {
		return toActNo;
	}

	public void setToActNo(String toActNo) {
		this.toActNo = toActNo;
	}

	public String getTxnDt() {
		return txnDt;
	}

	public void setTxnDt(String txnDt) {
		this.txnDt = txnDt;
	}

	public String getTxnNot() {
		return txnNot;
	}

	public void setTxnNot(String txnNot) {
		this.txnNot = txnNot;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public String getFxRt() {
		return fxRt;
	}

	public void setFxRt(String fxRt) {
		this.fxRt = fxRt;
	}

	public String getTotFee() {
		return totFee;
	}

	public void setTotFee(String totFee) {
		this.totFee = totFee;
	}

	public String getFrMskActNo() {
		return frMskActNo;
	}

	public void setFrMskActNo(String frMskActNo) {
		this.frMskActNo = frMskActNo;
	}

	public String getToMskActNo() {
		return toMskActNo;
	}

	public void setToMskActNo(String toMskActNo) {
		this.toMskActNo = toMskActNo;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getToActCurr() {
		return toActCurr;
	}

	public void setToActCurr(String toActCurr) {
		this.toActCurr = toActCurr;
	}

	public String getFrActCurr() {
		return frActCurr;
	}

	public void setFrActCurr(String frActCurr) {
		this.frActCurr = frActCurr;
	}

	public AmountJSONModel getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(AmountJSONModel txnAmt) {
		this.txnAmt = txnAmt;
	}

	public AmountJSONModel getEqAmt() {
		return eqAmt;
	}

	public void setEqAmt(AmountJSONModel eqAmt) {
		this.eqAmt = eqAmt;
	}

	public String getToActDesc() {
		return toActDesc;
	}

	public void setToActDesc(String toActDesc) {
		this.toActDesc = toActDesc;
	}

	public String getFrmActDesc() {
		return frmActDesc;
	}

	public void setFrmActDesc(String frmActDesc) {
		this.frmActDesc = frmActDesc;
	}

	public AmountJSONModel getTxnLmt() {
		return txnLmt;
	}

	public void setTxnLmt(AmountJSONModel txnLmt) {
		this.txnLmt = txnLmt;
	}

	public String getMkdCrdNo() {
		return mkdCrdNo;
	}

	public void setMkdCrdNo(String mkdCrdNo) {
		this.mkdCrdNo = mkdCrdNo;
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
