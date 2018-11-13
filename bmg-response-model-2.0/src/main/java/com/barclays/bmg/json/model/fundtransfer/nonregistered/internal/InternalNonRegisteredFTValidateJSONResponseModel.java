package com.barclays.bmg.json.model.fundtransfer.nonregistered.internal;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

public class InternalNonRegisteredFTValidateJSONResponseModel extends BMBPayloadData {

	private static final long serialVersionUID = -3965274920166340031L;
	private CasaAccountJSONModel frmAct;
	private CreditCardAccountJSONModel creditcard;
	private String beneName;
	private String beneBrnCde;
	private String beneAccountNumber;

	private AmountJSONModel txnAmt;
	private String txnDt = "";
	private String txnNot = "";
	private String txnRefNo = "";

	private String fxRt = "";
	private AmountJSONModel eqAmt;

	// Fields for PayBills MakeDomesticFundTransfer request CPB - 31/05/2017
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;
	private AmountJSONModel txnChargeAmt;

	public AmountJSONModel getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(AmountJSONModel txnAmt) {
		this.txnAmt = txnAmt;
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

	public AmountJSONModel getEqAmt() {
		return eqAmt;
	}

	public void setEqAmt(AmountJSONModel eqAmt) {
		this.eqAmt = eqAmt;
	}

	public String getBeneAccountNumber() {
		return beneAccountNumber;
	}

	public void setBeneAccountNumber(String beneAccountNumber) {
		this.beneAccountNumber = beneAccountNumber;
	}

	public CasaAccountJSONModel getFrmAct() {
		return frmAct;
	}

	public void setFrmAct(CasaAccountJSONModel frmAct) {
		this.frmAct = frmAct;
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

	public CreditCardAccountJSONModel getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(CreditCardAccountJSONModel creditcard) {
		this.creditcard = creditcard;
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

}
